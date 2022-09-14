package com.fan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.common.utils.DateUtils;
import com.fan.common.utils.RedisUtils;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.FanUtils;
import com.fan.system.dao.SysMenuDao;
import com.fan.system.entity.SysMenu;
import com.fan.system.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * (SysMenu)表服务实现类
 *
 * @author fgq
 * @since 2021-12-22 14:13:30
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao,SysMenu> implements SysMenuService {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SysMenuDao sysMenuDao;

    /**
     * 根据用户ID 查询菜单
     * @return 返回菜单
     */
    @Override
    public FanResponse getMenuByUserId() {
        Long userId = FanUtils.getLoginUserId();
        //查询缓存中是否有数据
        List<SysMenu> menus = (List<SysMenu>) redisUtils.get("menu_" + userId);
        if (CollectionUtils.isEmpty(menus)){
            //如果没数据，数据库中查询，并设置到缓存中
            List<SysMenu> sysMenus = this.baseMapper.getMenusByUserId(userId);
            menus = router(sysMenus);
            redisUtils.set("menu_"+userId,menus,600L);
        }
        return FanResponse.success(menus);
    }


    /**
     * 组织前端路由数据
     * @param sysMenus
     * @return
     */
    private List<SysMenu> router(List<SysMenu> sysMenus) {
        List<SysMenu> menus = new ArrayList<>();
//        菜单搜索使用
        List<Long> menuIds = new ArrayList<>();
//        父子菜单数据结构
        sysMenus.forEach(menu -> {
            if(menu.getParentId() == 0){
                menus.add(menu);
                menuIds.add(menu.getMenuId());
            }else {
                menus.forEach( m -> {
                    if(m.getMenuId() == menu.getParentId()){
                        m.getChildren().add(menu);
                        menuIds.add(menu.getMenuId());
                    }
                });
//                菜单搜索添加父菜单
                if(!menuIds.contains(menu.getParentId())){
                    SysMenu sysMenu = this.baseMapper.selectById(menu.getParentId());
                    sysMenu.getChildren().add(menu);
                    menuIds.add(menu.getMenuId());
                    menus.add(sysMenu);
                }
            }
        });
        return menus;
    }

    /**
     * 根据Menu条件查询菜单列表
     * @return
     * @param pageVo
     * @param sysMenu
     */
    @Override
    public FanResponse getMenuList(PageVo pageVo, SysMenu sysMenu) {
        IPage<SysMenu> page = new Page<>(pageVo.getPageNum(),pageVo.getPageSize());
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        if(!Objects.equals(sysMenu.getMenuName(),null) && !Objects.equals(sysMenu.getMenuName(),"")){
            wrapper.eq("menu_name",sysMenu.getMenuName());
        }
        if(!Objects.equals(sysMenu.getMenuType(),null)){
            wrapper.eq("menu_type",sysMenu.getMenuType());
        }
        IPage<SysMenu> menus = this.baseMapper.selectPage(page,wrapper);
        menus.setRecords(router(menus.getRecords()));
        return FanResponse.successPage(menus);
    }

    /**
     * 添加菜单
     * @param sysMenu 菜单信息
     * @return 添加成功或失败
     */
    @Override
    public FanResponse addMenu(SysMenu sysMenu) {
        Long userId = FanUtils.getLoginUserId();
        sysMenu.setCreateTime(DateUtils.getNowDate());
        sysMenu.setCreateUser(userId);
        sysMenu.setUpdateTime(DateUtils.getNowDate());
        sysMenu.setUpdateUser(userId);
        if(StringUtils.isBlank(sysMenu.getComponent())){
            sysMenu.setComponent("home");
        }
        int insert = this.baseMapper.insert(sysMenu);
        if(insert>0){
            return FanResponse.success("添加成功");
        }
        return FanResponse.error("添加失败");
    }

    /**
     * 修改菜单
     * @param sysMenu 修改信息
     * @return 修改结果
     */
    @Override
    public FanResponse updateMenu(SysMenu sysMenu) {
        Long userId = FanUtils.getLoginUserId();
        sysMenu.setUpdateUser(userId);
        sysMenu.setUpdateTime(DateUtils.getNowDate());
        int update = this.baseMapper.updateById(sysMenu);
        if(update>0){
            return FanResponse.success("修改成功");
        }
        return FanResponse.error("修改失败");
    }

    /**
     * 删除菜单
     * @param ids 菜单id数组
     * @return 返回删除结果
     */
    @Override
    public FanResponse deleteMenus(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        for (int i = ids.length - 1; i >= 0; i--) {
            QueryWrapper<SysMenu> wrapper = new QueryWrapper();
            wrapper.eq("parent_id",ids[i]);
            List<SysMenu> sysMenus = this.baseMapper.selectList(wrapper);
            if(sysMenus!=null && sysMenus.size()>0){
                return FanResponse.error("删除菜单失败,请先删除子菜单！");
            }
        }
        int batchIds = this.baseMapper.deleteBatchIds(list);
        if(batchIds>0){
            return FanResponse.success("删除成功");
        }
        return FanResponse.error("删除失败");
    }

    /**
     * 改变菜单状态
     * @param sysMenu 菜单信息
     * @return 返回改变结果
     */
    @Override
    public FanResponse changeState(SysMenu sysMenu) {
        int i = this.baseMapper.updateById(sysMenu);
        String str =  sysMenu.getMenuState() == 1 ? "启用" : "禁用";
        if(i>0){
            return FanResponse.success(str + "成功");
        }
        return FanResponse.error(str + "失败");
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Override
    public FanResponse getMenus() {
        List<SysMenu> sysMenus = this.baseMapper.selectList(null);
        List<SysMenu> menus = router(sysMenus);
        return FanResponse.success(menus);
    }
}