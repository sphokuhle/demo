package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER_ROLE", schema="SECURITY")
public class UserRole {
    @Id
    @SequenceGenerator(name="USER_USER_ROLE_ID_GENERATOR", sequenceName="USER_ROLE_SEQ", schema = "SECURITY", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_USER_ROLE_ID_GENERATOR")
    @Column(name = "USER_ROLE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
}
