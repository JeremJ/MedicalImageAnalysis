package com.imageanalysis.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        UserDTO user = userService.getUser(userId);
        return ok(user);
    }

    @GetMapping
    public ResponseEntity<UserBasicPageDTO> getAllUsers(Pageable pageable) {
        UserBasicPageDTO userBasicDTOs = userService.getUsers(pageable);
        return ok(userBasicDTOs);
    }
}
