package com.example.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class DemoJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        realmAccess.put("resource_access", jwt.getClaim("resource_access"));
        Collection<String> resourceRoles;

        if (realmAccess != null &&
                (resourceRoles = (Collection<String>) realmAccess.get("roles")) != null)

            return resourceRoles.stream()
                    .map(x -> new SimpleGrantedAuthority(x))
                    .collect(Collectors.toSet());
        return Collections.emptySet();
    }

    @Override
    public AbstractAuthenticationToken convert(final Jwt source) {
        Collection<GrantedAuthority> authorities = extractResourceRoles(source).stream().collect(Collectors.toSet());
        return new JwtAuthenticationToken(source, authorities);
    }
}
