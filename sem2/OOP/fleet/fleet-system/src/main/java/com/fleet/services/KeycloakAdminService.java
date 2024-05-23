package com.fleet.services;

import com.fleet.models.UserRegistrationModel;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.*;
import java.util.stream.Collectors;

public class KeycloakAdminService {

     private String serverUrl = "http://localhost:8079";

      private String realm = "fleet-realm";

     private String clientId = "fleet-web";

     private String adminUsername = "test";
     private String adminPassword = "test";


    private final Map<String, List<String>> roleMapping = new HashMap<>();

    public KeycloakAdminService(){}

    public UserRepresentation createKeycloakUser(UserRegistrationModel user) {

        this.roleMapping.put(this.clientId, Arrays.asList("client_user"));

        Keycloak adminKeycloak = getAdminKeycloak();
        CredentialRepresentation cr = new CredentialRepresentation();
        cr.setType(OAuth2Constants.PASSWORD);
        cr.setValue(user.getPassword());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setClientRoles(roleMapping);
        userRepresentation.setCredentials(Collections.singletonList(cr));
        userRepresentation.setEnabled(true);

        adminKeycloak.realm(realm).users().create(userRepresentation);
        List<UserRepresentation> userList = adminKeycloak.realm(realm).users().search(user.getUsername()).stream()
                .filter(userRep -> userRep.getUsername().equals(user.getUsername())).collect(Collectors.toList());
        userRepresentation = userList.get(0);


        this.assignRoleToUser(userRepresentation.getId(), "client_user");

        return userRepresentation;
    }

    private void assignRoleToUser(String userId, String role) {
        Keycloak keycloak = getAdminKeycloak();
        UsersResource usersResource = keycloak.realm(realm).users();
        UserResource userResource = usersResource.get(userId);

        //getting client
        ClientRepresentation clientRepresentation = keycloak.realm(realm).clients().findAll().stream().filter(client -> client.getClientId().equals(clientId)).collect(Collectors.toList()).get(0);
        ClientResource clientResource = keycloak.realm(realm).clients().get(clientRepresentation.getId());
        //getting role
        RoleRepresentation roleRepresentation = clientResource.roles().list().stream().filter(element -> element.getName().equals(role)).collect(Collectors.toList()).get(0);
        //assigning to user
        userResource.roles().clientLevel(clientRepresentation.getId()).add(Collections.singletonList(roleRepresentation));
    }

    private Keycloak getAdminKeycloak() {
        return KeycloakBuilder.builder().serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .username(adminUsername)
                .password(adminPassword)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

    public void deleteKeycloakUser(UserRegistrationModel student) {
        Keycloak keycloak = getAdminKeycloak();
        List<UserRepresentation> userList = keycloak.realm(realm).users().search(student.getUsername());
        for (UserRepresentation user : userList) {
            if (user.getUsername().equals(student.getUsername())) {
                keycloak.realm(realm).users().delete(user.getId());
            }
        }
    }

}