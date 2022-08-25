package com.fan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.common.constant.SystemConstant;
import com.fan.common.utils.CaptchaUtils;
import com.fan.common.utils.DateUtils;
import com.fan.common.utils.RedisUtils;
import com.fan.common.utils.SM3Utils;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.PageVo;
import com.fan.system.config.security.JwtTokenUtil;
import com.fan.system.dao.SysUserDao;
import com.fan.system.entity.SysUser;
import com.fan.system.pojo.LoginParams;
import com.fan.system.service.SysUserRoleService;
import com.fan.system.service.SysUserService;
import com.wf.captcha.base.Captcha;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author fgq
 * @since 2021-12-22 14:13:33
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao,SysUser> implements SysUserService {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${captcha.type}")
    private String type;
    @Value("${captcha.time}")
    private Long time;

    /**
     * 根据用户名返回用户名 密码 用于登录验证
     * @param username 用户名
     * @return 登录参数
     */
    @Override
    public LoginParams getSysUserByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        SysUser sysUser = this.baseMapper.selectOne(wrapper);
        if(sysUser==null){
            return null;
        }
        LoginParams login = new LoginParams();
        login.setUserId(sysUser.getUserId());
        login.setUsername(sysUser.getUsername());
        login.setPassword(sysUser.getPassword());
        login.setUserStatus(sysUser.getUserStatus()==0);
        return login;
    }

    /**
     * 根据用户名查询用户信息
     * @param name 用户名
     * @return 用户信息
     */
    @Override
    public FanResponse getUserByUserName(String name) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.ge("username",name);
        SysUser sysUser = this.baseMapper.selectOne(wrapper);
        if(sysUser!=null){
            sysUser.setPassword(null);
        }
        return FanResponse.success(sysUser);
    }

    /**
     * 生成验证码
     * @param response 响应
     * @param key redis key
     */
    @SneakyThrows
    @Override
    public void genCaptcha(HttpServletResponse response, String key) {
        setHeader(response, type);
        Captcha captcha = CaptchaUtils.genCaptcha(type);
        redisUtils.set("captcha"+key, captcha.text(), time);
        captcha.out(response.getOutputStream());
    }

    /**
     * 退出登录 清除菜单和token缓存
     * @param userId 用户id
     */
    @Override
    public void logout(Long userId) {
        redisUtils.del("menu_"+userId,"token");
    }

    /**
     * 查询所有用户列表
     *
     * @param pageVo  分页
     * @param sysUser 条件
     * @return 返回结果
     */
    @Override
    public FanResponse getUserList(PageVo pageVo, SysUser sysUser) {
        IPage<SysUser> page = new Page<>(pageVo.getPageNum(),pageVo.getPageSize());
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (!Objects.equals(sysUser.getUsername(),"")){
            wrapper.like("username",sysUser.getUsername());
        }
        IPage<SysUser> iPage = this.baseMapper.selectPage(page, wrapper);
        return FanResponse.successPage(iPage);
    }

    /**
     * 添加用户
     *
     * @param sysUser 用户信息
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public FanResponse addUser(SysUser sysUser) {
        Long userId = ((LoginParams) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        sysUser.setCreateTime(DateUtils.getNowDate());
        sysUser.setCreateUser(userId);
        sysUser.setUpdateTime(DateUtils.getNowDate());
        sysUser.setUpdateUser(userId);
        int insert = this.baseMapper.insert(sysUser);
        if(!Objects.equals(sysUser.getUserId(),null) && !Objects.equals(sysUser.getRoleId(),null) ){
            sysUserRoleService.deleteUserRole(sysUser.getUserId());
            sysUserRoleService.addUserRole(sysUser.getUserId(),sysUser.getRoleId());
        }
        if(insert>0){
            return FanResponse.success("添加成功");
        }
        return FanResponse.error("添加失败");
    }

    /**
     * 修改用户
     *
     * @param sysUser 用户信息
     * @return 返回
     */
    @Override
    public FanResponse updateUser(SysUser sysUser) {
        Long userId = ((LoginParams) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        sysUser.setUpdateTime(DateUtils.getNowDate());
        sysUser.setUpdateUser(userId);
        if(!Objects.equals(sysUser.getUserId(),null) && !Objects.equals(sysUser.getRoleId(),null) ){
            sysUserRoleService.deleteUserRole(sysUser.getUserId());
            sysUserRoleService.addUserRole(sysUser.getUserId(),sysUser.getRoleId());
        }
        int update = this.baseMapper.updateById(sysUser);
        if(update>0){
            return FanResponse.success("修改成功");
        }
        return FanResponse.error("修改失败");
    }

    /**
     * 删除多个用户
     *
     * @param ids 用户id 数组
     * @return 返回结果
     */
    @Override
    public FanResponse deleteUsers(Long[] ids) {
        int batchIds = this.baseMapper.deleteBatchIds(Arrays.asList(ids));
        if(batchIds>0){
            return FanResponse.success("删除成功");
        }
        return FanResponse.error("删除失败");
    }

    /**
     * 修改用户状态
     * @param sysUser 用户信息
     * @return 返回结果
     */
    @Override
    public FanResponse changeState(SysUser sysUser) {
        int i = this.baseMapper.updateById(sysUser);
        String str =  sysUser.getUserStatus() == 0 ? "启用" : "禁用";
        if(i>0){
            return FanResponse.success(str + "成功");
        }
        return FanResponse.error(str + "失败");
    }

    /**
     * 设置响应头
     * @param response 输出流
     * @param type 验证码类型
     */
    private void setHeader(HttpServletResponse response, String type) {
        if (Objects.equals(type, SystemConstant.CAPTCHA_GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

    /**
     * 用户登录
     * @param login 用户信息
     * @return 登录成功
     */
    @Override
    public FanResponse login(LoginParams login) {
        String captcha = (String) redisUtils.get("captcha"+login.getKey());
        if (StringUtils.isBlank(login.getCode()) || !Objects.equals(login.getCode(),captcha)) {
            return FanResponse.error("验证码填写错误！");
        }
        // 登录 使用SM3进行密码验证
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
        if(userDetails==null || !SM3Utils.compareHashValue(login.getPassword(),userDetails.getPassword())){
            return FanResponse.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled()){
            return FanResponse.error("账号被禁用，请联系管理员");
        }
        // 更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        String token = jwtTokenUtil.genToken(userDetails);
//        存储token
        redisUtils.set("token",token);
        Map<String,Object> map = new HashMap<>(10);
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return FanResponse.success("登录成功",map);
    }


}