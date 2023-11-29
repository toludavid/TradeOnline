package com.stanbic.redbox.fincale.online.rest.service.utils;



import org.springframework.stereotype.Component;
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
//@Component
public class JwtTokenUtil {
    //private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 1800000; // 30 mins
   
   
  //  public String generateToken(String username) {
   //     Date now = new Date();
   //     Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
    //    return Jwts.builder()  .setSubject(username).setIssuedAt(now).setExpiration(expiryDate).signWith(secretKey).compact();
    //}
        
    /**
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            // Check token expiration
            Date expirationDate = claims.getExpiration();
            Date now = new Date();
            if (expirationDate.before(now)) {
                // Token is expired
                return false;
            }

            // If the parsing and expiration checks pass, the token is valid
            return true;
        } catch (ExpiredJwtException ex) {
            // Token is expired
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            // Token is invalid (failed parsing or verification)
            return false;
        }
    }**/
}