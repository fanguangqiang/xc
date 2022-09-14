package com.fan.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.entity.SysMenu;

/**
 * (SysMenu)表服务接口
 *
 * @author fgq
 * @since 2021-12-22 14:13:29
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据用户ID 查询菜单
     * @return
     */
    FanResponse getMenuByUserId();

    /**
     * 根据Menu条件查询菜单列表
     * @return 返回列表
     * @param pageVo
     * @param sysMenu 菜单条件
     */
    FanResponse getMenuList(PageVo pageVo, SysMenu sysMenu);

    /**
     * 添加菜单
     * @param sysMenu 菜单信息
     * @return 返回添加成功失败
     */
    FanResponse addMenu(SysMenu sysMenu);

    /**
     * 修改菜单
     * @param sysMenu 修改信息
     * @return 返回是否修改成功
     */
    FanResponse updateMenu(SysMenu sysMenu);

    /**
     * 删除一个或多个菜单
     * @param ids 菜单id数组
     * @return 返回是否删除成功
     */
    FanResponse deleteMenus(Long[] ids);

    /**
     * 修改菜单状态 启用禁用菜单
     * @param sysMenu 菜单信息
     * @return 返回是否成功
     */
    FanResponse changeState(SysMenu sysMenu);

    /**
     * 查询所有菜单
     * @return
     */
    FanResponse getMenus();
}