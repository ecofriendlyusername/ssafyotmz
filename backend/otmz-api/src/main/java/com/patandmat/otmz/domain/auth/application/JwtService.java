package com.patandmat.otmz.domain.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SALT;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 6; // 6 hours
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 60; // 60 days

    public <T> String createAccessToken(String key, T data) {
        return create(key, data, "access-token", ACCESS_TOKEN_EXPIRE_TIME);
    }

    public <T> String createRefreshToken(String key, T data) {
        return create(key, data, "refresh-token", REFRESH_TOKEN_EXPIRE_TIME);
    }

    public <T> String create(String key, T data, String subject, long expire) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .setSubject(subject)
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
    }

    private byte[] generateKey() {
        byte[] key = null;
        key = SALT.getBytes(StandardCharsets.UTF_8);

        return key;
    }

    //	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
    public boolean checkToken(String jwt, Long id) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
            return (id == -1 || id.equals(claims.getBody().get("member_id", Long.class)));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getIdFromToken(String jwt, Long id) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
            return (id == -1 || id.equals(claims.getBody().get("member_id", Long.class)));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Jws<Claims> getClaims(String jwt) {
        try {
            return Jwts.parser().setSigningKey(SALT.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(jwt);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
