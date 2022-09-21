package com.pundir.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.pundir.usermanagement"})
@EntityScan("com.pundir.usermanagement.entities")
public class UserMgmtApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMgmtApplication.class, args);
    }
}
