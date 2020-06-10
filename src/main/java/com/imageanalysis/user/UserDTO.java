package com.imageanalysis.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDTO {

    private Long id;
    private String username;
    private String avatar;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String zipCode;
    private String city;
}
