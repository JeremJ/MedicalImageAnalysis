package com.imageanalysis.user;

import com.imageanalysis.exception.NotFoundException;
import com.imageanalysis.image.Image;
import com.imageanalysis.user.image.UserImage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toUserDTO(user);
    }

    public UserBasicPageDTO getUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserBasicDTO> userBasics = userMapper.toUserBasicDTOs(usersPage.getContent());
        return new UserBasicPageDTO(usersPage.getTotalElements(), userBasics);
    }

    @Transactional
    public User addUserImage(Long userId, Image image, String description) {
        User user = userRepository
                .findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.getImages().add(new UserImage(user, image, description));
        return userRepository.save(user);
    }
}
