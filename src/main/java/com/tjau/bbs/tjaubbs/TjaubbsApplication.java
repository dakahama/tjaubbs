package com.tjau.bbs.tjaubbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.tjau.bbs.tjaubbs.mapper")
@SpringBootApplication
public class TjaubbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TjaubbsApplication.class, args);
    }

}
