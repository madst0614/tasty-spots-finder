package wanted.n.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.n.domain.SggLatLon;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SggLatLonDTO {
    private Long id;
    private String doSi;
    private String sgg;
    private Double lon;
    private Double lat;

    public static SggLatLonDTO from(SggLatLon sggLatLon) {
        return SggLatLonDTO.builder()
                .id(sggLatLon.getId())
                .doSi(sggLatLon.getDoSi())
                .sgg(sggLatLon.getSgg())
                .lon(sggLatLon.getLon())
                .lat(sggLatLon.getLat())
                .build();
    }
}
