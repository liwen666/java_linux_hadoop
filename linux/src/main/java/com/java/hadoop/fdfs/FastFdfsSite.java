package com.java.hadoop.fdfs;

import com.java.hadoop.filemanager.FileManager;

public class FastFdfsSite {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置获取模板配置文件
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"lwnginx.conf","/etc/fdfs/");
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"lwnginx.conf","/usr/local/nginx/conf/");
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"nginx.conf","/usr/local/nginx/conf/");
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"tracker.conf","/etc/fdfs/");
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"storage.conf","/etc/fdfs/");
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"storagelw.conf","/etc/fdfs/");
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"config","/usr/local/fastdfs-nginx-module/src");
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"mod_fastdfs.conf","/etc/fdfs/");






        fileManager.upLinuxFileCategory("est_center_new", "192.168.42.230","root","root",22,"mod_fastdfs.conf");
//        fileManager.upLinuxFileCategory("192.168.42.230","root","root",22,"nginx.conf");
//        fileManager.upLinuxFileCategory("192.168.42.230","root","root",22,"lwnginx.conf");
//        fileManager.upLinuxFileCategory("192.168.42.230","root","root",22,"config");
//        fileManager.upLinuxFileCategory("192.168.42.230","root","root",22,"storage.conf");
        fileManager.upLinuxFileCategory("est_center_new", "192.168.42.230","root","root",22,"storagelw.conf");
//        fileManager.upLinuxFileCategory("192.168.42.230","root","root",22,"tracker.conf");
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
