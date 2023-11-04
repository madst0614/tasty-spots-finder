package wanted.n.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import wanted.n.domain.Restaurant;
import wanted.n.domain.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ApiModel(value = "review request", description = "리뷰 등록 요청 정보")
public class ReviewRequestDTO {

    @NotNull(message = "유저id는 필수 입력 항목입니다.")
    @Setter
    private Long userId;

    @NotBlank(message = "맛집id는 필수 입력 항목입니다.")
    private String restaurantId;

    @NotNull(message = "평점은 필수 입력 항목입니다.")
    @Min(value = 1, message = "최소 점수는 1점입니다.")
    @Max(value = 5, message = "최대 점수는 5점입니다.")
    private Integer rate;

    private String content;

    @Setter
    private User user;

    @Setter
    private Restaurant restaurant;
}
