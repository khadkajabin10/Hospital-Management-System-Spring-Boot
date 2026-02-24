package com.example.demo.Security;

import com.example.demo.Entity.User;
import com.example.demo.Entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class AuthUtil {
    @Value("${jwt.secretkey}")
    private String jwtSecretkey;
    private SecretKey getSecretKey() { return Keys.hmacShaKeyFor(jwtSecretkey.getBytes(StandardCharsets.UTF_8)); }
    public String generateAccessToken(User user){
        return Jwts.builder()
                //Claims = all the key–value pairs inside the payload (like userId, iat, exp).
                //
                //Subject (sub) = one special claim inside that payload,
                // reserved by JWT spec to represent the principal (usually username or user ID).
                //The subject is not separate from claims — it’s one of the claims.
                .subject(user.getUsername())
                .claim("userId",user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+(1000*60*10)))
                .signWith(getSecretKey())//This takes the secret key you use for JWT signing.the JJWT library automatically creates a header for you.
                .compact();//This assembles the JWT into its final string form:
    }

    public String getusernameFromToken(String token) {
        Claims claims= Jwts.parser()
                .verifyWith(getSecretKey())//the token text has secret key and header, and that key is verified with getSecretKey() secretkey and header.
                .build()
                .parseSignedClaims(token)//take text
                .getPayload();
        return claims.getSubject();

        //parseSignedClaims(token) → takes the raw JWT string (which is just text) and parses it:
        //
        //Splits the token into its 3 parts: header, payload (claims), and signature.
        //
        //Verifies the signature using your secret key.
        //
        //Converts the payload (JSON inside the token) into a Claims object you can work with in Java.
    }

    //return Jwts.builder()
    //        .subject(user.getUsername())   // 👈 here you set the "sub" claim
    //        .claim("userId", user.getId().toString())
    //        .issuedAt(new Date())
    //        .expiration(new Date(System.currentTimeMillis() + (1000*60*10)))
    //        .signWith(getSecretKey())
    //        .compact();
    //.subject(user.getUsername()) → puts the username into the subject claim (sub) inside the JWT payload.
    //
    //So the token now contains JSON like:
    //
    //json
    //{
    //  "sub": "jabin",
    //  "userId": "123",
    //  "iat": 1708170000,
    //  "exp": 1708170600
    //}
    //2. When you parse the token

    //Claims claims = Jwts.parser()
    //        .verifyWith(getSecretKey())
    //        .build()
    //        .parseSignedClaims(token)
    //        .getPayload();
    //return claims.getSubject();
    //.parseSignedClaims(token) → reads the token string, verifies the signature, and converts the payload JSON into a Claims object.
    //
    //.getPayload() → gives you that claims object.
    //
    //.getSubject() → retrieves the sub field (the subject claim) from the payload.
    public AuthProviderType getProviderType(String registrationId) {
        return switch (registrationId.toLowerCase()){
            case "google" ->AuthProviderType.GOOGLE;
            case "github" ->AuthProviderType.GITHUB;
            case "facebook" ->AuthProviderType.FACEBOOK;
            default -> throw new IllegalArgumentException("Unsupported OAuth2 provider :" +registrationId);
        };
    }

    public String determineProviderIdFromOauth2User(OAuth2User oAuth2User, String registrationId) {
       String providerId= switch (registrationId.toLowerCase()){
           case "google"->oAuth2User.getAttribute("sub");//getiing username as providerID think like this
           case "github"->oAuth2User.getAttribute("id").toString();
           default ->{
               log.error("Unsupported oAuth2 provider:{}",registrationId);
               throw new IllegalArgumentException("Unsupported OAuth2 provider ok :" +registrationId);
           }
       };
       if(providerId==null||providerId.isEmpty()){
           log.error("Unable to determine providerId for  provider:{}",registrationId);
           throw new IllegalArgumentException("Unsupported OAuth2 provider :" +registrationId);
       }
       return providerId;
    }

    public String determineusernamefromoauth2user(OAuth2User oAuth2User, String registrationId, String providerId) {
        String email=oAuth2User.getAttribute("email");
        if(email!=null && !email.isBlank()){
            return  email;
        }
      return   switch (registrationId.toLowerCase()){
            case "google"->oAuth2User.getAttribute("sub");
            case "github"->oAuth2User.getAttribute("id").toString();
            default ->  providerId;

        };

    }
}
