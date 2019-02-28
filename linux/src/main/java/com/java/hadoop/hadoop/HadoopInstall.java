package com.java.hadoop.hadoop;

import com.java.hadoop.linux.LinuxConfig;
import com.java.hadoop.linux.LinuxUtil;
import com.java.hadoop.linux.filecontroller.download.LinuxFileDomain;
import com.jcraft.jsch.*;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Vector;

/**
 * java 登录linux系统，并读取执行shell命令结果
 *
 * @author wanghonggang
 * 2018-10-30
 */
public class HadoopInstall {

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
//            Login("hadoop", "192.168.42.210", 22);
//            Login("hadoop", "192.168.42.210", 22);
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
            try {
                sftp.mkdir("/home/hadoop/hadoop/");
            } catch (SftpException e) {
            }
            Vector ls = sftp.ls("/home/hadoop/hadoop/");
            for (Object str : ls) {
                ChannelSftp.LsEntry cl = (ChannelSftp.LsEntry) str;
                if (cl.getLongname().endsWith("hadoop-2.8.5")) {
                    System.out.println(str + "   hadoop 已经被安装");
                    mark = true;
                    break;
                }
            }
            if (!mark) {
                File javaFile = new File("H:\\开发安装包\\大数据分布式需要的技术\\hadoop-2.8.5.tar.gz");
//                File javaFile = new File("H:\\hadoop-2.8.5.tar.gz");
                sftp.put(new FileInputStream(javaFile), javaFile.getName());
            }
            /*******************************************************************/

            /**
             * 解压缩
             */
            /****************************解压java文件***********************/
            if (!mark) {
                String s1 = executeShell("mkdir -vp hadoop");
                System.out.println(s1 + "创建目录");
                String s2 = executeShell("tar zxvf hadoop-2.8.5.tar.gz  -C /home/hadoop/hadoop");
                executeShell("cp /home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop/mapred-site.xml.template /home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop/mapred-site.xml");
                executeShell("mkdir -vp /home/hadoop/hadoop/hadoop-2.8.5/hdfs/{tmp,data,name}");
//                System.out.println("解压hadoop_____________________" + s2);
            }
            /****************************解压java文件***********************/

            /**修改profile文件  修改环境变量*/
//            if(!mark){
            LinuxConfig linuxConfig = new LinuxConfig();
            JSch sftpRootJsch = new JSch();
            Session ftpsessionRoot = sftpRootJsch.getSession("root", "192.168.42.210", 22);
            ftpsessionRoot.setPassword("root");
            // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
            ftpsessionRoot.setConfig("StrictHostKeyChecking", "no");
            // 连接超时
            ftpsessionRoot.connect(1000 * 10);
            ChannelSftp sftpRoot = (ChannelSftp) ftpsessionRoot.openChannel("sftp");
            sftpRoot.connect();
            linuxConfig.upLoadProfileFile(new String[]{"#hadoop Env", "#Java Env"}, sftpRoot);
            executeShell("source /etc/profile");
            System.out.println("hadoop  安装完成！");
//            }
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

    @Test
    public void sshMasterConfig() throws Exception {
//        登录hadoop 的shell和ftp
        Login("hadoop", "192.168.42.210", 22);
        execSession.setPassword("hadoop");
        ftpsession.setPassword("hadoop");
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        // 连接超时
        execSession.connect(1000 * 10);
        ftpsession.connect(1000 * 10);
        //下载密钥
//        LinuxUtil.userFileDownload("hadoop",ftpsession);
        LinuxUtil.userFileUpload("hadoop",ftpsession);


        ftpsession.disconnect();
        execSession.disconnect();
    }

    @Test
    public void sshAuthorization() throws Exception {


//        Login("hadoop", "192.168.42.210", 22);
        Login("hadoop", "192.168.42.211", 22);
        execSession.setPassword("hadoop");
        ftpsession.setPassword("hadoop");
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("userauth.gssapi-with-mic", "no");
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("userauth.gssapi-with-mic", "no");
        // 连接超时
        execSession.connect(1000 * 10);
        ftpsession.connect(1000 * 10);
//        LinuxUtil.executeShell("chmod 600 /home/hadoop/.ssh/authorized_keys",execSession);
        String s = executeShell("df -h");
        System.out.println(s);
        String pwd = executeShell("pwd");
        System.out.println(pwd);
        ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
        sftp.connect();
        try {
            sftp.mkdir("/home/hadoop/.ssh");
        } catch (SftpException e) {
        }
        LinuxUtil.userFileUpload("hadoop", execSession);
        LinuxUtil.executeShell("chmod 600 /home/hadoop/.ssh/authorized_keys", execSession);
        execSession.disconnect();
        ftpsession.disconnect();
    }

    @Test
    public void sshSlaver() throws Exception {
        //对其他机器授权
        Login("hadoop", "192.168.42.136", 22);
        execSession.setPassword("hadoop");
        ftpsession.setPassword("hadoop");
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        execSession.setConfig("userauth.gssapi-with-mic", "no");
        ftpsession.setConfig("userauth.gssapi-with-mic", "no");
        // 连接超时
        execSession.connect(1000 * 10);
        ftpsession.connect(1000 * 10);
        String s = executeShell("df -h");
        System.out.println(s);
        String pwd = executeShell("pwd");
        System.out.println(pwd);

        ftpsession.disconnect();
        execSession.disconnect();
    }

    @Test
    public void sshSlaverRoot() throws Exception {
        //对其他机器授权
        Login("root", "192.168.42.136", 22);
        execSession.setPassword("root");
        ftpsession.setPassword("root");
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        execSession.setConfig("userauth.gssapi-with-mic", "no");
        ftpsession.setConfig("userauth.gssapi-with-mic", "no");
        // 连接超时
        execSession.connect(1000 * 10);
        ftpsession.connect(1000 * 10);

        List<LinuxFileDomain> hadoop = LinuxUtil.findLinuxFile("root");
        for (LinuxFileDomain l : hadoop) {
            Resource resource = LinuxUtil.findResource("com.java.hadoop.linux.filecontroller.download.slaver_root", l.getFileName());
            LinuxUtil.upLoadFile(resource, ftpsession, l.getFilePath());
        }
        ftpsession.disconnect();
        execSession.disconnect();
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


    public static void main(String[] args) throws JSchException, IOException, InterruptedException {


        Socket socket = Util.createSocket("192.168.42.136", 22, 1000);
        SocketChannel channel = socket.getChannel();
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        socket.setTcpNoDelay(true);
        byte[] result = new byte[1024];
//        outputStream.write("ls -l ".getBytes());
//        outputStream.flush();
//        inputStream.read(result);
//        System.out.println(new String (result));
        outputStream.write("pwd".getBytes());
        outputStream.flush();
        Thread.sleep(2000);
        while (inputStream.available() > 0) {
            inputStream.read(result);
            System.out.println(new String(result));
            Thread.sleep(2000);
        }


    }


}