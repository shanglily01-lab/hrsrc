package com.pmp.hrsrc.config;

import com.pmp.hrsrc.util.DatabasePasswordEncryptor;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource dataSource() {
        try {

            
            // 解密数据库用户名和密码
            String decryptedUsername = DatabasePasswordEncryptor.decrypt(username);
            String decryptedPassword = DatabasePasswordEncryptor.decrypt(password);
            

            
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(url);
            dataSource.setUsername(decryptedUsername);
            dataSource.setPassword(decryptedPassword);
            dataSource.setDriverClassName(driverClassName);
            
            // 添加额外的连接参数
            dataSource.addDataSourceProperty("useSSL", "false");
            dataSource.addDataSourceProperty("allowPublicKeyRetrieval", "true");
            dataSource.addDataSourceProperty("useJDBCCompliantTimezoneShift", "true");
            dataSource.addDataSourceProperty("serverTimezone", "UTC");
            dataSource.addDataSourceProperty("autoReconnect", "true");
            dataSource.addDataSourceProperty("failOverReadOnly", "false");
            dataSource.addDataSourceProperty("connectTimeout", "30000");
            dataSource.addDataSourceProperty("socketTimeout", "60000");
            
            // 添加连接测试
            try {
                dataSource.getConnection().close();
                logger.info("Database connection test successful");
            } catch (Exception e) {
                logger.error("Database connection test failed", e);
            }
            
            return dataSource;
        } catch (Exception e) {
            logger.error("Failed to decrypt database credentials", e);
            throw new RuntimeException("Failed to decrypt database credentials", e);
        }
    }
} 