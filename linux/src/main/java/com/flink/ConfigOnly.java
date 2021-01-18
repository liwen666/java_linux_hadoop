package com.flink;

import com.java.hadoop.filemanager.FileManager;
import com.java.hadoop.linux.LinuxUtil;
import com.jcraft.jsch.*;
import com.linux.temp.utils.PropertiesThreadLocalHolder;
import com.linux.temp.utils.YamlUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * java 登录linux系统，并读取执行shell命令结果
 *
 * @author wanghonggang
 * 2018-10-30
 */
public class ConfigOnly {

    private Session ftpsession;
    private Session execSession;



    /**
     * 登录linux系统
     */
    @Test
    public void installRedis() throws Exception {
        boolean getConfigs = false;
        FileManager fileManager = new FileManager();
        String filePath="D:/workspace/java_linux_hadoop/linux/src/main/resources/file-path.json";
        String upLoadFile="D:\\workspace\\java_linux_hadoop\\linux\\src\\main\\java\\com\\filemanager\\";
        String upLoadFilePackage="com.filemanager";
        PropertiesThreadLocalHolder.addProperties("file_linux_cfg", filePath);
        PropertiesThreadLocalHolder.addProperties("upLoadFile", upLoadFile);
        PropertiesThreadLocalHolder.addProperties("upLoadFilePackage", upLoadFilePackage);
        /**
         * 下载
         */
        if(getConfigs){
            fileManager.getLinuxFileCategory("redis_cluster", "11.11.1.79", "root", "liwen", 22, "10110-redis.conf", "/usr/local/redis/cluster",new HashMap(){{
                put("file_linux_cfg",filePath);
            }});
            fileManager.getLinuxFileCategory("redis_cluster", "11.11.1.79", "root", "liwen", 22, "10111-redis.conf", "/usr/local/redis/cluster",new HashMap(){{
                put("file_linux_cfg",filePath);
            }});
            fileManager.getLinuxFileCategory("redis_cluster", "11.11.1.79", "root", "liwen", 22, "10112-redis.conf", "/usr/local/redis/cluster",new HashMap(){{
                put("file_linux_cfg",filePath);
            }});
            fileManager.getLinuxFileCategory("redis_cluster", "11.11.1.79", "root", "liwen", 22, "10113-redis.conf", "/usr/local/redis/cluster",new HashMap(){{
                put("file_linux_cfg",filePath);
            }});
            fileManager.getLinuxFileCategory("redis_cluster", "11.11.1.79", "root", "liwen", 22, "10114-redis.conf", "/usr/local/redis/cluster",new HashMap(){{
                put("file_linux_cfg",filePath);
            }});
            fileManager.getLinuxFileCategory("redis_cluster", "11.11.1.79", "root", "liwen", 22, "10115-redis.conf", "/usr/local/redis/cluster",new HashMap(){{
                put("file_linux_cfg",filePath);
            }});
        }else{
            fileManager.upLinuxFileCategory("redis_cluster", "11.11.1.79","root","liwen",22,"10110-redis.conf");
            fileManager.upLinuxFileCategory("redis_cluster", "11.11.1.79","root","liwen",22,"10111-redis.conf");
            fileManager.upLinuxFileCategory("redis_cluster", "11.11.1.79","root","liwen",22,"10112-redis.conf");
            fileManager.upLinuxFileCategory("redis_cluster", "11.11.1.79","root","liwen",22,"10113-redis.conf");
            fileManager.upLinuxFileCategory("redis_cluster", "11.11.1.79","root","liwen",22,"10114-redis.conf");
            fileManager.upLinuxFileCategory("redis_cluster", "11.11.1.79","root","liwen",22,"10115-redis.conf");

        }





    }
}