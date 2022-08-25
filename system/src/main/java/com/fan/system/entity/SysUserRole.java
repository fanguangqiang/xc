package com.fan.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户角色中间表(SysUserRole)实体类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 675797688885885995L;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 角色id
    */
    private Long roleId;

}