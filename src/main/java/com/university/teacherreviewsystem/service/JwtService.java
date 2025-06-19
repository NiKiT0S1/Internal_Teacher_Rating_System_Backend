/**
 * Назначение: Работа с JWT токенами
 * Создает JWT токены с ролью пользователя
 * Извлекает данные из токенов
 * Проверяет валидность и истечение токенов
 * Время жизни токена: 10 часов
 */

package com.university.teacherreviewsystem.service;


import com.university.teacherreviewsystem.model.User; // NEW
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.HashMap; // NEW
import java.util.Map; // NEW

@Service
public class JwtService {

    private final String SECRET_KEY = "super-puper-duper-secret-key-in-the-world";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // NEW
    public String extractUserRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
    //

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name()); // добавляем роль в JWT

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    public boolean isTokenValid(String token, String username) {
        final String extractUsername = extractUsername(token);
        return (extractUsername(token).equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
