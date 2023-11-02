package wanted.n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.n.dto.ReviewRequestDTO;
import wanted.n.exception.CustomException;
import wanted.n.exception.ErrorCode;
import wanted.n.repository.RestaurantRepository;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

//    public Page<RestaurantSearchResponseDTO> searchList(RestaurantSearchRequestDTO restaurantSearchRequestDTO, Pageable pageable) {
//        return restaurantQuerydslRepository.searchList(restaurantSearchRequestDTO, pageable);
//    }
//
//    public RestaurantDetailResponseDTO getDetail(String id) {
//        Restaurant findRestaurant = restaurantRepository.findById(id)
//                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
//        return RestaurantDetailResponseDTO.from(findRestaurant);
//    }

}
