package wanted.n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.n.repository.RestaurantRepository;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantDetailResponseDTO getDetail(String id) {
        return restaurantRepository.findByIdWithReviewList(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
    }

}
