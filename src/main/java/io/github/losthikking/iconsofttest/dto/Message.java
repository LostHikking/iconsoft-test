package io.github.losthikking.iconsofttest.dto;

import java.io.Serializable;

public class Message implements Serializable {
	private final String sender;
	private String text;
	private final long time;
	private boolean encrypted;

	public void setText(String text) {
		this.text = text;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Message)) return false;

		Message message = (Message) o;

		if (time != message.time) return false;
		if (encrypted != message.encrypted) return false;
		if (!sender.equals(message.sender)) return false;
		return text.equals(message.text);
	}

	@Override
	public int hashCode() {
		int result = sender.hashCode();
		result = 31 * result + text.hashCode();
		result = 31 * result + (int) (time ^ (time >>> 32));
		result = 31 * result + (encrypted ? 1 : 0);
		return result;
	}

	public String getSender() {
		return sender;
	}

	public String getText() {
		return text;
	}

	public long getTime() {
		return time;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public Message(String sender, String text, long time, boolean encrypted) {
		this.sender = sender;
		this.text = text;
		this.time = time;
		this.encrypted = encrypted;
	}

	@Override
	public String toString() {
		return sender + ": " + text;
	}
}