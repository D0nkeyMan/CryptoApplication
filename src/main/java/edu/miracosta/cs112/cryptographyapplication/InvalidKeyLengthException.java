package edu.miracosta.cs112.cryptographyapplication;

/**
 * Exception thrown when an invalid key length is used for Aes encryption.
 *
 * <p>
 * This exception is thrown when a key length that is not supported by Aes
 * (i.e., not 128, 192, or 256 bits) is provided.
 * </p>
 *
 * @see Aes
 *
 * @version 1.0
 */
public class InvalidKeyLengthException extends Exception {
    /**
     * Constructs a new {@code InvalidKeyLengthException} with {@code null} as its
     * detail message.
     */
    public InvalidKeyLengthException() {
        this("");
    }

    /**
     * Constructs a new {@code InvalidKeyLengthException} with the specified detail
     * message.
     *
     * @param message the detail message
     */
    public InvalidKeyLengthException(String message) {
        super(message + "\nHere are the valid key lengths:\n16 bytes = AES-128\n24 bytes = AES-192\n32 bytes = AES-256");
    }
}