package com.fan.photo.controller;

import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.photo.entity.Photo;
import com.fan.photo.service.PhotoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Photo)表控制层
 *
 * @author makejava
 * @since 2022-02-28 20:36:08
 */
@RestController
@RequestMapping("photo")
public class PhotoController {

    @Resource
    private PhotoService photoService;

    @ApiOperation(value = "通过搜索条件查询相册列表")
    @GetMapping
    public FanResponse list(PageVo pageVo, Photo photo){
        return photoService.getPhotoList(pageVo,photo);
    }

    @ApiOperation(value = "添加照片")
    @PostMapping
    public FanResponse add(@RequestBody Photo photo){
        return photoService.addPhoto(photo);
    }

    @ApiOperation(value = "修改照片信息")
    @PutMapping
    public FanResponse updateRole(@RequestBody Photo photo){
        return photoService.updatePhoto(photo);
    }

    @ApiOperation(value = "删除一个或多个照片")
    @DeleteMapping("/{ids}")
    public FanResponse deleteRoles(@PathVariable Long[] ids){
        return photoService.deletePhotoes(ids);
    }
}