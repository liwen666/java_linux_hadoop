package com.gp;

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
public class GpConfigOnly {

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

            fileManager.getLinuxFileCategory("gp", "192.168.60.160", "gpadmin", "gpadmin", 22, "gpinit_config", "/opt/greenplum/greenplum-db/docs/cli_help/gpconfigs", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("gp", "192.168.60.161", "gpadmin", "gpadmin", 22, "gpinit_config", "/opt/greenplum/greenplum-db/docs/cli_help/gpconfigs", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("gp", "192.168.60.162", "gpadmin", "gpadmin", 22, "gpinit_config", "/opt/greenplum/greenplum-db/docs/cli_help/gpconfigs", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
             fileManager.getLinuxFileCategory("gp", "192.168.60.160", "gpadmin", "gpadmin", 22, "pg_hba.conf", "/home/gpadmin/gpdata/gpmaster/gpseg-1", new HashMap() {{
                    put("file_linux_cfg", filePath);
                }});



            fileManager.getLinuxFileCategory("gp", "10.0.22.87", "gpadmin", "gpadmin", 22, "gpinit_config", "/opt/greenplum/greenplum-db/docs/cli_help/gpconfigs", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("gp", "10.0.22.87", "gpadmin", "gpadmin", 22, "pg_hba.conf", "/home/gpadmin/gpdata/gpmaster/gpseg-1", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});



        } else {
            fileManager.upLinuxFileCategory("gp", "192.168.60.160", "gpadmin", "gpadmin", 22, "gpinit_config");
            fileManager.upLinuxFileCategory("gp", "192.168.60.161", "gpadmin", "gpadmin", 22, "gpinit_config");
            fileManager.upLinuxFileCategory("gp", "192.168.60.162", "gpadmin", "gpadmin", 22, "gpinit_config");
            fileManager.upLinuxFileCategory("gp", "192.168.60.160", "gpadmin", "gpadmin", 22, "pg_hba.conf");
            fileManager.upLinuxFileCategory("gp", "10.0.22.87", "gpadmin", "gpadmin", 22, "pg_hba.conf");
            fileManager.upLinuxFileCategory("gp", "10.0.22.87", "gpadmin", "gpadmin", 22, "gpinit_config");

        }


    }
}