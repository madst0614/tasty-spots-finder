package wanted.n.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import wanted.n.domain.QRestaurant;
import wanted.n.domain.Restaurant;
import wanted.n.dto.RestaurantDetailResponseDTO;
import wanted.n.enums.RestaurantCategory;
import wanted.n.dto.RestaurantSearchRequestDTO;
import wanted.n.dto.RestaurantSearchResponseDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static wanted.n.domain.QRestaurant.*;
import static wanted.n.domain.QReview.*;
import static wanted.n.domain.QUser.*;

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

    /**
     *  지정된 위치에서 범위 내 맛집리스트 조회 후 DTO로 Projection 후 Page로 반환
     */
    @Override
    public Page<RestaurantSearchResponseDTO> findBySearch(RestaurantSearchRequestDTO restaurantSearchRequestDTO, Pageable pageable) {

        List<RestaurantSearchResponseDTO> content = queryFactory.select(Projections.constructor(RestaurantSearchResponseDTO.class, restaurant,
                        rangeCalculate(restaurantSearchRequestDTO).as("distance")))
                .from(restaurant)
                .where(inRange(restaurantSearchRequestDTO))
                .orderBy(orderBy(restaurantSearchRequestDTO))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalQuery = queryFactory.select(restaurant.count())
                .from(restaurant)
                .where(inRange(restaurantSearchRequestDTO));

        return PageableExecutionUtils.getPage(content, pageable, totalQuery::fetchOne);
    }

    /**
     *  거리 계산 쿼리 생성
     */
    public NumberTemplate<Double> rangeCalculate(RestaurantSearchRequestDTO restaurantSearchRequestDTO){
        return Expressions.numberTemplate(Double.class,
                "6371 * acos(cos(radians({0})) * cos(radians({2})) * cos(radians({3}) - radians({1})) + sin(radians({0})) * sin(radians({2})))",
                restaurantSearchRequestDTO.getLat(), restaurantSearchRequestDTO.getLon(), restaurant.refinedLatitude, restaurant.refinedLongitude);
    }

    /**
     *  검색조건 : 거리 계산 후 범위 보다 작을 시 true
     */
    public BooleanExpression inRange(RestaurantSearchRequestDTO restaurantSearchRequestDTO){
        return rangeCalculate(restaurantSearchRequestDTO).loe(restaurantSearchRequestDTO.getRange());
    }

    /**
     *  정렬조건 : DEFAULT - 거리 가까운 순
     *  rate - 평점 높은 순
     */
    public OrderSpecifier<?> orderBy(RestaurantSearchRequestDTO restaurantSearchRequestDTO){
        boolean dtoHasOrderCondition = StringUtils.hasText(restaurantSearchRequestDTO.getOrderBy());

        if(dtoHasOrderCondition){
            boolean orderByEqRate = restaurantSearchRequestDTO.getOrderBy().equals("rate");
            if(orderByEqRate){
                return restaurant.rate.desc();
            }
        }
        return rangeCalculate(restaurantSearchRequestDTO).asc();
    }

    /**
     *  id로 restaurant상세정보 검색 후 DTO로 Projection
     */
    @Override
    public Optional<RestaurantDetailResponseDTO> findByIdWithReviewList(String id) {
        return Optional.ofNullable(queryFactory.select(Projections.constructor(RestaurantDetailResponseDTO.class,restaurant))
                .from(restaurant)
                .leftJoin(restaurant.reviewList, review)
                .fetchJoin()
                .leftJoin(review.user, user)
                .fetchJoin()
                .where(eqId(id))
                .fetchFirst());
    }

    public BooleanExpression eqId(String id){
        return StringUtils.hasText(id) ? restaurant.id.eq(id) : null;
    }
}
