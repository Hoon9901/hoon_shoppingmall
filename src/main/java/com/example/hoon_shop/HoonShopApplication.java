package com.example.hoon_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class HoonShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoonShopApplication.class, args);
    }
}
