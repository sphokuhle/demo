package com.example.demo.dto.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GrantedRolesDto {
    //@JsonProperty(value = "realm-access")
    private RealmAccessDto realmAccess;

    //@JsonProperty(value = "resource-access")
    private ResourceAccessDto resourceAccess;

    //@JsonProperty(value = "preferred_username")
    private String preferredUsername;
}

