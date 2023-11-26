package ru.hbb.learnstudio.Auth;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtCore {

    private String secret = "v5nTTf5S7DIcxu+kda8DVNCKlRRxrkljk3izWNFHh50=";
    private int lifetime = 60000;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256, secret).
                compact();
    }

    public String getNameFromJwt(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
