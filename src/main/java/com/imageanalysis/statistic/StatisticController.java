package com.imageanalysis.statistic;

import com.imageanalysis.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticController {

    private final ImageService imageService;

    @GetMapping("/images")
    public ResponseEntity<List<ImageCount>> getMedicalImagesByDate() {
        List<ImageCount> imageCount = imageService.countImagesByDay();
        return ok().body(imageCount);
    }
}
