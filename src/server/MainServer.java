package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainServer extends Application
{
	private GuiServerController guiServerController;
	private static Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		stage = primaryStage;
		
		stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		guiServerController = new GuiServerController();	
		guiServerController.guiAnzeigen("ServerGui");
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public static Stage getStage()
	{
		return stage;
	}

}
