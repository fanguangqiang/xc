package com.fan.photo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.photo.entity.Picture;

/**
 * (Picture)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 20:36:09
 */
public interface PictureService extends IService<Picture> {

    /**
     * 添加图片
     * @param picture
     * @return
     */
    FanResponse addPic(Picture picture);

    /**
     * 根据条件查询图片
     * @param pageVo
     * @param picture
     * @return
     */
    FanResponse getPicList(PageVo pageVo, Picture picture);

    /**
     * 更新照片信息
     * @param picture
     * @return
     */
    FanResponse updatePic(Picture picture);

    /**
     * 逻辑删除照片
     * @param ids
     * @return
     */
    FanResponse deletePics(Long[] ids);

    /**
     * 更新收藏状态
     * @param picture
     * @return
     */
    FanResponse updateFavorite(Picture picture);

    /**
     * 还原一个或多个照片
     * @param ids
     * @return
     */
    FanResponse reductionPics(Long[] ids);

    /**
     * 物理删除照片
     * @param ids
     * @return
     */
    FanResponse delPics(Long[] ids);
}