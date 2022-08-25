package com.fan.system.controller.feign;

import com.fan.system.FanUtils;
import com.fan.system.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fgq
 * @date 2022/7/9
 */
@RequestMapping("system")
@RestController
public class SystemController {

    @Autowired
    private SysFileService sysFileService;

    @GetMapping("getLoginUserId")
    public String getLoginUserId(){
        return String.valueOf(FanUtils.getLoginUserId());
    }

    @GetMapping("getUploadFilePath")
    public String getUploadFilePath(){
        return sysFileService.getUploadFilePath();
    }
}
