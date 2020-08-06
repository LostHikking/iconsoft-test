package io.github.losthikking.iconsofttest;

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
import java.util.List;
import java.util.concurrent.Semaphore;

public class Client implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream ous;
	private final String ipAddress;
	private final int port;
	private final ObservableList<Message> messages = FXCollections.observableList(new ArrayList<>());
	private final Semaphore mutex = new Semaphore(1);


	public Client(String ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port;
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			LOG.info("New connection to {}:{}", ipAddress, port);
			clientSocket = new Socket(ipAddress, port);
			ous = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
			messages.add(new Message("System",
							"Connection established",
							System.currentTimeMillis(),
							false));
			mutex.release();
			while (clientSocket.isConnected()) {
				Message message = null;
				try {
					message = (Message) ois.readObject();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (message != null) {
					LOG.info("New message: {}", message);
					messages.add(message);
				}
			}
			LOG.info("Connection closed to server closed");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			stop();
		}
	}

	public void sendMessage(Message msg) {
		LOG.info("Sending message:{}", msg);
		try {
			mutex.acquire();
			ous.writeObject(msg);
			ous.flush();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.release();
		}
	}

	public void stop() {
		messages.add(new Message("System",
						"Connection closed.",
						System.currentTimeMillis(),
						false));
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


	public List<Message> getMessages() {
		return messages;
	}
}
