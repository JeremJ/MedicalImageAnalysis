package com.imageanalysis.user.image;

import com.imageanalysis.image.Image;
import com.imageanalysis.image.ImageDTO;
import com.imageanalysis.image.ImageService;
import com.imageanalysis.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserImageService {

    private final UserImageRepository userImageRepository;
    private final ImageService imageService;
    private final UserImageMapper userImageMapper;
    private final UserService userService;

    public UserImagePageDTO getUserImages(Long userId, Pageable pageable) {
        Page<UserImage> imagePage = userImageRepository.findAllByUserId(userId, pageable);
        List<UserImageDTO> images = userImageMapper.toUserImageDTOs(imagePage.getContent());
        return new UserImagePageDTO(imagePage.getTotalPages(), images);
    }

    @Transactional
    public void createUserImage(Long userId, ImageDTO imageDTO, MultipartFile multipartFile) {
        String description = imageDTO.getDescription();
        Image image = imageService.createMedicalImage(multipartFile);
        userService.addUserImage(userId, image, description);
    }

    public UserImageDTO getUserImage(Long userId, Long imageId) {
        UserImage userImage = userImageRepository.findByIdAndUserId(imageId, userId);
        return userImageMapper.toUserImageDTO(userImage);
    }
}
