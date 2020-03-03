package com.imageanalysis.user.role;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Role {
    ROLE_PATIENT, ROLE_ADMIN, ROLE_DOCTOR;

    public static List<String> getRoleNames() {
        return Stream.of(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
