package com.fleet.services;

import javax.crypto.Cipher;
import java.security.*;

public class AuthService {
    private KeyPair keyPair;

    public AuthService() throws NoSuchAlgorithmException {
        // Generate a new key pair when the service is instantiated
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        keyPair = keyGen.generateKeyPair();
    }

    public PublicKey getPublicKey() {
        // Return the public key
        return keyPair.getPublic();
    }

    public String decryptMessage(byte[] encryptedMessage) throws Exception {
        // Decrypt the message with the private key
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
        return new String(decryptedMessage);
    }
}