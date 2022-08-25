package com.fan.photo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fgq
 * @date 2022/4/12
 * @EnableFeignClients 开启Feign Client功能(远程调用)
 */
@EnableFeignClients
@ComponentScan({"com.fan"})
@MapperScan("com.fan.photo.dao")
@SpringBootApplication
public class PhotoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoApplication.class,args);
    }
}
