package com.wonderful;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wonderful.dao")
public class WonderfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(WonderfulApplication.class, args);
    }

}
