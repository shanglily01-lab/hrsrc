package com.pmp.hrsrc.util;

import com.pmp.hrsrc.entity.User;
import com.pmp.hrsrc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class PasswordEncryptor {
    
    private static final Logger logger = LoggerFactory.getLogger(PasswordEncryptor.class);
    
    @Autowired
    private UserService userService;
    
    public void encryptExistingPasswords() {
        List<User> users = userService.selectAll();

        
        for (User user : users) {
            if (user.getUpass() != null && !user.getUpass().isEmpty()) {
                String originalPassword = user.getUpass();
                user.encryptPassword();
                userService.updateUser(user);

            }
        }
    }
} 