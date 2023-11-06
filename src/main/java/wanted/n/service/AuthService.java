package wanted.n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.n.config.provider.JwtProvider;
import wanted.n.domain.User;
import wanted.n.dto.*;
import wanted.n.exception.CustomException;
import wanted.n.exception.ErrorCode;
import wanted.n.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    /**
     *  Access Token 발급 메소드
     *  기능
     *      RefreshToken을 확인해서 Access Token을 발급해줍니다.
     *      Refresh Token의 id를 추출해서 Access Token에 API 이용에 필요한 정보들을 넣어줍니다.
     */
    @Transactional
    public AccessTokenDTO issueNewAccessToken(RefreshTokenDTO refreshTokenDTO) {
        String refreshToken = refreshTokenDTO.getRefreshToken();

        if(jwtProvider.validateToken(refreshToken) & isRefreshTokenEqServer(refreshToken))
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);

        Long id = jwtProvider.getIdFromToken(refreshToken);

        User user = userRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken =
                jwtProvider.generateAccessToken(TokenIssuanceDTO.from(user));

        return AccessTokenDTO.builder()
                .accessToken(newAccessToken)
                .build();
    }

    /**
     *  로그인 시 Access Token 발급 메소드
     */
    @Transactional
    public AccessTokenDTO signInAccessToken(User user) {

        String newAccessToken =
                jwtProvider.generateAccessToken(TokenIssuanceDTO.from(user));

        return AccessTokenDTO.builder()
                .accessToken(newAccessToken)
                .build();
    }

    /**
     *  Refresh Token 발급 메소드
     *  기능
     *      Refresh Token을 발급해줍니다.
     *      <Key, Value> 형식으로 <id, Refresh Token>을 Redis 서버에 저장합니다.
     */
    @Transactional
    public RefreshTokenDTO issueNewRefreshToken(TokenUserInfoDTO tokenUserInfoDTO) {
        String refreshToken = jwtProvider.generateRefreshToken(tokenUserInfoDTO.getId());

        redisService.saveRefreshToken(tokenUserInfoDTO.getId(), refreshToken);

        return RefreshTokenDTO.builder()
                .refreshToken(refreshToken)
                .build();
    }

    /**
     *  Refresh Token 정합성 검사 메소드
     *  기능
     *      Refresh Token이 레디스에 있는 값과 일치하는지 확인합니다.
     */
    public Boolean isRefreshTokenEqServer(String refreshToken) {

        // 레디스에 있는 값과 일치하면 true 반환
        return redisService.isRefreshTokenInRedis
                (jwtProvider.getIdFromToken(refreshToken), refreshToken);
    }

    public Long getIdFromToken(AccessTokenDTO accessTokenDTO){
        return jwtProvider.getIdFromToken(accessTokenDTO.getAccessToken());
    }

    /**
        Refresh Token 삭제 메소드
     */
    public void deleteRefreshToken(TokenUserInfoDTO tokenUserInfoDTO) {

        redisService.deleteRefreshToken(tokenUserInfoDTO.getId());
    }

}
