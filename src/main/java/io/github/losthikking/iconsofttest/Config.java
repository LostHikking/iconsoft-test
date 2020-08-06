package io.github.losthikking.iconsofttest;

import io.github.losthikking.iconsofttest.enums.ConnectionType;

public class Config {
	private static final Config instance = new Config();
	private String username;
	private ConnectionType connectionType;
	private String encryptionKey;

	private Config() {
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static Config getInstance() {
		return instance;
	}

	public void setConnectionType(ConnectionType newValue) {
		this.connectionType = newValue;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
}
