package com.imageanalysis.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePageDTO {
    private Integer totalPages;
    private List<ExtendedImageDTO> images;
}
