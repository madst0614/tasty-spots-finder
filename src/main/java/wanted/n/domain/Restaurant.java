package wanted.n.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Restaurant {
    @Id
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
}
