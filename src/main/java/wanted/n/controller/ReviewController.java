package wanted.n.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.n.dto.ReviewRequestDTO;
import wanted.n.service.RestaurantService;
import wanted.n.service.ReviewService;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@Api(tags = "Review API", description = "리뷰 관련 API")
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final RestaurantService restaurantService;
    //private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("")
    @ApiOperation(value = "리뷰등록", notes = "리뷰등록 api입니다.")
    public ResponseEntity<Void> restaurantList(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO
            //, @RequestHeader(AUTHORIZATION) String token
    ){
        //reviewRequestDTO.setUserId(jwtTokenProvider.getIdFromToken(token));
        reviewService.createReview(reviewRequestDTO);
        restaurantService.updateRate(reviewRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
