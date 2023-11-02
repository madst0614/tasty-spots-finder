package wanted.n.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RestaurantTest {

    @Test
    void 평점_변경(){
        Restaurant restaurant = Restaurant.builder()
                .rate(3.5)
                .reviewedCount(4L)
                .build();

        restaurant.updateReview(1);

        assertThat(restaurant.getRate()).isEqualTo(3);
        assertThat(restaurant.getReviewedCount()).isEqualTo(5);
    }

}