package com.java.hadoop.linux.filecontroller;

import com.java.hadoop.linux.filecontroller.basefile.ResolutionAppConfig;
import com.jcraft.jsch.SftpException;
import com.temp.common.base.util.PackageScanUtil;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UpLoadFileToLinux {
    @Test
    public void upLoadJava() throws Exception {

        FtpJSch.getConnect();
        FtpJSch.sftp.cd("/");
        File file = new File("H:\\开发安装包\\java\\jdk-8u191-linux-x64.tar.gz");
        FileInputStream fileInputStream = new FileInputStream(file);
        FtpJSch.sftp.put(fileInputStream, file.getName());


    }

    @Test
    //修改环境变量
    public void upLoadProfileFile() throws IOException, SftpException {
        FtpJSch.getConnect();
        Resource[] resource = PackageScanUtil.findResource("com.java.hadoop.linux.filecontroller.basefile");
        for(Resource r:resource){
            File file = r.getFile();
            String realName = file.getName().substring(5, file.getName().length());
//            System.out.println(realName);
            if("profile".equals(realName)){
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] cache = new byte[1024*10] ;
                byte[]  cache2 = new byte[100];
                int read = fileInputStream.read(cache);
                int read1 = fileInputStream.read(cache2);
//                System.out.println(read);
//                System.out.println(read1+"文件第二次读取的位置");
                String oldFileStr = new String(cache,0,read);
                StringBuffer sb = new StringBuffer(oldFileStr);
//                sb.append("export PATH=/usr/local/mysql/bin:$PATH\t\r\n");//空格回车换行
//                配置java环境变量
                String configstr = ResolutionAppConfig.getConfigstr("#Java Env");
//                sb.append("\n#Java Env\n");
//                sb.append(configstr+"\n");
                System.out.println(sb.toString());








                byte[] bytes = sb.toString().getBytes();
                FtpJSch.sftp.cd("/etc");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                FtpJSch.sftp.put(byteArrayInputStream, file.getName().substring(5,file.getName().length()));
            }
//            FtpJSch.sftp.cd("/home/liwen");
//            FtpJSch.sftp.put(new FileInputStream(file), file.getName().substring(5,file.getName().length()));
        }

//        FtpJSch.closeSftp();
    }
}
