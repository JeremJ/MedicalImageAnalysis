package com.imageanalysis.image;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medical-images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<ImagePageDTO> getMedicalImages(Pageable pageable, Authentication authentication) {
        ImagePageDTO images = imageService.getMedicalImages(pageable);
        return ok().body(images);
    }

}
