package com.fan.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fan.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
public interface SysUserDao extends BaseMapper<SysUser> {

}