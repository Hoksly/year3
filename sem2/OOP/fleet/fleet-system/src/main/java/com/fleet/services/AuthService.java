package com.fleet.services;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AuthService {
    public void validateJwt(String jwt, PublicKey publicKey) throws VerificationException {
        TokenVerifier<AccessToken> verifier = TokenVerifier.create(jwt, AccessToken.class).withChecks(TokenVerifier.IS_ACTIVE);
        verifier.publicKey(publicKey);
        AccessToken token = verifier.verify().getToken();
    }

    public PublicKey getPublicKeyFromKeycloak() throws IOException {
        URL url = new URL("http://localhost:8079/realms/fleet-realm");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = jsonReader.readObject();
        String base64PublicKey = jsonObject.getString("public_key");
        try{
            byte[] byteKey = Base64.getDecoder().decode(base64PublicKey.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(X509publicKey);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}