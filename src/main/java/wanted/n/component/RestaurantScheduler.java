package wanted.n.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import wanted.n.domain.Restaurant;
import wanted.n.repository.RestaurantRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestaurantScheduler {

    private final RestTemplate restTemplate;
    private final RestaurantRepository restaurantRepository;
    private final ObjectMapper objectMapper;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.suburis}")
    private List<String> subUris;

    @Value("${api.baseuri}")
    private String baseUri;

    /**
     * 매일 23:59에 실행되는 스케줄링
     * 외부 API에서 데이터를 가져와서 데이터베이스에 저장
     */
    @Scheduled(cron = "0 59 23 * * ?")
    @Transactional
    public void getRestaurantsInfo() {
        subUris.forEach(uri -> {
            String response = callExternalAPI(uri);
            jsonParser(uri, response);
        });
    }

    /**
     * 외부 API를 호출하여 응답 데이터를 문자열로 반환.
     *
     * @param uri API 엔드포인트 URI
     * @return API 응답 데이터 문자열
     */
    private String callExternalAPI(String uri) {
        return restTemplate.getForObject(baseUri + uri + apiKey, String.class);
    }

    /**
     * 응답 데이터를 파싱하여 DB에 저장.
     *
     * @param uri      API 엔드포인트 URI
     * @param response API 응답 데이터 문자열
     */
    private void jsonParser(String uri, String response) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response);

            JsonNode foodList = jsonNode.at("/" + uri + "/1");

            JsonNode rowArray = foodList.get("row");

            rowArray.forEach(this::saveRestaurant);

            log.info(uri + "식당 정보 저장 완료 : {}", LocalDateTime.now());

        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류", e);
        }
    }

    /**
     * 음식점 정보를 저장하는 메서드
     * 기존에 존재하는 영업점일 경우 업데이트
     * 신규 영업점일 경우 추가
     * 영업->폐업 시 삭제
     *
     * @param row JsonNode 형태의 응답 데이터 행
     */
    private void saveRestaurant(JsonNode row) {
        String restaurantName = row.get("BIZPLC_NM").asText();
        String refineZipCd = row.get("REFINE_LOTNO_ADDR").asText();
        String id = (restaurantName + refineZipCd).replaceAll("\\s", "");

        Optional<Restaurant> existingRestaurants = restaurantRepository.findById(id);
        Restaurant restaurant = mapJsonToRestaurant(row, id);

        existingRestaurants.ifPresent(existingRestaurant -> {
            if ("폐업".equals(asString(row, "BSN_STATE_NM"))) {
                restaurantRepository.delete(existingRestaurant);
            } else {
                existingRestaurant.updateRestaurant(row);
                restaurantRepository.save(existingRestaurant);
            }
        });


        if (existingRestaurants.isEmpty() && restaurant != null) {
            restaurantRepository.save(restaurant);
        }
    }

    /**
     * JSON 데이터를 Restaurant 객체로 매핑
     *
     * @param row JsonNode 형태의 응답 데이터 행
     * @param id  음식점의 고유 ID
     * @return 매핑된 Restaurant 객체 (폐업일 경우는 null반환)
     */
    private Restaurant mapJsonToRestaurant(JsonNode row, String id) {
        String status = asString(row, "BSN_STATE_NM");

        return status.equals("폐업") ? null :
                Restaurant.builder()
                        .id(id)
                        .sigunName(asString(row, "SIGUN_NM"))
                        .sigunCode(asString(row, "SIGUN_CD"))
                        .bizPlaceName(asString(row, "BIZPLC_NM"))
                        .licenseDate(asString(row, "LICENSG_DE"))
                        .businessState(asString(row, "BSN_STATE_NM"))
                        .closeDate(asString(row, "CLSBIZ_DE"))
                        .locationPlaceArea(asString(row, "LOCPLC_AR"))
                        .graduatedFacilityDivision(asString(row, "GRAD_FACLT_DIV_NM"))
                        .maleEmployeeCount(asInteger(row, "MALE_ENFLPSN_CNT"))
                        .year(asString(row, "YY"))
                        .multiUseBusinessEstablishment(asString(row, "MULTI_USE_BIZESTBL_YN"))
                        .graduatedDivision(asString(row, "GRAD_DIV_NM"))
                        .totalFacilityScale(asString(row, "TOT_FACLT_SCALE"))
                        .femaleEmployeeCount(asInteger(row, "FEMALE_ENFLPSN_CNT"))
                        .businessSiteCircumferenceDivision(asString(row, "BSNSITE_CIRCUMFR_DIV_NM"))
                        .sanitationIndustryType(asString(row, "SANITTN_INDUTYPE_NM"))
                        .sanitationBusinessCondition(asString(row, "SANITTN_BIZCOND_NM"))
                        .totalEmployeeCount(asInteger(row, "TOT_EMPLY_CNT"))
                        .refinedLotNumberAddress(asString(row, "REFINE_LOTNO_ADDR"))
                        .refinedRoadNameAddress(asString(row, "REFINE_ROADNM_ADDR"))
                        .refinedZipCode(asString(row, "REFINE_ZIP_CD"))
                        .refinedLongitude(asDouble(row, "REFINE_WGS84_LOGT"))
                        .refinedLatitude(asDouble(row, "REFINE_WGS84_LAT"))
                        .build();
    }

    /**
     * JsonNode에서 문자열 값을 추출하거나 기본 값을 반환하는 메서드.
     *
     * @param row JsonNode 형태의 응답 데이터 행
     * @param key 키 값
     * @return 추출한 문자열 값 또는 기본 값 (키가 없거나 null인 경우)
     */
    private String asString(JsonNode row, String key) {
        return isNull(row, key) ? row.get(key).asText() : "데이터 없음";
    }

    /**
     * JsonNode에서 정수 값을 추출하거나 기본 값을 반환하는 메서드.
     *
     * @param row JsonNode 형태의 응답 데이터 행
     * @param key 키 값
     * @return 추출한 정수 값 또는 기본 값 (키가 없거나 null인 경우)
     */
    private Integer asInteger(JsonNode row, String key) {
        return isNull(row, key) ? row.get(key).asInt() : 0;
    }

    /**
     * JsonNode에서 실수 값을 추출하거나 기본 값을 반환하는 메서드.
     *
     * @param row JsonNode 형태의 응답 데이터 행
     * @param key 키 값
     * @return 추출한 실수 값 또는 기본 값 (키가 없거나 null인 경우)
     */
    private Double asDouble(JsonNode row, String key) {
        return isNull(row, key) ? row.get(key).asDouble() : 0.0;
    }

    /**
     * JsonNode에서 특정 키의 값이 null인지 확인하는 메서드.
     *
     * @param row JsonNode 형태의 응답 데이터 행
     * @param key 키 값
     * @return 해당 키의 값이 null이 아니면 true, 그렇지 않으면 false를 반환
     */
    private boolean isNull(JsonNode row, String key) {
        JsonNode node = row.get(key);
        return node != null && !node.isNull();
    }

}
