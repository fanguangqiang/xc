package com.fan.common.utils;

import com.fan.common.constant.SystemConstant;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author fgq
 * @date 2021/12/23
 */
public class CaptchaUtils {

    /**
     * 生成png验证码
     * @param response 响应流
     * @throws IOException IO异常
     */
    public static Captcha genCaptcha(String type) {
        if(!StringUtils.isEmpty(type)){
            switch (type.toLowerCase()){
                case SystemConstant.CAPTCHA_PNG:
                    // png类型
                    SpecCaptcha specCaptcha = new SpecCaptcha(130, 48);
                    specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
                    return specCaptcha;
                case SystemConstant.CAPTCHA_GIF:
                    GifCaptcha gifCaptcha = new GifCaptcha(100, 48, 4);
                    // 设置类型：字母数字混合 TYPE_DEFAULT  纯数字 TYPE_ONLY_NUMBER
                    gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
                    return gifCaptcha;
                case SystemConstant.CAPTCHA_CHINESE:
                    // 中文类型
                    ChineseCaptcha chineseCaptcha = new ChineseCaptcha(130, 48);
                    return chineseCaptcha;
                case SystemConstant.CAPTCHA_ARITHMETIC:
                    // 算术类型
                    ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(130, 48);
                    // 几位数运算，默认是两位
                    arithmeticCaptcha.setLen(3);
                    return arithmeticCaptcha;
                default:
                    break;
            }
        }
        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(100, 48, 4);
        // 设置类型：字母数字混合 TYPE_DEFAULT  纯数字 TYPE_ONLY_NUMBER
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        //获取验证码
        String text = gifCaptcha.text();
        System.out.println("验证码为："+text);
        return gifCaptcha;
    }

    /**
     * 废弃
     */
    public void getGifCaptcha() {
        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(100, 48, 4);
        // 设置类型：字母数字混合 TYPE_DEFAULT  纯数字 TYPE_ONLY_NUMBER
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        //获取验证码
        String text = gifCaptcha.text();
        System.out.println("验证码为："+text);
        // 输出验证码
//        gifCaptcha.out(response.getOutputStream());
    }
}
