package com.imageanalysis.user;

import com.imageanalysis.exception.NotFoundException;
import com.imageanalysis.fixture.ImageFixture;
import com.imageanalysis.fixture.UserFixture;
import com.imageanalysis.image.Image;
import com.imageanalysis.keycloak.KeycloakService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private KeycloakService keycloakService;
    @InjectMocks
    private UserService userService;

    @Test
    public void shouldReturnUsers() {
        //given
        final String USERNAME = "janusz";
        Pageable pageable = PageRequest.of(0, 1);
        User user = UserFixture.createUser(1L).build();
        User userWithUsername = UserFixture.createUser(2L)
                .username(USERNAME)
                .build();
        List<User> users = List.of(user, userWithUsername);
        Page<User> usersPage = new PageImpl<>(users, pageable, users.size());
        UserBasicDTO userBasicDTO = UserFixture.createUserBasicDTO(1L).build();
        UserBasicDTO userBasicDTOWithDate = UserFixture.createUserBasicDTO(1L)
                .username(USERNAME)
                .build();
        List<UserBasicDTO> usersBasic = List.of(userBasicDTO, userBasicDTOWithDate);

        //when
        when(userRepository.findAll(pageable)).thenReturn(usersPage);
        when(userMapper.toUserBasicDTOs(usersPage.getContent())).thenReturn(usersBasic);

        UserBasicPageDTO returnedUsers = userService.getUsers(pageable);

        //then
        assertThat(returnedUsers.getTotalElements()).isEqualTo(2);
        assertThat(returnedUsers.getUsers()).isEqualTo(usersBasic);
    }

    @Test
    public void shouldCreateUserImageWithPrediction() {
        //given
        final String USERNAME = "janusz";
        final Long USER_ID = 2L;
        final String FILE_NAME = "input.jpg";
        final String ORIGINAL_FILE_NAME = "input.jpg";
        final String DESCRIPTION = "test";
        final byte[] CONTENT = null;

        Image image = ImageFixture.createImage(1L)
                .file(CONTENT)
                .fileName(FILE_NAME)
                .fileExtension(StringUtils.stripFilenameExtension(ORIGINAL_FILE_NAME))
                .build();
        User user = UserFixture.createUser(USER_ID)
                .username(USERNAME)
                .images(new HashSet<>())
                .build();
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(any(User.class));

        userService.addUserImage(USER_ID, image, DESCRIPTION);

        //then
        verify(userRepository).save(argument.capture());
        assertThat(argument.getValue().getImages()).isNotEmpty();
    }

    @Test
    public void shouldThrowExceptionWhenAddImageToNotExistingUser() {
        //given
        final Long USER_ID = 2L;
        final String FILE_NAME = "input.jpg";
        final String ORIGINAL_FILE_NAME = "input.jpg";
        final String DESCRIPTION = "test";
        final byte[] CONTENT = null;

        Image image = ImageFixture.createImage(1L)
                .file(CONTENT)
                .fileName(FILE_NAME)
                .fileExtension(StringUtils.stripFilenameExtension(ORIGINAL_FILE_NAME))
                .build();

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> userService.addUserImage(USER_ID, image, DESCRIPTION));

        // then
        assertThat(throwable)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    public void shouldReturnUser() {
        //given
        final Long USER_ID = 1L;
        final String USERNAME = "test";
        User user = UserFixture.createUser(USER_ID)
                .username(USERNAME)
                .build();
        UserDTO userDTO = UserFixture.createUserDTO(USER_ID)
                .username(USERNAME)
                .build();

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO returnedUser = userService.getUser(USER_ID);

        //then
        assertThat(returnedUser.getId()).isEqualTo(USER_ID);
        assertThat(returnedUser.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    public void shouldUpdateUser() {
        //given
        final Long USER_ID = 1L;
        final String USERNAME = "test";
        final String CITY = "WARSZAWA";
        final String FIRST_NAME = "JANUSZ";
        User userToBeUpdated = UserFixture.createUser(USER_ID)
                .username(USERNAME)
                .build();
        UserUpdateDTO updatedUserData = UserFixture.createUserUpdateDTO()
                .city(CITY)
                .firstName(FIRST_NAME)
                .build();
        User updatedUser = UserFixture.createUser(USER_ID)
                .username(USERNAME)
                .city(CITY)
                .firstName(FIRST_NAME)
                .build();

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(userToBeUpdated));
        when(userMapper.updateUser(updatedUserData, userToBeUpdated)).thenReturn(updatedUser);

        userService.updateUser(USER_ID, updatedUserData);

        //then
        verify(userRepository).saveAndFlush(updatedUser);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenTryingToUpdateNotExistingUser() {
        //given
        final Long USER_ID = 1L;
        final String CITY = "WARSZAWA";
        final String FIRST_NAME = "JANUSZ";
        UserUpdateDTO updatedUserData = UserFixture.createUserUpdateDTO()
                .city(CITY)
                .firstName(FIRST_NAME)
                .build();

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> userService.updateUser(USER_ID, updatedUserData));

        // then
        assertThat(throwable)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");

    }

    @Test
    public void shouldThrowNotFoundExceptionWhenTryingToFetchNotExistingUser() {
        //given
        final Long USER_ID = 1L;

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> userService.getUser(USER_ID));

        // then
        assertThat(throwable)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
    }
}
