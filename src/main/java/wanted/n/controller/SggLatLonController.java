package wanted.n.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wanted.n.service.SggLatLonService;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@Api(tags = "시군구 File API", description = "시군구 파일 관련 API")
@RestController
public class SggLatLonController {

    private final SggLatLonService sggLatLonService;

    @PostMapping
    @ApiOperation(value = "csv 업데이트", notes = "csv 파일을 읽어서 데이터베이스에 저장")
    public ResponseEntity<Void> readCsv(MultipartFile csvFile) {

        sggLatLonService.updateCsv(csvFile);

        return ResponseEntity.status(CREATED).build();
    }

}

