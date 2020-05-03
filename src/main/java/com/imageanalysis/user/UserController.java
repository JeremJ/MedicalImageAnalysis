package com.imageanalysis.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

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

    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO user = userService.getCurrentUser();
        return ok(user);
    }

    @GetMapping
    public ResponseEntity<UserBasicPageDTO> getAllUsers(Pageable pageable) {
        UserBasicPageDTO userBasicDTOs = userService.getUsers(pageable);
        return ok(userBasicDTOs);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUser(userId, userUpdateDTO);
        return status(NO_CONTENT).build();
    }
}
