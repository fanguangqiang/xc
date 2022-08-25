package com.fan.system.controller;

import com.fan.common.vo.FanResponse;
import com.fan.system.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用文件上传 controller
 * @author fgq
 * @date 2022/4/14
 */
@RequestMapping("upload")
@RestController
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;

    @PostMapping
    public FanResponse upload(@RequestParam("file") MultipartFile file){
//        MultipartFile multipartFile = request.getFile("file");
        return sysFileService.upload(file);
    }


}
