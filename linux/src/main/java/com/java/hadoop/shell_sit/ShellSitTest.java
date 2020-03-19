package com.java.hadoop.shell_sit;

import com.java.hadoop.filemanager.FileManager;

public class ShellSitTest {
    public static void main(String[] args) throws Exception {
        String filePath="/home/liwen/shell_test";
        FileManager fileManager = new FileManager();



        //从服务器配置
        /**
         * 测试修改文件格式脚本  将文件从doc 改为linux
         */
//        fileManager.getLinuxFileCategory("192.168.42.136","root","root",22,"test1.sh",filePath);
//        fileManager.getLinuxFileCategory("192.168.42.136","root","root",22,"test2.sh",filePath);






        fileManager.upLinuxFileCategory("est_center_new", "192.168.42.136","root","root",22,"test1.sh");
        fileManager.upLinuxFileCategory("est_center_new", "192.168.42.136","root","root",22,"test2.sh");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
