package wanted.n.dto;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.n.domain.Restaurant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ApiModel(value = "restaurant response", description = "맛집 리스트 조회시 반환 정보")
public class RestaurantSearchResponseDTO {

    private String id;
    private String sigunName;
    private String sigunCode;
    private String bizPlaceName;
    private String businessState;
    private String closeDate;
    private String locationPlaceArea;
    private String graduatedFacilityDivision;
    private String year;
    private String multiUseBusinessEstablishment;
    private String graduatedDivision;
    private String totalFacilityScale;
    private String businessSiteCircumferenceDivision;
    private String sanitationIndustryType;
    private String sanitationBusinessCondition;
    private String refinedRoadNameAddress;
    private String refinedLotNumberAddress;
    private String refinedZipCode;
    private Double refinedLatitude;
    private Double refinedLongitude;
    private Double rate;
    private Long reviewedCount;
    private Double distance;

    public RestaurantSearchResponseDTO(Restaurant restaurant, Double distance) {
        this.id = restaurant.getId();
        this.sigunName = restaurant.getSigunName();
        this.sigunCode = restaurant.getSigunCode();
        this.bizPlaceName = restaurant.getBizPlaceName();
        this.businessState = restaurant.getBusinessState();
        this.closeDate = restaurant.getCloseDate();
        this.locationPlaceArea = restaurant.getLocationPlaceArea();
        this.graduatedFacilityDivision = restaurant.getGraduatedFacilityDivision();
        this.year = restaurant.getYear();
        this.multiUseBusinessEstablishment = restaurant.getMultiUseBusinessEstablishment();
        this.graduatedDivision = restaurant.getGraduatedDivision();
        this.totalFacilityScale = restaurant.getTotalFacilityScale();
        this.businessSiteCircumferenceDivision = restaurant.getBusinessSiteCircumferenceDivision();
        this.sanitationIndustryType = restaurant.getSanitationIndustryType();
        this.sanitationBusinessCondition = restaurant.getSanitationBusinessCondition();
        this.refinedRoadNameAddress = restaurant.getRefinedRoadNameAddress();
        this.refinedLotNumberAddress = restaurant.getRefinedLotNumberAddress();
        this.refinedZipCode = restaurant.getRefinedZipCode();
        this.refinedLatitude = restaurant.getRefinedLatitude();
        this.refinedLongitude = restaurant.getRefinedLongitude();
        this.rate = restaurant.getRate();
        this.reviewedCount = restaurant.getReviewedCount();
        this.distance = distance;
    }
}
