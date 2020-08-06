package io.github.losthikking.iconsofttest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	@FXML
	private Button createNewChatButton;

	@FXML
	private Button connectToChatButton;

	@FXML
	private TextField usernameTextField;

	@FXML
	private Config config = Config.getInstance();

	@FXML
	protected void connectToChat(ActionEvent event) throws IOException {
		Window owner = connectToChatButton.getScene().getWindow();
		if (usernameTextField.getText().isEmpty()) {
			AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!",
					"Please enter your username");
			return;
		}
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat_connection.fxml"));
		Parent root = fxmlLoader.load();
		Stage parent = (Stage)createNewChatButton.getScene().getWindow();
		parent.setScene(new Scene(root));
		parent.show();
	}

	@FXML
	protected void createChat(ActionEvent event) throws IOException {
		Window owner = connectToChatButton.getScene().getWindow();
		if (usernameTextField.getText().isEmpty()) {
			AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!",
					"Please enter your username");
			return;
		}
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat.fxml"));
		Parent root = fxmlLoader.load();
		ChatController chatController = fxmlLoader.getController();
		chatController.setChat(Chat.createChat());
		Stage parent = (Stage)createNewChatButton.getScene().getWindow();
		parent.setScene(new Scene(root));
		parent.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameTextField.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					config.setUsername(newValue);
				}
		);
	}
}
