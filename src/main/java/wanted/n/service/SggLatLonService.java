package wanted.n.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wanted.n.domain.SggLatLon;
import wanted.n.dto.SggLatLonDTO;
import wanted.n.repository.SggLatLonRepository;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static wanted.n.enums.FileValues.CSV_CONTENT;

@Slf4j
@RequiredArgsConstructor
@Service
public class SggLatLonService {

    private final SggLatLonRepository sggLatLonRepository;

    /**
     * CSV 파일을 읽어서 데이터베이스에 저장
     * 업로드 시 기존 시군구 리스트 캐시 초기화
     * @param csvFile 업로드된 CSV 파일
     */
    @CacheEvict(value = "sggLatLonList", key = "'sggList'")
    public void updateCsv(MultipartFile csvFile) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            // 첫 번째 줄은 제목 행이므로 한 번 읽어서 무시.
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                sggLatLonRepository.save(SggLatLon.from(data));
            }

        } catch (IOException e) {
            log.error("파일 읽기 실패 {}", e.getMessage());
        }
    }

    /**
     * CSV 데이터 양식을 생성하고 이를 InputStreamResource 형태로 반환
     *
     * @return CSV 데이터 양식을 포함하는 InputStreamResource 객체
     */
    public InputStreamResource getTemplate() {
        // CSV 데이터를 바이트 배열로 변환
        byte[] csvBytes = CSV_CONTENT.getBytes();

        // 바이트 배열 -> InputStream -> Resource로 래핑
        return new InputStreamResource(new ByteArrayInputStream(csvBytes));
    }

    /**
     * 경기도 행정동 위도 및 경도 정보 조회, DTO리스트로 반환
     * 캐싱 적용 하여 (1일유효) 중복 조회 방지로 성능 개선 (새로운 업로드시 캐시초기화)
     * @return 시군구, 위도 및 경도 정보를 담은 DTO 목록
     */
    @Cacheable(value = "sggLatLonList", key = "'sggList'")
    public List<SggLatLonDTO> getSggLatLons() {
        return sggLatLonRepository.findAll().stream()
                .map(SggLatLonDTO::from)
                .collect(java.util.stream.Collectors.toList());
    }
}
