//package com.jobportal.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JwtHelper {
//
//    private static final Key SECRET_KEYY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    private static final long JWT_TOKEN_VALIDITY = 3600000;
//
//    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // Min 256-bit for HS256
//    private static final long JWT_EXPIRATION = 1000 * 60 * 60; // 5 hours
//
//    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//
//    // Generate token
//    public String generateToken(String username) {
//        return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION)).signWith(key, SignatureAlgorithm.HS256).compact();
//    }
//    public String generateTokenOld(UserDetails userDetails){
//        Map<String,Object>  claims= new HashMap<>();
//        return doGenerateToken(claims,userDetails.getUsername());
//    }
//
//    public String doGenerateToken(Map<String,Object> claims,String subject){
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)).signWith(SECRET_KEYY).compact();
//    }
//    // Validate token
//    public boolean validateToken(String token, String username) {
//        final String tokenUsername = extractUsername(token);
//        return (username.equals(tokenUsername) && !isTokenExpired(token));
//    }
//
//    // Extract username
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    // Check if token expired
//    public boolean isTokenExpired(String token) {
//        final Date expiration = extractClaim(token, Claims::getExpiration);
//        return expiration.before(new Date());
//    }
//
//    // Generic claim extractor
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    // Extract all claims
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//    }
//
//    public Date getExpirationDateFormToken(String token){
//        return extractClaim(token,Claims::getExpiration);
//    }
//
//    private Claims getAllClaimsFromToken(String token){
//        return Jwts.parserBuilder().setSigningKey(SECRET_KEYY).build().parseClaimsJws(token).getBody();
//    }
//}

package com.jobportal.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    private final Key key;
    private final long jwtExpirationInMillis;

    public JwtHelper(@Value("${jwt.secret}") String secretKey,
                     @Value("${jwt.expiration}") long jwtExpirationInMillis) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMillis = jwtExpirationInMillis;
    }

    // Generate token from username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Generate token from UserDetails (optionally with claims)
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails customUser = (CustomUserDetails) userDetails;
        claims.put("id",customUser.getId());
        claims.put("name",customUser.getName());
        claims.put("accountType",customUser.getAccountType());
        claims.put("profileId",customUser.getProfileId());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate token
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    // Extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    // Extract a specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get expiration date
    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
