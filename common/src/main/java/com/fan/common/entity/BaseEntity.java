package com.fan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fgq
 * @date 2021/12/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 更新人
     */
    private Long updateUser;
}
