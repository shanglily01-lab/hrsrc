package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.User;
import com.pmp.hrsrc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/api/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, String> loginData, HttpSession session,HttpServletRequest req) {
        Map<String, Object> result = new HashMap<>();
        
        // 验证码校验
        String captcha = loginData.get("captcha");
        String sessionCaptcha = (String) session.getAttribute("captchaCode");
        
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            result.put("success", false);
            result.put("error", "captcha");
            result.put("message", "验证码错误");
            return result;
        }
        
        // 清除验证码，防止重复使用
        session.removeAttribute("captchaCode");
        
        // 用户名密码校验
        String username = loginData.get("username");
        String password = loginData.get("password");
        
        User user = new User();
        user.setUname(username);
        user.setUpass(password);
        
        User validatedUser = userService.validateUser(user);
        
        if (validatedUser != null) {
            // 验证密码
            if (!passwordEncoder.matches(password, validatedUser.getUpass())) {
                result.put("success", false);
                result.put("error", "credentials");
                result.put("message", "用户名或密码错误");
                return result;
            }
            
            session.setAttribute("user", validatedUser);
            session.setAttribute("username", validatedUser.getUname());
            session.setAttribute("loginTime", new java.util.Date());
            // 使用Calendar设置北京时间
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
            Date ldate = calendar.getTime();
            validatedUser.setLodate(ldate);
            // 获取客户端IP地址
            String ip = getClientIp(req);
            validatedUser.setIp(ip);
            // 更新用户登录时间和IP
            userService.updateDateAndIp(validatedUser);
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("error", "credentials");
            result.put("message", "用户名或密码错误");
        }
        
        return result;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    /**
     * 获取客户端真实IP地址
     * @param request HTTP请求
     * @return IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = null;

        // 优先获取X-Forwarded-For
        ip = request.getHeader("X-Forwarded-For");
        if (isValidIp(ip)) {
            return getFirstIp(ip);
        }

        // 获取其他代理头信息
        String[] headers = {
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headers) {
            ip = request.getHeader(header);
            if (isValidIp(ip)) {
                return getFirstIp(ip);
            }
        }

        // 获取远程地址
        ip = request.getRemoteAddr();

        // 处理本地访问的情况
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            // 尝试获取本机IP
            try {
                ip = java.net.InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                ip = "127.0.0.1";
            }
        }

        return ip;
    }

    /**
     * 验证IP地址是否有效
     * @param ip IP地址
     * @return 是否有效
     */
    private boolean isValidIp(String ip) {
        return ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip);
    }

    /**
     * 获取多个IP中的第一个IP
     * @param ip 包含多个IP的字符串
     * @return 第一个IP
     */
    private String getFirstIp(String ip) {
        if (ip != null && ip.contains(",")) {
            return ip.split(",")[0].trim();
        }
        return ip;
    }
} 