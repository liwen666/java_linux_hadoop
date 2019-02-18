package com.java.hadoop.linux;

import org.junit.Test;

public class MakeDir {
    String ip="192.168.42.210";
    String username="root";
    String password="root";
    String testCommand = "df -h";


    @Test
    public void makeLiwen() throws Exception {
        LinuxShell.login(ip, 22, username,password);
        String result = LinuxShell.executeShell("cd /home/liwen");
        System.out.println(result);
        String pwd = LinuxShell.executeShell("pwd");
        System.out.println(pwd);
        LinuxShell.close();
    }
}
