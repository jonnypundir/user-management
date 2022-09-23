package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.UserDetailsDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtService {

    @Value("${user.management.issuer}")
    private String issuer;

    @Value("${user.management.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${user.management.secretKey}")
    private String secretKey;

    private static final String SCOPES = "scopes";
    private static final String USER_ID = "userId";
    private static final String ENABLED = "enabled";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "LastName";

    public String generateJwtToken(UserDetailsDto userPrincipal) {
        log.info("generate token by {}", userPrincipal);
        ZonedDateTime currentTime = ZonedDateTime.now();
        Claims claims = Jwts.claims().setSubject(userPrincipal.getEmail());
        claims.put(SCOPES, userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        claims.put(USER_ID, userPrincipal.getId());
        claims.put(ENABLED, userPrincipal.isEnabled());
        claims.put(LAST_NAME, userPrincipal.getLastName());
        claims.put(FIRST_NAME, userPrincipal.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(currentTime.toInstant()))
                .setExpiration(Date.from(currentTime.plusSeconds(jwtExpirationMs).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
