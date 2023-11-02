package wanted.n.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.n.domain.Restaurant;
import wanted.n.dto.ReviewRequestDTO;
import wanted.n.repository.RestaurantRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @InjectMocks private RestaurantService restaurantService;

    @Mock private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;
    private ReviewRequestDTO reviewRequestDTO;

    @BeforeEach
    void before(){
        restaurant = Restaurant.builder()
                .id("test")
                .rate(3.5)
                .reviewedCount(4L)
                .build();
        reviewRequestDTO = ReviewRequestDTO.builder()
                .userId(1L)
                .restaurantId("test")
                .rate(1)
                .content("testtesttesttesttest")
                .build();
    }
    @Test
    void 평점_리뷰수_변경(){
        when(restaurantRepository.findById("test")).thenReturn(Optional.of(restaurant));

        restaurantService.updateRate(reviewRequestDTO);

        Assertions.assertThat(restaurant.getRate()).isEqualTo(3);
        Assertions.assertThat(restaurant.getReviewedCount()).isEqualTo(5);
    }
}