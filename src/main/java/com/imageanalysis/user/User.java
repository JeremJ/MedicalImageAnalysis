package com.imageanalysis.user;

import com.imageanalysis.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    @Enumerated(STRING)
    private Role role;
    private byte[] avatar;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String zipCode;
    private String city;
}
