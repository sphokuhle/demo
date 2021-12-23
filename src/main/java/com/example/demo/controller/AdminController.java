package com.example.demo.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.AdminService;
import com.example.demo.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
@PreAuthorize("hasAuthority('admin_authorization')")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    EmailUtil emailUtil;

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin_authorization')")
    public void register(@RequestHeader("Authorization") String token, @RequestBody StudentDto student) throws Exception {
        adminService.registerUser(token, student);
    }

    @PostMapping(value="send/email")
    public void sendEmail() {
        emailUtil.send("Demo Application Email Test");
    }
}
