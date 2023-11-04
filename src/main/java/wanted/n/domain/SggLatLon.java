package wanted.n.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SggLatLon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doSi;

    private String sgg;

    private Double lon;

    private Double lat;

    public static SggLatLon from(String[] data) {
        return SggLatLon.builder()
                .doSi(data[0])
                .sgg(data[1])
                .lon(Double.parseDouble(data[2]))
                .lat(Double.parseDouble(data[3]))
                .build();
    }
}