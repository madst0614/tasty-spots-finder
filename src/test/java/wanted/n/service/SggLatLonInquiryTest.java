package wanted.n.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import wanted.n.domain.SggLatLon;
import wanted.n.dto.SggLatLonDTO;
import wanted.n.repository.SggLatLonRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("시군구 목록 조회 테스트")
public class SggLatLonInquiryTest {

    private SggLatLonService sggLatLonService;

    @Mock
    private SggLatLonRepository sggLatLonRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sggLatLonService = new SggLatLonService(sggLatLonRepository);
    }

    @Test
    @DisplayName("성공")
    public void getLatLonList_Successful() {
        // Given
        List<SggLatLon> list = Arrays.asList(
                new SggLatLon(1L,"경기도","부천", 37.5665, 126.9780),
                new SggLatLon(2L,"경기도","시흥", 35.1796, 129.0756)
        );

        Mockito.when(sggLatLonRepository.findAll()).thenReturn(list);

        // When
        List<SggLatLonDTO> result = sggLatLonService.getSggLatLons();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(list.size());
        assertThat(result.get(0).getId()).isEqualTo(list.get(0).getId());
        assertThat(result.get(1).getLat()).isEqualTo(list.get(1).getLat());
    }
}
