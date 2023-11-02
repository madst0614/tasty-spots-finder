package wanted.n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wanted.n.domain.Restaurant;
import wanted.n.dto.RestaurantDetailResponseDTO;
import wanted.n.dto.RestaurantSearchRequestDTO;
import wanted.n.dto.RestaurantSearchResponseDTO;
import wanted.n.exception.CustomException;
import wanted.n.exception.ErrorCode;
import wanted.n.repository.RestaurantRepository;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Page<RestaurantSearchResponseDTO> searchList(RestaurantSearchRequestDTO restaurantSearchRequestDTO, Pageable pageable) {
        return restaurantQRepository.searchList(restaurantSearchRequestDTO, pageable);
    }

    public RestaurantDetailResponseDTO getDetail(String id) {
        Restaurant findRestaurant = restaurantRepository.findByIdWithReviewList(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
        return RestaurantDetailResponseDTO.from(findRestaurant);
    }

}
