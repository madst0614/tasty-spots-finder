package wanted.n.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wanted.n.domain.Restaurant;
import wanted.n.dto.RestaurantDetailResponseDTO;
import wanted.n.dto.RestaurantSearchRequestDTO;
import wanted.n.dto.RestaurantSearchResponseDTO;

import java.util.List;
import java.util.Optional;

public interface RestaurantQRepository {
    List<Restaurant> findRecommendRestaurants(Double latitude, Double longitude);

    Page<RestaurantSearchResponseDTO> findBySearch(RestaurantSearchRequestDTO restaurantSearchRequestDTO, Pageable pageable);

    Optional<RestaurantDetailResponseDTO> findByIdWithReviewList(String id);
}
