package com.fan.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.system.entity.SysRoleMenu;

/**
 * 角色菜单中间表(SysRoleMenu)表服务接口
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
public interface SysRoleMenuService  extends IService<SysRoleMenu> {

    public Long[] getMenusByRoleId(Long roleId);
    /**
     * 批量插入
     * @param roleId 角色id
     * @param menuIds 菜单数组
     */
    public void insertRoleMenu(Long roleId,Long[] menuIds);
    /**
     * 批量删除
     * @param roleId 角色id
     */
    public void deleteRoleMenu(Long roleId);
}