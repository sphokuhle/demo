package com.example.demo.dto;

import com.example.demo.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRoleDto {
    private Long userRoleId;
    private StudentDto user;
    private RoleDto role;

    public UserRoleDto(UserRole userRole) {
        this.userRoleId = userRole.getId();
        this.role = new RoleDto(userRole.getRole());
    }
}
