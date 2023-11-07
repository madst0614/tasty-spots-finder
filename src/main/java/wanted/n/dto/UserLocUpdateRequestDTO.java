package wanted.n.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLocUpdateRequestDTO {
    Double lat;
    Double lon;
}
