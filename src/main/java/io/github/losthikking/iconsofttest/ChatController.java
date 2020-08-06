package io.github.losthikking.iconsofttest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
	@FXML
	public ListView<Message> messagesListView;
	@FXML
	public TextArea messageInputTextArea;
	@FXML
	public Button sendButton;

	private Chat chat;

	public void setChat(Chat chat) {
		this.chat = chat;
		messagesListView.setItems(chat.getMessages());
	}

	@FXML
	protected void sendMessage(ActionEvent event) {
		if (!messageInputTextArea.getText().isEmpty()) {
			chat.sendMessage(messageInputTextArea.getText());
			messageInputTextArea.clear();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		messageInputTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
			sendButton.setDisable(newValue.isEmpty());
		});
	}
}
