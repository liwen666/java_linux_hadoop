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
public class Hadoop878889Only_New {

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

            fileManager.getLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "core-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "workers", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
               fileManager.getLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "hadoop-env.sh", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
               fileManager.getLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "hdfs-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
               fileManager.getLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "yarn-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }}); fileManager.getLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "yarn-env.sh", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }}); fileManager.getLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "mapred-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});

            fileManager.getLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "core-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "hadoop-env.sh", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "hdfs-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "yarn-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }}); fileManager.getLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "yarn-env.sh", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }}); fileManager.getLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "mapred-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});

fileManager.getLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "core-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "hadoop-env.sh", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "hdfs-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});
            fileManager.getLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "yarn-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }}); fileManager.getLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "yarn-env.sh", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }}); fileManager.getLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "mapred-site.xml", "/data/apps/hadoop/etc/hadoop", new HashMap() {{
                put("file_linux_cfg", filePath);
            }});


        } else {
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "core-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "hadoop-env.sh");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "hdfs-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "yarn-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "yarn-env.sh");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.87", "jrxany", "jrx@36ec88.com", 22, "mapred-site.xml");

            fileManager.upLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "core-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "hadoop-env.sh");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "hdfs-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "yarn-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "yarn-env.sh");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.88", "jrxany", "jrx@dda575.com", 22, "mapred-site.xml");

    fileManager.upLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "core-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "hadoop-env.sh");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "hdfs-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "yarn-site.xml");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "yarn-env.sh");
            fileManager.upLinuxFileCategory("hadoop", "10.0.22.89", "jrxany", "jrx@d898db.com", 22, "mapred-site.xml");


        }


    }
}