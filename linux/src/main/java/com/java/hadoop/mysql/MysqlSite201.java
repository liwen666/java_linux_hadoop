package com.java.hadoop.mysql;

import com.java.hadoop.filemanager.FileManager;
import com.linux.temp.utils.PropertiesThreadLocalHolder;

import java.util.HashMap;

public class MysqlSite201 {
    public static void main(String[] args) throws Exception {
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

            fileManager.getLinuxFileCategory("mysql_slave", "192.168.60.201", "root", "root", 22, "my.cnf", "/etc", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("mysql_master", "192.168.60.200", "root", "root", 22, "my.cnf", "/etc", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});

        } else {
            fileManager.upLinuxFileCategory("mysql_slave", "192.168.60.201", "root", "root", 22, "my.cnf");
            fileManager.upLinuxFileCategory("mysql_master", "192.168.60.200", "root", "root", 22, "my.cnf");

        }


    }
}
