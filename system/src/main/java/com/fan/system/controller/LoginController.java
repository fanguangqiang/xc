package com.fan.system.controller;

import com.fan.common.vo.FanResponse;
import com.fan.system.pojo.LoginParams;
import com.fan.system.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * @author fgq
 * @date 2021/12/23
 */
@RestController
public class LoginController {

    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value = "登录返回token")
    @PostMapping("/login")
    public FanResponse login(@RequestBody LoginParams login){
        return sysUserService.login(login);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("admin/info")
    public FanResponse getAdminInfo(Principal principal){
        if(principal == null ){
            return null;
        }
        return sysUserService.getUserByUserName(principal.getName());
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("/logout/{userId}")
    public FanResponse logout(@PathVariable Long userId){
        sysUserService.logout(userId);
        return FanResponse.success("注销成功");
    }

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha/{key}",produces = "image/gif")
    public void captcha(@PathVariable String key , HttpServletResponse response) {
        sysUserService.genCaptcha(response,key);
    }
}
