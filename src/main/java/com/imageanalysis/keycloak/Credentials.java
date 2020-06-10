package com.imageanalysis.keycloak;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credentials {

    private String secret;
    private String username;
    private String password;
}
