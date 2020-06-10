package com.imageanalysis.fixture;

import com.imageanalysis.user.User;
import com.imageanalysis.user.UserBasicDTO;
import com.imageanalysis.user.UserDTO;
import com.imageanalysis.user.UserUpdateDTO;

public class UserFixture {

    public static User.UserBuilder createUser(Long userId) {
        return User.builder()
                .id(userId);
    }

    public static UserDTO.UserDTOBuilder createUserDTO(Long userId) {
        return UserDTO.builder()
                .id(userId);
    }

    public static UserBasicDTO.UserBasicDTOBuilder createUserBasicDTO(Long userId) {
        return UserBasicDTO.builder()
                .id(userId);
    }

    public static UserUpdateDTO.UserUpdateDTOBuilder createUserUpdateDTO() {
        return UserUpdateDTO.builder();
    }
}
