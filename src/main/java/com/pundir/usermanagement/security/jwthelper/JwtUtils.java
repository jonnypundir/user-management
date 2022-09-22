package com.pundir.usermanagement.security.jwthelper;

import com.pundir.usermanagement.security.jwtservices.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${user.management.issuer}")
	private String issuer;

	@Value("${user.management.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${user.management.secretKey}")
	private String secretKey;

	private static final String SCOPES = "scopes";
	private static final String USER_ID = "userId";
	private static final String ENABLED = "enabled";

	public String generateJwtToken(UserDetailsImpl userPrincipal) {
		ZonedDateTime currentTime = ZonedDateTime.now();
		Claims claims = Jwts.claims().setSubject(userPrincipal.getEmail());
		claims.put(SCOPES,  userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		claims.put(USER_ID, userPrincipal.getId());
		claims.put(ENABLED, userPrincipal.isEnabled());

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
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
