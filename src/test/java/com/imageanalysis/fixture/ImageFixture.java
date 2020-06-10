package com.imageanalysis.fixture;

import com.imageanalysis.image.ExtendedImageDTO;
import com.imageanalysis.image.Image;
import com.imageanalysis.image.ImageDTO;

public class ImageFixture {

    public static Image.ImageBuilder createImage(Long imageId) {
        return Image.builder()
                .id(imageId);
    }

    public static ImageDTO.ImageDTOBuilder createImageDTO(String description) {
        return ImageDTO.builder()
                .description(description);
    }

    public static ExtendedImageDTO.ExtendedImageDTOBuilder createExtendedImageDTO(Long imageId) {
        return ExtendedImageDTO.builder()
                .id(imageId);
    }
}
