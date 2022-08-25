package com.fan.system.entity;

import com.fan.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 操作日志表(SysOperLog)实体类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 571391607626612518L;
    /**
    * 操作日志id
    */
    private Long operlogId;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 操作内容
    */
    private String operation;
    /**
    * 操作ip
    */
    private String operIp;
    /**
    * 操作地点
    */
    private String location;
    /**
    * 操作类型 0其他 1 新增 2修改 3删除
    */
    private Integer operType;
    /**
    * 操作方法
    */
    private String operMethod;
    /**
    * 请求方式
    */
    private String requestMode;
    /**
    * 请求URL
    */
    private String url;
    /**
    * 请求参数
    */
    private String params;
    /**
    * 返回结果
    */
    private String result;
    /**
    * 操作状态 0 正常 1 异常
    */
    private Integer operStatus;
    /**
    * 错误信息
    */
    private String errorMsg;

}