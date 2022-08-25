package com.fan.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.entity.SysUser;
import com.fan.system.pojo.LoginParams;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
public interface SysUserService extends IService<SysUser> {

    LoginParams getSysUserByUserName(String username);

    FanResponse login(LoginParams login);

    FanResponse getUserByUserName(String name);

    /**
     * 生成验证码
     * @param response 返回验证码
     * @param key redis key
     */
    void genCaptcha(HttpServletResponse response, String key);

    /**
     * 退出登录 清除菜单和token
     * @param userId
     */
    void logout(Long userId);

    /**
     * 查询所有用户列表
     * @param pageVo 分页
     * @param sysUser 条件
     * @return 返回结果
     */
    FanResponse getUserList(PageVo pageVo, SysUser sysUser);

    /**
     * 添加用户
     * @param sysUser 用户信息
     * @return 返回结果
     */
    FanResponse addUser(SysUser sysUser);

    /**
     * 修改用户
     * @param sysUser 用户信息
     * @return 返回
     */
    FanResponse updateUser(SysUser sysUser);

    /**
     * 删除多个用户
     * @param ids 用户id 数组
     * @return 返回结果
     */
    FanResponse deleteUsers(Long[] ids);

    /**
     * 修改用户状态
     * @param sysUser 用户信息
     * @return 返回结果
     */
    FanResponse changeState(SysUser sysUser);
}