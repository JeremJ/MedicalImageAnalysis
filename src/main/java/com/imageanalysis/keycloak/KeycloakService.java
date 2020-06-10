package com.imageanalysis.keycloak;

import com.imageanalysis.user.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class KeycloakService {
    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;
    private final KeycloakMapper keycloakMapper;

    @Transactional
    public void updateKeycloakUser(User user) {
        var currentUser = keycloak.realm(keycloakConfig.getRealm()).users().get(user.getKeycloakId());
        var userToBeUpdated = keycloakMapper.updateUserRepresentationFromUser(user, currentUser.toRepresentation());
        currentUser.update(userToBeUpdated);
    }
}
