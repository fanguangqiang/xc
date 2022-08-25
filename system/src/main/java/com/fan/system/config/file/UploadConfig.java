//package com.fan.system.config.file;
//
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.multipart.MultipartResolver;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//
///**
// * @author fgq
// * @date 2022/4/16
// */
//@Configuration
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
//public class UploadConfig {
//    //显示声明CommonsMultipartResolver为mutipartResolver
//    @Bean(name = "commonsMultipartResolver")
//    public MultipartResolver commonsMultipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("UTF-8");
//        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
//        resolver.setResolveLazily(true);
//        resolver.setMaxInMemorySize(40960);
//        //上传文件大小 100M 100*1024*1024
//        resolver.setMaxUploadSize(100 * 1024 * 1024);
//        return resolver;
//    }
//
//
//}
