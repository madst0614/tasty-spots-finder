package wanted.n.domain;

import lombok.*;
import wanted.n.enums.UserRole;
import wanted.n.enums.UserStatus;

import javax.persistence.*;
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

    @Column
    private String account;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column
    private Double lat;

    @Column
    private Double lon;

    @Column
    private Boolean lunch_served;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList;

}
