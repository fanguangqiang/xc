package com.fan.system.service;

import com.fan.common.vo.FanResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fgq
 * @date 2022/4/16
 */
public interface SysFileService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    FanResponse upload(MultipartFile file);

    /**
     * 获取上传文件路径
     * @return
     */
    String getUploadFilePath();
}
