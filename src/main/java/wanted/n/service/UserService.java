package wanted.n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.n.domain.User;
import wanted.n.dto.*;
import wanted.n.exception.CustomException;
import wanted.n.repository.UserRepository;

import static wanted.n.enums.UserStatus.*;
import static wanted.n.exception.ErrorCode.*;
import static wanted.n.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 회원가입을 처리하는 메서드
     *  email 중복 체크 하여 중복 시 이메일 중복 exception
     */
    @Transactional
    public void signUpUser(UserSignUpRequestDTO userSignUpRequestDTO) {

        String password = userSignUpRequestDTO.getPassword();

        // 계정 중복 체크
        userRepository.findByEmail(userSignUpRequestDTO.getEmail())
                .ifPresent(user -> {
                    throw new CustomException(DUPLICATED_EMAIL);
                });

        // 비밀번호를 암호화하여 저장
        userSignUpRequestDTO.setPassword(passwordEncoder.encode(password));
        userRepository.save(User.from(userSignUpRequestDTO));
    }

    /**
     * 사용자 로그인 처리를 하는 메서드
     *  비밀번호를 비교하고 액세스 토큰 및 리프레시 토큰을 생성하여 사용자 정보를 반환합니다.
     */
    public UserSignInResponseDTO signInUser(UserSignInRequestDTO signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (user.getUserStatus().equals(DELETED)) {
            throw new CustomException(USER_DELETED);
        }

        isPasswordMatch(signInRequest.getPassword(), user.getPassword());

        String accessToken = authService.signInAccessToken(user).getAccessToken();
        String refreshToken = authService.issueNewRefreshToken(TokenUserInfoDTO.builder().id(user.getId()).build()).getRefreshToken();

        return UserSignInResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 사용자 로그아웃 처리를 하는 메서드
     *  토큰으로부터 id를 추출하여 refresh 토큰을 제거합니다
     */
    public void signOutUser(UserSignOutRequestDTO userSignOutRequestDTO) {
        Long id = authService.getIdFromToken
                        (AccessTokenDTO.builder().accessToken(userSignOutRequestDTO.getToken()).build());

        authService.deleteRefreshToken(TokenUserInfoDTO.builder().id(id).build());
    }

    /**
     * 사용자 위치 업데이트를 하는 메서드
     *  토큰으로부터 id를 추출하여 UserRepo에 접근하여 위치 정보를 업데이트 합니다.
     */
    public void updateUserLoc(String token, UserLocUpdateRequestDTO userLocUpdateRequestDTO) {
        Long id = getIdFromToken(token);

        User user = userRepository.findById(id).orElseThrow(()-> new CustomException(USER_NOT_FOUND));
        user = User.builder()
                .id(user.getId())
                .account(user.getAccount())
                .email(user.getEmail())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus())
                .lat(userLocUpdateRequestDTO.getLat())
                .lon(userLocUpdateRequestDTO.getLon())
                .lunch_served(user.getLunch_served())
                .build();

        userRepository.save(user);
    }

    /**
     * 사용자 런치 제공 여부 업데이트를 하는 메서드
     *  토큰으로부터 id를 추출하여 UserRepo에 접근하여 런치 제공 여부를 업데이트 합니다.
     */
    public void updateUserLunchServed(String token, UserLunchUpdateRequestDTO userLunchUpdateRequestDTO) {
        Long id = getIdFromToken(token);

        User user = userRepository.findById(id).orElseThrow(()-> new CustomException(USER_NOT_FOUND));
        user = User.builder()
                .id(user.getId())
                .account(user.getAccount())
                .email(user.getEmail())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus())
                .lat(user.getLat())
                .lon(user.getLon())
                .lunch_served(userLunchUpdateRequestDTO.getLunch_served())
                .build();

        userRepository.save(user);
    }

    /**
     * 사용자 정보를 가져오는 메서드
     *  토큰으로부터 id를 추출하여 UserRepo에 접근하여 UserInfo를 가져옵니다.
     */
    public UserInfoResponseDTO getUserInfo(String token) {
        Long id = getIdFromToken(token);

        return UserInfoResponseDTO.from(userRepository.findById(id)
                .orElseThrow(()-> new CustomException(USER_NOT_FOUND)));
    }

    // 비밀번호 일치 여부를 확인하는 메소드
    private void isPasswordMatch(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }
    }

    // 토큰으로부터 id 정보를 가져오는 메소드
    private Long getIdFromToken(String token){
        return authService.getIdFromToken
                (AccessTokenDTO.builder().accessToken(token).build());
    }
}
