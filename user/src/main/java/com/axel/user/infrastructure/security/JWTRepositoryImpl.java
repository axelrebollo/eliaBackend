package com.axel.user.infrastructure.security;

import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.repositories.IJWTRepository;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Repository
public class JWTRepositoryImpl implements IJWTRepository {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expiration:86400000}") //24 hours
    private Long JWT_EXPIRATION;

    //Generates JWT token from email and role
    public String generateToken(String email, String role) {
        //Creation date and expiration date
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + JWT_EXPIRATION);

        //Additional Claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", role);

        //Signature key
        SecretKey key = getKey();
        MacAlgorithm signatureAlgorithm = Jwts.SIG.HS256;

        return Jwts
                .builder()
                .header()
                .type("JWT")
                .and()
                .subject(email)
                .claims(claims)
                .issuedAt(issuedAt)                //Date of creation
                .expiration(expiration)            //Date of expire
                .signWith(key, signatureAlgorithm) //Firm with secret key and algorithm
                .compact();
    }

    //Check if token is correct
    public boolean isTokenValid(String token, String email) {
        String emailFromToken = getEmailFromToken(token);
        Boolean isExpired = isTokenExpired(token);

        return emailFromToken.equals(email) && !isExpired;
    }

    //Obtains email from token
    public String getEmailFromToken(String token) {
        try{
            return getClaim(token, Claims::getSubject);
        }
        catch(InfrastructureException e){
            throw new ApplicationException(e.getMessage());
        }
    }

    //Obtains date expire token
    private Date getExpirationFromToken(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    //Obtains firm key to generation and validation token
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    //Obtains a specific claim from token
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims payload;
        try{
            payload = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (JwtException e) {
            throw new InfrastructureException("El token no es valido", e);
        }
        return claimsResolver.apply(payload);
    }

    //Check if token is expired
    private Boolean isTokenExpired(String token) {
        return getExpirationFromToken(token).before(new Date());
    }
}