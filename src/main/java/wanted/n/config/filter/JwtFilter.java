package wanted.n.config.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import wanted.n.config.provider.JwtProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private JwtProvider jwtProvider;

    /* 프리 패스 리스트(인증 없이) : auth(refresh token), users(sign-in, sign-up) */
    private static final String[] ALL_WHITELIST
            ={"/api/v1/auth/token/access"
            , "/api/v1/auth/token/refresh"
            , "/api/v1/users/sign-in"
            , "/api/v1/users/sign-up"};

    /*
        do Filtering Internal
        1. ALL_WHITELIST 체크
        2. 토큰 유무 체크 -> 없으면 HttpsResponse UNAUTHORIZED
        3. jwtProvider 인증 객체 생성 후  ThreadLocal에 저장하여 앱 전반에 전역적으로 참조 가능

        인증 객체 구조(Authentication 객체)
        1. Id 혹은 User 객체
        2. 비밀번호
        3. 권한 목록
        4. 인증 부가 정보
        5. 인증 여부
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(isFilterCheck(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }

        if(!isContainToken(request)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"인증이 필요합니다");
            return;
        }

        String token = getTokenFromRequest(request);

        try{
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(jwtProvider.getAuthentication(token));

            filterChain.doFilter(request,response);
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /* 프리 패스 White-List 대상 판별 */
    private boolean isFilterCheck(String requestURI){
        return PatternMatchUtils.simpleMatch(ALL_WHITELIST, requestURI);
    }

    // Http 요청에 토큰 유무 확인
    private boolean isContainToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return authorization != null && authorization.startsWith("Bearer ");
    }

    // Http 요청에서 토큰 가져오기
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Bearer prefix 지우고 반환
            return bearerToken.substring(7);
        }

        return null;
    }

}
