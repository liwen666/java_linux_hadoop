package com.java.hadoop.spark;

import com.java.hadoop.filemanager.FileManager;
import com.java.hadoop.linux.port.FireWallController;

public class SingleSparkSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/home/hadoop/hadoop/spark-2.4.0-bin-hadoop2.7/conf";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置core-site.xml
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"core-site.xml",filePath);
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"hadoop-env.sh",filePath);
////        3.配置 yarn-env.sh
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"yarn-env.sh",filePath);
////        4.配置hdfs-site.xml
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"hdfs-site.xml",filePath);
////        复制mapred-site.xml.template文件，并命名为mapred-site.xml
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"mapred-site.xml",filePath);
////        6.配置yarn-site.xml
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"yarn-site.xml",filePath);
////        7.配置slaves 文件
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"slaves",filePath);
////          配置profile
//        fileManager.getLinuxFileCategory("192.168.42.220","root","root",22,"profile","/etc/");
//        fileManager.getLinuxFileCategory("192.168.42.220","root","root",22,"hosts","/etc/");
//        fileManager.getLinuxFileCategory("192.168.42.220","root","root",22,"network","/etc/sysconfig/");
//        fileManager.getLinuxFileCategory("192.168.42.220","root","root",22,"hostname","/etc/");
//        配置spark
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"spark-env.sh",filePath);
        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"slaves",filePath);






//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"core-site.xml");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"hadoop-env.sh");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"hdfs-site.xml");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"mapred-site.xml");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"slaves");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"yarn-env.sh");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"yarn-site.xml");

//        fileManager.upLinuxFileCategory("192.168.42.220","root","root",22,"profile");
//        fileManager.upLinuxFileCategory("192.168.42.220","root","root",22,"hosts");
//        fileManager.upLinuxFileCategory("192.168.42.220","root","root",22,"network");
//        fileManager.upLinuxFileCategory("192.168.42.220","root","root",22,"hostname");
//        spark配置
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"spark-env.sh");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"slaves");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
