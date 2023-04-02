package com.patandmat.otmz.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.model.BaseResponseBody;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res); // go to 'JwtAuthenticationFilter'
        } catch (JwtException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, res, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        BaseResponseBody jwtExceptionResponse = new BaseResponseBody(401 ,ex.getMessage());
        res.getWriter().write(objectMapper.writeValueAsString(jwtExceptionResponse));
    }
}

