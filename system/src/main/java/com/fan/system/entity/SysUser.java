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
 * 用户表(SysUser)实体类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysUser extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -10428001804861385L;
    /**
    * 用户id
    */
    @TableId(value = "user_id",type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
    * 用户名称
    */
    private String username;
    /**
    * 用户密码
    */
    private String password;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 手机号
    */
    private String phone;
    /**
    * 性别 0 男 1 女 2保密
    */
    private Integer sex;
    /**
    * 头像
    */
    private Object avatar;
    /**
    * 用户状态 0正常 1禁用
    */
    private Integer userStatus;

    /**
     *  用户角色Id
     */
    @TableField(exist = false)
    private Long roleId;
}