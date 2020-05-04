package client;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuiControllerPrivat
{
	// ClientPrivat-GUI
		@FXML
		private TextField textField_fluesterNachricht;
		@FXML
		private ListView list_fluesterNachricht;
		
		private Nickname empfaenger;
		private GuiController guiController;
		private ClientControl clientControl;
		private Stage stage;
		
		public GuiControllerPrivat(Nickname empfaenger, GuiController guiController, ClientControl clientControl) throws IOException
		{
			this.empfaenger = empfaenger;
			this.guiController = guiController;
			this.clientControl = clientControl;
		}
		
		@FXML
		public void privateNachrichtSenden()
		{
			clientControl.privateNachrichtSenden(empfaenger, this);
			textField_fluesterNachricht.setText("");
		}
		
		//ClientPrivat
		public TextField getTextField_fluesterNachricht()
		{
			return textField_fluesterNachricht;
		}

		public void setTextField_fluesterNachricht(TextField textField_fluesterNachricht)
		{
			this.textField_fluesterNachricht = textField_fluesterNachricht;
		}

		public ListView getList_fluesterNachricht()
		{
			return list_fluesterNachricht;
		}

		public void setList_fluesterNachricht(ListView list_fluesterNachricht)
		{
			this.list_fluesterNachricht = list_fluesterNachricht;
		}

		public Nickname getEmpfaenger()
		{
			return empfaenger;
		}

		public void setEmpfaenger(Nickname empfaenger)
		{
			this.empfaenger = empfaenger;
		}

		public Stage getStage()
		{
			return stage;
		}

		public void setStage(Stage stage)
		{
			this.stage = stage;
		}
		//ClientPrivat-Ende
}
