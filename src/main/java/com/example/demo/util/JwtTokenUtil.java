package com.example.demo.util;

import com.example.demo.config.KeyGeneratorConfig;
import com.example.demo.dto.role.GrantedRolesDto;
import com.example.demo.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

import static java.lang.String.format;

@Component
public class JwtTokenUtil {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class.getCanonicalName());

    @Autowired
    KeyGeneratorConfig keyGeneratorConfig;

    public String getUsername(String token) {
        token = token.replaceFirst("Bearer ","");
        Claims claims = Jwts.parser()
                .setSigningKey(keyGeneratorConfig.getParsedPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("preferred_username");
    }

    public GrantedRolesDto getGrantedAuthorities(String token) throws JsonProcessingException {
        token = token.replaceFirst("Bearer ","");
        Claims claims = Jwts.parser()
                .setSigningKey(keyGeneratorConfig.getParsedPublicKey())
                .parseClaimsJws(token)
                .getBody();
        Map<String, Object> response = (Map<String, Object>)claims;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = mapper.writeValueAsString(response)
                .replaceAll("realm_access","realmAccess")
                .replaceAll("resource_access","resourceAccess");
        GrantedRolesDto grantedRolesDto = mapper.readValue(json, GrantedRolesDto.class);
        return grantedRolesDto;
    }

    public Date getExpirationDate(String token) {
        token = token.replaceFirst("Bearer ","");
        Claims claims = Jwts.parser()
                .setSigningKey(keyGeneratorConfig.getParsedPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            token = token.replaceFirst("Bearer ","");
            Jwts.parser().setSigningKey(keyGeneratorConfig.getParsedPublicKey()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
