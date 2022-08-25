package com.fan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.system.dao.SysRoleMenuDao;
import com.fan.system.entity.SysRoleMenu;
import com.fan.system.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色菜单中间表(SysRoleMenu)表服务实现类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao,SysRoleMenu> implements SysRoleMenuService {

    @Override
    public Long[] getMenusByRoleId(Long roleId) {
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        List<SysRoleMenu> sysRoleMenus = this.baseMapper.selectList(wrapper);
        Long[] menus = new Long[sysRoleMenus.size()];
        for (int i = 0; i < sysRoleMenus.size(); i++) {
            menus[i] = sysRoleMenus.get(i).getMenuId();
        }
        return menus;
    }

    /**
     * 批量插入
     *
     * @param roleId  角色id
     * @param menuIds 菜单数组
     */
    @Override
    public void insertRoleMenu(Long roleId, Long[] menuIds) {
        for (int i = 0; i < menuIds.length; i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu(roleId,menuIds[i]);
            this.baseMapper.insert(sysRoleMenu);
        }
    }

    /**
     * 批量删除
     *
     * @param roleId 角色id
     */
    @Override
    public void deleteRoleMenu(Long roleId) {
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        this.baseMapper.delete(wrapper);
    }
}