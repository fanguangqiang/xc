package com.fan.photo.feign;

import com.fan.common.constant.FanConstant;
import com.fan.photo.config.FeignConfig;
import com.fan.photo.feign.impl.IUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fgq
 * @date 2022/6/6
 */
@FeignClient(value = FanConstant.SYSTEM,contextId = "SystemController-getUploadFilePath", fallbackFactory = IUserServiceFallback.class,configuration = FeignConfig.class)
public interface IFileService {

    @RequestMapping("system/getUploadFilePath")
    String getUploadFilePath();
}
