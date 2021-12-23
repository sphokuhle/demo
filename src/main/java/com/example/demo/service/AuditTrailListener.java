package com.example.demo.service;

import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;

/**
 * @author S'phokuhle on 9/13/2021
 */
@Slf4j
public class AuditTrailListener {
    @PostPersist
    private void afterInsertion(User user) {
        log.info("Change detected: User {} was inserted", user.getUsername());
    }
}
