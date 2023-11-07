package wanted.n.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.n.dto.AccessTokenDTO;
import wanted.n.dto.AccessTokenRequestDTO;
import wanted.n.dto.AccessTokenResponseDTO;
import wanted.n.dto.RefreshTokenDTO;
import wanted.n.service.AuthService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Api(tags = "Auth API", description = "인증과 관련된 API")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token/access")
    @ApiOperation(value = "Access Token 발급", notes= "Refresh Token을 확인하여 Access Token을 발급해 줍니다.")
    public ResponseEntity<AccessTokenResponseDTO> issueNewAccessToken(
            @Valid @RequestBody AccessTokenRequestDTO accessTokenRequestDTO) {

        // Access Token 발급 서비스 호출
        AccessTokenDTO accessTokenDTO =
                authService.issueNewAccessToken(RefreshTokenDTO.from(accessTokenRequestDTO));

        return new ResponseEntity<>(AccessTokenResponseDTO.from(accessTokenDTO), HttpStatus.OK);
    }
}
