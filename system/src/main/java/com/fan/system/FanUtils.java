package com.fan.system;

import com.fan.system.pojo.LoginParams;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 系统工具类
 * @author fgq
 * @date 2022/4/19
 */
public class FanUtils {

    /**
     * 获取当前登录用户id
     * @return
     */
    public static Long getLoginUserId() {
        return ((LoginParams)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
    }
}
