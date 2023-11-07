package wanted.n.domain;

import wanted.n.dto.UserSignUpRequestDTO;
import lombok.*;
import wanted.n.enums.UserRole;
import wanted.n.enums.UserStatus;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String account;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column
    private Double lat;

    @Column
    private Double lon;

    @Column(name = "lunch_served")
    private Boolean lunch_served;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList;


    public static User from(UserSignUpRequestDTO userSignUpRequestDTO){
        return User.builder()
                .email(userSignUpRequestDTO.getEmail())
                .password(userSignUpRequestDTO.getPassword())
                .account(userSignUpRequestDTO.getAccount())
                .userRole(UserRole.ROLE_USER)
                .userStatus(UserStatus.UNVERIFIED)
                .lat(userSignUpRequestDTO.getLat())
                .lon(userSignUpRequestDTO.getLon())
                .lunch_served(userSignUpRequestDTO.getLunchServed())
                .build();

    }
}
