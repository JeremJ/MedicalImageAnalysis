package com.imageanalysis.user;

import com.imageanalysis.user.image.UserImage;
import com.imageanalysis.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "user", schema = "public")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
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
    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private Set<UserImage> images = new HashSet<>();
}
