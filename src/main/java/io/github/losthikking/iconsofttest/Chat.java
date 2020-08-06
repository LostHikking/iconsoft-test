package io.github.losthikking.iconsofttest;

import io.github.losthikking.iconsofttest.dto.Message;
import io.github.losthikking.iconsofttest.net.Client;
import io.github.losthikking.iconsofttest.net.Server;
import io.github.losthikking.iconsofttest.net.TcpServer;
import io.github.losthikking.iconsofttest.util.MessageUtils;
import javafx.collections.ObservableList;

public class Chat {
	private static final Config CFG = Config.getInstance();
	private final String username;
	private final Client client;
	private final ObservableList<Message> messages;

	public ObservableList<Message> getMessages() {
		return messages;
	}

	private Chat(Client client) {
		this.username = CFG.getUsername();
		this.client = client;
		this.messages = client.getMessages();
	}

	public static Chat createChat() {
		Server server = new TcpServer();
		Thread serverThread = new Thread(server);
		serverThread.setName("Server Thread");
		serverThread.start();
		Chat chat = connectToChat("localhost", server.getPort());
		chat.addSystemMessage("This chat working on port " + server.getPort());
		chat.addSystemMessage("Waiting for client");
		return chat;
	}

	public static Chat connectToChat(String ipAddress, int port) {
		Client client = new Client(ipAddress, port, CFG.getEncryptionKey());
		Thread clientThread = new Thread(client);
		clientThread.setName("Client Thread");
		clientThread.start();
		Chat chat = new Chat(client);
		chat.addSystemMessage("Connecting to " + ipAddress + ":" + port);
		return new Chat(client);
	}


	public void sendMessage(String text) {
		client.sendMessage(new Message(username, text, System.currentTimeMillis(), false));
	}

	private void addSystemMessage(String text) {
		MessageUtils.addSystemMessage(messages, text);
	}
}
