package com.fan.photo.feign.impl;

import com.fan.photo.feign.IFileService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fgq
 * @date 2022/6/6
 */
@Slf4j
//@Fallback
public class IFileServiceFallback implements FallbackFactory<IFileService> {

    @Override
    public IFileService create(Throwable throwable) {
        return () -> {
            log.error("获取文件路径失败", throwable);
            return null;
        };
    }

}