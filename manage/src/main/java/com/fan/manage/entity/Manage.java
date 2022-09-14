package com.fan.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (Manage)实体类
 *
 * @author makejava
 * @since 2022-08-29 21:12:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manage implements Serializable {
    private static final long serialVersionUID = 788010435754816713L;

    private Long mId;
    /**
     * 账号
     */
    private String account;
    /**
     * 登录密码
     */
    private String loginPw;
    /**
     * 账号名称
     */
    private String accName;
    /**
     * 支付密码
     */
    private String payPw;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 修改时间
     */
    private Date updatetime;

}

