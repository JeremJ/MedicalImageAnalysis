package com.imageanalysis.image;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public List<ImageExtendedDTO> getMedicalImages(Pageable pageable) {
        List<Image> images = imageRepository.findAll(pageable).getContent();
        return imageMapper.toImageExtendedDTO(images);
    }

    @Transactional
    public Image createMedicalImage(MultipartFile multipartFile) {
        Image image = imageMapper.multipartToImage(multipartFile);
        return imageRepository.save(image);
    }
}
