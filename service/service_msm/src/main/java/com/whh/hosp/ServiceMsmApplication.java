package com.whh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 阿里云短信模块
 * 申请不了 用不了(当作没有吧)
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//取消数据源自动配置
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.whh"})
public class ServiceMsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMsmApplication.class,args);
    }
}
