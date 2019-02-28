package com.java.hadoop.scala;

import com.java.hadoop.linux.LinuxConfig;
import com.jcraft.jsch.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Vector;

/**
 * java 登录linux系统，并读取执行shell命令结果
 *
 * @author wanghonggang
 * 2018-10-30
 */
public class ScalaInstall {

    private Session ftpsession;
    private Session execSession;

    /**
     * 登录linux系统
     */
    @Test
    public void installHadoop() {
        try {
//			登录hadoop 的shell和ftp
            Login("hadoop", "192.168.42.220", 22);
//            Login("hadoop", "192.168.42.220", 22);
//            Login("hadoop", "192.168.42.220", 22);
            execSession.setPassword("hadoop");
            ftpsession.setPassword("hadoop");
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
            Vector ls = sftp.ls("/home/hadoop/");
            for (Object str : ls) {
                ChannelSftp.LsEntry cl = (ChannelSftp.LsEntry) str;
                if (cl.getLongname().endsWith("scala-2.12.8.tgz")) {
                    System.out.println(str + "   scala 已经被安装");
                    mark = true;
                    break;
                }
            }
            if (!mark) {
                File javaFile = new File("H:\\开发安装包\\scala-java\\scala-2.12.8.tgz");
                sftp.put(new FileInputStream(javaFile), javaFile.getName());
            }
            /*******************************************************************/

            /**
             * 解压缩
             */
            /****************************解压java文件***********************/
            if (!mark) {
                System.out.println("创建spark目录");
                String s2 = executeShell("tar zxvf scala-2.12.8.tgz  -C /home/hadoop/hadoop");
//                executeShell("cp /home/hadoop/hadoop/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh.template /home/hadoop/hadoop/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh");
            }
            /****************************解压java文件***********************/

            /**修改profile文件  修改环境变量*/

            LinuxConfig linuxConfig = new LinuxConfig();
            JSch sftpRootJsch = new JSch();
            Session ftpsessionRoot = sftpRootJsch.getSession("root", "192.168.42.220", 22);
            ftpsessionRoot.setPassword("root");
            if(!mark){
            // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
            ftpsessionRoot.setConfig("StrictHostKeyChecking", "no");
            // 连接超时
            ftpsessionRoot.connect(1000 * 10);
            ChannelSftp sftpRoot = (ChannelSftp) ftpsessionRoot.openChannel("sftp");
            sftpRoot.connect();
            linuxConfig.upLoadProfileFile(new String[]{"#hadoop Env", "#Java Env","#spark Env",}, sftpRoot);
            executeShell("source /etc/profile");
            System.out.println("scala  安装完成！");
            }
            /****************************配置环境变量***********************/
            ftpsessionRoot.disconnect();
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