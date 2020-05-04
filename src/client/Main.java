package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application
{
	private GuiController guiController;
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
		
		guiController = new GuiController();
		guiController.guiAnzeigen("Anmelden");
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
