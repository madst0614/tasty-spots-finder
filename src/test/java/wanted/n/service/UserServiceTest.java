package wanted.n.service;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import wanted.n.domain.User;
import wanted.n.dto.*;
import wanted.n.enums.UserRole;
import wanted.n.enums.UserStatus;
import wanted.n.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@DisplayName("사용자 서비스 테스트")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, authService, passwordEncoder);

   }

   @Test
   @DisplayName("sign-up 테스트")
   public void signUpTest(){
        //given
       UserSignUpRequestDTO dto =
               UserSignUpRequestDTO.builder()
                       .email("madst0614@gmail.com")
                       .password("4321")
                       .account("최승호")
                       .lat(123D)
                       .lon(321D)
                       .lunchServed(false)
                       .build();

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.ofNullable(null));

        //when
       userService.signUpUser(dto);

       //then
       verify(userRepository, times(1))
               .save(argThat(user ->
                       user.getAccount().equals(dto.getAccount()) &&
                               user.getEmail().equals(dto.getEmail())
               ));
   }

    @Test
    @DisplayName("sign-in 테스트")
    public void signInTest(){
        //given
        UserSignInRequestDTO dto =
                UserSignInRequestDTO.builder()
                        .email("madst0614@gmail.com")
                        .password("4321")
                                .build();
        User user = User.builder()
                .id(1L)
                .email(dto.getEmail())
                .password(dto.getPassword())
                .userStatus(UserStatus.UNVERIFIED)
                        .build();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(authService.signInAccessToken(any(User.class))).thenReturn(accessToken);
        when(authService.issueNewRefreshToken(any(Long.class))).thenReturn(refreshToken);

        //when
        UserSignInResponseDTO userSignInResponseDTO
                = userService.signInUser(dto);

        //then
        assertThat(userSignInResponseDTO.getAccessToken()).isEqualTo(accessToken);
        assertThat(userSignInResponseDTO.getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("updateUserLoc 테스트")
    public void updateLocTest(){
        //given
        UserLocUpdateRequestDTO dto =
                UserLocUpdateRequestDTO.builder()
                        .lat(123D)
                        .lon(321D)
                        .build();

        when(authService.getIdFromToken(any())).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(User.builder().lat(0D).lon(0D).build()));

        //when
        userService.updateUserLoc("token",dto);

        //then
        verify(userRepository, times(1))
                .save(argThat(user ->
                        user.getLat().equals(dto.getLat()) &&
                                user.getLon().equals(dto.getLon())
                ));
    }

    @Test
    @DisplayName("updateUserLunchServed 테스트")
    public void updateLunchTest(){
        //given
        UserLunchUpdateRequestDTO dto =
                UserLunchUpdateRequestDTO.builder()
                        .lunch_served(true)
                        .build();

        when(authService.getIdFromToken(any())).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(User.builder().lunch_served(false).build()));

        //when
        userService.updateUserLunchServed("token",dto);

        //then
        verify(userRepository, times(1))
                .save(argThat(user ->
                        user.getLunch_served().equals(dto.getLunch_served()))
                );
    }

    @Test
    @DisplayName("getUserInfo 테스트")
    public void getUserInfoTest(){
        //given
        UserSignInRequestDTO dto =
                UserSignInRequestDTO.builder()
                        .email("madst0614@gmail.com")
                        .password("4321")
                        .build();
        User user = User.builder()
                .id(1L)
                .email(dto.getEmail())
                .password(dto.getPassword())
                .userStatus(UserStatus.UNVERIFIED)
                .build();

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        when(authService.getIdFromToken(any())).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        //when
        Long id = userService.getUserInfo("").getId();

        //then
        assertThat(id).isEqualTo(user.getId());
    }
}

