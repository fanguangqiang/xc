package com.fan.photo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.photo.dao.PhotoDao;
import com.fan.photo.entity.Photo;
import com.fan.photo.feign.IUserService;
import com.fan.photo.service.PhotoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * (Photo)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 20:36:08
 */
@Service("photoService")
public class PhotoServiceImpl extends ServiceImpl<PhotoDao,Photo> implements PhotoService {

    @Autowired
    private IUserService userService;

    /**
     * 获取相册列表
     *
     * @param pageVo
     * @param photo
     * @return
     */
    @Override
    public FanResponse getPhotoList(PageVo pageVo, Photo photo) {
        IPage<Photo> page = new Page<>(pageVo.getPageNum(),pageVo.getPageSize());
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(photo.getPhotoName())){
            wrapper.like("photo_name",photo.getPhotoName());
        }
        IPage<Photo> iPage = this.baseMapper.selectPage(page, wrapper);
        return FanResponse.successPage(iPage);
    }

    /**
     * 添加相册
     *
     * @param photo
     * @return
     */
    @Override
    public FanResponse addPhoto(Photo photo) {
        photo.setCreateTime(new Date());
        photo.setDelState(0);
        String loginUserId = userService.getLoginUserId();
        photo.setUserId(Long.valueOf(loginUserId));
        photo.setUpdateTime(new Date());
        int insert = this.baseMapper.insert(photo);
        if(insert>0){
            return FanResponse.success("添加成功");
        }
        return FanResponse.error("添加失败");
    }

    /**
     * 更新相册信息
     *
     * @param photo
     * @return
     */
    @Override
    public FanResponse updatePhoto(Photo photo) {
        return null;
    }

    /**
     * 删除一个或多个相册
     *
     * @param ids
     * @return
     */
    @Override
    public FanResponse deletePhotoes(Long[] ids) {
        return null;
    }
}