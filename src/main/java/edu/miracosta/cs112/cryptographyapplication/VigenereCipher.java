package edu.miracosta.cs112.cryptographyapplication;

/**
 * VigenereCipher class that extends CryptographyMethod to provide encryption and decryption
 * using the Vigenere Cipher algorithm.
 *
 * <p>
 * This class implements the encrypt and decrypt methods using the Vigenere Cipher
 * algorithm. It uses a keyword for encryption and decryption. Non-alphabetical characters
 * in the plaintext or ciphertext are ignored in terms of encryption/decryption and left unchanged.
 * </p>
 *
 * <p>
 * The default key used if none is provided is "password".
 * </p>
 *
 * @see CryptographyMethod
 *
 * @version 1.0
 */
public class VigenereCipher extends CryptographyMethod {
    public static final String DEFAULT_KEY = "password";

    private String key;

    /**
     * Constructs a new {@code VigenereCipher} object with the default input and output modes,
     * and the default key.
     */
    public VigenereCipher() {
        this(DEFAULT_INPUT_MODE, DEFAULT_OUTPUT_MODE, DEFAULT_KEY);
    }

    /**
     * Constructs a new {@code VigenereCipher} object with the specified input and output modes,
     * and the provided key.
     *
     * @param inputMode  the input mode
     * @param outputMode the output mode
     * @param key        the keyword used for encryption and decryption
     * @throws IllegalArgumentException if the key is null, empty, or contains non-alphabetical characters
     */
    public VigenereCipher(int inputMode, int outputMode, String key) throws IllegalArgumentException {
        super(inputMode, outputMode);
        setKey(key);
    }

    /**
     * Sets the key for the Vigenere Cipher algorithm.
     *
     * @param key the keyword used for encryption and decryption
     * @throws IllegalArgumentException if the key is null, empty, or contains non-alphabetical characters
     */
    public void setKey(String key) throws IllegalArgumentException {
        if (key == null || key.isEmpty() || !key.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Key must only contain alphabetical characters!");
        } else {
            this.key = key;
        }
    }

    /**
     * Gets the key for the Vigenere Cipher algorithm.
     *
     * @return the keyword used for encryption and decryption
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the rotation value for a given key character.
     *
     * @param keyChar the key character
     * @return the rotation value for the key character
     */
    private int getRotationFromChar(char keyChar) {
        if (Character.isLowerCase(keyChar)) {
            return keyChar - 'a';
        } else {
            return keyChar - 'A';
        }
    }

    /**
     * Generates a full-length key based on the provided text and the initial key.
     * Non-alphabetical characters are replaced with '?'.
     *
     * @param text the text for which the key is generated
     * @return the generated key
     */
    private String generateKey(String text) {
        int keyLength = key.length();
        int j = 0;
        StringBuilder keyBuilder = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (!String.valueOf(text.charAt(i)).matches("^[a-zA-Z]+$")) {
                keyBuilder.append("?");
            } else {
                keyBuilder.append(key.charAt(j % keyLength));
                j++;
            }
        }

        return keyBuilder.toString();
    }

    /**
     * Encrypts the plaintext using the Vigenere Cipher algorithm.
     *
     * @param plaintext the plaintext to be encrypted
     * @return the encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext) {
        plaintext = decodeInput(plaintext); // Decode plaintext based on inputMode
        key = generateKey(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char plaintextChar = plaintext.charAt(i);
            char keyChar = key.charAt(i);

            if (keyChar == '?') {
                ciphertext.append(plaintextChar);
                continue;
            }

            int rotations = getRotationFromChar(keyChar);

            CaesarCipher tempCaesar = new CaesarCipher(0, 0, rotations);
            ciphertext.append(tempCaesar.encrypt(String.valueOf(plaintextChar)));
        }

        return encodeOutput(ciphertext.toString());
    }

    /**
     * Decrypts the ciphertext using the Vigenere Cipher algorithm.
     *
     * @param ciphertext the ciphertext to be decrypted
     * @return the decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext) {
        ciphertext = decodeInput(ciphertext);
        key = generateKey(ciphertext);
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char ciphertextChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i);

            if (keyChar == '?') {
                plaintext.append(ciphertextChar);
                continue;
            }

            int rotations = getRotationFromChar(keyChar);

            CaesarCipher tempCaesar = new CaesarCipher(0, 0, rotations);
            plaintext.append(tempCaesar.decrypt(String.valueOf(ciphertextChar)));
        }

        return encodeOutput(plaintext.toString());
    }

    /**
     * Returns a string representation of the VigenereCipher object.
     *
     * @return a string representation of the VigenereCipher object
     */
    @Override
    public String toString() {
        String inputOutput = super.toString();
        return "Vigenere Cipher:\n" + inputOutput.substring(inputOutput.indexOf(':') + 2) + "\nKey: " + this.key;
    }

    /**
     * Compares this VigenereCipher instance with another object for equality.
     *
     * @param other the object to compare to
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof VigenereCipher otherVigenereCipher)) {
            return false;
        }
        return super.equals(otherVigenereCipher) && this.key.equals(otherVigenereCipher.key);
    }
}
