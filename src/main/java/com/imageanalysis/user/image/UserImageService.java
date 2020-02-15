package com.imageanalysis.user.image;

import com.imageanalysis.image.Image;
import com.imageanalysis.image.ImageDTO;
import com.imageanalysis.image.ImageService;
import com.imageanalysis.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserImageService {

    private final UserImageRepository userImageRepository;
    private final ImageService imageService;
    private final UserImageMapper userImageMapper;
    private final UserService userService;

    public List<UserImageDTO> getUserImages(Long userId, Pageable pageable) {
        return userImageMapper
                .toUserImageDTOs(userImageRepository.findAllByUserId(userId, pageable));
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
