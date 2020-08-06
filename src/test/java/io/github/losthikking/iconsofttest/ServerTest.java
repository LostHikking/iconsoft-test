package io.github.losthikking.iconsofttest;

import io.github.losthikking.iconsofttest.dto.Message;
import io.github.losthikking.iconsofttest.net.Client;
import io.github.losthikking.iconsofttest.net.TcpServer;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {


	@Test
	void testServer() throws InterruptedException {
		List<Message> testList = new ArrayList<>();
		Message message = new Message("Someone", "Something", System.currentTimeMillis(), false);
		for (int i = 0; i < 50; i++) {
			testList.add(message);
		}
		System.out.println(testList.size());
		TcpServer server = new TcpServer();
		Thread serverThread = new Thread(server);
		serverThread.setName("Server Thread");
		serverThread.start();
		Client client = new Client("localhost", server.getPort(), "");
		Client client1 = new Client("localhost", server.getPort(), "");
		Thread clientThread = new Thread(client);
		clientThread.setName("Client 1 Thread");
		clientThread.start();
		Thread clientThread1 = new Thread(client1);
		clientThread1.start();
		//clientThread1.join();
		testList.forEach(client::sendMessage);
		Thread.sleep(5000);
		System.out.println(server.getMessages().size());
		System.out.println(client.getMessages().size());
		System.out.println(client1.getMessages().size());
		assertEquals(testList.size(), server.getMessages().size());
		//Минус 1, так как одно сообщение сервисное, внутри самого клиента
		assertEquals(testList.size(), client.getMessages().size() - 1);
		assertEquals(testList.size(), client1.getMessages().size() - 1);
	}
}