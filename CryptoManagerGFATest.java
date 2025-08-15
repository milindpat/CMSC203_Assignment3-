/**
 * This class represents GFA test cases for a CryptoManager object.
 * 
 * @author Huseyin Aygun
 * @version 5/3/2025
 * 
 */

import junit.framework.TestCase;

public class CryptoManagerGFATest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    public void testIsStringInBounds() {
        assertTrue(CryptoManager.isStringInBounds("MONTGOMERY"));
    }

	public void testVigenereEncryptDecrypt() {
        String plain = "MONTGOMERY";
        String key = "MNT";
        String encrypted = CryptoManager.vigenereEncryption(plain, key);
        String decrypted = CryptoManager.vigenereDecryption(encrypted, key);
        assertEquals(plain, decrypted);
    }
    

}
