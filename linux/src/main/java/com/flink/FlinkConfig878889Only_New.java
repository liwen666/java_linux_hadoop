package com.flink;

import com.java.hadoop.filemanager.FileManager;
import com.linux.temp.utils.PropertiesThreadLocalHolder;
import org.junit.Test;

import java.util.HashMap;

/**
 * java 登录linux系统，并读取执行shell命令结果
 *
 * @author wanghonggang
 * 2018-10-30
 */
public class FlinkConfig878889Only_New {

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

            fileManager.getLinuxFileCategory("flink", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "flink-conf.yaml", "/data/apps/flink-1.12.2/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("flink", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "masters", "/data/apps/flink-1.12.2/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("flink", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "workers", "/data/apps/flink-1.12.2/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("flink", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "zoo.cfg", "/data/apps/flink-1.12.2/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("flink", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "flink-conf.yaml", "/data/apps/flink-1.12.2/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("flink", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "flink-conf.yaml", "/data/apps/flink-1.12.2/conf", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});

        } else {
            fileManager.upLinuxFileCategory("flink", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "flink-conf.yaml");
            fileManager.upLinuxFileCategory("flink", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "flink-conf.yaml");
            fileManager.upLinuxFileCategory("flink", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "flink-conf.yaml");
            fileManager.upLinuxFileCategory("flink", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "masters");
            fileManager.upLinuxFileCategory("flink", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "workers");

        }


    }
}