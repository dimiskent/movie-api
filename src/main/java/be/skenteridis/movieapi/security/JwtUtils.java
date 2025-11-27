package be.skenteridis.movieapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    private final Key key;
    private final long expiration;
    public JwtUtils(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration}") long expiration) {
        this.expiration = expiration;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generate(String subject, Map<String, Object> claims) {
        Date date = new Date();
        Date expiration = new Date(date.getTime() + this.expiration);
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(date)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public boolean isValid(String token) {
        try {
            return new Date().before(parse(token).getBody().getExpiration());
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        return parse(token).getBody().getSubject();
    }
}
