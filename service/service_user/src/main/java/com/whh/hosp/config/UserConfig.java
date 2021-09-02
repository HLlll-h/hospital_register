package com.whh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@MapperScan("com.whh.hosp.dao")
public class UserConfig {

    public static void main(String[] args) {
        System.out.println(StringUtils.isEmpty(null));
    }



}
