package com.zeeyeh.vortexiaserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zeeyeh.vortexiaserver.mapper")
public class VortexiaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VortexiaServerApplication.class, args);
    }

}
