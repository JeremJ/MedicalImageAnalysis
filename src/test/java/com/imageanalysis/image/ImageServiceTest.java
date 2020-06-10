package com.imageanalysis.image;

import com.imageanalysis.fixture.ImageFixture;
import com.imageanalysis.image.predict.PredictService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImageMapper imageMapper;
    @Mock
    private PredictService predictService;
    @InjectMocks
    private ImageService imageService;

    @Test
    public void shouldReturnImages() {
        //given
        Pageable pageable = PageRequest.of(0, 1);
        Image medicalImage = ImageFixture.createImage(1L).build();
        Image medicalImageWithDate = ImageFixture.createImage(2L)
                .date(Instant.now())
                .build();
        List<Image> medicalImages = List.of(medicalImage, medicalImageWithDate);
        Page<Image> imagePage = new PageImpl<>(medicalImages, pageable, 1);
        ExtendedImageDTO extendedImageDTO = ImageFixture.createExtendedImageDTO(1L).build();
        ExtendedImageDTO extendedImageDTOWithDate = ImageFixture.createExtendedImageDTO(1L)
                .date(Instant.now())
                .build();
        List<ExtendedImageDTO> extendedImages = List.of(extendedImageDTO, extendedImageDTOWithDate);

        //when
        when(imageRepository.findAll(pageable)).thenReturn(imagePage);
        when(imageMapper.toImageExtendedDTO(imagePage.getContent())).thenReturn(extendedImages);

        ImagePageDTO returnedImages = imageService.getMedicalImages(pageable);

        //then
        assertThat(returnedImages.getTotalPages()).isEqualTo(1);
        assertThat(returnedImages.getImages()).isEqualTo(extendedImages);
    }

    @Test
    public void shouldCreateImageWithPrediction() {
        //given
        final String FILE_NAME = "input.jpg";
        final String ORIGINAL_FILE_NAME = "input.jpg";
        final String CONTENT_TYPE = "image/jpeg";
        final byte[] CONTENT = null;
        final Double DISEASE_PROBABILITY = 0.9;
        Image image = ImageFixture.createImage(1L)
                .file(CONTENT)
                .fileName(FILE_NAME)
                .fileExtension(StringUtils.stripFilenameExtension(ORIGINAL_FILE_NAME))
                .build();

        MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CONTENT);

        //when
        when(imageMapper.multipartToImage(multipartFile)).thenReturn(image);
        when(predictService.createPrediction(multipartFile)).thenReturn(DISEASE_PROBABILITY);
        when(imageRepository.save(image)).thenReturn(image);

        Image returnedImage = imageService.createMedicalImage(multipartFile);

        //then
        assertThat(returnedImage.getDiseaseProbability()).isEqualTo(DISEASE_PROBABILITY);
    }

    @Test
    public void shouldDeleteImage() {
        //when
        imageService.deleteMedicalImage(1L);

        //then
        verify(imageRepository, times(1)).deleteById(1L);
    }
}
