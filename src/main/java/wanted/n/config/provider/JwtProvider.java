package wanted.n.config.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import wanted.n.dto.TokenIssuanceDTO;
import wanted.n.enums.UserRole;
import wanted.n.service.UserDetailsServiceImpl;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import java.util.Date;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider{

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${token.key}")
    private String issuer;

    private SecretKey secretKey;

    /* SecretKey 초기화 메서드 (빈 생성 시 1회 실행) */
    @PostConstruct
    public void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /* Access Token 생성 메서드 - 클레임에 ID와 닉네임, UserRole 삽입 */
    public String generateAccessToken(TokenIssuanceDTO tokenIssuanceDTO) {
        Claims claims = Jwts.claims().setSubject(tokenIssuanceDTO.getEmail());
        claims.put("id", tokenIssuanceDTO.getId().toString());
        claims.put("nickName", tokenIssuanceDTO.getNickname());
        claims.put("userRole", tokenIssuanceDTO.getUserRole().getRoleName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 유효 : 5분
                .setExpiration(new Date(System.currentTimeMillis() + (5 * 60 * 1000)))
                .signWith(secretKey)
                .compact();
    }

    /* Refresh Token 생성 메서드 - 클레임에 이메일 삽입 (추후 엑세스 토큰 재발급 시 사용예정)*/
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .claim("email", email)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 유효 : 1시간
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .signWith(secretKey)
                .compact();
    }

    /* 토큰 유효성 검증 */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    /* 토큰에서 이메일 추출 */
    public String getEmailFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    /* 토큰에서 id 추출 */
    public Long getIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return Long.parseLong(
                Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", String.class));
    }

    /* 토큰에서 닉네임 추출 */
    public String getNickNameFromToken(String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("nickname", String.class);
    }

    /* Spring Context에서 처리할 인증 객체 생성 */
    public Authentication getAuthentication(String token) {

        // "Bearer " 프리픽스 제거
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 토큰을 파싱하여 클레임 추출
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // claim의 email 이용해서 사용자 정보 로드
        UserDetails userDetails = userDetailsService.loadUserByUsername
                (claims.get("email", String.class));

        // 사용자 권한을 추출하여 Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities()
        );
    }
}
