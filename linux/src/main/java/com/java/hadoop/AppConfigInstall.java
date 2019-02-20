package com.java.hadoop;

import com.java.hadoop.linux.adduser.AddUser;
import com.java.hadoop.linux.javainstall.JavaInstall;
import com.java.hadoop.linux.port.FireWallController;

public class AppConfigInstall {
    public static void main(String[] args) throws Exception {
        //创建用户
        AddUser au = new AddUser();
        au.addUser();
        //安装java
        JavaInstall ji= new JavaInstall();
        ji.installJava();
//        //配置端口
        FireWallController fwc = new FireWallController();
        fwc.modifyPortSet();
        fwc.execShell();



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
