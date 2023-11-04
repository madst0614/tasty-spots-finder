package wanted.n.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenDTO {
    private String refreshToken;

    public static RefreshTokenDTO from(AccessTokenRequestDTO accessTokenRequestDTO) {
        return RefreshTokenDTO.builder()
                .refreshToken(accessTokenRequestDTO.getRefreshToken())
                .build();
    }
}
