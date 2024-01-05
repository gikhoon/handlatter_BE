package handlatter.config.jwt;

import handlatter.exception.ErrorCode;
import handlatter.exception.ImageException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secretKey}")
    String secretKey;

    @Value("${jwt.access.expiration}")
    Long accessExpiration;

    public boolean validateToken(String accessToken) throws ImageException {
        if (accessToken.isEmpty()) {
            throw new ImageException(ErrorCode.JWT_NOT_EXISTS);
        }
        return isExpired(accessToken);
    }

    public boolean isExpired(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public String getOAuthName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("oauthName", String.class);
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return getBearer(header);
        } else {
            return null;
        }
    }

    public String getBearer(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }

    public String createAccessToken(String oauthName) {
        Claims claims = Jwts.claims();
        claims.put("oauthName", oauthName);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
