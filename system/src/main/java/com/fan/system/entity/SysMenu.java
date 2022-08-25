package com.fan.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fan.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (SysMenu)实体类
 *
 * @author fgq
 * @since 2021-12-22 14:13:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -44436620618115037L;
    /**
     * 菜单id
     */
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Long menuId;
    /**
    * 菜单名称
    */
    private String menuName;
    /**
    * 父菜单id
    */
    private Long parentId;
    /**
    * 路由地址
    */
    private String path;
    /**
    * 组件路径
    */
    private String component;
    /**
    * 权限标识
    */
    private String perms;
    /**
    * 菜单图标
    */
    private String icon;
    /**
    * 菜单状态 1显示（启用） 0 隐藏 （禁用）
    */
    private Integer menuState;
    /**
    * 菜单类型 0 目录 1 菜单 2 按钮
    */
    private Integer menuType;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}