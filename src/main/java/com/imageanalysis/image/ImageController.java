package com.imageanalysis.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medical-images")
public class ImageController {

    @GetMapping
    public ResponseEntity<List<ImageExtendedDTO>> getMedicalImages() {
        //TODO implementation
        return status(CREATED).body(Collections.singletonList(new ImageExtendedDTO()));
    }

}
