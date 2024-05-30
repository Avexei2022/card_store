package ru.gb.group5984.service.auth;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Сервис токенов
 */
@Service
@Log
public class JwtService {

    /**
     * Секретный ключ подписи.
     */
    private static final String SECRET_KEY = "cnUuZ2IuZ3JvdXA1OTg0";

    /**
     * Извлечь имя пользователя из токена.
     * @param token токен.
     * @return имя пользователя.
     */
    public String extractUsername(String token) {
        log.info("LOG: JwtService.extractUsername(String token).token = " + token);
        return extractClaim(token,Claims::getSubject);
    }

    /**
     * Извлечь данные из токена
     * @param token токен
     * @param claimsResolver функция извлечения данных
     * @param <T> тип данных
     * @return данные
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.info("LOG: JwtService.extractClaim.token = " + token);
        final Claims claims = extractAllClaims(token);
        log.info("LOG: JwtService.extractClaim.claims = " + claims);
        return claimsResolver.apply(claims);
    }

    /**
     * Получить токен.
     * @param userDetails данные пользователя
     * @return токен
     */
    public String generateToken(UserDetails userDetails) {
        log.info("LOG: JwtService.generateToken.userDetails = " + userDetails);
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Генератор токена.
     * @param extraClaims дополнительные данные.
     * @param userDetails данные пользователя.
     * @return токен.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        log.info("LOG: JwtService.generateToken.userDetails = " + userDetails + " extraClaims = " + extraClaims);
        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        log.info("LOG: JwtService.generateToken.token = " + token);
        return token;
    }

    /**
     * Проверить токен на действительность.
     * @param token токен.
     * @param userDetails данные пользователя.
     * @return true, если токен действителен.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("LOG: JwtService.isTokenValid.userDetails = " + userDetails + " token = " + token);
        final String username = extractUsername(token);
        log.info("LOG: JwtService.isTokenValid.username = " + username);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        log.info("LOG: JwtService.isTokenValid.isValid = " + isValid);
        return isValid;
    }

    /**
     * Проверить токен на истечение срока действия.
     * @param token токен
     * @return true, если токен просрочен.
     */
    private boolean isTokenExpired(String token) {
        log.info("LOG: JwtService.isTokenExpired.token = " + token);
        boolean isExpired = extractExpiration(token).before(new Date());
        log.info("LOG: JwtService.isTokenExpired.isExpired  = " + isExpired);
        return isExpired;
    }

    /**
     * Извлечь дату истечения срока действия токена.
     * @param token токен.
     * @return дата истечения.
     */
    private Date extractExpiration(String token) {
        log.info("LOG: JwtService.extractExpiration.token = " + token);
        Date date = extractClaim(token, Claims::getExpiration);
        log.info("LOG: JwtService.extractExpiration.date = " + date);
        return date;
    }

    /**
     * Извлечь все данные из токена.
     * @param token токен.
     * @return данные.
     */
    private Claims extractAllClaims(String token) {
        log.info("LOG: JwtService.extractAllClaims.token = " + token);
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        log.info("LOG: JwtService.extractAllClaims.claims = " + claims);
        return claims;
    }

    /**
     * Получить ключ для подписи токена.
     * @return ключ.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        log.info("LOG: JwtService.getSigningKey.keyBytes = " + Arrays.toString(keyBytes));
        Key key = Keys.hmacShaKeyFor(keyBytes);
        log.info("LOG: JwtService.getSigningKey.key = " + key);
        return key;
    }
}
