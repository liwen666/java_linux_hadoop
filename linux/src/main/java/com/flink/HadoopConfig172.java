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
public class HadoopConfig172 {

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

            fileManager.getLinuxFileCategory("hadoop", "172.16.101.12", "hadoop", "hadoop", 22, "yarn-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.12", "hadoop", "hadoop", 22, "core-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.12", "hadoop", "hadoop", 22, "hdfs-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.12", "hadoop", "hadoop", 22, "mapred-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});

            fileManager.getLinuxFileCategory("hadoop", "172.16.101.13", "hadoop", "hadoop", 22, "yarn-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.13", "hadoop", "hadoop", 22, "core-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.13", "hadoop", "hadoop", 22, "hdfs-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.13", "hadoop", "hadoop", 22, "mapred-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});

            fileManager.getLinuxFileCategory("hadoop", "172.16.101.14", "hadoop", "hadoop", 22, "yarn-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.14", "hadoop", "hadoop", 22, "core-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.14", "hadoop", "hadoop", 22, "hdfs-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "172.16.101.14", "hadoop", "hadoop", 22, "mapred-site.xml", "/data/hadoop-3.2.0/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});

        } else {
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.12", "hadoop", "hadoop", 22, "yarn-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.12", "hadoop", "hadoop", 22, "mapred-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.12", "hadoop", "hadoop", 22, "hdfs-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.12", "hadoop", "hadoop", 22, "core-site.xml");

            fileManager.upLinuxFileCategory("hadoop", "172.16.101.13", "hadoop", "hadoop", 22, "yarn-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.13", "hadoop", "hadoop", 22, "mapred-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.13", "hadoop", "hadoop", 22, "hdfs-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.13", "hadoop", "hadoop", 22, "core-site.xml");

            fileManager.upLinuxFileCategory("hadoop", "172.16.101.14", "hadoop", "hadoop", 22, "yarn-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.14", "hadoop", "hadoop", 22, "mapred-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.14", "hadoop", "hadoop", 22, "hdfs-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "172.16.101.14", "hadoop", "hadoop", 22, "core-site.xml");

        }


    }
}