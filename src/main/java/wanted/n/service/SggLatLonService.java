package wanted.n.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wanted.n.domain.SggLatLon;
import wanted.n.repository.SggLatLonRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@RequiredArgsConstructor
@Service
public class SggLatLonService {

    private final SggLatLonRepository sggLatLonRepository;

    /**
     * CSV 파일을 읽어서 데이터베이스에 저장
     *
     * @param csvFile 업로드된 CSV 파일
     */
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

}
