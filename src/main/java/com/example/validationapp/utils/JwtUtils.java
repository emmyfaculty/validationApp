package com.example.validationapp.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
public class JwtUtils {
    private static final Key SECRET_KEY = generateSecretKey();
    private static final long TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    private static Key generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
    public static String generateJWT(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }
    public static boolean verifyJWT(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
