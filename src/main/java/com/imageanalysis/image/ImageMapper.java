package com.imageanalysis.image;

import com.imageanalysis.exception.ProcessingFileException;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Base64.getEncoder;
import static java.util.Objects.requireNonNull;
import static org.mapstruct.ReportingPolicy.IGNORE;
import static org.springframework.util.StringUtils.getFilenameExtension;
import static org.springframework.util.StringUtils.stripFilenameExtension;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface ImageMapper {

    default List<ImageExtendedDTO> toImageExtendedDTO(List<Image> images) {
        List<ImageExtendedDTO> imageExtendedDTOs = new ArrayList<>();

        images.forEach(image -> {
            imageExtendedDTOs.add(new ImageExtendedDTO(
                    image.getId(),
                    image.getFileName(),
                    image.getDate(),
                    image.getSize(),
                    image.getFileExtension(),
                    byteArrayToString(image.getFile()),
                    image.getDiseaseProbability()
            ));
        });
        return imageExtendedDTOs;
    }

    default Image multipartToImage(MultipartFile multipartFile) {
        try {
            return new Image(multipartFile.getBytes(),
                    stripFilenameExtension(requireNonNull(multipartFile.getOriginalFilename())),
                    multipartFile.getSize(),
                    getFilenameExtension(multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new ProcessingFileException("Error encountered when processing File");
        }
    }

    default String byteArrayToString(byte[] value) {
        if (value == null) {
            return null;
        } else
            return getEncoder().encodeToString(value);
    }
}
