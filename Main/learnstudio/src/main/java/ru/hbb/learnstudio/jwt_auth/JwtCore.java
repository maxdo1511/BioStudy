package ru.hbb.learnstudio.jwt_auth;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.hbb.learnstudio.user.UserDetailsImpl;

import java.util.Date;

@Component
public class JwtCore {

    private String secret = "v5nTTf5S7DIcxu+kda8DVNCKlRRxrkljk3izWNFHh50=";
    private long lifetime = 10 * 1000 * 60;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(new Date());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256, secret).
                compact();
    }

    public String getNameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
