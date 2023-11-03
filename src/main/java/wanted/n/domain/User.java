package wanted.n.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import wanted.n.dto.UserSignUpRequestDTO;
import wanted.n.enums.UserRole;
import wanted.n.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @NotNull
    private String nickname;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private Double lat;
    private Double lon;

    @Column(name = "lunch_served")
    private Boolean lunchServed;

    public static User from(UserSignUpRequestDTO userSignUpRequestDTO){
        return User.builder()
                .email(userSignUpRequestDTO.getEmail())
                .password(userSignUpRequestDTO.getPassword())
                .nickname(userSignUpRequestDTO.getNickname())
                .userRole(UserRole.ROLE_USER)
                .userStatus(UserStatus.UNVERIFIED)
                .lat(userSignUpRequestDTO.getLat())
                .lon(userSignUpRequestDTO.getLon())
                .lunchServed(userSignUpRequestDTO.getLunchServed())
                .build();

    }
}
