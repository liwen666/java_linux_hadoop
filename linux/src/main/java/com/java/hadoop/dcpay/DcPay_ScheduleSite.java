package com.java.hadoop.dcpay;

import com.java.hadoop.filemanager.FileManager;

public class DcPay_ScheduleSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/home/deploy/tomcat8_28086_dcpay_schedule_center1/webapps/dcpay_schedule_center1";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("192.168.1.124","root","1qaz#EDC2019",22,"jdbc.properties",filePath+"/WEB-INF/classes");
//        fileManager.getLinuxFileCategory("192.168.1.124","root","1qaz#EDC2019",22,"application.properties",filePath+"/WEB-INF/classes");
        fileManager.getLinuxFileCategory("192.168.1.124","root","1qaz#EDC2019",22,"application.conf",filePath+"/WEB-INF/classes");





//        上传文件
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"server.properties");
//
        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
