package com.imageanalysis.user;

import com.imageanalysis.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private Role role;
    private String avatar;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String zipCode;
    private String city;
}
