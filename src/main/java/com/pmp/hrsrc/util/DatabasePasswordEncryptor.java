package com.pmp.hrsrc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DatabasePasswordEncryptor {
    private static final Logger logger = LoggerFactory.getLogger(DatabasePasswordEncryptor.class);
    private static final String KEY = "HrSrc2024SecretKey";  // 16字节密钥
    
    public static String encrypt(String text) {
        try {
            logger.debug("Encrypting text: {}", text);
            // 确保密钥长度为16字节
            byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
            byte[] paddedKey = new byte[16];
            System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 16));
            
            SecretKeySpec secretKey = new SecretKeySpec(paddedKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            String result = Base64.getEncoder().encodeToString(encryptedBytes);
            logger.debug("Encrypted result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Encryption failed", e);
            throw new RuntimeException("加密失败", e);
        }
    }

    public static String decrypt(String encryptedText) {
        try {
            logger.debug("Decrypting text: {}", encryptedText);
            // 确保密钥长度为16字节
            byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
            byte[] paddedKey = new byte[16];
            System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 16));
            
            SecretKeySpec secretKey = new SecretKeySpec(paddedKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String result = new String(decryptedBytes);
            logger.debug("Decrypted result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Decryption failed", e);
            throw new RuntimeException("解密失败", e);
        }
    }
/*
    public static void main(String[] args) {
        try {
            // 数据库账号密码
          //  String dbUsername = "root";  // 改成您的数据库用户名
          //  String dbPassword = "Tonny@123456";  // 改成您的数据库密码
            String dbUsername = "admin";  // 改成您的数据库用户名
            String dbPassword = "0cb[pY*s1-z7:(>E_RzmVLykM}LX";  // 改成您的数据库密码
            // 加密
            String encryptedUsername = encrypt(dbUsername);
            String encryptedPassword = encrypt(dbPassword);
            
            System.out.println("=== 加密结果 ===");
            System.out.println("原始用户名: " + dbUsername);
            System.out.println("加密后用户名: " + encryptedUsername);
            System.out.println("原始密码: " + dbPassword);
            System.out.println("加密后密码: " + encryptedPassword);
            
            // 验证解密
            System.out.println("\n=== 解密验证 ===");
            System.out.println("解密后用户名: " + decrypt(encryptedUsername));
            System.out.println("解密后密码: " + decrypt(encryptedPassword));
        } catch (Exception e) {
            System.out.println("加密/解密过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
*/
} 