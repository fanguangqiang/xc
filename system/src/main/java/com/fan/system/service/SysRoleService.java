package com.fan.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.entity.SysRole;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 角色列表
     *
     * @param pageVo
     * @param sysRole 查询条件
     * @return 返回结果
     */
    FanResponse getRoleListByPage(PageVo pageVo, SysRole sysRole);

    /**
     * 添加角色
     * @param sysRole 角色信息
     * @return 返回是否添加成功
     */
    FanResponse addRole(SysRole sysRole);

    /**
     * 修改角色信息
     * @param sysRole 角色信息
     * @return 返回是否修改成功
     */
    FanResponse updateRole(SysRole sysRole);

    /**
     * 删除角色信息
     * @param ids 角色id
     * @return 返回是否删除成功
     */
    FanResponse deleteRoles(Long[] ids);

    /**
     * 改变角色状态
     * @param sysRole 角色信息
     * @return 返回是否改变成功
     */
    FanResponse changeState(SysRole sysRole);

    /**
     * 查询所有角色列表
     * @return
     */
    FanResponse getRoleList();
}