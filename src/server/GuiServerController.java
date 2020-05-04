package server;

import java.io.IOException;

import client.Nickname;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

public class GuiServerController
{
	private ServerControl serverControl;
	
	public GuiServerController()
	{
		serverControl = new ServerControl(this);
	}
	
	@FXML
	private ListView <Nickname> list_angemeldeteUser;
	
	@FXML
	public void starteServer()
	{
		serverControl.starteServer();;
	}
	
	@FXML
	public void stoppeServer()
	{
		serverControl.beenden();
	}
	
	public void guiAnzeigen (String guiName) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(guiName + ".fxml"));		
		Parent root = (Parent) loader.load();
		MainServer.getStage().setScene(new Scene(root));
		MainServer.getStage().show();
	}

	//GETTER/SETTER
	
	public ListView <Nickname> getList_angemeldeteUser()
	{
		return list_angemeldeteUser;
	}

	public void setList_angemeldeteUser(ListView <Nickname> list_angemeldeteUser)
	{
		this.list_angemeldeteUser = list_angemeldeteUser;
	}
	
	
}
