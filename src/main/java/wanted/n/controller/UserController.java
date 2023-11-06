package wanted.n.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.n.dto.*;
import wanted.n.service.UserService;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Api(tags = "User API", description = "사용자와 관련된 API")
@RestController
public class UserController {

    private final UserService userService;
    @PostMapping("/sign-up")
    @ApiOperation(value = "회원가입", notes = "사용자가 회원정보를 입력하여 회원가입을 진행합니다.")
    public ResponseEntity<Void> signUp(
            @Valid @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {

        // 입력받은 정보로 회원가입
        userService.signUpUser(userSignUpRequestDTO);

        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping("/sign-in")
    @ApiOperation(value = "로그인", notes = "사용자가 입력한 정보로 로그인을 진행합니다.")
    public ResponseEntity<UserSignInResponseDTO> signIn(
            @Valid @RequestBody UserSignInRequestDTO signInRequest) {

        // 사용자 로그인 후 사용자 정보 및 토큰(엑세스/리프레시) 발급
        return ResponseEntity.status(OK).body( userService.signInUser(signInRequest));
    }

    @PostMapping("/sign-out")
    @ApiOperation(value = "로그아웃", notes = "사용자의 로그아웃을 진행합니다.")
    public ResponseEntity<Void> signOut(@RequestHeader(AUTHORIZATION) String token) {
        userService.signOutUser(UserSignOutRequestDTO.builder().token(token).build());

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PatchMapping("/update-loc")
    @ApiOperation(value = "위치 업데이트", notes = "사용자의 위치 정보를 업데이트 합니다.")
    public ResponseEntity<Void> updateLocation(@RequestHeader(AUTHORIZATION) String token
            , @Valid @RequestBody UserLocUpdateRequestDTO userLocUpdateRequestDTO) {
        userService.updateUserLoc(token, userLocUpdateRequestDTO);

        return ResponseEntity.status(OK).build();
    }

    @PatchMapping("/lunch-served")
    @ApiOperation(value = "점심 제공 여부 업데이트", notes = "사용자의 점심 제공 정보를 업데이트 합니다.")
    public ResponseEntity<Void> updateLunchServed(@RequestHeader(AUTHORIZATION) String token
            , @Valid @RequestBody UserLunchUpdateRequestDTO userLunchUpdateRequestDTO) {
        userService.updateUserLunchServed(token, userLunchUpdateRequestDTO);

        return ResponseEntity.status(OK).build();
    }

    @GetMapping("/info")
    @ApiOperation(value = "사용자 정보 가져오기", notes = "사용자의 정보를 가져옵니다")
    public ResponseEntity<UserInfoResponseDTO> getUserInfo(@RequestHeader(AUTHORIZATION) String token){

        return ResponseEntity.status(OK).body(userService.getUserInfo(token));
    }
}
