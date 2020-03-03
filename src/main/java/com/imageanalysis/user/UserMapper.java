package com.imageanalysis.user;

import org.keycloak.representations.AccessToken;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

import static java.util.Base64.getEncoder;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface UserMapper {

    UserDTO toUserDTO(User user);

    default String byteArrayToString(byte[] value) {
        if (value == null) {
            return null;
        } else
            return getEncoder().encodeToString(value);
    }

    default List<UserBasicDTO> toUserBasicDTOs(List<User> users) {
        List<UserBasicDTO> userBasicDTOs = new ArrayList<>();
        users.forEach(user -> {
            userBasicDTOs.add(new UserBasicDTO(
                    user.getId(),
                    user.getUsername(),
                    byteArrayToString(user.getAvatar()),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getCountry(),
                    user.getCity()));
        });
        return userBasicDTOs;
    }

    default User accessTokenToUser(AccessToken accessToken) {
        return new User(accessToken.getPreferredUsername(),
                accessToken.getGivenName(),
                accessToken.getFamilyName(),
                accessToken.getEmail());
    }
}
