package info.deuriib.securityjwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());
    }

    public String generateToken(Authentication authentication) {
        final String sub = authentication.getName();
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + SecurityConstants.JWT_EXPIRY);

        return Jwts.builder()
                .subject(sub)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(generateKey(), Jwts.SIG.HS512)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token or has expired.");
        }
    }
}
