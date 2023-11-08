package wanted.n.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.n.domain.Restaurant;
import wanted.n.domain.Review;
import wanted.n.domain.User;
import wanted.n.dto.ReviewRequestDTO;
import wanted.n.exception.CustomException;
import wanted.n.repository.RestaurantRepository;
import wanted.n.repository.ReviewRepository;
import wanted.n.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static wanted.n.exception.ErrorCode.RESTAURANT_NOT_FOUND;
import static wanted.n.exception.ErrorCode.USER_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks private ReviewService reviewService;

    @Mock private ReviewRepository reviewRepository;
    @Mock private RestaurantRepository restaurantRepository;
    @Mock private UserRepository userRepository;

    private User user;
    private Restaurant restaurant;
    private ReviewRequestDTO reviewRequestDTO;
    private Review review;
    @BeforeEach
    void before(){
        user = User.builder()
                .id(1L)
                .build();
        restaurant = Restaurant.builder()
                .id("test")
                .rate(0.0)
                .reviewedCount(0L)
                .build();
        reviewRequestDTO = ReviewRequestDTO.builder()
                .userId(1L)
                .restaurantId("test")
                .rate(5)
                .content("testtesttesttesttest")
                .build();
        review = Review.builder()
                .user(user)
                .restaurant(restaurant)
                .rate(5)
                .content("testtesttesttesttest")
                .build();
    }
    @Test
    void 리뷰등록(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById("test")).thenReturn(Optional.of(restaurant));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        reviewService.createReview(reviewRequestDTO);

        assertThat(restaurant.getReviewedCount()).isEqualTo(1);
        assertThat(restaurant.getRate()).isEqualTo(5);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void 리뷰등록_실패_유저id(){
        when(userRepository.findById(1L)).thenThrow(new CustomException(USER_NOT_FOUND));

        assertThatThrownBy(() -> reviewService.createReview(reviewRequestDTO))
                .isInstanceOf(CustomException.class)
                .hasMessage("해당 유저가 존재하지 않습니다.");
    }


    @Test
    void 리뷰등록_실패_맛집id(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById("test")).thenThrow(new CustomException(RESTAURANT_NOT_FOUND));

        assertThatThrownBy(() -> reviewService.createReview(reviewRequestDTO))
                .isInstanceOf(CustomException.class)
                .hasMessage("해당 맛집 정보를 찾을 수 없습니다.");
    }
}