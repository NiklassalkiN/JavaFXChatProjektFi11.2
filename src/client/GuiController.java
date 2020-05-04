package client;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GuiController
{
	// Chat-GUI
	@FXML
	private TextField textFieldNachricht;
	@FXML
	private Button btnSenden;
	@FXML
	private ListView <String> listView_Nachrichten;
	@FXML
	private ListView <Nickname> listView_angemeldeteNutzer;

	// Home-GUI
	@FXML
	private ListView listViewChats;
	@FXML
	private ListView listViewKontakte;

	// Anmelden-GUI
	@FXML
	private TextField textField_emailadresseAnmelden;
	@FXML
	private TextField textField_passwortAnmelden;

	// Registrieren-GUI
	@FXML
	private TextField textField_nicknameRegistrieren;
	@FXML
	private TextField textField_emailadresseRegistrieren;
	@FXML
	private TextField textField_passwortRegistrieren;
	@FXML
	private TextField textField_passwortWiederholenRegistrieren;
	
	@FXML
	private FXMLLoader loader;
	
	private ClientControl control;
	
	public GuiController()
	{
		control = new ClientControl(this);
		control.clientStart();
	}

	//Methoden
	@FXML
	public void anmelden()
	{
		control.clientAnmelden();
	}
	
	@FXML
	public void registrieren() throws IOException
	{
		control.oeffneRegistrierung();
	}
	
	@FXML
	public void registrierungAbschicken()
	{
		control.registrieren();
	}
	
	@FXML
	public void sendeNachricht()
	{
		control.sendeObject(control.createNachricht(textFieldNachricht.getText()));
		textFieldNachricht.setText("");
	}
	
	@FXML
	public void clientPrivatOeffnen() throws IOException
	{
		Nickname empfaenger = new Nickname(((Nickname)listView_angemeldeteNutzer.getSelectionModel().getSelectedItem()).getEmail(), ((Nickname)listView_angemeldeteNutzer.getSelectionModel().getSelectedItem()).getName());
		control.clientPrivatOeffnen(empfaenger);
	}
	
	public void guiAnzeigen (String guiName) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(guiName + ".fxml"));
		loader.setController(this);
		Parent root = (Parent) loader.load();
		
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				Main.getStage().setScene(new Scene(root));
				Main.getStage().show();
			}

		});
	}
	
	public GuiControllerPrivat stageAnlegen(String guiName, Nickname empfaenger) throws IOException
	{
		GuiControllerPrivat gCP = new GuiControllerPrivat(empfaenger,this , control);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(guiName + ".fxml"));
		loader.setController(gCP);
		Parent root = (Parent) loader.load();
		
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				Stage neuesFenster = new Stage();
				gCP.setStage(neuesFenster);
				neuesFenster.setTitle(empfaenger.getName());
				neuesFenster.setScene(new Scene(root));
		        neuesFenster.show();
		        
		        neuesFenster.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>()
		        {
		            @Override
		            public void handle(WindowEvent t)
		            {
		            	control.getPrivateChatraeume().remove(gCP);
		            	neuesFenster.close();
		            	Thread.currentThread().interrupt();
		            }
		        });
			}

		});
		
		return gCP;
	}
	
	public void itemsZurListeHinzufuegen(ListView listView, Object object)
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				listView.getItems().add(object);
			}
		});
	}
	
	public void itemsVonListeEntfernen(ListView listView, Object object)
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				listView.getItems().remove(object);
			}
		});
	}
	
	//######### GETTER/SETTER ##########	
	//Chat
	public FXMLLoader getLoader()
	{
		return loader;
	}

	public TextField getTextFieldNachricht()
	{
		return textFieldNachricht;
	}

	public void setTextFieldNachricht(TextField textFieldNachricht)
	{
		this.textFieldNachricht = textFieldNachricht;
	}

	public Button getBtnSenden()
	{
		return btnSenden;
	}

	public void setBtnSenden(Button btnSenden)
	{
		this.btnSenden = btnSenden;
	}
	
	public ListView<String> getListView_Nachrichten()
	{
		return listView_Nachrichten;
	}

	public void setListView_Nachrichten(ListView<String> listView_Nachrichten)
	{
		this.listView_Nachrichten = listView_Nachrichten;
	}

	public ListView<Nickname> getListView_angemeldeteNutzer()
	{
		return listView_angemeldeteNutzer;
	}

	public void setListView_angemeldeteNutzer(ListView<Nickname> listView_angemeldeteNutzer)
	{
		this.listView_angemeldeteNutzer = listView_angemeldeteNutzer;
	}
	//Chat-Ende
	
	//Registrieren
	public TextField getTextField_nicknameRegistrieren()
	{
		return textField_nicknameRegistrieren;
	}

	public void setTextField_nicknameRegistrieren(TextField textField_nicknameRegistrieren)
	{
		this.textField_nicknameRegistrieren = textField_nicknameRegistrieren;
	}

	public TextField getTextField_emailadresseRegistrieren()
	{
		return textField_emailadresseRegistrieren;
	}

	public void setTextField_emailadresseRegistrieren(TextField textField_emailadresseRegistrieren)
	{
		this.textField_emailadresseRegistrieren = textField_emailadresseRegistrieren;
	}

	public TextField getTextField_passwortRegistrieren()
	{
		return textField_passwortRegistrieren;
	}

	public void setTextField_passwortRegistrieren(TextField textField_passwortRegistrieren)
	{
		this.textField_passwortRegistrieren = textField_passwortRegistrieren;
	}

	public TextField getTextField_passwortWiederholenRegistrieren()
	{
		return textField_passwortWiederholenRegistrieren;
	}

	public void setTextField_passwortWiederholenRegistrieren(TextField textField_passwortWiederholenRegistrieren)
	{
		this.textField_passwortWiederholenRegistrieren = textField_passwortWiederholenRegistrieren;
	}
	//Registrieren-Ende
	
	//Anmelden
	public TextField getTextField_emailadresseAnmelden()
	{
		return textField_emailadresseAnmelden;
	}

	public void setTextField_emailadresseAnmelden(TextField textField_emailadresseAnmelden)
	{
		this.textField_emailadresseAnmelden = textField_emailadresseAnmelden;
	}

	public TextField getTextField_passwortAnmelden()
	{
		return textField_passwortAnmelden;
	}

	public void setTextField_passwortAnmelden(TextField textField_passwortAnmelden)
	{
		this.textField_passwortAnmelden = textField_passwortAnmelden;
	}
	//Anmelden-Ende
	
	public void setLoader(FXMLLoader loader)
	{
		this.loader = loader;
	}
}
