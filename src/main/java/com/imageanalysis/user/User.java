package com.imageanalysis.user;

import com.imageanalysis.user.image.UserImage;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "user", schema = "public")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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
    private String keycloakId;
    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private Set<UserImage> images = new HashSet<>();

    public User(String username, String firstName, String lastName, String email, String keycloakId) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.keycloakId = keycloakId;
    }
}
