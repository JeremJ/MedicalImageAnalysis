package com.imageanalysis.user.image;

import com.imageanalysis.image.ImageDTO;
import com.imageanalysis.image.ImageExtendedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserImageController {

    @GetMapping("/{userId}/medical-images")
    public ResponseEntity<List<ImageExtendedDTO>> getUserImages(@PathVariable Long userId) {
        //TODO implementation
        return ok(Collections.singletonList(new ImageExtendedDTO()));
    }

    @PostMapping("/{userId}/medical-images")
    public ResponseEntity<ImageExtendedDTO> createUserImage(@PathVariable Long userId,
                                                            @Valid ImageDTO imageDTO,
                                                            @RequestPart(value = "file", required = false) MultipartFile file) {
        //TODO implementation
        return status(CREATED).body(new ImageExtendedDTO());
    }

    @GetMapping("/{userId}/medical-images/{imageId}")
    public ResponseEntity<ImageExtendedDTO> createUserImage(@PathVariable Long userId, @PathVariable Long imageId) {
        //TODO implementation
        return ok(new ImageExtendedDTO());
    }

}
