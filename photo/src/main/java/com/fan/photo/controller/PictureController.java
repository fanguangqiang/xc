package com.fan.photo.controller;

import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.photo.entity.Picture;
import com.fan.photo.service.PictureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Picture)表控制层
 *
 * @author makejava
 * @since 2022-02-28 20:36:09
 */
@RestController
@RequestMapping("picture")
public class PictureController {

    @Resource
    private PictureService pictureService;

    @ApiOperation(value = "通过搜索条件查询图片列表")
    @GetMapping
    public FanResponse list(PageVo pageVo, Picture picture){
        return pictureService.getPicList(pageVo,picture);
    }

    @ApiOperation(value = "添加照片")
    @PostMapping
    public FanResponse add(@RequestBody Picture picture){
        return pictureService.addPic(picture);
    }

    @ApiOperation(value = "修改照片信息")
    @PutMapping
    public FanResponse updateRole(@RequestBody Picture picture){
        return pictureService.updatePic(picture);
    }

    @ApiOperation(value = "逻辑删除一个或多个照片")
    @DeleteMapping("/{ids}")
    public FanResponse deletePics(@PathVariable Long[] ids){
        return pictureService.deletePics(ids);
    }

    @ApiOperation(value = "是否收藏照片")
    @PutMapping("favorite")
    public FanResponse updateFavorite(@RequestBody Picture picture){
        return pictureService.updateFavorite(picture);
    }

    @ApiOperation(value = "还原一个或多个照片")
    @PutMapping("/{ids}")
    public FanResponse reduction(@PathVariable Long[] ids){
        return pictureService.reductionPics(ids);
    }

    @ApiOperation(value = "物理删除一个或多个照片")
    @DeleteMapping("del/{ids}")
    public FanResponse delPics(@PathVariable Long[] ids){
        return pictureService.delPics(ids);
    }
}