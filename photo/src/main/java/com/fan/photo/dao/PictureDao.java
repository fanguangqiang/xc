package com.fan.photo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fan.photo.entity.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Picture)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 20:36:09
 */
public interface PictureDao extends BaseMapper<Picture> {

    int updateBatchIds(@Param("list") List<Long> list,@Param("delState") int delState);

}