package io.github.losthikking.iconsofttest.net;

import io.github.losthikking.iconsofttest.dto.Message;
import io.github.losthikking.iconsofttest.util.CryptUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);

	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream ous;
	private final String encryptionKey;
	private final ObservableList<Message> messages = FXCollections.observableList(new ArrayList<>());


	public Client(String ipAddress, int port, String encryptionKey) {
		if (encryptionKey != null)
			this.encryptionKey = encryptionKey;
		else this.encryptionKey = "";
		LOG.info("New connection to {}:{}", ipAddress, port);
		try {
			clientSocket = new Socket(ipAddress, port);
			ous = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Platform.runLater(() -> messages.add(new Message("System",
				"Connection established",
				System.currentTimeMillis(),
				false)));
	}

	@Override
	public void run() {
		try {
			while (clientSocket.isConnected()) {
				Message message = null;
				try {
					message = (Message) ois.readObject();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (message != null) {
					LOG.info("New message: {}", message);
					if (message.isEncrypted()) {
						if (!encryptionKey.isEmpty())
							message.setText(CryptUtils.decryptText(message.getText(), encryptionKey));
						else
							message.setText("Encrypted Message, plz fill key fill in menu");
					}
					Message finalMessage = message;
					Platform.runLater(() -> messages.add(finalMessage));
				}
			}
			LOG.info("Connection closed to server closed");
		} finally {
			stop();
		}
	}

	public void sendMessage(Message msg) {
		LOG.info("Sending message:{}", msg);
		try {
			if (!encryptionKey.isEmpty()) {
				msg.setText(CryptUtils.cryptText(msg.getText(), encryptionKey));
				msg.setEncrypted(true);
			}
			ous.writeObject(msg);
			ous.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		Platform.runLater(() ->
				messages.add(new Message("System",
						"Connection closed.",
						System.currentTimeMillis(),
						false)));
		LOG.info("Client Stopped");
		if (ois != null)
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (ous != null)
			try {
				ous.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (clientSocket != null)
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}


	public ObservableList<Message> getMessages() {
		return messages;
	}
}
