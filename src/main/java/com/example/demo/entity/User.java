package com.example.demo.entity;

import com.example.demo.service.AuditTrailListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS", schema="SECURITY")
@EntityListeners(AuditTrailListener.class)
public class User implements UserDetails, Serializable {
    @Id@SequenceGenerator(name="USER_USER_ID_GENERATOR", sequenceName="USER_SEQ", schema = "SECURITY", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_USER_ID_GENERATOR")
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ENABLED")
    private boolean enabled = true;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> authorities;

    @Override
    public Collection<? extends Role> getAuthorities() {
        return authorities.stream().map(UserRole::getRole).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
