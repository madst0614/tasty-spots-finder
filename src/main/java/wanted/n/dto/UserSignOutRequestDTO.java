package wanted.n.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignOutRequestDTO {
    private String token;
}
