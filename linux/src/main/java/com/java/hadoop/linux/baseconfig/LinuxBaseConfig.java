package com.java.hadoop.linux.baseconfig;

import com.java.hadoop.linux.LinuxShell;
import com.java.hadoop.linux.LinuxUtil;
import com.jcraft.jsch.*;
import com.temp.common.base.util.PackageScanUtil;
import org.junit.Test;
import org.springframework.core.io.Resource;

public class LinuxBaseConfig {
    private Session ftpsession;
    private Session execSession;
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
    /**
     * 因为文件格式的原因上传的文件无法执行
     */
    public void upLoadShell() throws Exception {
        Login("root", "192.168.42.210", 22);

        execSession.setPassword("root");
        ftpsession.setPassword("root");
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        // 连接超时
        execSession.connect(1000 * 10);
        ftpsession.connect(1000 * 10);
        ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
        //必须得连接，否则无法操作
        sftp.connect();
        sftp.cd("/home/hadoop/bash");
        Resource[] resource = PackageScanUtil.findResource("com.java.hadoop.linux.baseconfig");
        for(Resource r:resource){
            if(r.getFilename().endsWith(".sh")){
                System.out.println(r.getFilename());
                sftp.put(r.getInputStream(), r.getFilename());
                sftp.chmod(777,r.getFilename());
            }

        }
      sftp.disconnect();
        ftpsession.disconnect();
        execSession.disconnect();
    }

    @Test
    public void execShell() throws Exception {
        upLoadShell();
        Login("root", "192.168.42.210", 22);
        execSession.setPassword("root");
        execSession.setConfig("StrictHostKeyChecking", "no");
        execSession.connect();
        //测试循环shell
        for(int i=1;i<3;i++){
            String s = LinuxUtil.executeShell("sh /home/hadoop/bash/for-"+i+".sh \n", execSession);
            System.out.println(s);
        }
        String s = LinuxUtil.executeShell("sh /home/hadoop/bash/baseconfig.sh \n", execSession);
        System.out.println(s);
        //添加用户

    }
    @Test
    public void addDependies() throws Exception {
        upLoadShell();
        Login("root", "192.168.42.210", 22);
        execSession.setPassword("root");
        execSession.setConfig("StrictHostKeyChecking", "no");
        execSession.connect();
        //测试循环shell
        String s = LinuxUtil.executeShell("sh /home/hadoop/bash/baseconfig.sh \n", execSession);
        System.out.println(s);
        //添加用户
        execSession.disconnect();

    }

    public void Login(String user, String host, int port) throws JSchException {
        JSch jsch = new JSch();
        execSession = jsch.getSession(user, host, port);
        ftpsession = jsch.getSession(user, host, port);


    }
    @Test
    public void addDependiesByParam(String userName,String host,int port,String passWord) throws Exception {
        Login(userName, host, port);

        execSession.setPassword(passWord);
        ftpsession.setPassword(passWord);
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        // 连接超时
        execSession.connect(1000 * 10);
        ftpsession.connect(1000 * 10);
        ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
        //必须得连接，否则无法操作
        sftp.connect();

        try {
            sftp.cd("/home/hadoop/bash");
        } catch (SftpException e) {
            sftp.mkdir("/home/hadoop/bash");
            sftp.cd("/home/hadoop/bash");
        }
        Resource[] resource = PackageScanUtil.findResource("com.java.hadoop.linux.baseconfig");
        //上传初始化脚本
        for(Resource r:resource){
            if(r.getFilename().endsWith(".sh")){
                System.out.println(r.getFilename());
                sftp.put(r.getInputStream(), r.getFilename());
                sftp.chmod(777,r.getFilename());
            }

        }
        String s = LinuxUtil.executeShell("sh /home/hadoop/bash/baseconfig.sh \n", execSession);
        System.out.println(s);
        //添加用户
        sftp.disconnect();
        ftpsession.disconnect();
        execSession.disconnect();

    }


}
