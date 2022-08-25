package com.fan.system.controller;

import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.entity.SysRole;
import com.fan.system.service.SysRoleMenuService;
import com.fan.system.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色表(SysRole)表控制层
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@RestController
@RequestMapping("sysRole")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @ApiOperation(value = "通过搜索条件查询角色列表")
    @GetMapping
    public FanResponse list(PageVo pageVo, SysRole sysRole){
        return sysRoleService.getRoleListByPage(pageVo,sysRole);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping
    public FanResponse addRole(@RequestBody SysRole sysRole){
        return sysRoleService.addRole(sysRole);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping
    public FanResponse updateRole(@RequestBody SysRole sysRole){
        return sysRoleService.updateRole(sysRole);
    }

    @ApiOperation(value = "删除一个或多个角色")
    @DeleteMapping("/{ids}")
    public FanResponse deleteRoles(@PathVariable Long[] ids){
        return sysRoleService.deleteRoles(ids);
    }

    @ApiOperation(value = "根据角色id获取菜单权限")
    @GetMapping("getMenus/{roleId}")
    public FanResponse getMenus(@PathVariable("roleId")Long roleId){
        Long[] menusByRoleId = sysRoleMenuService.getMenusByRoleId(roleId);
        return FanResponse.success(menusByRoleId);
    }

    @ApiOperation(value = "修改角色状态")
    @PutMapping("changeState")
    public FanResponse changeState(@RequestBody SysRole sysRole){
        return sysRoleService.changeState(sysRole);
    }

    @ApiOperation(value = "查询所有角色列表")
    @GetMapping("list")
    public FanResponse list(){
        return sysRoleService.getRoleList();
    }
}