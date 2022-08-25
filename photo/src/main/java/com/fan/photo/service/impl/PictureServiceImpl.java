package com.fan.photo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.photo.dao.PictureDao;
import com.fan.photo.entity.Picture;
import com.fan.photo.feign.IFileService;
import com.fan.photo.feign.IUserService;
import com.fan.photo.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * (Picture)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 20:36:09
 */
@Slf4j
@Service("pictureService")
public class PictureServiceImpl extends ServiceImpl<PictureDao, Picture> implements PictureService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private PictureDao pictureDao;
    /**
     * 添加图片
     * @param picture
     * @return
     */
    @Override
    public FanResponse addPic(Picture picture) {
        String loginUserId = userService.getLoginUserId();
        picture.setUserId(Long.valueOf(loginUserId));
        picture.setDelState(0);
        int insert = this.baseMapper.insert(picture);
        if(insert>0){
            return FanResponse.success("添加成功");
        }
        return FanResponse.error("添加失败");
    }

    /**
     * 根据条件查询图片
     *
     * @param pageVo
     * @param picture
     * @return
     */
    @Override
    public FanResponse getPicList(PageVo pageVo, Picture picture) {
        IPage<Picture> page = new Page<>(pageVo.getPageNum(),pageVo.getPageSize());
        QueryWrapper<Picture> wrapper = new QueryWrapper<>();
        if (!Objects.equals(picture.getPhotoId(),null)){
            wrapper.eq("photo_id",picture.getPhotoId());
        }
        if (StringUtils.isNotBlank(picture.getPicName())){
            wrapper.like("pic_name",picture.getPicName());
        }
        if (picture.getFavorite()!=null){
            wrapper.eq("favorite",picture.getFavorite());
        }
        if (picture.getDelState()!=null){
            wrapper.eq("del_state",picture.getDelState());
        }else{
            wrapper.ne("del_state",1);
        }
        IPage<Picture> iPage = this.baseMapper.selectPage(page, wrapper);
        return FanResponse.successPage(iPage);
    }

    /**
     * 更新照片信息
     *
     * @param picture
     * @return
     */
    @Override
    public FanResponse updatePic(Picture picture) {
//        修改删除原照片
        Picture pic = this.baseMapper.selectById(picture.getPicId());
        File file = new File(fileService.getUploadFilePath()+pic.getPath());
        boolean r = file.exists() ? file.delete():false;
        log.info("删除成功");
        int i = this.baseMapper.updateById(picture);
        if(i>0){
            return FanResponse.success("修改成功！");
        }
        return FanResponse.error("修改失败");
    }

    /**
     * 删除照片
     * @param ids
     * @return
     */
    @Override
    public FanResponse deletePics(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        int batchIds = this.pictureDao.updateBatchIds(list,1);
        if(batchIds>0){
            return FanResponse.success("删除成功") ;
        }
        return FanResponse.error("删除失败");
    }

    /**
     * 更新收藏状态
     * @param picture
     * @return
     */
    @Override
    public FanResponse updateFavorite(Picture picture) {
        UpdateWrapper<Picture> wrapper = new UpdateWrapper<>();
        wrapper.eq("pic_id",picture.getPicId());
        wrapper.set("favorite",picture.getFavorite());
        int batchIds = this.baseMapper.update(picture,wrapper);
        if(batchIds>0){
            return FanResponse.success(picture.getFavorite()==0? "取消收藏":"收藏成功");
        }
        return FanResponse.error("操作失败");
    }

    /**
     * 还原一个或多个照片
     *
     * @param ids
     * @return
     */
    @Override
    public FanResponse reductionPics(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        int batchIds = this.pictureDao.updateBatchIds(list,0);
        if(batchIds>0){
            return FanResponse.success("还原成功") ;
        }
        return FanResponse.error("还原失败");
    }

    /**
     * 物理删除照片
     *
     * @param ids
     * @return
     */
    @Override
    public FanResponse delPics(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        int deleteBatchIds = this.baseMapper.deleteBatchIds(list);
        if(deleteBatchIds>0){
            return FanResponse.success("删除成功") ;
        }
        return FanResponse.error("删除失败");
    }
}