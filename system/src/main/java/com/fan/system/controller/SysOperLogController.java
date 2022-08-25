package com.fan.system.controller;

import com.fan.system.entity.SysOperLog;
import com.fan.system.service.SysOperLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 操作日志表(SysOperLog)表控制层
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@RestController
@RequestMapping("sysOperLog")
public class SysOperLogController {
    /**
     * 服务对象
     */
    @Resource
    private SysOperLogService sysOperLogService;


}