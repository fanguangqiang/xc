package com.fan.manage.controller;

import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.manage.entity.Manage;
import com.fan.manage.service.ManageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Manage)表控制层
 *
 * @author dong
 * @since 2022-08-29 21:11:59
 */
@RestController
@RequestMapping("mm")
public class ManageController {
    /**
     * 服务对象
     */
    @Resource
    private ManageService manageService;

    /**
     * 分页查询
     *
     * @param manage      筛选条件
     * @param pageVo 分页对象
     * @return 查询结果
     */
    @GetMapping
    public FanResponse queryByPage(PageVo pageVo,Manage manage) {
        return this.manageService.queryByPage(pageVo,manage);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    /*@GetMapping("{id}")
    public FanResponse queryById(@PathVariable("id") Long id) {
        return this.manageService.queryById(id);
    }*/

    /**
     * 新增数据
     *
     * @param manage 实体
     * @return 新增结果
     */
    @PostMapping
    public FanResponse add(Manage manage) {
        return this.manageService.insert(manage);
    }

    /**
     * 编辑数据
     *
     * @param manage 实体
     * @return 编辑结果
     */
    @PutMapping
    public FanResponse edit(Manage manage) {
        return this.manageService.update(manage);
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{ids}")
    public FanResponse deleteById(@PathVariable Long[] ids) {
        return this.manageService.deleteByIds(ids);
    }

}

