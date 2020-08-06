package io.github.losthikking.iconsofttest.net;

import io.github.losthikking.iconsofttest.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Server implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(Server.class);
	protected final List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList<>());
	private final List<Message> messages = Collections.synchronizedList(new ArrayList<>());
	protected int port;

	protected void sendToAll(Message message){
		clients.forEach(socket -> {
			try {
				LOG.info("Send message");
				socket.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				socket.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public int getPort() {
		return port;
	}

	public List<Message> getMessages() {
		return messages;
	}
}
