package com.imageanalysis.user.image;

import com.imageanalysis.image.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserImageController {

    private final UserImageService userImageService;

    @GetMapping("/{userId}/medical-images")
    public ResponseEntity<List<UserImageDTO>> getUserImages(@PathVariable Long userId, Pageable pageable) {
        List<UserImageDTO> userImages = userImageService.getUserImages(userId, pageable);
        return ok(userImages);
    }

    @PostMapping("/{userId}/medical-images")
    public ResponseEntity<Void> createUserImage(@PathVariable Long userId,
                                                @Valid ImageDTO imageDTO,
                                                @RequestPart(value = "file") MultipartFile file) {
        userImageService.createUserImage(userId, imageDTO, file);
        return status(CREATED).build();
    }

    @GetMapping("/{userId}/medical-images/{imageId}")
    public ResponseEntity<UserImageDTO> createUserImage(@PathVariable Long userId, @PathVariable Long imageId) {
        UserImageDTO userImageDTO = userImageService.getUserImage(userId, imageId);
        return ok(userImageDTO);
    }

}
