package wanted.n.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.n.dto.RestaurantDetailResponseDTO;
import wanted.n.service.RestaurantService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
@Api(tags = "Restaurant API", description = "맛집 관련 API")
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @ApiOperation(value = "맛집리스트", notes = "입력된 위치에서 지정된 거리내의 맛집 리스트 검색")
    public ResponseEntity<Page<RestaurantSearchResponseDTO>> searchList(@Valid RestaurantSearchRequestDTO restaurantSearchRequestDTO, @PageableDefault(size = 15) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.searchList(restaurantSearchRequestDTO, pageable));
    }

    @GetMapping("/{restaurantId}")
    @ApiOperation(value = "맛집 상세정보", notes = "맛집 상세정보")
    public ResponseEntity<RestaurantDetailResponseDTO> getDetail(@PathVariable("restaurantId") String restaurantId){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getDetail(restaurantId));
    }
}
