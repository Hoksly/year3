package com.fleet.services;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AuthService {
    private static AuthService instance;
    private static KeyPair keyPair;

    private AuthService() throws Exception {

    }


    private static void initKeys() throws NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException, IOException {
        // Read the private key from file
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(AuthService.class.getResource("/private_key.der").toURI()));
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);

        // Read the public key from file
        byte[] publicKeyBytes = Files.readAllBytes(Paths.get(AuthService.class.getResource("/public_key.der").toURI()));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);

        // Create the key pair
        keyPair = new KeyPair(publicKey, privateKey);
    }

    public static AuthService getInstance() throws Exception {
        if (instance == null) {
            try {
                instance = new AuthService();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return instance;
    }

    private static KeyPair getKeyPair() {
        if (keyPair == null) {
            try {
                initKeys();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return keyPair;
    }

    public PublicKey getPublicKey() {
        // Return the public key
        return getKeyPair().getPublic();
    }

    public String decryptMessage(byte[] encryptedMessage) throws Exception {
        // Decrypt the message with the private key
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, getKeyPair().getPrivate());
        byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessage);
        return new String(decryptedMessageBytes);
    }

    public static String decryptSymmetricKey(String encryptedKey) throws Exception {
        // Decode the Base64-encoded encrypted key
        byte[] encryptedKeyBytes = Base64.getDecoder().decode(encryptedKey);

        // Decrypt the key using RSA private key
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.DECRYPT_MODE, getKeyPair().getPrivate());
        byte[] decryptedKeyBytes = rsaCipher.doFinal(encryptedKeyBytes);

        return new String(decryptedKeyBytes);
    }

    public static String decryptData(String encryptedData, String encryptedKey) throws Exception {
        // Decrypt the key using RSA private key with PKCS1Padding
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.DECRYPT_MODE, getKeyPair().getPrivate());
        byte[] decryptedKeyBytes = rsaCipher.doFinal(Base64.getDecoder().decode(encryptedKey));

        // Convert the decrypted key bytes to a SecretKey object
        SecretKey symmetricKey = new SecretKeySpec(decryptedKeyBytes, 0, 16, "AES");

        // Decrypt the data using AES symmetric key
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, symmetricKey);
        byte[] decryptedDataBytes = aesCipher.doFinal(Base64.getDecoder().decode(encryptedData));

        return new String(decryptedDataBytes);
    }
}