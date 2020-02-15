package com.imageanalysis.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserDTO>> getUser(@PathVariable Long userId) {
        //TODO implementation
        return ok(Collections.singletonList(new UserDTO()));
    }

    @GetMapping
    public ResponseEntity<List<UserBasicDTO>> getAllUsers() {
        //TODO implementation
        return ok(Collections.singletonList(new UserBasicDTO()));
    }
}
