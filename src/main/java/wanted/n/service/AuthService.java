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
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public AccessTokenDTO issueNewAccessToken(RefreshTokenDTO refreshTokenDTO) {
        String refreshToken = refreshTokenDTO.getRefreshToken();

        if(jwtProvider.validateToken(refreshToken))
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);

        String email = jwtProvider.getEmailFromToken(refreshToken);

        // redis User 데이터 확인
        User user = redisService.findUserByEmail(email);

        // 없으면 DB에서 User 데이터 가져오기
        if(user == null){
            user = userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

            redisService.saveUser(user);
        }

        String newAccessToken =
                jwtProvider.generateAccessToken(TokenIssuanceDTO.from(user));

        return AccessTokenDTO.builder()
                .accessToken(newAccessToken)
                .build();
    }
}
