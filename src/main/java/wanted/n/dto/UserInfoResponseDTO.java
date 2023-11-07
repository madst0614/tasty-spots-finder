package wanted.n.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.n.domain.User;
import wanted.n.enums.UserRole;
import wanted.n.enums.UserStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponseDTO {
    private Long id;

    private String account;

    private String email;

    private UserRole userRole;

    private UserStatus userStatus;

    private Double lat;
    private Double lon;

    private Boolean lunch_served;

    public static UserInfoResponseDTO from(User user){
        return UserInfoResponseDTO.builder()
                .id(user.getId())
                .account(user.getAccount())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus())
                .lat(user.getLat())
                .lon(user.getLon())
                .lunch_served(user.getLunch_served())
                .build();
    }
}
