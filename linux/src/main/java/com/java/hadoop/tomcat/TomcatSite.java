package com.java.hadoop.tomcat;

import com.java.hadoop.filemanager.FileManager;

public class TomcatSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
//        String filePath="/tomcat/analysis_service/apache-tomcat-8.0.53/conf";
        String filePath="/home/jrxany/apache-tomcat-8.0.53/conf";
        FileManager fileManager = new FileManager();


//        fileManager.getLinuxFileCategory("172.16.104.12","jrxany","20191022",22,"server.xml",filePath);
        fileManager.getLinuxFileCategory("172.16.104.12","jrxany","20191022",22,"initialAdminPassword","/home/jrxany/.jenkins/secrets");
//        fileManager.getLinuxFileCategory("192.168.42.138","root","root",22,"catalina.sh","/develop/tomcat8_28085_dcpay_commission_local/bin");
//        fileManager.getLinuxFileCategory("192.168.100.109","root","oracle",22,"catalina.sh","/tomcat/analysis_service/apache-tomcat-8.0.53/bin");
//        fileManager.getLinuxFileCategory("192.168.100.109","root","oracle",22,"tomcat-users.xml",filePath);
//        fileManager.getLinuxFileCategory("192.168.100.109","root","oracle",22,"context.xml",filePath);






//        fileManager.upLinuxFileCategory("192.168.100.109","root","oracle",22,"server.xml");
//        fileManager.upLinuxFileCategory("192.168.42.138","root","root",22,"catalina.sh");
//        fileManager.upLinuxFileCategory("192.168.100.109","root","oracle",22,"catalina.sh");
//        fileManager.upLinuxFileCategory("192.168.100.109","root","oracle",22,"tomcat-users.xml");
//        fileManager.upLinuxFileCategory("192.168.100.109","root","oracle",22,"context.xml");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
