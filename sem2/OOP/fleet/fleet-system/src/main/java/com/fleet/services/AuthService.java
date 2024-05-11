package com.fleet.services;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class AuthService {
    private static AuthService instance;
    private static KeyPair keyPair;
    private static final String ALGORITHM = "AES";
    private static Key AESKey;

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


        public String encryptAES(final String valueEnc, final String secKey) {
            String encryptedVal = null;

            try {
                final Key key = generateKeyFromString(secKey);
                final Cipher c = Cipher.getInstance(ALGORITHM);
                c.init(Cipher.ENCRYPT_MODE, key);
                final byte[] encValue = c.doFinal(valueEnc.getBytes());
                encryptedVal = Base64.getEncoder().encodeToString(encValue);
            } catch(Exception ex) {
                System.out.println("The Exception is=" + ex);
            }

            return encryptedVal;
        }

        public String decryptAES(final String encryptedValue) {
            String decryptedValue = null;

            try {

                final Cipher c = Cipher.getInstance(ALGORITHM);
                c.init(Cipher.DECRYPT_MODE, AESKey);
                final byte[] decorVal = Base64.getDecoder().decode(encryptedValue);
                final byte[] decValue = c.doFinal(decorVal);
                decryptedValue = new String(decValue);
            } catch(Exception ex) {
                System.out.println("The Exception is=" + ex);
            }

            return decryptedValue;
        }

        private Key generateKeyFromString(final String secKey) throws Exception {
            final byte[] keyVal = Base64.getDecoder().decode(secKey);
            final Key key = new SecretKeySpec(keyVal, ALGORITHM);
            return key;
        }

    public static String generateStringFromKey(final Key key) {
        StringBuilder sb;
        sb = new StringBuilder();
        for(byte b : key.getEncoded()) {
            sb.append(b);
            sb.append(" ");
        }
        return sb.toString();
    }

        public String getAESKey() throws NoSuchAlgorithmException {
            // Generate a new AES key
          if (AESKey == null) {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            AESKey = keyGen.generateKey();
          }
          return generateStringFromKey(AESKey);
        }
}
