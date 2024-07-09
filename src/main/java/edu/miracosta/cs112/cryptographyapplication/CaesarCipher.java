package edu.miracosta.cs112.cryptographyapplication;

/**
 * <p>Represents a Caesar cipher encryption and decryption method.</p>
 * <p>This class extends the {@link CryptographyMethod} abstract class.</p>
 *
 * @author Matin Sadeghian
 * @version 1.0
 */
public class CaesarCipher extends CryptographyMethod {
    /** Default number of rotations for the Caesar cipher. */
    public static final int DEFAULT_ROTATIONS = 13;

    /** The number of rotations for the Caesar cipher. */
    private int rotations;

    /**
     * Constructs a Caesar cipher with the specified input mode, output mode, and number of rotations.
     *
     * @param inputMode  The input mode for encryption.
     * @param outputMode The output mode for encryption.
     * @param rotations  The number of rotations for the Caesar cipher.
     */
    public CaesarCipher(int inputMode, int outputMode, int rotations) {
        super(inputMode, outputMode);
        setRotations(rotations);
    }

    /**
     * Constructs a Caesar cipher with the specified input mode, output mode, and default number of rotations.
     *
     * @param inputMode  The input mode for encryption.
     * @param outputMode The output mode for encryption.
     */
    public CaesarCipher(int inputMode, int outputMode) {
        this(inputMode, outputMode, DEFAULT_ROTATIONS);
    }

    /**
     * Constructs a Caesar cipher with default input mode, output mode, and number of rotations.
     */
    public CaesarCipher() {
        this(DEFAULT_INPUT_MODE, DEFAULT_OUTPUT_MODE, DEFAULT_ROTATIONS);
    }

    /**
     * Sets the number of rotations for the Caesar cipher.
     *
     * @param rotations The number of rotations to set.
     */
    public void setRotations(int rotations) {
        this.rotations = rotations;
    }

    /**
     * Sets input and output modes, along with the number of rotations.
     *
     * @param inputMode  the input mode to set.
     * @param outputMode the output mode to set.
     * @param rotations The number of rotations to set.
     * @return {@code true} if both modes are valid, {@code false} otherwise.
     */
    public boolean setAll(int inputMode, int outputMode, int rotations) {
        setRotations(rotations);
        return super.setAll(inputMode, outputMode);
    }

    /**
     * Gets the number of rotations for the Caesar cipher.
     *
     * @return The number of rotations.
     */
    public int getRotations() {
        return this.rotations;
    }

    /**
     * <p>Encrypts the given plaintext using the Caesar cipher algorithm.</p>
     *
     * <p>The encryption process involves shifting each character in the plaintext by a fixed number of positions
     * determined by the rotations. Uppercase and lowercase letters are shifted separately while non-alphabetic
     * characters remain unchanged.</p>
     *
     * <p>The plaintext is first decoded based on the input mode of the cryptography method. Then, each character
     * in the plaintext is shifted by the specified number of rotations, considering both positive and negative rotations.
     * Uppercase and lowercase letters are shifted separately while non-alphabetic characters remain unchanged.</p>
     *
     * @param plaintext The plaintext to encrypt.
     * @return The ciphertext produced by encrypting the plaintext using the Caesar cipher algorithm.
     * @throws IllegalArgumentException If an invalid input mode is selected during decoding, or if an invalid output mode is selected during encoding.
     */
    @Override
    public String encrypt(String plaintext) {
        plaintext = decodeInput(plaintext); // Decode plaintext based on inputMode
        StringBuilder ciphertext = new StringBuilder();

        // Check if rotation is negative
        if (rotations < 0) {
            rotations = 26 + rotations % 26;
        }

        for (int i = 0; i < plaintext.length(); i++) {
            if (Character.isUpperCase(plaintext.charAt(i))) { // Uppercase letters
                char encryptedChar = (char)(((int)plaintext.charAt(i) + rotations - 65) % 26 + 65);
                ciphertext.append(encryptedChar);
            } else if (Character.isLowerCase(plaintext.charAt(i))){ // Lowercase letters
                char encryptedChar = (char)(((int)plaintext.charAt(i) + rotations - 97) % 26 + 97);
                ciphertext.append(encryptedChar);
            } else { // Non-alphanumeric characters
                ciphertext.append(plaintext.charAt(i));
            }
        }
        return encodeOutput(ciphertext.toString()); // Encode ciphertext based on outputMode
    }

    /**
     * <p>Decrypts the given ciphertext using the Caesar cipher algorithm.</p>
     *
     * <p>The decryption process is essentially the reverse of encryption, involving shifting each character
     * in the ciphertext backwards by the same number of positions used for encryption.</p>
     *
     * @param ciphertext The ciphertext to decrypt.
     * @return The plaintext produced by decrypting the ciphertext using the Caesar cipher algorithm.
     */
    @Override
    public String decrypt(String ciphertext) {
        int originalRotations = this.rotations; // temp variable to store rotations
        this.rotations = 26 - this.rotations;
        String plaintext = encrypt(ciphertext);
        this.rotations = originalRotations;

        return plaintext;
    }

    /**
     * <p>Returns a string representation of the Caesar cipher.</p>
     *
     * <p>The string representation includes information about the input mode, output mode, and the number of
     * rotations used for encryption and decryption.</p>
     *
     * @return A string representation of the Caesar cipher.
     */
    @Override
    public String toString() {
        String inputOutput = super.toString();
        return "Caesar Cipher:\n" + inputOutput.substring(inputOutput.indexOf(':') + 2) + "\nShift: " + this.rotations;
    }

    /**
     * <p>Compares this Caesar cipher with the specified object for equality.</p>
     *
     * <p>The comparison checks if the specified object is also a Caesar cipher and if it has the same input mode,
     * output mode, and number of rotations.</p>
     *
     * @param other The object to compare with this Caesar cipher for equality.
     * @return {@code true} if the specified object is equal to this Caesar cipher, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CaesarCipher otherCaesarCipher)) {
            return false;
        }
        return super.equals(otherCaesarCipher) && this.rotations == otherCaesarCipher.rotations;
    }
}
