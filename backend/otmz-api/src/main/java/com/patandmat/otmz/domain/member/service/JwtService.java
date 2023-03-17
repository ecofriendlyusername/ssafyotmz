package com.patandmat.otmz.domain.member.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SALT;
    private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 2;
    private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 2;

    public <T> String createAccessToken(String key, T data) {
        return create(key, data, "access-token", 1000 * 60 * 60 * 24 * 7 * ACCESS_TOKEN_EXPIRE_MINUTES);
    }

    //	AccessToken에 비해 유효기간을 길게
    public <T> String createRefreshToken(String key, T data) {
        return create(key, data, "refresh-token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
    }
    public <T> String create(String key, T data, String subject, long expire) {
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .setSubject(subject)
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }
    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("temp");
            e.printStackTrace();
        }

        return key;
    }

    //	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
    public boolean checkToken(String jwt, Long id) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
            return (id == -1 || id == claims.getBody().get("user_id",Long.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("temp");
            return false;
        }
    }

    public boolean getIdFromToken(String jwt, Long id) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
            return (id == -1 || id == claims.getBody().get("user_id",Long.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("temp");
            return false;
        }
    }
    public Jws<Claims> getClaims(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
            return claims;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
