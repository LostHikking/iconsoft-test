package io.github.losthikking.iconsofttest;

public class Config {
	private static final Config instance = new Config();
	private String username;
	private Config() {
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
}
