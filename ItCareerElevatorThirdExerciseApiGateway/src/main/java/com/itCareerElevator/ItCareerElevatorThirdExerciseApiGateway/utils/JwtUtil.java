package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "qwAZS7TCtKxr6ahhUkVGp7lAcdiM98vl2/kzN4ZLJrYqIqdEJyIAZN6CxZJxM4VJ63kEY045TOJryI+c/ewY0w=="; // >= 32 chars

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private JwtParser jwtParser() {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = jwtParser()
                .parseSignedClaims(token)
                .getPayload();

        return claimsResolver.apply(claims);
    }

    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1_000 * 60 * 60))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

}
