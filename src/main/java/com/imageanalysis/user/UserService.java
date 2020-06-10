package com.imageanalysis.user;

import com.imageanalysis.config.SecurityUtil;
import com.imageanalysis.exception.NotFoundException;
import com.imageanalysis.image.Image;
import com.imageanalysis.keycloak.KeycloakService;
import com.imageanalysis.user.image.UserImage;
import com.imageanalysis.user.role.Role;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final KeycloakService keycloakService;

    public UserDTO getUser(Long userId) {
        User user = findUser(userId);
        return userMapper.toUserDTO(user);
    }

    public UserBasicPageDTO getUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserBasicDTO> userBasics = userMapper.toUserBasicDTOs(usersPage.getContent());
        return new UserBasicPageDTO(usersPage.getTotalElements(), userBasics);
    }

    @Transactional
    public User addUserImage(Long userId, Image image, String description) {
        User user = findUser(userId);
        user.getImages().add(new UserImage(user, image, description));
        return userRepository.save(user);
    }

    @Transactional
    public UserDTO getCurrentUser() {
        Authentication authentication = getContext().getAuthentication();
        AccessToken currentUserToken = ((SimpleKeycloakAccount) authentication.getDetails()).getKeycloakSecurityContext().getToken();
        User currentUser = userRepository.findByUsername(currentUserToken.getPreferredUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toUserDTO(currentUser);
    }

    @Transactional
    public boolean createUserIfNotExists(Authentication authentication) {
        AccessToken accessToken = SecurityUtil.getCurrentAuthenticateToken(authentication);
        boolean isUserExists = existsByUsername(accessToken);
        if (!isUserExists) {
            createCurrentUser(accessToken);
        }
        return isUserExists;
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        var currentUser = findUser(userId);
        var updatedUser = userMapper.updateUser(userUpdateDTO, currentUser);
        keycloakService.updateKeycloakUser(updatedUser);
        userRepository.saveAndFlush(updatedUser);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private boolean existsByUsername(AccessToken accessToken) {
        return userRepository.existsByUsername(accessToken.getPreferredUsername());
    }

    private void createCurrentUser(AccessToken accessToken) {
        var authorities = getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toCollection(ArrayList::new));
        authorities.retainAll(Role.getRoleNames());
        User user = userMapper.accessTokenToUser(accessToken);
        userRepository.save(user);
    }

}
