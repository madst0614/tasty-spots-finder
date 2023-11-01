package wanted.n.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
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

    private Double rate;

    private Long reviewedCount;

    public void updateRestaurant(JsonNode row) {
        this.sigunName = row.get("SIGUN_NM").asText();
        this.sigunCode = row.get("SIGUN_CD").asText();
        this.bizPlaceName = row.get("BIZPLC_NM").asText();
        this.refinedRoadNameAddress = row.get("REFINE_ROADNM_ADDR").asText();
        this.refinedLotNumberAddress = row.get("REFINE_LOTNO_ADDR").asText();
        this.refinedZipCode = row.get("REFINE_ZIP_CD").asText();
        this.refinedLatitude = row.get("REFINE_WGS84_LAT").asDouble();
        this.refinedLongitude = row.get("REFINE_WGS84_LOGT").asDouble();
    }
}
