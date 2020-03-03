package com.imageanalysis.image;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medical-images")
public class ImageController {

    private final ImageService imageService;

    //((SimpleKeycloakAccount) authentication.getDetails()).getKeycloakSecurityContext().getToken().getEmail()
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImagePageDTO> getMedicalImages(Pageable pageable, Authentication authentication) {
        ImagePageDTO images = imageService.getMedicalImages(pageable);
        return status(CREATED).body(images);
    }

}
