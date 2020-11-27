package com.java.hadoop.hadoop.site;

import com.java.hadoop.filemanager.FileManager;

public class FlinkSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/root/flink/flink-1.11.2/conf";
        FileManager fileManager = new FileManager();
        /**
         * 下载
         */
//        fileManager.getLinuxFileCategory("flink", "192.168.137.111","root","liwen",22,"flink-conf.yaml","/home/liwen/application/flink/flink-server/conf");
//        fileManager.getLinuxFileCategory("flink", "192.168.60.220","root","root",22,"flink-conf.yaml",filePath);
//  mkdir -vp /root/flink/flink-1.11.2/tmp

        /**
         * 上传
         */
        fileManager.upLinuxFileCategory("flink", "192.168.137.111","root","liwen",22,"flink-conf.yaml");
//        fileManager.upLinuxFileCategory("flink", "192.168.60.220","root","root",22,"flink-conf.yaml");

//        fileManager.upLinuxFileCategory("192.168.42.210","root","root",22,"hosts");
//        fileManager.upLinuxFileCategory("192.168.42.210","root","root",22,"profile");


        /**
         * 添加
         *  <property>
         *     <name>system:java.io.tmpdir</name>
         *     <value>/usr/local/hive/tmp</value>
         *   </property>
         *   <property>
         *          *     <name>system:user.name</name>
         *          *     <value>liwen</value>
         *          *   </property>
         *
         *
         */



        //从服务器配置
//        配置core-site.xml
//          fileManager.getLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"core-site.xml",filePath);
//        fileManager.getLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"hadoop-env.sh",filePath);
////        3.配置 yarn-env.sh
//        fileManager.getLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"yarn-env.sh",filePath);
////        4.配置hdfs-site.xml
//        fileManager.getLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"hdfs-site.xml",filePath);
////        复制mapred-site.xml.template文件，并命名为mapred-site.xml
//        fileManager.getLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"mapred-site.xml",filePath);
////        6.配置yarn-site.xml
//        fileManager.getLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"yarn-site.xml",filePath);
////        7.配置slaves 文件
//        fileManager.getLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"slaves",filePath);
////          配置profile
//        fileManager.getLinuxFileCategory("192.168.42.211","root","root",22,"profile","/etc/");
//        fileManager.getLinuxFileCategory("192.168.42.211","root","root",22,"hosts","/etc/");



//        fileManager.upLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"core-site.xml");
//        fileManager.upLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"hadoop-env.sh");
//        fileManager.upLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"hdfs-site.xml");
//        fileManager.upLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"mapred-site.xml");
//        fileManager.upLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"slaves");
//        fileManager.upLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"yarn-env.sh");
//        fileManager.upLinuxFileCategory("192.168.42.211","hadoop","hadoop",22,"yarn-site.xml");
//        fileManager.upLinuxFileCategory("192.168.42.211","root","root",22,"profile");
//        fileManager.upLinuxFileCategory("192.168.42.211","root","root",22,"hosts");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.211",22,"root");
//        fwc.execShellByParam("root","192.168.42.211",22,"root");
//         FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.210",22,"root");
//        fwc2.execShellByParam("root","192.168.42.210",22,"root");

    }
}
