package edu.miracosta.cs112.cryptographyapplication;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Aes class that extends CryptographyMethod to provide encryption and decryption
 * using the Aes algorithm.
 *
 * <p>
 * This class implements the encrypt and decrypt methods using the Aes encryption
 * algorithm. It also integrates a custom exception for invalid key lengths.
 * </p>
 *
 * <p>
 * Supported key lengths: 128, 192, 256 bits.
 * </p>
 *
 * @see CryptographyMethod
 * @see InvalidKeyLengthException
 *
 * @version 1.0
 */
public class Aes extends CryptographyMethod {

    private static final int[] VALID_KEY_LENGTHS = { 128, 192, 256 };
    private SecretKey secretKey;
    private IvParameterSpec iv;

    /**
     * Constructs a new {@code Aes} object with the specified input and output modes,
     * the provided key, and the provided IV.
     *
     * @param inputMode  the input mode
     * @param outputMode the output mode
     * @param key        the secret key
     * @param iv         the initialization vector
     * @throws InvalidKeyLengthException if the key length is not valid
     */
    public Aes(int inputMode, int outputMode, byte[] key, byte[] iv) throws InvalidKeyLengthException {
        super(inputMode, outputMode);
        setKey(key);
        setIv(iv);
    }

    /**
     * Constructs a new {@code Aes} object with the default input and output modes,
     * the provided key, and a randomly generated IV.
     *
     * @param key the secret key
     * @throws InvalidKeyLengthException if the key length is not valid
     */
    public Aes(byte[] key) throws InvalidKeyLengthException {
        this(DEFAULT_INPUT_MODE, DEFAULT_OUTPUT_MODE, key, generateIv());
    }

    /**
     * Constructs a new {@code Aes} object with the default input and output modes,
     * a randomly generated key of 128 bits, and a randomly generated IV.
     *
     * @throws InvalidKeyLengthException if the key generation fails
     */
    public Aes() throws InvalidKeyLengthException {
        this(DEFAULT_INPUT_MODE, DEFAULT_OUTPUT_MODE, generateKey(128), generateIv());
    }

    /**
     * Sets the secret key for the Aes algorithm.
     *
     * @param key the secret key
     * @throws InvalidKeyLengthException if the key length is not valid
     */
    public void setKey(byte[] key) throws InvalidKeyLengthException {
        if (!isValidKeyLength(key.length * 8)) {
            throw new InvalidKeyLengthException("Invalid Aes key length: " + (key.length * 8));
        }
        this.secretKey = new SecretKeySpec(key, "Aes");
    }

    /**
     * Sets the initialization vector (IV) for the Aes algorithm.
     *
     * @param iv the initialization vector
     */
    public void setIv(byte[] iv) {
        if (iv.length != 16) {
            throw new IllegalArgumentException("Invalid IV length: " + iv.length + ". IV must be 16 bytes long.");
        }
        this.iv = new IvParameterSpec(iv);
    }

    /**
     * Checks if the provided key length is valid for Aes.
     *
     * @param keyLength the key length in bits
     * @return {@code true} if the key length is valid, {@code false} otherwise
     */
    public boolean isValidKeyLength(int keyLength) {
        return Arrays.stream(VALID_KEY_LENGTHS).anyMatch(length -> length == keyLength);
    }

    /**
     * Generates a random key for Aes with the specified length.
     *
     * @param keyLength the length of the key in bits
     * @return the generated key
     * @throws InvalidKeyLengthException if the key length is not valid
     */
    public static byte[] generateKey(int keyLength) throws InvalidKeyLengthException {
        if (Arrays.stream(VALID_KEY_LENGTHS).noneMatch(length -> length == keyLength)) {
            throw new InvalidKeyLengthException("Invalid Aes key length: " + keyLength);
        }
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("Aes");
            keyGen.init(keyLength);
            return keyGen.generateKey().getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Key generation failed", e);
        }
    }

    /**
     * Generates a random IV for Aes.
     *
     * @return the generated IV
     */
    public static byte[] generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    @Override
    public String encrypt(String plaintext) {
        plaintext = decodeInput(plaintext);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    @Override
    public String decrypt(String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance("Aes/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return encodeOutput(new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext))));
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }

    /**
     * Returns a string representation of the Aes object.
     *
     * @return a string representation of the Aes object
     */
    @Override
    public String toString() {
        String inputOutput = super.toString();
        return "Aes:\n" + inputOutput.substring(inputOutput.indexOf(':') + 2) + "\nSecret Key: " + Arrays.toString(this.secretKey.getEncoded()) + "\nIV: " + Arrays.toString(this.iv.getIV());
    }

    /**
     * Compares this Aes instance with another object for equality.
     *
     * @param other the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Aes otherAes)) {
            return false;
        }
        return super.equals(otherAes) && Arrays.equals(this.secretKey.getEncoded(), otherAes.secretKey.getEncoded()) &&
                Arrays.equals(this.iv.getIV(), otherAes.iv.getIV());
    }
}