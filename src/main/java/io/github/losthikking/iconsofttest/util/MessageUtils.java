package io.github.losthikking.iconsofttest.util;

import io.github.losthikking.iconsofttest.dto.Message;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class MessageUtils {

	private MessageUtils() {
	}

	/**
	 * @param messages список сообщений, куда надо добавить системное сообщение
	 * @param text системное сообщение
	 */
	public static void addSystemMessage(ObservableList<Message> messages, String text) {
		addMessage(messages, new Message("System", text, System.currentTimeMillis(), false));
	}

	/**
	 * @param messages список сообщений, куда надо добавить сообщение
	 * @param message сообщение, которое необходимо добавить
	 */
	public static void addMessage(ObservableList<Message> messages, Message message) {
		Platform.runLater(() -> messages.add(message));
	}
}
