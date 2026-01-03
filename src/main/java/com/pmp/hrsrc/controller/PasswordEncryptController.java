package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.util.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordEncryptController {
    
    @Autowired
    private PasswordEncryptor passwordEncryptor;
    
    @GetMapping("/encrypt-passwords")
    public String encryptPasswords() {
        try {
            passwordEncryptor.encryptExistingPasswords();
            return "密码加密完成";
        } catch (Exception e) {
            return "密码加密失败: " + e.getMessage();
        }
    }
} 