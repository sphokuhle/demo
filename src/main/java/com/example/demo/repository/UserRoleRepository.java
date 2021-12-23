package com.example.demo.repository;

import com.example.demo.entity.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.GrantedAuthority;

import javax.transaction.Transactional;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long>, QuerydslPredicateExecutor<UserRole> {
//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO security.user_role values(nextval('security.user_role_seq'), :user_Id, role_Id)", nativeQuery = true)
//    UserRole insert(Long userId, Long roleId);
}
