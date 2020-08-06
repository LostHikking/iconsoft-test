package io.github.losthikking.iconsofttest.controlers;

import io.github.losthikking.iconsofttest.Chat;
import io.github.losthikking.iconsofttest.Config;
import io.github.losthikking.iconsofttest.enums.ConnectionType;
import io.github.losthikking.iconsofttest.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	private static final ObservableList<ConnectionType> enums = FXCollections.observableArrayList(
			ConnectionType.TCP,
			ConnectionType.UDP
	);
	@FXML
	private final Config config = Config.getInstance();
	@FXML
	private ChoiceBox<ConnectionType> connectionTypeChoiceBox;

	@FXML
	private Button createNewChatButton;

	@FXML
	private Button connectToChatButton;

	@FXML
	private TextField usernameTextField;

	@FXML
	private TextField encryptionKeyTextField;

	@FXML
	protected void connectToChat() throws IOException {
		Window owner = connectToChatButton.getScene().getWindow();
		if (usernameTextField.getText().isEmpty()) {
			AlertUtils.showAlert(Alert.AlertType.ERROR, owner, "Error!",
					"Please enter your username");
			return;
		}
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat_connection.fxml"));
		Parent root = fxmlLoader.load();
		Stage parent = (Stage) createNewChatButton.getScene().getWindow();
		parent.setScene(new Scene(root));
		parent.show();
	}

	@FXML
	protected void createChat() throws IOException {
		Window owner = connectToChatButton.getScene().getWindow();
		if (usernameTextField.getText().isEmpty()) {
			AlertUtils.showAlert(Alert.AlertType.ERROR, owner, "Error!",
					"Please enter your username");
			return;
		}
		String key = encryptionKeyTextField.getText();
		if (!key.isEmpty() && key.toCharArray().length != 16)
		{
			AlertUtils.showAlert(Alert.AlertType.ERROR, owner, "Error!",
					"Key must be 128 bit or empty");
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
				(observable, oldValue, newValue) -> config.setUsername(newValue)
		);
		connectionTypeChoiceBox.setItems(enums);
		connectionTypeChoiceBox.setValue(ConnectionType.TCP);
		connectionTypeChoiceBox.valueProperty().addListener((observable, oldValue, newValue) ->
				config.setConnectionType(newValue)
		);
		encryptionKeyTextField.textProperty().addListener((observable, oldValue, newValue) -> config.setEncryptionKey(newValue)
		);
		//Выключено, потому что только TCP
		connectionTypeChoiceBox.setDisable(true);
	}
}
