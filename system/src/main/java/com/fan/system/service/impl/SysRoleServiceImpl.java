package com.fan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.common.utils.DateUtils;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.dao.SysRoleDao;
import com.fan.system.entity.SysRole;
import com.fan.system.pojo.LoginParams;
import com.fan.system.service.SysRoleMenuService;
import com.fan.system.service.SysRoleService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao,SysRole> implements SysRoleService {

    @Resource
    private SysRoleMenuService roleMenuService;
    /**
     * 角色列表
     * @param pageVo
     * @param sysRole 查询条件
     * @return 返回结果
     */
    @Override
    public FanResponse getRoleListByPage(PageVo pageVo, SysRole sysRole) {
        IPage<SysRole> page = new Page<>(pageVo.getPageNum(),pageVo.getPageSize());
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (!Objects.equals(sysRole.getRoleName(),"")){
            wrapper.like("role_name",sysRole.getRoleName());
        }
        IPage<SysRole> iPage = this.baseMapper.selectPage(page, wrapper);
        return FanResponse.successPage(iPage);
    }

    /**
     * 添加角色
     *
     * @param sysRole 角色信息
     * @return 返回是否添加成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public FanResponse addRole(SysRole sysRole) {
        Long userId = ((LoginParams) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        sysRole.setCreateTime(DateUtils.getNowDate());
        sysRole.setCreateUser(userId);
        sysRole.setUpdateTime(DateUtils.getNowDate());
        sysRole.setUpdateUser(userId);
        int insert = this.baseMapper.insert(sysRole);
        //        修改角色菜单权限 需要修改中间表
        if(!Objects.equals(sysRole.getMenuIds(),null) && sysRole.getMenuIds().length>0){
            roleMenuService.deleteRoleMenu(sysRole.getRoleId());
            roleMenuService.insertRoleMenu(sysRole.getRoleId(),sysRole.getMenuIds());
        }
        if(insert>0){
            return FanResponse.success("添加成功");
        }
        return FanResponse.error("添加失败");
    }

    /**
     * 修改角色信息
     *
     * @param sysRole 角色信息
     * @return 返回是否修改成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public FanResponse updateRole(SysRole sysRole) {
        Long userId = ((LoginParams) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        sysRole.setUpdateUser(userId);
        sysRole.setUpdateTime(DateUtils.getNowDate());
//        修改角色菜单权限 需要修改中间表
        if(!Objects.equals(sysRole.getMenuIds(),null) && sysRole.getMenuIds().length>0){
            roleMenuService.deleteRoleMenu(sysRole.getRoleId());
            roleMenuService.insertRoleMenu(sysRole.getRoleId(),sysRole.getMenuIds());
        }
        int update = this.baseMapper.updateById(sysRole);
        if(update>0){
            return FanResponse.success("修改成功");
        }
        return FanResponse.error("修改失败");
    }

    /**
     * 删除角色信息
     *
     * @param ids 角色id
     * @return 返回是否删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public FanResponse deleteRoles(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        for (Long roleId: ids ) {
            roleMenuService.deleteRoleMenu(roleId);
        }
        int batchIds = this.baseMapper.deleteBatchIds(list);
        if(batchIds>0){
            return FanResponse.success("删除成功");
        }
        return FanResponse.error("删除失败");
    }

    /**
     * 改变角色状态
     *
     * @param sysRole 角色信息
     * @return 返回是否改变成功
     */
    @Override
    public FanResponse changeState(SysRole sysRole) {
        int i = this.baseMapper.updateById(sysRole);
        String str =  sysRole.getRoleStatus() == 0 ? "启用" : "禁用";
        if(i>0){
            return FanResponse.success(str + "成功");
        }
        return FanResponse.error(str + "失败");
    }

    /**
     * 查询所有角色列表
     *
     * @return
     */
    @Override
    public FanResponse getRoleList() {
        List<SysRole> roles = this.baseMapper.selectList(new QueryWrapper<SysRole>());
        return FanResponse.success(roles);
    }
}