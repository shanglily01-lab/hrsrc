package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.User;
import com.pmp.hrsrc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public Map<String, Object> createUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 加密密码
            user.setUpass(passwordEncoder.encode(user.getUpass()));
            user.setId(userService.selectMaxId());
            userService.insertUser(user);
            result.put("success", true);
            result.put("message", "用户创建成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "用户创建失败: " + e.getMessage());
        }
        return result;
    }

    @PutMapping("/{id}/password")
    public Map<String, Object> updatePassword(@PathVariable Integer id, @RequestBody Map<String, String> passwords) {
        Map<String, Object> result = new HashMap<>();
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");
        
        // 获取用户信息
        User user = userService.selectById(id);
        if (user == null) {
            result.put("success", false);
            result.put("message", "用户不存在");
            return result;
        }

        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getUpass())) {
            result.put("success", false);
            result.put("message", "旧密码错误");
            return result;
        }

        // 加密新密码
        user.setUpass(passwordEncoder.encode(newPassword));
        try {
            userService.updateUser(user);
            result.put("success", true);
            result.put("message", "密码修改成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "密码修改失败: " + e.getMessage());
        }
        return result;
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 如果密码不为空，则加密密码
            if (user.getUpass() != null && !user.getUpass().isEmpty()) {
                user.setUpass(passwordEncoder.encode(user.getUpass()));
            }
            user.setId(id);
            userService.updateUser(user);
            result.put("success", true);
            result.put("message", "用户更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "用户更新失败: " + e.getMessage());
        }
        return result;
    }
} 