package com.fan.photo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.photo.entity.Photo;

/**
 * (Photo)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 20:36:07
 */
public interface PhotoService extends IService<Photo> {

    /**
     * 获取相册列表
     * @param pageVo
     * @param photo
     * @return
     */
    FanResponse getPhotoList(PageVo pageVo, Photo photo);

    /**
     * 添加相册
     * @param photo
     * @return
     */
    FanResponse addPhoto(Photo photo);

    /**
     * 更新相册信息
     * @param photo
     * @return
     */
    FanResponse updatePhoto(Photo photo);

    /**
     * 删除一个或多个相册
     * @param ids
     * @return
     */
    FanResponse deletePhotoes(Long[] ids);
}