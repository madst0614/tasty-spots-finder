package wanted.n.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SggLatLonResponseDTO {
    private List<SggLatLonDTO> sggLatLonResponseDTOList;

    public static SggLatLonResponseDTO from(List<SggLatLonDTO> sggLatLonDTOList) {
        return SggLatLonResponseDTO.builder()
                .sggLatLonResponseDTOList(sggLatLonDTOList)
                .build();
    }
}
