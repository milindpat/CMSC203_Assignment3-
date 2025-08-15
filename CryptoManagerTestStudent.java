import static org.junit.Assert.*;
import org.junit.Test;

public class CryptoManagerTestStudent {

    @Test
    //Checks if string bounds validation works for allowed ASCII range
    public void testIsStringInBounds() {
        assertTrue(CryptoManager.isStringInBounds("HELLO_WORLD"));
        assertFalse(CryptoManager.isStringInBounds("Hello World")); // lowercase + space
        assertTrue(CryptoManager.isStringInBounds("Z_YXWVU"));
        assertFalse(CryptoManager.isStringInBounds("abc123"));
    }

    @Test
    //Ensures Vigenère encryption and decryption return the original text
    public void testVigenereEncryptionDecryption() {
        String plain = "UNTILDAWN";
        String key = "LEMON";
        String encrypted = CryptoManager.vigenereEncryption(plain, key);
        String decrypted = CryptoManager.vigenereDecryption(encrypted, key);

        assertEquals("Decrypted text should match original", plain, decrypted);
        assertNotEquals("Encrypted text should not match original", plain, encrypted);
    }

    @Test
    //Tests Playfair cipher with even-length text
    public void testPlayfairEncryptionDecryption() {
        String plain = "MEETMEATTHEPARKX";
        String key = "SECRET";
        String encrypted = CryptoManager.playfairEncryption(plain, key);
        String decrypted = CryptoManager.playfairDecryption(encrypted, key);

        assertEquals("Decrypted text should match original", plain, decrypted);
        assertNotEquals("Encrypted text should not match original", plain, encrypted);
    }

    @Test
    //Ensures Playfair works for odd-length text
    public void testPlayfairOddLengthText() {
        String plain = "HELXLO";
        String key = "SECRET";
        String encrypted = CryptoManager.playfairEncryption(plain, key);
        String decrypted = CryptoManager.playfairDecryption(encrypted, key);

        assertEquals("Odd-length decryption should match original", plain, decrypted);
    }

    @Test
    //Validates encryption/decryption behavior for empty text
    public void testEmptyAndSpecialCases() {
        String key = "KEY";

        assertEquals("Empty text should remain empty", "", CryptoManager.vigenereEncryption("", key));
        assertEquals("Empty text should remain empty", "", CryptoManager.vigenereDecryption("", key));

        assertEquals("Empty text should remain empty", "", CryptoManager.playfairEncryption("", key));
        assertEquals("Empty text should remain empty", "", CryptoManager.playfairDecryption("", key));
    }
}
