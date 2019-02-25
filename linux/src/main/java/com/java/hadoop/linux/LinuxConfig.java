package com.java.hadoop.linux;

import com.java.hadoop.linux.filecontroller.FtpJSch;
import com.java.hadoop.linux.filecontroller.basefile.ResolutionAppConfig;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.temp.common.base.util.PackageScanUtil;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 用来修改linux的配置文件
 */
public class LinuxConfig {

    //修改环境变量
    public void upLoadProfileFile(String[] appCfgs, ChannelSftp ftpsession) throws IOException, SftpException {
        Resource[] resource = PackageScanUtil.findResource("com.java.hadoop.linux.filecontroller.basefile");
        for (Resource r : resource) {
            File file = r.getFile();
            String realName = file.getName().substring(5, file.getName().length());
            if ("profile".equals(realName)) {

                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] cache = new byte[1024 * 1024];
                    int read = fileInputStream.read(cache);
                    String oldFileStr = new String(cache, 0, read);
                    StringBuffer sb = new StringBuffer(oldFileStr);
                for (String appCfg : appCfgs) {
//                配置java环境变量
                    String configstr = ResolutionAppConfig.getConfigstr(appCfg);
                    sb.append("\n" + appCfg + "");
                    sb.append(configstr);
                }
                byte[] bytes = sb.toString().getBytes();
                ftpsession.cd("/etc");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ftpsession.put(byteArrayInputStream, file.getName().substring(5, file.getName().length()));

            }
        }
    }

}
