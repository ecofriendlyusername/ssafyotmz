package com.patandmat.otmz.global.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Read the Authorization header, where the JWT Token should be
        // 헤더에서 JWT 를 받아옵니다.
        String headerAccessToken = jwtTokenUtil.resolveAccessToken(request);

        // 유효한 토큰인지 확인합니다.
        if (headerAccessToken != null) {
            // 어세스 토큰이 유효한 상황
            String accessToken = headerAccessToken.replace(JwtUtil.TOKEN_PREFIX, "");
            try {
                if (jwtTokenUtil.validateToken(accessToken)) {
                    this.setAuthentication(accessToken);
                }
            } catch (ExpiredJwtException e) {
                log.info("ExpiredJwtException: {}", e.getMessage());
                throw new JwtException("토큰 기한이 만료");
            } catch (IllegalArgumentException e) {
                log.info("IllegalArgumentException: {}", e.getMessage());
                throw new JwtException("유효하지 않은 토큰");
            } catch (SignatureException e) {
                log.info("SignatureException: {}", e.getMessage());
                throw new JwtException("사용자 인증 실패");
            }
        }
        filterChain.doFilter(request, response);
    }

    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(String token) {
        // 토큰으로부터 유저 정보를 받아옵니다.
        Authentication authentication = jwtTokenUtil.getAuthentication(token);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
