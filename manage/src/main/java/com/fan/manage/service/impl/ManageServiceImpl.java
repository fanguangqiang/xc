package com.fan.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.manage.dao.ManageDao;
import com.fan.manage.entity.Manage;
import com.fan.manage.feign.IUserService;
import com.fan.manage.service.ManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * (Manage)表服务实现类
 *
 * @author makejava
 * @since 2022-08-29 21:12:10
 */
@Service("manageService")
public class ManageServiceImpl extends ServiceImpl<ManageDao,Manage> implements ManageService {
    @Resource
    private ManageDao manageDao;
    @Autowired
    private IUserService userService;
    /**
     * 通过ID查询单条数据
     *
     * @param mId 主键
     * @return 实例对象
     */
    @Override
    public FanResponse queryById(Long mId) {
        Manage manage = this.baseMapper.selectById(mId);
        if(manage!=null){
            return FanResponse.success(manage);
        }
        return FanResponse.error("未查询到此数据");
    }

    /**
     * 分页查询
     *
     * @param pageVo 分页对象
     * @param manage 筛选条件
     * @return 查询结果
     */
    @Override
    public FanResponse queryByPage(PageVo pageVo, Manage manage) {
        IPage<Manage> page = new Page<>(pageVo.getPageNum(),pageVo.getPageSize());
        QueryWrapper<Manage> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(manage.getAccName())){
            wrapper.like("acc_name",manage.getAccName());
        }
        IPage<Manage> iPage = this.baseMapper.selectPage(page, wrapper);
        return FanResponse.successPage(iPage);
    }

    /**
     * 新增数据
     *
     * @param manage 实例对象
     * @return 实例对象
     */
    @Override
    public FanResponse insert(Manage manage) {
        manage.setCreatetime(new Date());
        manage.setUpdatetime(new Date());
        String loginUserId = userService.getLoginUserId();
        manage.setUserId(Long.valueOf(loginUserId));
        int insert = this.baseMapper.insert(manage);
        if(insert>0){
            return FanResponse.success("新增成功");
        }
        return FanResponse.error("新增失败");
    }

    /**
     * 修改数据
     *
     * @param manage 实例对象
     * @return 实例对象
     */
    @Override
    public FanResponse update(Manage manage) {
        manage.setUpdatetime(new Date());
        int i = this.baseMapper.updateById(manage);
        if(i>0){
            return FanResponse.success("修改成功！");
        }
        return FanResponse.error("修改失败");
    }

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public FanResponse deleteByIds(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        int deleteBatchIds = this.baseMapper.deleteBatchIds(list);
        if(deleteBatchIds>0){
            return FanResponse.success("删除成功") ;
        }
        return FanResponse.error("删除失败");
    }
}
