package io.github.losthikking.iconsofttest.net;

import io.github.losthikking.iconsofttest.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TcpServer extends Server {
	private static final Logger LOG = LoggerFactory.getLogger(TcpServer.class);

	private ServerSocket serverSocket;
	private final List<Socket> clientSockets = Collections.synchronizedList(new ArrayList<>());
	private final List<Message> messages = Collections.synchronizedList(new ArrayList<>());
	private final List<Thread> listeners = Collections.synchronizedList(new ArrayList<>());

	public TcpServer(){
		try {
			this.serverSocket = new ServerSocket(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.port = serverSocket.getLocalPort();
	}

	@Override
	public void run() {
		LOG.info("Creating new server connection.");
		try {
			LOG.info("New connection with port {} created.", serverSocket.getLocalPort());
			while (clients.size() < 2)
			{
				try {
					LOG.info("Waiting for connection");
					Socket clientSocket = serverSocket.accept();
					LOG.info("New client connected {}", clientSocket.getInetAddress());
					ObjectOutputStream ous = new ObjectOutputStream(clientSocket.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
					clients.add(ous);
					Runnable listener = () -> {
						while (clientSocket.isConnected())
						{
							Message message = null;
							try {
								message = (Message) ois.readObject();
							} catch (IOException | ClassNotFoundException e) {
								e.printStackTrace();
							}
							if (message != null) {
								messages.add(message);
								sendToAll(message);
								LOG.info("New message: {}", message);
							}
						}
					};
					Thread thread = new Thread(listener);
					thread.start();
					listeners.add(thread);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			LOG.info("All clients connected");
			listeners.forEach(it -> {
				try {
					it.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		} finally {
			stop();
		}
	}

	public void stop() {
		LOG.info("Server stopped");
		for (ObjectOutputStream ous: clients)
		{
			try {
				ous.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			for (Socket clientSocket : clientSockets) {
				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
