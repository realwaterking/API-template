package com.chzu.apitemplate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@SpringBootApplication
@MapperScan("com.chzu.apitemplate.mapper")
public class ApiTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiTemplateApplication.class, args);
    }

}
