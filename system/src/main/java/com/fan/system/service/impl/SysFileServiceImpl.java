package com.fan.system.service.impl;

import com.fan.common.utils.FileUploadUtils;
import com.fan.common.vo.FanResponse;
import com.fan.common.vo.FileVo;
import com.fan.system.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author fgq
 * @date 2022/4/16
 */
@Slf4j
@Service
public class SysFileServiceImpl implements SysFileService {

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.upload.path}")
    private String localFilePath;

    /**
     * 资源映射路径 前缀
     */
    @Value("${file.prefix}")
    public String localFilePrefix;
    /**
     * 域名或本机访问地址
     */
    @Value("${file.domain}")
    public String domain;


    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public FanResponse upload(MultipartFile file) {
        String path = null;
        String url = null;
        FileVo fileVo = null;
        try {
            path = FileUploadUtils.upload(localFilePath, file);
            url = domain + localFilePrefix + path;
            fileVo =  new FileVo();
            fileVo.setFileName(file.getOriginalFilename());
            fileVo.setFilePath(path);
            fileVo.setFileSize(Double.parseDouble(String.format("%.1f",file.getSize()/1024d)));
            fileVo.setType(file.getContentType());
            fileVo.setUploadTime(new Date());
            fileVo.setUrl(url);
        } catch (IOException e) {
            log.error("上传文件",e);
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            log.error("文件大小超出限制",e);
        }
        if(fileVo!=null) {
            return FanResponse.success(fileVo);
        }else{
            return FanResponse.error("上传失败");
        }
    }

    /**
     * 获取上传文件路径
     *
     * @return
     */
    @Override
    public String getUploadFilePath() {
        return localFilePath;
    }
}
