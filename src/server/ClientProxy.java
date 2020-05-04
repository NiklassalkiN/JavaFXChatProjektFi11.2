package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.AktiveNutzerUpdate;
import client.AnmeldeObjekt;
import client.Nachricht;
import client.Nickname;
import client.PrivateNachricht;
import client.Registrierung;
import client.Transport;


public class ClientProxy implements Runnable
{

	private ServerControl aServer;
	private Socket aSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Nickname nick;
	private AnmeldeObjekt ao;
	protected Thread t;
	static int benutzer = 0;
	
	
	public ClientProxy(ServerControl control,Socket s)
	{
		this.aServer = control;
		this.aSocket = s;
		this.nick = new Nickname("", "");
		benutzer++;
		
		try
		{
			this.in = new ObjectInputStream(aSocket.getInputStream());
			this.out = new ObjectOutputStream(aSocket.getOutputStream());
			
			t = new Thread(this);
			t.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void run()
	{
		System.out.println("Client wurde angemeldet!");
		while(!t.isInterrupted())
		{
			try
			{
				Thread.sleep(100);
				empfangeNachricht();
			}
			catch(InterruptedException e)
			{
				t.interrupt();
			}
		}
	}
	
	private void empfangeNachricht()
	{
		try
		{
			Object o = (Object) in.readObject();
			Transport t = (Transport) o;
			
			if(t != null)
			{
				switch(t.getIdentifier())
				{
					case "Nachricht":
						Nachricht n = (Nachricht) o;
						aServer.broadcast(n);
						break;
					case "Registrierung": 
						Registrierung reg = (Registrierung) o;
						aServer.registrierungPruefen(reg);
						break;
					case "AnmeldeObjekt":
						ao = (AnmeldeObjekt) o;
						aServer.anmelden(ao);
						break;
					case "privateNachricht":
						PrivateNachricht pn = (PrivateNachricht) o;
						aServer.privateNachrichtSenden(pn);
						break;
					default: break;
				}
			}
		}
		catch (ClassNotFoundException | IOException e)
		{
			AktiveNutzerUpdate anu = new AktiveNutzerUpdate(nick, false); 

			if(nick != null)
			{
				aServer.aktivenBenutzerEntfernen(nick.getEmail());
			}
			else
			{
				aServer.aktivenBenutzerEntfernen(ao.getEmail());
			}
			aServer.notifyObserver(anu);
			this.t.interrupt();
		}
	}
	
	public void sendeObject(Object o)
	{
		try
		{
			out.writeObject(o);
			out.flush();
		}
		catch (IOException e)
		{
			System.out.println("Empfänger nicht erreichbar!");
		}
	}


	public Nickname getNick()
	{
		return nick;
	}


	public void setNick(Nickname nick)
	{
		this.nick = nick;
	}
	
	
}
