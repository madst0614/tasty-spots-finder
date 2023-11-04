package wanted.n.dto;

import lombok.Builder;
import lombok.Getter;
import wanted.n.domain.User;
import wanted.n.enums.UserRole;

@Getter
@Builder
public class TokenIssuanceDTO {
    private String email;
    private Long id;
    private String nickname;
    private UserRole userRole;

    public static TokenIssuanceDTO from(User user) {
        return TokenIssuanceDTO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .nickname(user.getNickname())
                .userRole(user.getUserRole())
                .build();
    }
}
