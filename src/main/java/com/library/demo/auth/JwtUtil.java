package com.library.demo.auth;

import com.library.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final long expirationTime = 1000 * 60 * 60;
    private static final Key key =
            Keys.hmacShaKeyFor("my-super-secret-key-my-super-secret-key".getBytes());

    public String generateToken(User user){
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token){
        try{
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        }catch (JwtException e){
            System.out.println(e);
            return false;
        }
    }
}
