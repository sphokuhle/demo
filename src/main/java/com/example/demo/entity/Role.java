package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROLE", schema="SECURITY")
public class Role implements GrantedAuthority {

    @Id
    @SequenceGenerator(name="ROLE_ROLE_ID_GENERATOR", sequenceName="ROLE_SEQ", schema = "SECURITY", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ROLE_ROLE_ID_GENERATOR")
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
