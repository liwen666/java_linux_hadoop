package com.java.hadoop;

import com.java.hadoop.linux.adduser.AddUser;
import com.java.hadoop.linux.baseconfig.LinuxBaseConfig;
import com.java.hadoop.linux.javainstall.JavaInstall;
import com.java.hadoop.linux.port.FireWallController;

public class AppConfigInstall {
    public static void main(String[] args) throws Exception {

//master配置
        configMaster();

//slaver配置
//        configSlaver();

        System.out.println("结束");

    }

    private static void configMaster() throws Exception {

        //创建用户
        AddUser au = new AddUser();
        au.addUserByParam("root","192.168.42.210",22,"root");

        //初始化linux 的依赖配置
        LinuxBaseConfig lbc = new LinuxBaseConfig();
        lbc.addDependiesByParam("root","192.168.42.210",22,"root");
           //安装java
        JavaInstall ji= new JavaInstall();
        ji.installJavaByParam("hadoop","192.168.42.210",22,"hadoop");
//        //配置端口
        FireWallController fwc = new FireWallController();
        fwc.modifyPortSetByParam("root","192.168.42.210",22,"root");
        fwc.execShellByParam("root","192.168.42.210",22,"root");
    }

    private static void configSlaver() throws Exception {
        //创建用户
        AddUser au = new AddUser();
//        au.addUser();
        au.addUserByParam("root","192.168.42.211",22,"root");

        //初始化linux 的依赖配置
        LinuxBaseConfig lbc = new LinuxBaseConfig();
//        lbc.addDependies();
        lbc.addDependiesByParam("root","192.168.42.211",22,"root");
        //安装java
        JavaInstall ji= new JavaInstall();
//        ji.installJava();
        ji.installJavaByParam("hadoop","192.168.42.211",22,"hadoop");
//        //配置端口
        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSet();
        fwc.modifyPortSetByParam("root","192.168.42.211",22,"root");
//        fwc.execShell();
        fwc.execShellByParam("root","192.168.42.211",22,"root");
        System.out.println("结束");

    }

    static class testMethod{
        public static void main(String[] args) throws Exception {
            FireWallController fwc = new FireWallController();
            fwc.modifyPortSet();
//            fwc.upLoadShell();
        }
    }
}
