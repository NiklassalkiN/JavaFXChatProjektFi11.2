package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import client.AktiveNutzer;
import client.AktiveNutzerUpdate;
import client.AnmeldeBestaetigung;
import client.AnmeldeObjekt;
import client.Nickname;
import client.PrivateNachricht;
import client.Registrierung;
import javafx.application.Platform;
import javafx.scene.control.ListView;

public class ServerControl extends Thread implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ArrayList<ClientProxy> clientProxyListe = new ArrayList<>();
	private ArrayList<Registrierung> registrierungsliste = new ArrayList<>();
	private ClientProxy clientproxy;
	private int port = 8088;
	private ServerSocket socket;
	
	private ArrayList<Nickname> angemeldeteNutzer;
	
	private AktiveNutzer aktiveNutzer;
	
	private GuiServerController guiServerController;
	
	public ServerControl(GuiServerController guiServerController)
	{
		this.guiServerController = guiServerController;
		angemeldeteNutzer = new ArrayList<>();
		aktiveNutzer = new AktiveNutzer();
	}
	
	public void starteServer()
	{
		this.start();
	}
	
	public void beenden()
	{
		if(this != null)
		{
			this.interrupt();
		}
	}
	
	
	public void run()
	{
		System.out.println("Server läuft");
		try
		{
			socket = new ServerSocket(port);
			socket.setSoTimeout(100);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		while(!isInterrupted() && socket != null)
		{
			try
			{
				sleep(100);
				clientproxy = new ClientProxy(this,socket.accept());
				clientProxyListe.add(clientproxy);
			}
			catch(SocketTimeoutException e)
			{
				
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(InterruptedException e)
			{
				interrupt();
				try
				{
					clientProxyListe.forEach(proxys->{
						proxys.t.interrupt();
					});
					socket.close();
					System.out.println("Server wird beendet!");
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
		try
		{
			clientProxyListe.forEach(proxys->{
				proxys.t.interrupt();
			});
			socket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		socket = null;
	}
	
	public void registrierungPruefen(Registrierung register)
	{	
		boolean flag = false;
	
		//Deserialisierung
		try(FileInputStream fis = new FileInputStream("datei.ser");
				ObjectInputStream ois = new ObjectInputStream(fis))
		{
			registrierungsliste = (ArrayList<Registrierung>) ois.readObject();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		for(Registrierung reg : registrierungsliste)
		{
			if(reg.getEmail().equals(register.getEmail()))
			{
				flag = true;
				//TODO
				//Benutzer das auch wissen lassen
			}
		}
		
		if(flag == false)
		{
			//Benutzer registrieren
			//Serialisierung
			try(FileOutputStream fos = new FileOutputStream("datei.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fos))
			{
				registrierungsliste.add(register);
				oos.writeObject(registrierungsliste);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			anmelden(new AnmeldeObjekt(register.getEmail(), register.getPasswort()));
		}
	}
	
	public void anmelden(AnmeldeObjekt ao)
	{
		//Deserialisierung
		try(FileInputStream fis = new FileInputStream("datei.ser");
				ObjectInputStream ois = new ObjectInputStream(fis))
		{
			registrierungsliste = (ArrayList<Registrierung>) ois.readObject();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		for(Registrierung reg : registrierungsliste)
		{			
			if(reg.getEmail().equals(ao.getEmail()) && reg.getPasswort().equals(ao.getPasswort()))
			{
				Nickname nick = new Nickname(reg.getEmail(), reg.getName());
				AktiveNutzerUpdate anu = new AktiveNutzerUpdate(nick, true);
				
				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						guiServerController.getList_angemeldeteUser().getItems().add(nick);
					}
				});
				//Benutzer vorhanden, an Proxy senden
				clientproxy.sendeObject(new AnmeldeBestaetigung(true, nick));
				clientproxy.setNick(nick);
				notifyObserver(anu);
			}
		}
	}
	
	public void notifyObserver(AktiveNutzerUpdate anu)
	{	
		convertListViewToArrayList(guiServerController.getList_angemeldeteUser());
		aktiveNutzer.setBenutzer(angemeldeteNutzer);
		
		for(ClientProxy c : clientProxyListe)
		{
			if(c != null && c.getNick().getEmail().equalsIgnoreCase(anu.getNick().getEmail()))
			{
				c.sendeObject(aktiveNutzer);
			}
			else
			{
				c.sendeObject(anu);
			}
		}
	}
	
	public void broadcast(Object o) //bearbeiteNachricht wollen wir nicht nur für Nachrichten, sondern allgemein als broadcast verwenden
	{	
		if(o != null)
		{
			for(ClientProxy c : clientProxyListe)
			{
				c.sendeObject(o);
			}
		}
	}
	
	public void aktivenBenutzerEntfernen(String email)
	{
		ClientProxy temp = null;
		
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				for(int i = 0; i < guiServerController.getList_angemeldeteUser().getItems().size(); i++)
				{
					if(((Nickname)guiServerController.getList_angemeldeteUser().getItems().get(i)).getEmail().equalsIgnoreCase(email))
					{
						guiServerController.getList_angemeldeteUser().getItems().remove(i);
					}
				}
			}
		});

		for(ClientProxy c : clientProxyListe)
		{
			if(c.getNick().getEmail().equals(email))
			{
				temp = c;
			}
		}
		clientProxyListe.remove(temp);
	}
	
	public void privateNachrichtSenden(PrivateNachricht pn)
	{
		for(ClientProxy c : clientProxyListe)
		{
			if(c != null && c.getNick().getEmail().equalsIgnoreCase(pn.getEmpfaenger().getEmail()))
			{
				c.sendeObject(pn);
			}
		}
	}
	
	public void convertListViewToArrayList(ListView<Nickname> listView)
	{
		angemeldeteNutzer = new ArrayList<>();
		for(Nickname nick : listView.getItems())
		{
			angemeldeteNutzer.add(nick);
		}
	}
	
	public ArrayList<Nickname> getAngemeldeteNutzer()
	{
		return angemeldeteNutzer;
	}

	public void setAngemeldeteNutzer(ArrayList<Nickname> angemeldeteNutzer)
	{
		this.angemeldeteNutzer = angemeldeteNutzer;
	}

	public ArrayList<ClientProxy> getClientProxyListe()
	{
		return clientProxyListe;
	}

	public void setClientProxyListe(ArrayList<ClientProxy> clientProxyListe)
	{
		this.clientProxyListe = clientProxyListe;
	}	
}
