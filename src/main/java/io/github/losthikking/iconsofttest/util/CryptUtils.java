package io.github.losthikking.iconsofttest.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class CryptUtils {
	private CryptUtils() {
	}

	public static String cryptText(String text, String key) {
		String encryptedText = null;
		Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : encrypted) {
				sb.append((char) b);
				encryptedText = sb.toString();
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	public static String decryptText(String text, String key) {
		String decryptedText = null;
		try {
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] bb = new byte[text.length()];
			for (int i = 0; i < text.length(); i++) {
				bb[i] = (byte) text.charAt(i);
			}
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			decryptedText = new String(cipher.doFinal(bb));
		} catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return decryptedText;
	}
}
