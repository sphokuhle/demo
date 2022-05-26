package com.example.demo.service;

import com.example.demo.converter.ObjectToUrlEncodedConverter;
import com.example.demo.dto.authorization.KeycloakCredentialsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KeycloakKeyService {
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String KEYS_URL;

    public Map<String, String> getPublicKeys() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        ResponseEntity<Map> response = null;
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
        response = restTemplate.exchange(KEYS_URL,
                HttpMethod.GET,
                entity,
                Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            List<Object> keys = (List<Object>) response.getBody().get("keys");
            if(keys != null && !keys.isEmpty()) {
                Map<String, Object> finalMap = (Map<String, Object>) keys.get(0);
                return new HashMap<String, String>() {
                    {
                        put("secret", (String) finalMap.get("kid"));
                        put("publicKey", (String) ((List<String>)finalMap.get("x5c")).get(0));
                    }
                };
            }
        }
        return null;
    }
}
