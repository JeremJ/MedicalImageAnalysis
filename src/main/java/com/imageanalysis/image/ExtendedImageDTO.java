package com.imageanalysis.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExtendedImageDTO {

    private Long id;
    private String fileName;
    private Instant date;
    private Long size;
    private String fileExtension;
    private String file;
    private Double diseaseProbability;
}
