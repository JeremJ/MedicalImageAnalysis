package com.imageanalysis.keycloak;

import com.imageanalysis.user.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface KeycloakMapper {

    default UserRepresentation updateUserRepresentationFromUser(User user, UserRepresentation userRepresentation) {
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        return userRepresentation;
    }
}
