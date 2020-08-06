package io.github.losthikking.iconsofttest.util;

import io.github.losthikking.iconsofttest.dto.Message;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class MessageUtils {

	private MessageUtils() {
	}

	public static void addSystemMessage(ObservableList<Message> messages, String text) {
		addMessage(messages, new Message("System", text, System.currentTimeMillis(), false));
	}

	public static void addMessage(ObservableList<Message> messages, Message message) {
		Platform.runLater(() -> messages.add(message));
	}
}
