package io.github.losthikking.iconsofttest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chat {
	private final String username;
	private final Client client;
	private final ObservableList<Message> messages;
	private final boolean encrypted;

	public ObservableList<Message> getMessages() {
		return messages;
	}

	private Chat(boolean encrypted, Client client) {
		Config config = Config.getInstance();
		this.username = config.getUsername();
		this.encrypted = encrypted;
		this.client = client;
		this.messages = FXCollections.observableList(client.getMessages());
	}

	public static Chat createChat() {
		Server server = new Server();
		Thread serverThread = new Thread(server);
		serverThread.setName("Server Thread");
		serverThread.start();
		Chat chat = connectToChat("localhost", server.getPort());
		chat.addSystemMessage("This chat working on port " + server.getPort());
		chat.addSystemMessage("Waiting for client");
		return chat;
	}

	public static Chat connectToChat(String ipAddress, int port) {
		Client client = new Client(ipAddress, port);
		Thread clientThread = new Thread(client);
		clientThread.setName("Client Thread");
		clientThread.start();
		Chat chat = new Chat(false, client);
		chat.addSystemMessage("Connecting to " + ipAddress + ":" + port);
		return new Chat( false, client);
	}


	public void sendMessage(String text) {
		client.sendMessage(new Message(username, text, System.currentTimeMillis(), encrypted));
	}

	public void addSystemMessage(String text) {
		messages.add(new Message("System", text, System.currentTimeMillis(), false));
	}
}
