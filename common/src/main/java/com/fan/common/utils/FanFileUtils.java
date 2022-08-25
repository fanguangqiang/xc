package com.fan.common.utils;


import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * 文件管理工具类
 * @author Dong
 */
public class FanFileUtils {

    public static void main(String[] args) throws IOException {
//        输入你想要复制的文件或文件夹
        String str1="F:\\设备信息.csv";
//        输入你想要粘贴在哪的文件或文件夹
        String str2="L:\\阿里云盘\\ttt\\设备信息.csv";
        File f1=new File(str1);
        File f2=new File(str2);
        copy(f1,f2);
    }

    /**
     * 复制文件到目标目录 或 目录到目录 或 文件到文件
     * 注意：如果目标目录存在要复制的文件会直接覆盖
     * @param fs 源目录 或 源文件
     * @param ft 目标目录 或 文件
     * @throws IOException
     */
    public static void copy(File fs,File ft) throws IOException {
        //先判断fs是否存在
        if(!fs.exists()) {
            System.out.println("目录不存在");
            return;
        }
        if(!ft.exists()){
            ft.mkdirs();
        }
        if(fs.isFile()&&ft.isFile()){
            FileUtils.copyFile(fs,ft);
        }
        if(fs.isDirectory()&&ft.isDirectory()) {
            //fs 下的每个子目录
            File[] f=fs.listFiles();
            for(File x:f) {
                //创建同名的目录或文件抽象
                File c=new File(ft,x.getName());
                System.out.println("复制"+c.getName());
                if(x.isDirectory()){
                    c.mkdirs();  //创建目录
                    copy(x,c);
                }else{
                    c.createNewFile();
                    FileUtils.copyFile(x,c);
                }
            }
        }
        if(fs.isFile()&&ft.isDirectory()) {
            //若是将文件复制到一个目录，则应该在改目录下面创建一个文件
            ft=new File(ft,fs.getName());
            FileUtils.copyFile(fs,ft,true);
        }
        if(fs.isDirectory()&&fs.isFile()){
            System.out.println("无法将目录复制到文件中/(ㄒoㄒ)/~~");
            return;
        }
    }

    public static void copyfile(File fs,File ft) throws IOException {
//        if(ft.isFile() && ft.exists()){
//            //重名的进行改名
//            ft=new File(ft.getParent(), FilenameUtils.getBaseName(ft.getName())+"-副本."+FilenameUtils.getExtension(ft.getName()));
//        }
        FileInputStream fi=new FileInputStream(fs);
        FileOutputStream fo=new FileOutputStream(ft);

        BufferedInputStream bfi = null;
        BufferedOutputStream bfo =null;

        bfi=new BufferedInputStream(fi);
        bfo=new BufferedOutputStream(fo);

        int count=-1;
        while((count=bfi.read())!=-1) {
            bfo.write(count);
        }

        bfo.flush();
        bfo.close();
        bfi.close();
    }



}
