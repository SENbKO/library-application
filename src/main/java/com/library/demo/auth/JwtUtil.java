package com.library.demo.auth;

import com.library.demo.model.user_model.Role;
import com.library.demo.model.user_model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private final long expirationTime = 1000 * 60 * 60;

    private final Key key;

    public JwtUtil(@Value("${jwt-key}") String jwtKey) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtKey));
    }


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

    public String extractEmail(String token){
        try{
            Claims claims = extractClaims(token);
            return claims.getSubject();
        }catch (JwtException e){
            System.out.println(e);
            return null;
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token){
        try{
            Claims claims = extractClaims(token);
            String role = (String) claims.get("role");
            return List.of(new SimpleGrantedAuthority("ROLE_"+ role));
        }catch (JwtException e){
            e.printStackTrace();
            return null;
        }

    }
}

