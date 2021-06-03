package com.gitblit;

import com.java.hadoop.filemanager.FileManager;
import com.jcraft.jsch.Session;
import com.linux.temp.utils.PropertiesThreadLocalHolder;
import org.junit.Test;

import java.util.HashMap;

/**
 * java 登录linux系统，并读取执行shell命令结果
 *
 * @author wanghonggang
 * 2018-10-30
 */
public class GitBlitConfig {

    private Session ftpsession;
    private Session execSession;


    /**
     * 登录linux系统
     */
    @Test
    public void installRedis() throws Exception {
        boolean getConfigs = true;
        FileManager fileManager = new FileManager();
        String filePath = "D:/workspace/java_linux_hadoop/linux/src/main/resources/file-path.json";
        String upLoadFile = "D:\\workspace\\java_linux_hadoop\\linux\\src\\main\\java\\com\\filemanager\\";
        String upLoadFilePackage = "com.filemanager";
        PropertiesThreadLocalHolder.addProperties("file_linux_cfg", filePath);
        PropertiesThreadLocalHolder.addProperties("upLoadFile", upLoadFile);
        PropertiesThreadLocalHolder.addProperties("upLoadFilePackage", upLoadFilePackage);
        /**
         * 下载
         */
        if (getConfigs) {

            fileManager.getLinuxFileCategory("gitblit", "11.11.1.79", "root", "liwen", 22, "gitblit.properties", "/home/liwen/gitlib/gitblit-1.9.1/data", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("gitblit", "11.11.1.79", "root", "liwen", 22, "liwen.properties", "/home/liwen/gitlib/gitblit-1.9.1/data", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("gitblit", "11.11.1.79", "root", "liwen", 22, "ActivityUtils.class", "/home/liwen/gitlib/gitblit-1.9.1/data/temp/webapp/com/gitblit/utils", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});


        } else {
            fileManager.upLinuxFileCategory("gitblit", "11.11.1.79", "root", "liwen", 22, "gitblit.properties");
            fileManager.upLinuxFileCategory("gitblit", "11.11.1.79", "root", "liwen", 22, "liwen.properties");

        }


    }
}