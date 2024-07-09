package edu.miracosta.cs112.cryptographyapplication;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Abstract class representing a cryptography method for encryption and
 * decryption.
 *
 * <p>
 * This class provides the functionality for selecting various input and output
 * modes. Subclasses must implement the {@code encrypt} and {@code decrypt}
 * methods to define the specific cryptographic algorithms.
 *
 *
 * <p>
 * Supported input and output modes:
 * <ul>
 * <li>0: plaintext</li>
 * <li>1: base64</li>
 * <li>2: hex</li>
 * </ul>
 *
 *
 * @author Matin Sadeghian
 * @version 1.0
 */
public abstract class CryptographyMethod {

    /**
     * Default input mode.
     */
    public static final int DEFAULT_INPUT_MODE = 0;

    /**
     * Default output mode.
     */
    public static final int DEFAULT_OUTPUT_MODE = 0;

    /**
     * Input mode.
     */
    private int inputMode;

    /**
     * Output mode.
     */
    private int outputMode;

    /**
     * Constructs a new {@code CryptographyMethod} object with the specified input and
     * output modes.
     *
     * @param inputMode  the input mode
     * @param outputMode the output mode
     * @throws IllegalArgumentException if the input mode or output mode is not supported
     */
    public CryptographyMethod(int inputMode, int outputMode) throws IllegalArgumentException {
        if (!this.setAll(inputMode, outputMode)) {
            throw new IllegalArgumentException("Invalid input or output mode selected! Please make sure you select a valid mode (0 = plaintext, 1 = base64, 2 = hex)");
        }
    }

    /**
     * Constructs a new {@code CryptographyMethod} object with the default input and output
     * modes.
     */
    public CryptographyMethod() {
        this(DEFAULT_INPUT_MODE, DEFAULT_OUTPUT_MODE);
    }

    /**
     * Sets the input mode.
     *
     * @param inputMode the input mode to set.
     * @return {@code true} if the input mode is valid, {@code false} otherwise.
     */
    public boolean setInputMode(int inputMode) {
        if (inputMode >= 0 && inputMode <= 2) {
            this.inputMode = inputMode;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the output mode.
     *
     * @param outputMode the output mode to set.
     * @return {@code true} if the output mode is valid, {@code false} otherwise.
     */
    public boolean setOutputMode(int outputMode) {
        if (outputMode >= 0 && outputMode <= 2) {
            this.outputMode = outputMode;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets both the input and output modes.
     *
     * @param inputMode  the input mode to set.
     * @param outputMode the output mode to set.
     * @return {@code true} if both modes are valid, {@code false} otherwise.
     */
    public boolean setAll(int inputMode, int outputMode) {
        return setInputMode(inputMode) && setOutputMode(outputMode);
    }

    /**
     * Gets the input mode.
     *
     * @return the input mode.
     */
    public int getInputMode() {
        return this.inputMode;
    }

    /**
     * Gets the output mode.
     *
     * @return the output mode.
     */
    public int getOutputMode() {
        return this.outputMode;
    }

    /**
     * Returns a string representation of this {@code CryptographyMethod} object, including
     * its input and output modes.
     *
     * @return a string representation of this {@code CryptographyMethod} object.
     */
    @Override
    public String toString() {
        return "Cryptography Method:\nMode: " + modeToString(this.inputMode) + " -> " + modeToString(this.outputMode);
    }

    /**
     * Compares this {@code CryptographyMethod} object to the specified object. The result
     * is {@code true} if and only if the specified object is not {@code null} and
     * is a {@code CryptographyMethod} object with the same input and output modes
     * as this object.
     *
     * @param other The object to compare this {@code CryptographyMethod} object to.
     * @return {@code true} if the specified object is equal to this
     * {@code CryptographyMethod} object, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CryptographyMethod otherCryptographyMethod)) {
            return false;
        }
        return this.inputMode == otherCryptographyMethod.inputMode && this.outputMode == otherCryptographyMethod.outputMode;
    }

    /**
     * Converts the given mode integer to the corresponding string representation.
     *
     * @param mode The mode integer to convert.
     * @return The string representation of the mode.
     */
    public String modeToString(int mode) {
        return switch (mode) {
            case 0 -> "plaintext";
            case 1 -> "base64";
            case 2 -> "hex";
            default -> "Invalid Mode";
        };
    }

    /**
     * Decodes the input string based on the input mode.
     *
     * <p>
     * This method decodes the input string based on the current input mode of the cryptography method.
     * If the input mode is plaintext (0), the input string remains unchanged.
     * If the input mode is base64 (1), the input string is decoded from base64 encoding.
     * If the input mode is hexadecimal (2), the input string is decoded from hexadecimal encoding.
     * </p>
     *
     * @param input The input string to decode.
     * @return The decoded input string.
     * @throws IllegalArgumentException If an invalid input mode is selected.
     */
    public String decodeInput(String input) throws IllegalArgumentException {
        return switch(this.inputMode) {
            case 0 -> input;
            case 1 -> base64Decode(input);
            case 2 -> hexDecode(input);
            default -> throw new IllegalArgumentException("Invalid mode selected!");
        };
    }

    /**
     * Encodes the output string based on the output mode.
     *
     * <p>
     * This method encodes the output string based on the current output mode of the cryptography method.
     * If the output mode is plaintext (0), the output string remains unchanged.
     * If the output mode is base64 (1), the output string is encoded to base64.
     * If the output mode is hexadecimal (2), the output string is encoded to hexadecimal.
     * </p>
     *
     * @param output The output string to encode.
     * @return The encoded output string.
     * @throws IllegalArgumentException If an invalid output mode is selected.
     */
    public String encodeOutput(String output) throws IllegalArgumentException {
        return switch (this.outputMode) {
            case 0 -> output;
            case 1 -> base64Encode(output);
            case 2 -> hexEncode(output);
            default -> throw new IllegalArgumentException("Invalid mode selected!");
        };
    }

    /**
     * Encodes the given message to base64 format.
     *
     * @param message The message to encode.
     * @return The base64-encoded message.
     */
    public String base64Encode(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

    /**
     * Decodes the given base64-encoded message.
     *
     * @param encoded The base64-encoded message to decode.
     * @return The decoded plaintext message.
     */
    public String base64Decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }

    /**
     * Encodes the given message to hexadecimal format.
     *
     * @param message The message to encode.
     * @return The hexadecimal-encoded message.
     */
    public String hexEncode(String message) {
        StringBuilder hexString = new StringBuilder();
        for (char character : message.toCharArray()) {
            hexString.append(Integer.toHexString(character));
        }
        return hexString.toString();
    }

    /**
     * Decodes the given hexadecimal-encoded message.
     *
     * @param encoded The hexadecimal-encoded message to decode.
     * @return The decoded plaintext message.
     */
    public String hexDecode(String encoded) throws IllegalArgumentException {
        if (encoded.length() % 2 != 0) {
            throw new IllegalArgumentException("Hexadecimal string length must be even.");
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < encoded.length(); i += 2) {
            String hex = encoded.substring(i, i + 2);
            try {
                result.append((char) Integer.parseInt(hex, 16));
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid hexadecimal character: " + hex, nfe);
            }
        }
        return result.toString();
    }

    /**
     * Abstract method that encrypts the given plaintext.
     *
     * @param plaintext The plaintext to encrypt.
     * @return The encrypted ciphertext.
     */
    public abstract String encrypt(String plaintext);

    /**
     * Abstract method that decrypts the given ciphertext.
     *
     * @param ciphertext The ciphertext to decrypt.
     * @return The decrypted plaintext.
     */
    public abstract String decrypt(String ciphertext);
}