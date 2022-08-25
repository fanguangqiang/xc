package com.fan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.system.dao.SysUserRoleDao;
import com.fan.system.entity.SysUserRole;
import com.fan.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色中间表(SysUserRole)表服务实现类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao,SysUserRole> implements SysUserRoleService {

    /**
     * 根据userId查询用户对应的角色
     * @param userId 用户id
     * @return 返回角色id
     */
    @Override
    public Long getRoleIdByUserId(Long userId){
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        SysUserRole sysUserRole = this.baseMapper.selectOne(wrapper);
        if(sysUserRole!=null){
            return sysUserRole.getRoleId();
        }
        return null;
    }

    /**
     * 添加用户角色关系
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 返回
     */
    @Override
    public void addUserRole(Long userId, Long roleId) {
        SysUserRole userRole = new SysUserRole(userId,roleId);
        this.baseMapper.insert(userRole);
    }

    /**
     * 删除
     *
     * @param userId 用户id
     */
    @Override
    public void deleteUserRole(Long userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        this.baseMapper.delete(wrapper);
    }

}