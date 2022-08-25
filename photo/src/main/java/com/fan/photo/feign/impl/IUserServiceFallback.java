package com.fan.photo.feign.impl;

import com.fan.photo.feign.IUserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fgq
 * @date 2022/6/6
 */
@Slf4j
//@Fallback
public class IUserServiceFallback implements FallbackFactory<IUserService> {

    @Override
    public IUserService create(Throwable throwable) {
        return () -> {
            log.error("获取用户ID失败", throwable);
            return null;
        };
    }

}