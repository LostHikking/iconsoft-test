package io.github.losthikking.iconsofttest.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptUtilsTest {

	@Test
	void textCryptAndEncrypt(){
		String text = "Something";
		String key = "abcdabcdabcdabcd";
		String encryptedText = CryptUtils.cryptText(text, key);
		String decryptedText = CryptUtils.decryptText(encryptedText, key);
		assertNotEquals(text, encryptedText);
		assertEquals(text, decryptedText);
	}
}