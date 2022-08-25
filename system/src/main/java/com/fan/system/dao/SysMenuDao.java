package com.fan.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fan.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysMenu)表数据库访问层
 *
 * @author fgq
 * @since 2021-12-22 14:13:28
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {

    /**
     * 根据用户ID查询菜单
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> getMenusByUserId(Long userId);

    /**
     * 根据菜单条件和用户id查询菜单
     *
     * @param page
     * @param sysMenu 菜单条件
     * @param userId 用户ID
     * @return 菜单列表
     */
    IPage<SysMenu> getMenuList(IPage<SysMenu> page, @Param("sysMenu") SysMenu sysMenu, @Param("userId") Long userId);
}