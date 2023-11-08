package wanted.n.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.n.config.provider.JwtProvider;
import wanted.n.dto.ReviewRequestDTO;
import wanted.n.service.ReviewService;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@Api(tags = "Review API", description = "리뷰 관련 API")
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtProvider jwtProvider;

    @PostMapping("")
    @ApiOperation(value = "리뷰등록", notes = "리뷰등록 api입니다.")
    public ResponseEntity<Void> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO, @RequestHeader(AUTHORIZATION) String token){
        reviewRequestDTO.setUserId(jwtProvider.getIdFromToken(token));
        reviewService.createReview(reviewRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
