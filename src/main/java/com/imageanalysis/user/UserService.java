package com.imageanalysis.user;

import com.imageanalysis.exception.NotFoundException;
import com.imageanalysis.image.Image;
import com.imageanalysis.security.SecurityUtil;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

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

    @Transactional
    public String getCurrentUser() {
        Authentication authentication = getContext().getAuthentication();
        AccessToken currentUserToken = ((SimpleKeycloakAccount) authentication.getDetails()).getKeycloakSecurityContext().getToken();
        currentUserToken.getGivenName();
        currentUserToken.getFamilyName();
        return authentication.getName();
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
