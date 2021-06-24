package com.flink;

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
public class Kafka26Install {

    private Session ftpsession;
    private Session execSession;


    /**
     * 登录linux系统
     */
    @Test
    public void installRedis() throws Exception {
        boolean getConfigs = false;
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

            fileManager.getLinuxFileCategory("kafka26", "11.11.1.79", "root", "liwenroot", 22, "server.properties", "/home/liwen/kafka26/kafka/config", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});

        } else {
            fileManager.upLinuxFileCategory("kafka26", "11.11.1.79", "root", "liwenroot", 22, "server.properties");

        }


    }
}