package com.java.hadoop.linux.adduser;

import com.java.hadoop.linux.LinuxShell;
import org.junit.Test;

public class AddUser {
    @Test //查看linux系统的磁盘状态
    public  void testLinux (){
        String ip="192.168.42.210";
        String username="root";
        String password="root";
        LinuxShell.login(ip, 22, username,password);
        String command = "df -h";
        try {
            String result = LinuxShell.executeShell(command);
            System.out.println(result);
            LinuxShell.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addUser() throws Exception {

    }
}
