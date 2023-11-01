package wanted.n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.n.dto.ReviewRequestDTO;
import wanted.n.exception.CustomException;
import wanted.n.exception.ErrorCode;
import wanted.n.repository.RestaurantRepository;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    /**
     * restaurant 평점, 리뷰 수 업데이트
     *
     * @param reviewRequestDTO 레스토랑 ID와 새로운 평점 정보를 포함하는 데이터.
     * @throws CustomException 지정된 레스토랑을 찾을 수 없는 경우 발생.
     */
    @Transactional
    public void updateRate(ReviewRequestDTO reviewRequestDTO){
        restaurantRepository.findById(reviewRequestDTO.getRestaurantId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND))
                .updateReview(reviewRequestDTO.getRate());
    }
}
