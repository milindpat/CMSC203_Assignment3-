/**
 * This is a utility class that encrypts and decrypts a phrase using two
 * different approaches. 
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption 
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph) 
 * at a time instead of just one.
 * 
 * @author Huseyin Aygun
 * @version 5/3/2025
 */

public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_";

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   

    public static String vigenereEncryption(String plainText, String key) {
         //to be implemented by students
        StringBuilder encrypted = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (int i = 0; i < plainText.length(); i++) {
            char p = plainText.charAt(i);
            char k = key.charAt(keyIndex);
            int shift = (k - 'A') % RANGE;
            char enc = (char) (((p - LOWER_RANGE + shift) % RANGE) + LOWER_RANGE);
            encrypted.append(enc);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return encrypted.toString();
    }

    // Vigenere Decryption
    public static String vigenereDecryption(String encryptedText, String key) {
         //to be implemented by students
        StringBuilder decrypted = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (int i = 0; i < encryptedText.length(); i++) {
            char c = encryptedText.charAt(i);
            char k = key.charAt(keyIndex);
            int shift = (k - 'A') % RANGE;
            char dec = (char) (((c - LOWER_RANGE - shift + RANGE) % RANGE) + LOWER_RANGE);
            decrypted.append(dec);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return decrypted.toString();
    }


	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    

    public static String playfairEncryption(String plainText, String key) {
        //to be implemented by students
        char[][] matrix = buildPlayfairMatrix(key);
        String processedText = preparePlayfairPlainText(plainText);
        StringBuilder encrypted = new StringBuilder();

        for (int i = 0; i < processedText.length(); i += 2) {
            char a = processedText.charAt(i);
            char b = processedText.charAt(i + 1);
            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            if (posA[0] == posB[0]) { // same row
                encrypted.append(matrix[posA[0]][(posA[1] + 1) % 8]);
                encrypted.append(matrix[posB[0]][(posB[1] + 1) % 8]);
            } else if (posA[1] == posB[1]) { // same column
                encrypted.append(matrix[(posA[0] + 1) % 8][posA[1]]);
                encrypted.append(matrix[(posB[0] + 1) % 8][posB[1]]);
            } else { // rectangle swap
                encrypted.append(matrix[posA[0]][posB[1]]);
                encrypted.append(matrix[posB[0]][posA[1]]);
            }
        }
        return encrypted.toString();

    }

    // Vigenere Decryption
    public static String playfairDecryption(String encryptedText, String key) {
         //to be implemented by students
        char[][] matrix = buildPlayfairMatrix(key);
        StringBuilder decrypted = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i += 2) {
            char a = encryptedText.charAt(i);
            char b = encryptedText.charAt(i + 1);
            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            if (posA[0] == posB[0]) { // same row
                decrypted.append(matrix[posA[0]][(posA[1] + 7) % 8]);
                decrypted.append(matrix[posB[0]][(posB[1] + 7) % 8]);
            } else if (posA[1] == posB[1]) { // same column
                decrypted.append(matrix[(posA[0] + 7) % 8][posA[1]]);
                decrypted.append(matrix[(posB[0] + 7) % 8][posB[1]]);
            } else { // rectangle swap
                decrypted.append(matrix[posA[0]][posB[1]]);
                decrypted.append(matrix[posB[0]][posA[1]]);
            }
        }
        return decrypted.toString();
    }

    //Adding additional methods to make logic easy.

    // Builds an 8x8 Playfair matrix from key without duplicates
    private static char[][] buildPlayfairMatrix(String key) {
        StringBuilder sb = new StringBuilder();
        key = key.toUpperCase();
        for (char c : key.toCharArray()) {
            if (ALPHABET64.indexOf(c) != -1 && sb.indexOf(String.valueOf(c)) == -1) {
                sb.append(c);
            }
        }
        for (char c : ALPHABET64.toCharArray()) {
            if (sb.indexOf(String.valueOf(c)) == -1) {
                sb.append(c);
            }
        }
        char[][] matrix = new char[8][8];
        for (int i = 0; i < sb.length(); i++) { // fixed from 64 to sb.length()
            matrix[i / 8][i % 8] = sb.charAt(i);
        }
        return matrix;
    }

    // Prepares text for Playfair encryption (fix doubles, pad with X)
    private static String preparePlayfairPlainText(String text) {
        StringBuilder sb = new StringBuilder(text.toUpperCase());
        for (int i = 0; i < sb.length() - 1; i += 2) {
            if (sb.charAt(i) == sb.charAt(i + 1)) {
                sb.insert(i + 1, 'X');
            }
        }
        if (sb.length() % 2 != 0) {
            sb.append('X');
        }
        return sb.toString();
    }

    // Finds row and column of character in Playfair matrix
    private static int[] findPosition(char[][] matrix, char c) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (matrix[row][col] == c) {
                    return new int[] {row, col};
                }
            }
        }
        return null;
    }

}
