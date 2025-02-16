package com.hyeobjin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hyeobjin")
public class HyeobjinApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyeobjinApplication.class, args);
    }
}
