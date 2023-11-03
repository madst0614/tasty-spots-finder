package wanted.n.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModel;
import lombok.*;
import wanted.n.domain.Restaurant;
import wanted.n.domain.Review;
import wanted.n.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ApiModel(value = "restaurant detail response", description = "맛집 상세페이지 반환 정보")
@Builder
public class RestaurantDetailResponseDTO {

    private String id;
    private String sigunName;
    private String sigunCode;
    private String bizPlaceName;
    private String licenseDate;
    private String businessState;
    private String closeDate;
    private String locationPlaceArea;
    private String graduatedFacilityDivision;
    private Integer maleEmployeeCount;
    private String year;
    private String multiUseBusinessEstablishment;
    private String graduatedDivision;
    private String totalFacilityScale;
    private Integer femaleEmployeeCount;
    private String businessSiteCircumferenceDivision;
    private String sanitationIndustryType;
    private String sanitationBusinessCondition;
    private Integer totalEmployeeCount;
    private String refinedRoadNameAddress;
    private String refinedLotNumberAddress;
    private String refinedZipCode;
    private Double refinedLatitude;
    private Double refinedLongitude;
    private Double rate;
    private Long reviewedCount;
    private List<ReviewDTO> reviewList;
    public RestaurantDetailResponseDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.sigunName = restaurant.getSigunName();
        this.sigunCode = restaurant.getSigunCode();
        this.bizPlaceName = restaurant.getBizPlaceName();
        this.licenseDate = restaurant.getLicenseDate();
        this.businessState = restaurant.getBusinessState();
        this.closeDate = restaurant.getCloseDate();
        this.locationPlaceArea = restaurant.getLocationPlaceArea();
        this.graduatedFacilityDivision = restaurant.getGraduatedFacilityDivision();
        this.maleEmployeeCount = restaurant.getMaleEmployeeCount();
        this.year = restaurant.getYear();
        this.multiUseBusinessEstablishment = restaurant.getMultiUseBusinessEstablishment();
        this.graduatedDivision = restaurant.getGraduatedDivision();
        this.totalFacilityScale = restaurant.getTotalFacilityScale();
        this.femaleEmployeeCount = restaurant.getFemaleEmployeeCount();
        this.businessSiteCircumferenceDivision = restaurant.getBusinessSiteCircumferenceDivision();
        this.sanitationIndustryType = restaurant.getSanitationIndustryType();
        this.sanitationBusinessCondition = restaurant.getSanitationBusinessCondition();
        this.totalEmployeeCount = restaurant.getTotalEmployeeCount();
        this.refinedRoadNameAddress = restaurant.getRefinedRoadNameAddress();
        this.refinedLotNumberAddress = restaurant.getRefinedLotNumberAddress();
        this.refinedZipCode = restaurant.getRefinedZipCode();
        this.refinedLatitude = restaurant.getRefinedLatitude();
        this.refinedLongitude = restaurant.getRefinedLongitude();
        this.rate = restaurant.getRate();
        this.reviewedCount = restaurant.getReviewedCount();
        this.reviewList = restaurant.getReviewList().stream()
                .map(ReviewDTO::from)
                .collect(Collectors.toList());
    }

    @Builder
    @Getter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class ReviewDTO {
        private Long id;
        private UserDTO user;
        private Integer rate;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        public static ReviewDTO from(Review review){
            return ReviewDTO.builder()
                    .id(review.getId())
                    .user(UserDTO.from(review.getUser()))
                    .rate(review.getRate())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class UserDTO {
        private Long id;
        private String account;
        private String email;
        private Double lat;
        private Double lon;
        public static UserDTO from(User user){
            return UserDTO.builder()
                    .id(user.getId())
                    .account(user.getAccount())
                    .email(user.getEmail())
                    .lat(user.getLat())
                    .lon(user.getLon())
                    .build();
        }
    }
}
