package com.fan.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.system.entity.SysOperLog;
import com.fan.system.dao.SysOperLogDao;
import com.fan.system.service.SysOperLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志表(SysOperLog)表服务实现类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@Service("sysOperLogService")
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogDao,SysOperLog> implements SysOperLogService {

}