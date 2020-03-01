package com.imageanalysis.image;

import com.imageanalysis.image.predict.PredictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final PredictService predictService;

    public ImagePageDTO getMedicalImages(Pageable pageable) {
        Page<Image> imagesPage = imageRepository.findAll(pageable);
        List<ImageExtendedDTO> images = imageMapper.toImageExtendedDTO(imagesPage.getContent());
        return new ImagePageDTO(imagesPage.getTotalPages(), images);
    }

    @Transactional
    public Image createMedicalImage(MultipartFile multipartFile) {
        Image image = imageMapper.multipartToImage(multipartFile);
        Double prediction = predictService.invokePredictionApi(multipartFile);
        image.setDiseaseProbability(prediction);
        return imageRepository.save(image);
    }
}
