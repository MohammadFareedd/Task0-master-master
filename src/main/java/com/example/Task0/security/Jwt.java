package com.example.Task0.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class Jwt {


        private static final String SECRET = "eyJhbGciOiJIUzI1NiJ9Ye30Lm0AU9OQ5EFIHX2LIgiuJ1N0cRhQLMYsDgtArjiSyo0WWWWWWWWWSSSQSDECFFVTGBTBFJVBGFVUYFGCYECJGVg";
        private static final long EXPIRATION_TIME = 864_000_000; // 10 days
        public static String generateToken(String username) {


            return Jwts.builder()
                    .subject(username)
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .compact();
        }
        public static String extractUsername(String token) {

            try{ return Jwts.parser().
                    unsecured().
                    verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();}
            catch (Exception e){
               return null;
            }
        }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

}
