package wanted.n.repository;

import wanted.n.domain.Restaurant;

import java.util.List;

public interface RestaurantQRepository {
    List<Restaurant> findRecommendRestaurants(Double latitude, Double longitude);
}
