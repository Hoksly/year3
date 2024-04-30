package com.fleet.resolvers;

import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.KeycloakConfigResolver;

import java.io.InputStream;

public class CustomKeycloakConfigResolver implements KeycloakConfigResolver {

    private KeycloakDeployment keycloakDeployment;

    public CustomKeycloakConfigResolver() {
        getClass().getResourceAsStream("/keycloack.json");
        try (InputStream is = getClass().getResourceAsStream("/keycloack.json")) {
            keycloakDeployment = KeycloakDeploymentBuilder.build(is);
        } catch (Exception e) {
            throw new RuntimeException("Could not load keycloak.json", e);

        }
    }

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
        return keycloakDeployment;
    }
}