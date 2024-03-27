package PaperWeight.Biscuit_corpo.secuitry.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import PaperWeight.Biscuit_corpo.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
    @Value("${Biscuit_corpo.app.jwtSecret}")
    private String jwtSecret;
    @Value("${Biscuit_corpo.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("\n\nJWT THINKS ITS Malformed\n\n");;
        } catch (ExpiredJwtException e) {
            System.out.println("\n\nJWT THINKS ITS EXPIRED\n\n");
        } catch (UnsupportedJwtException e) {
            System.out.println("\n\nJWT THINKS ITS UNSOPPORTED\n\n");
        } catch (IllegalArgumentException e) {
            System.out.println("\n\nJWT THINKS ITS EMPTY\n\n");
        }

        return false;
    }


}
