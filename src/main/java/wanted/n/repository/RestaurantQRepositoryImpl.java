package wanted.n.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.n.domain.QRestaurant;
import wanted.n.domain.Restaurant;
import wanted.n.enums.RestaurantCategory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RestaurantQRepositoryImpl implements RestaurantQRepository {
    private final JPAQueryFactory queryFactory;
    private final List<String> CATEGORIES =
            Arrays.stream(RestaurantCategory.values())
                    .map(RestaurantCategory::getValue)
                    .collect(Collectors.toList());

    @Override
    public List<Restaurant> findRecommendRestaurants(Double latitude, Double longitude) {
        QRestaurant restaurant = QRestaurant.restaurant;

        return CATEGORIES.stream()
                .flatMap(category -> queryFactory
                        .selectFrom(restaurant)
                        .where(restaurant.sanitationBusinessCondition.eq(category))
                        .orderBy(
                                restaurant.refinedLatitude.subtract(latitude)
                                        .multiply(restaurant.refinedLatitude.subtract(latitude))
                                        .add(restaurant.refinedLongitude.subtract(longitude)
                                                .multiply(restaurant.refinedLongitude.subtract(longitude)))
                                        .asc(),
                                restaurant.rate.desc()
                        )
                        .limit(5)
                        .fetch().stream()
                )
                .collect(Collectors.toList());
    }
}
