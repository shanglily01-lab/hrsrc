package com.pmp.hrsrc.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws Exception {
        // 设置响应头
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        
        // 生成验证码文本
        String capText = defaultKaptcha.createText();
        // 将验证码存入session
        session.setAttribute("captchaCode", capText);
        
        // 生成验证码图片
        BufferedImage bi = defaultKaptcha.createImage(capText);
        
        // 输出图片
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/validateCaptcha")
    @ResponseBody
    public Map<String, Object> validateCaptcha(String captcha, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String sessionCaptcha = (String) session.getAttribute("captchaCode");
        
        if (sessionCaptcha != null && sessionCaptcha.equalsIgnoreCase(captcha)) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("message", "验证码错误");
        }
        
        return result;
    }
} 