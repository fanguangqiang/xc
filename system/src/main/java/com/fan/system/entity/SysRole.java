package com.fan.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fan.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色表(SysRole)实体类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -96787832842456377L;
    /**
    * 角色id
    */
    @TableId(value = "role_id",type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    private Long roleId;
    /**
    * 角色名称
    */
    private String roleName;
    /**
    * 角色状态 0正常 1禁用
    */
    private Integer roleStatus;

    /**
     * 菜单权限id
     */
    @TableField(exist = false)
    private Long[] menuIds;
}


