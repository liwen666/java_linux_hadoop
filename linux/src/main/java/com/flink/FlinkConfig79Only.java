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
public class FlinkConfig79Only {

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

            fileManager.getLinuxFileCategory("flink79", "11.11.1.79", "root", "liwen", 22, "flink-conf.yaml", "/home/liwen/flink12/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("flink79", "11.11.1.79", "root", "liwen", 22, "masters", "/home/liwen/flink12/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("flink79", "11.11.1.79", "root", "liwen", 22, "workers", "/home/liwen/flink12/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("flink79", "11.11.1.79", "root", "liwen", 22, "zoo.cfg", "/home/liwen/flink12/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});


        } else {
            fileManager.upLinuxFileCategory("flink", "11.11.1.79", "root", "jrx1129013", 22, "flink-conf.yaml");
            fileManager.upLinuxFileCategory("flink", "11.11.1.79", "root", "jrx1129013", 22, "masters");
            fileManager.upLinuxFileCategory("flink", "11.11.1.79", "root", "jrx1129013", 22, "workers");

        }


    }
}