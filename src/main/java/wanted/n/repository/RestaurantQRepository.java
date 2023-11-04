package wanted.n.repository;

import wanted.n.domain.Restaurant;
import wanted.n.dto.RestaurantDetailResponseDTO;

import java.util.List;
import java.util.Optional;

public interface RestaurantQRepository {
    List<Restaurant> findRecommendRestaurants(Double latitude, Double longitude);

    Optional<RestaurantDetailResponseDTO> findByIdWithReviewList(String id);
}
