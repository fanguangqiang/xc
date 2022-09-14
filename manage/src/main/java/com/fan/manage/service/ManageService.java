package com.fan.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.manage.entity.Manage;

/**
 * (Manage)表服务接口
 *
 * @author makejava
 * @since 2022-08-29 21:12:06
 */
public interface ManageService extends IService<Manage> {

    /**
     * 通过ID查询单条数据
     *
     * @param mId 主键
     * @return 实例对象
     */
    FanResponse queryById(Long mId);

    /**
     * 分页查询
     *
     * @param manage      筛选条件
     * @param pageVo 分页对象
     * @return 查询结果
     */
    FanResponse queryByPage(PageVo pageVo,Manage manage);

    /**
     * 新增数据
     *
     * @param manage 实例对象
     * @return 实例对象
     */
    FanResponse insert(Manage manage);

    /**
     * 修改数据
     *
     * @param manage 实例对象
     * @return 实例对象
     */
    FanResponse update(Manage manage);

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    FanResponse deleteByIds(Long[] ids);

}
