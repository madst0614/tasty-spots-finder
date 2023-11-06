package wanted.n.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ApiModel(value = "restaurant request", description = "맛집 리스트 조회시 요청 정보")
public class RestaurantSearchRequestDTO {

    @NotNull(message = "lat 필드는 필수 입력 항목입니다.")
    private Double lat;

    @NotNull(message = "lon 필드는 필수 입력 항목입니다.")
    private Double lon;

    @NotNull(message = "range 필드는 필수 입력 항목입니다.")
    private Double range;

    private String orderBy;
}
