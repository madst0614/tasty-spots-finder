package wanted.n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     *  지정된 위치에서 범위 내 맛집리스트 조회
     *
     *  필수 검색조건
     *  lat : 위도
     *  lon : 경도
     *  range : 검색범위 (km)
     */
    @Transactional(readOnly = true)
    public Page<RestaurantSearchResponseDTO> searchList(RestaurantSearchRequestDTO restaurantSearchRequestDTO, Pageable pageable) {
        return restaurantRepository.findBySearch(restaurantSearchRequestDTO, pageable);
    }

    /**
     *  맛집 상세정보 조회
     *  redis에 존재 -> redis에서 조회
     *  redis에 존재 X -> db에서 조회 후 redis에 저장
     */
    @Transactional
    @Cacheable(value = "RestaurantDetailResponseDTO", key = "#id")
    public RestaurantDetailResponseDTO getDetail(String id) {
        return restaurantRepository.findByIdWithReviewList(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
    }
}
