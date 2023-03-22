package com.patandmat.otmz.global.auth;

import com.patandmat.otmz.global.auth.model.TokenInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 6; // 6 hours

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 60; // 60 days

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_KEY = "Authorization";
    public static final String ISSUER = "Otmz";

    //accessToken 생성
    public String createAccessToken(String userId) {
        return createToken(userId, ACCESS_TOKEN_EXPIRE_TIME);
    }

    //refreshToken 생성
    public String createRefreshToken(String userId) {
        return createToken(userId, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String createToken(String userId, long expireTime) {
        Date now = new Date();
        Date expires = new Date(now.getTime() + expireTime);

        return TOKEN_PREFIX +
                Jwts.builder()
                        .setHeaderParam("typ", "JWT")
                        .setHeaderParam("regDate", System.currentTimeMillis() / 1000)
                        .setExpiration(expires)
                        .setSubject(userId)
                        .setIssuer(ISSUER)
                        .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();
    }

    // 토큰에서 회원 정보(아이디) 추출
    public String getMemberId(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    //토큰 유효시간 체크
    public boolean validateToken(String jwtToken) {
        Date expiration = new Date();
        expiration = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody().getExpiration();

        return expiration.after(new Date());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getMemberId(token));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        authenticationToken.setDetails(userDetails);

        return authenticationToken;
    }

    // accessToken HEADER 체크
    public String resolveAccessToken(HttpServletRequest request) {
        if (request.getHeader(HEADER_KEY) != null)
            return request.getHeader(HEADER_KEY);
        return null;
    }
}
