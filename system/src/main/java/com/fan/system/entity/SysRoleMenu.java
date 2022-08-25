package com.fan.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色菜单中间表(SysRoleMenu)实体类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = -29952229572047193L;
    /**
    * 角色id
    */
    private Long roleId;
    /**
    * 菜单id
    */
    private Long menuId;

}