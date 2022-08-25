package com.fan.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共返回对象
 * @author Dong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FanResponse {

    private int code;
    private String msg;
    private Object obj;

    /**
     * 成功返回信息
     * @param msg
     * @return
     */
    public static FanResponse success(String msg){
        return new FanResponse(200,msg,null);
    }
    public static FanResponse success(Object obj){
        return new FanResponse(200,"",obj);
    }
    public static FanResponse successPage(IPage<?> pageInfo) {
        Map<String, Object> data = new HashMap<>(2);
        if(pageInfo!=null){
            data.put("obj", pageInfo.getRecords());
            data.put("total", pageInfo.getTotal());
        }
        return new FanResponse(200,"",data);
    }

    public static FanResponse success(String msg,Object obj){
        return new FanResponse(200,msg,obj);
    }

    /**
     * 失败返回结果
     * @param msg
     * @return
     */
    public static FanResponse error(String msg){
        return new FanResponse(500,msg,null);
    }
    public static FanResponse error(String msg, Object obj){
        return new FanResponse(500,msg,obj);
    }
}
