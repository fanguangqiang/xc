package com.fan.photo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Photo)实体类
 *
 * @author makejava
 * @since 2022-02-28 20:36:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo implements Serializable {
    private static final long serialVersionUID = -19501024104931767L;
    /**
    * 相册ID
    */
    @TableId(value = "photo_id",type = IdType.AUTO)
    private Long photoId;
    /**
    * 相册名称
    */
    private String photoName;
    /**
    * 相册状态 1 隐藏 0 显示
    */
    private Integer state;
    /**
    * 相册类型  1 默认
    */
    private Integer type;
    /**
     * 删除状态 0 正常 1 删除
     */
    private Integer delState;
    /**
    * 创建人
    */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 相册封面
     */
    private String url;
}