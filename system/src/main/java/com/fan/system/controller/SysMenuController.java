package com.fan.system.controller;

import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.entity.SysMenu;
import com.fan.system.service.SysMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysMenu)表控制层
 *
 * @author fgq
 * @since 2021-12-22 14:13:30
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("routers")
    public FanResponse getUserRouters(){
        return sysMenuService.getMenuByUserId();
    }

    @ApiOperation(value = "通过用户id和搜索条件查询菜单列表")
    @GetMapping
    public FanResponse list(PageVo pageVo, SysMenu sysMenu){
        return sysMenuService.getMenuList(pageVo,sysMenu);
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping
    public FanResponse addMenu(@RequestBody SysMenu sysMenu){
        return sysMenuService.addMenu(sysMenu);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping
    public FanResponse updateMenu(@RequestBody SysMenu sysMenu){
//        SysMenu sysMenu = JSONObject.parseObject(json,SysMenu.class);
        return sysMenuService.updateMenu(sysMenu);
    }

    @ApiOperation(value = "删除一个或多个菜单")
    @DeleteMapping("/{ids}")
    public FanResponse deleteMenus(@PathVariable Long[] ids){
        return sysMenuService.deleteMenus(ids);
    }

    @ApiOperation(value = "修改菜单状态")
    @PutMapping("changeState")
    public FanResponse changeState(@RequestBody SysMenu sysMenu){
        return sysMenuService.changeState(sysMenu);
    }
}