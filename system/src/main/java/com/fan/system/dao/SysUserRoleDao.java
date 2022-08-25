package com.fan.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fan.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户角色中间表(SysUserRole)表数据库访问层
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

}