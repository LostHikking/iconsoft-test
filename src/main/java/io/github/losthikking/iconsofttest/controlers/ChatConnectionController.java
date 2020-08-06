package io.github.losthikking.iconsofttest.controlers;

import io.github.losthikking.iconsofttest.Chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatConnectionController implements Initializable {
	@FXML
	private Button connectButton;
	@FXML
	private TextField ipAddressTextField;
	@FXML
	private TextField portTextField;

	public void connect() throws IOException {
		Window owner = connectButton.getScene().getWindow();
		String ipAddress = ipAddressTextField.getText();
		String port = portTextField.getText();
		if (!ipAddress.isEmpty() && !port.isEmpty()) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat.fxml"));
			Parent root = fxmlLoader.load();
			ChatController chatController = fxmlLoader.getController();
			chatController.setChat(Chat.connectToChat(ipAddress, Integer.parseInt(port)));
			Stage parent = (Stage) owner;
			parent.setScene(new Scene(root));
			parent.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		portTextField.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (!newValue.matches("\\d*")) {
						portTextField.setText(newValue.replaceAll("[^\\d]", ""));
					}
				});
	}
}
