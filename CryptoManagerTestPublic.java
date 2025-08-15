/**
 * This class represents GFA test cases for a CryptoManager object.
 * 
 * @author Huseyin Aygun
 * @version 5/3/2025
 * 
 */

import junit.framework.TestCase;

public class CryptoManagerTestPublic extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    public void testIsStringInBounds() {
        assertTrue(CryptoManager.isStringInBounds("MONTGOMERY 2025!"));
        assertFalse(CryptoManager.isStringInBounds("Montgomery 2025!"));
        assertFalse(CryptoManager.isStringInBounds("MonTgomery #$%!_-"));
    }

	public void testVigenereEncryptDecrypt() {
        String plain = "MONTGOMERY 2025!";
        String key = "MNT132!";
        String encrypted = CryptoManager.vigenereEncryption(plain, key);
        String decrypted = CryptoManager.vigenereDecryption(encrypted, key);
        assertEquals(plain, decrypted);
    }
    
    public void testPlayfairEncryptDecrypt() {
        String plain = "MONTGOMERY 2025!";
        String key = "MNT132!";
        String encrypted = CryptoManager.playfairEncryption(plain, key);
        String decrypted = CryptoManager.playfairDecryption(encrypted, key);
        assertEquals("MONTGOMERY 2025!", decrypted);
    }

}
