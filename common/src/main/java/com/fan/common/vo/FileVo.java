package com.fan.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fgq
 * @date 2022/4/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVo {

    /**
     * 访问资源
     */
    private String url;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Double fileSize;

    /**
     * 文件类型
     */
    private String type;
    /**
     * 上传时间
     */
    private Date uploadTime;

}
