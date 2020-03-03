package com.java.hadoop.zookeeper;

import com.java.hadoop.linux.LinuxConfig;
import com.java.hadoop.linux.filecontroller.UpLoadFileToLinux;
import com.jcraft.jsch.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

/**
 * java 登录linux系统，并读取执行shell命令结果
 *
 * @author wanghonggang
 * 2018-10-30
 */
public class InstallZookeeper {

    private Session ftpsession;
    private Session execSession;
    /**
     * 登录linux系统
     */
    @Test
    public void installZookeeper() {
        try {
//			登录hadoop 的shell和ftp
            Login("jrxany", "172.16.102.22", 22);
            execSession.setPassword("20191022");
            ftpsession.setPassword("20191022");
            // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
            execSession.setConfig("StrictHostKeyChecking", "no");
            ftpsession.setConfig("StrictHostKeyChecking", "no");
            // 连接超时
            execSession.connect(1000 * 10);
            ftpsession.connect(1000 * 10);
            String s = executeShell("df -h");
            System.out.println(s);
            String pwd = executeShell("pwd");
            System.out.println(pwd);
//			__________________________________________________________________
//			上传zookeeper文件 到jrxany用户目录

            ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
            //必须得连接，否则无法操作
            sftp.connect();
            /**
             * 初始化目录
             */
            String s0 = executeShell("mkdir -vp   /home/jrxany/batch_node/zookeeper/{data,logs}");
            System.out.println(s0);

            sftp.cd("/home/jrxany/batch_node/zookeeper");

            /**
             * 将u盘的java上传到linux*******************************************************88
             */

            boolean mark = false;
            Vector ls = sftp.ls("/home/jrxany/batch_node/zookeeper");
            for (Object str : ls) {
                ChannelSftp.LsEntry cl = (ChannelSftp.LsEntry) str;
                if(cl.getLongname().endsWith("zookeeper-3.4.12.tar.gz")){
                    System.out.println(str+"   zookeeper 已经被安装");

                    mark= true;
                    break;
                }
            }
            if(!mark){
                File zookeeper = new File("G:/开发安装包/大数据分布式需要的技术/zookeeper-3.4.12.tar.gz");
                sftp.put(new FileInputStream(zookeeper),zookeeper.getName());
            }
            /*******************************************************************/

            /**
             * 解压缩
             */
           /****************************解压java文件***********************/
            if(!mark){
                String s2 = executeShell("tar zxvf /home/jrxany/batch_node/zookeeper/zookeeper-3.4.12.tar.gz  -C /home/jrxany/batch_node/zookeeper");
                System.out.println("解压java_____________________"+s2);
            }
           /****************************解压java文件***********************/

            /**修改profile文件  修改环境变量*/
//            if(!mark){
//                LinuxConfig linuxConfig = new LinuxConfig();
//                linuxConfig.upLoadProfileFile(new String[]{"#Java Env"},sftp);
//                executeShell("source /etc/profile");
//                System.out.println("java 安装完成！");
//            }
            /****************************配置环境变量***********************/






            sftp.disconnect();
            ftpsession.disconnect();
            execSession.disconnect();
        } catch (JSchException e) {
            System.out.println("登录时发生错误！");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void installJavaByParam(String userName,String host,int port,String passWord) {
        try {
//			登录hadoop 的shell和ftp
            Login(userName, host, port);
            execSession.setPassword(passWord);
            ftpsession.setPassword(passWord);
            // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
            execSession.setConfig("StrictHostKeyChecking", "no");
            ftpsession.setConfig("StrictHostKeyChecking", "no");
            // 连接超时
            execSession.connect(1000 * 10);
            ftpsession.connect(1000 * 10);
            String s = executeShell("df -h");
            System.out.println(s);
            String pwd = executeShell("pwd");
            System.out.println(pwd);
//			__________________________________________________________________
//			上传java文件 到hadoop用户目录

            ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
            //必须得连接，否则无法操作
            sftp.connect();
            sftp.cd("/home/hadoop");
            /**
             * 将u盘的java上传到linux*******************************************************88
             */
            boolean mark = false;
            try {
                sftp.mkdir("/home/hadoop/java/");
            } catch (SftpException e) {
            }
            Vector ls = sftp.ls("/home/hadoop/java/");
            for (Object str : ls) {
                ChannelSftp.LsEntry cl = (ChannelSftp.LsEntry) str;
                if(cl.getLongname().endsWith("jdk1.8.0_191")){
                    System.out.println(str+"   java 已经被安装");
                    mark= true;
                    break;
                }
            }
            if(!mark){
                File javaFile = new File("E:\\kingston\\开发安装包\\java\\jdk-8u191-linux-x64.tar.gz");
                sftp.put(new FileInputStream(javaFile),javaFile.getName());
            }
            /*******************************************************************/

            /**
             * 解压缩
             */
            /****************************解压java文件***********************/
            if(!mark){
                String s1 = executeShell("mkdir -vp java");
                System.out.println(s1+"创建目录");
                String s2 = executeShell("tar zxvf jdk-8u191-linux-x64.tar.gz  -C /home/hadoop/java");
                System.out.println("解压java_____________________"+s2);
            }
            /****************************解压java文件***********************/

            /**修改profile文件  修改环境变量*/
            if(!mark){
                LinuxConfig linuxConfig = new LinuxConfig();
                JSch sftpRootJsch = new JSch();
               Session ftpsessionRoot = sftpRootJsch.getSession("root", host, port);
                ftpsessionRoot.setPassword("root");
                // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
                ftpsessionRoot.setConfig("StrictHostKeyChecking", "no");
                // 连接超时
                ftpsessionRoot.connect(1000 * 10);
                ChannelSftp sftpRoot = (ChannelSftp) ftpsessionRoot.openChannel("sftp");
                sftpRoot.connect();

                linuxConfig.upLoadProfileFile(new String[]{"#Java Env"},sftpRoot);
                executeShell("source /etc/profile");
                 ftpsessionRoot.disconnect();
                System.out.println("java 安装完成！");

            }
            /****************************配置环境变量***********************/






            sftp.disconnect();
            ftpsession.disconnect();
            execSession.disconnect();
        } catch (JSchException e) {
            System.out.println("登录时发生错误！");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }















    public void Login(String user, String host, int port) throws JSchException {
        JSch jsch = new JSch();
        execSession = jsch.getSession(user, host, port);
        ftpsession = jsch.getSession(user, host, port);


    }

    /**
     * 执行shell脚本
     *
     * @param command shell命令脚本
     * @return
     * @throws Exception
     */
    public String executeShell(String command) throws Exception {

        byte[] tmp = new byte[1024];
        StringBuffer resultBuffer = new StringBuffer(); // 命令返回的结果

        Channel channel = execSession.openChannel("exec");
        ChannelExec exec = (ChannelExec) channel;
        // 返回结果流（命令执行错误的信息通过getErrStream获取）
        InputStream stdStream = exec.getInputStream();

        exec.setCommand(command);
        exec.connect();

        try {
            // 开始获得SSH命令的结果
            while (true) {
                while (stdStream.available() > 0) {
                    int i = stdStream.read(tmp, 0, 1024);
                    if (i < 0) break;
                    resultBuffer.append(new String(tmp, 0, i));
                }
                if (exec.isClosed()) {
//					System.out.println(resultBuffer.toString());
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            //关闭连接
            channel.disconnect();
        }

        return resultBuffer.toString();
    }


}