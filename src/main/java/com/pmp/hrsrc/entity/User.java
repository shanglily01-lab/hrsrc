package com.pmp.hrsrc.entity;

import java.util.Date;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class User {
    private static final Logger logger = LoggerFactory.getLogger(User.class);
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);  // 使用固定的强度值
    
    int id;
    String uname;
    String upass;
    Date   lodate;
    String scode;
    String ip;
    String val;

    // 检查密码是否已经加密
    private boolean isPasswordEncrypted(String password) {
        return password != null && password.startsWith("$2a$");
    }

    // 加密密码
    public void encryptPassword() {
        if (this.upass != null && !this.upass.isEmpty() && !isPasswordEncrypted(this.upass)) {
            String originalPassword = this.upass;
            this.upass = encoder.encode(originalPassword);
            logger.info("Password encrypted for user: {}", this.uname);
            logger.info("Original password: {}", originalPassword);
            logger.info("Encrypted password: {}", this.upass);
        }
    }

    // 验证密码
    public boolean matchesPassword(String rawPassword) {
        if (this.upass == null || rawPassword == null) {
            return false;
        }
        return encoder.matches(rawPassword, this.upass);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public Date getLodate() {
        return lodate;
    }

    public void setLodate(Date lodate) {
        this.lodate = lodate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getScode() {
        return scode;
    }
}
