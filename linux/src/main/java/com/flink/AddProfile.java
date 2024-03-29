package com.flink;

import com.java.hadoop.utils.PackageScanUtil;
import com.jcraft.jsch.*;
import com.linux.temp.utils.PropertiesThreadLocalHolder;
import com.linux.temp.utils.YamlUtil;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * java 登录linux系统，并读取执行shell命令结果
 *
 * @author wanghonggang
 * 2018-10-30
 */
public class AddProfile {

    private Session ftpsession;
    private Session execSession;


    /**
     * 登录linux系统
     */
    @Test
    public void addProfile() {
        try {
            Map<?, ?> map = YamlUtil.loadYaml("profile.yaml");
//            Map<?, ?> map = YamlUtil.loadYaml("hadoop_slaver.yaml");
            String baseDir = (String) YamlUtil.getProperty(map, "baseDir");
            String filePath = (String) YamlUtil.getProperty(map, "file_linux_cfg");
            String upLoadFile = (String) YamlUtil.getProperty(map, "upLoadFile");
            String upLoadFilePackage = (String) YamlUtil.getProperty(map, "upLoadFilePackage");

//			登录hadoop 的shell和ftp
            String user = (String) YamlUtil.getProperty(map, "profile.sit.linux.user");
            String ip = (String) YamlUtil.getProperty(map, "profile.sit.linux.ip");
            Integer sshPort = (Integer) YamlUtil.getProperty(map, "profile.sit.linux.port");
            String password = (String) YamlUtil.getProperty(map, "profile.sit.linux.password");

            Login(user, ip, sshPort);
            execSession.setPassword(password);
            ftpsession.setPassword(password);
            // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
            execSession.setConfig("StrictHostKeyChecking", "no");
            ftpsession.setConfig("StrictHostKeyChecking", "no");
            // 连接超时
            execSession.connect(1000 * 10);
            ftpsession.connect(1000 * 10);
            executeShell("mkdir -vp /home/bash");
            ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
            sftp.connect();
            sftp.cd("/etc");
            Resource resource = PackageScanUtil.findResource("com.flink", "profile", false);
            sftp.put(resource.getInputStream(), resource.getFilename());
            executeShell("vi +':w ++ff=unix' +':q' /etc/profile");
            executeShell("source /etc/profile");
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