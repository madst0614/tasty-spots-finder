package wanted.n.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;
import wanted.n.domain.SggLatLon;
import wanted.n.repository.SggLatLonRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static wanted.n.enums.FileValues.CSV_CONTENT;

@DisplayName("시군구 파일 업로드/다운로드 테스트")
public class SggLatLonUploadTest {

    private SggLatLonService sggLatLonService;

    @Mock
    private SggLatLonRepository sggLatLonRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sggLatLonService = new SggLatLonService(sggLatLonRepository);
    }

    @Test
    @DisplayName("업로드 성공")
    public void updateCsv_Successful() throws IOException {
        // Given
        MultipartFile csvFile = createMockMultipartFile("1,2,3,4,5\n6,7,8,9,10");
        SggLatLon item1 = SggLatLon.from(new String[]{"1", "2", "3", "4", "5"});
        SggLatLon item2 = SggLatLon.from(new String[]{"6", "7", "8", "9", "10"});

        when(sggLatLonRepository.save(eq(item1))).thenReturn(item1);
        when(sggLatLonRepository.save(eq(item2))).thenReturn(item2);

        // When
        sggLatLonService.updateCsv(csvFile);

        // Then
        verify(sggLatLonRepository).save(any(SggLatLon.class));
    }

    @Test
    @DisplayName("양식 다운로드 성공")
    public void getTemplate() throws IOException {
        // When
        InputStreamResource result = sggLatLonService.getTemplate();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.contentLength()).isEqualTo(CSV_CONTENT.getBytes().length);
    }

    private MultipartFile createMockMultipartFile(String content) throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(content.getBytes()));
        return multipartFile;
    }
}
