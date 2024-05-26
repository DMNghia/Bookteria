package org.nghia.identityservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.nghia.identityservice.dto.UserLoginPrinciple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${application.security.jwt.issuer}")
    private String issuer;

    public String generateToken(UserLoginPrinciple principle) {
        return buildToken(principle, jwtExpiration);
    }

    public String generateRefreshToken(UserLoginPrinciple principle) {
        return buildToken(principle, refreshExpiration);
    }

    private String buildToken(UserLoginPrinciple principle, long expiredTime) {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);
        Map<String, Object> claims = buildClaims(principle);
        return Jwts.builder()
                .setIssuedAt(new Date())
                .addClaims(claims)
                .setSubject(principle.getUsername())
                .setIssuer(issuer)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .compact();
    }

    public UserLoginPrinciple getPrinciple(String token) {
        if (!StringUtils.hasText(token))
            return null;
        Claims bodyClaims = extractClaims(token);
        if (ObjectUtils.isEmpty(bodyClaims))
            return null;
        return UserLoginPrinciple.builder()
                .username(bodyClaims.getSubject())
                .firstName((String) bodyClaims.get("firstName"))
                .lastName((String) bodyClaims.get("lastName"))
                .roles((List<String>) bodyClaims.get("roles"))
                .build();
    }

    private Claims extractClaims(String token) {
        if (!StringUtils.hasText(token))
            return null;
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, Object> buildClaims(UserLoginPrinciple principle) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", principle.getUsername());
        claims.put("firstName", principle.getFirstName());
        claims.put("lastName", principle.getLastName());
        claims.put("roles", principle.getRoles());
        return claims;
    }
}
