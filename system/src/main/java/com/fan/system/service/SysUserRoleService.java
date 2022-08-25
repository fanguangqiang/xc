package com.fan.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.system.entity.SysUserRole;

/**
 * 用户角色中间表(SysUserRole)表服务接口
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据userId查询用户对应的角色
     * @param userId 用户id
     * @return 返回角色id
     */
    public Long getRoleIdByUserId(Long userId);
    /**
     * 添加用户角色关系
     * @param userId 用户id
     * @param roleId 角色id
     * @return 返回
     */
    public void addUserRole(Long userId,Long roleId);

    /**
     * 删除
     * @param userId 用户id
     */
    public void deleteUserRole(Long userId);
}