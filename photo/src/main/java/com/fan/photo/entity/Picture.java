package com.fan.photo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Picture)实体类
 *
 * @author makejava
 * @since 2022-02-28 20:36:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture implements Serializable {
    private static final long serialVersionUID = 116325573171046429L;
    /**
    * 照片ID
    */
    @TableId(value = "pic_id",type = IdType.AUTO)
    private Long picId;
    /**
    * 照片名称
    */
    private String picName;
    /**
    * 路径
    */
    private String path;
    /**
    * http访问路径
    */
    private String url;
    /**
    * 文件大小
    */
    private Double fileSize;
    /**
    * 是否喜爱 0 否 1 是
    */
    private Integer favorite;
    /**
    * 相册ID
    */
    private Long photoId;
    /**
    * 图片状态 1 隐藏 0 显示
    */
    private Integer state;
    /**
    * 图片类型 
    */
    private String type;
    /**
    * 删除状态 0 正常 1 删除
    */
    private Integer delState;
    /**
    * 创建人
    */
    private Long userId;

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
}