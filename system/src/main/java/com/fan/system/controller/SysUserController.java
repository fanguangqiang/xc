package com.fan.system.controller;

import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.entity.SysUser;
import com.fan.system.service.SysUserRoleService;
import com.fan.system.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户表(SysUser)表控制层
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @ApiOperation(value = "通过搜索条件查询用户列表")
    @GetMapping
    public FanResponse list(PageVo pageVo, SysUser sysUser){
        return sysUserService.getUserList(pageVo,sysUser);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping
    public FanResponse add(@RequestBody SysUser sysUser){
        return sysUserService.addUser(sysUser);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping
    public FanResponse updateRole(@RequestBody SysUser sysUser){
        return sysUserService.updateUser(sysUser);
    }

    @ApiOperation(value = "删除一个或多个用户")
    @DeleteMapping("/{ids}")
    public FanResponse deleteRoles(@PathVariable Long[] ids){
        return sysUserService.deleteUsers(ids);
    }

    @ApiOperation(value = "根据用户id查询角色")
    @GetMapping("/{userId}")
    public FanResponse getRoleIdByUserId(@PathVariable Long userId){
        Long roleId = sysUserRoleService.getRoleIdByUserId(userId);
        return FanResponse.success(roleId);
    }

    @ApiOperation(value = "修改用户状态")
    @PutMapping("changeState")
    public FanResponse changeState(@RequestBody SysUser sysUser){
        return sysUserService.changeState(sysUser);
    }

}