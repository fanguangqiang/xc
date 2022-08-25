package com.fan.system.fact;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fgq
 * @date 2022/3/22
 */
@RestController
@RequestMapping("test")
public class TestInterface {

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @GetMapping("info")
    public String getAdminInfo(){
        return "ok"+tokenHead+ tokenHeader;
    }

}
