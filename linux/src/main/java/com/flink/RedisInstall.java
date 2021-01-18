package com.flink;

import com.java.hadoop.filemanager.FileManager;
import com.jcraft.jsch.*;
import com.linux.temp.utils.PropertiesThreadLocalHolder;
import com.linux.temp.utils.YamlUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * java 登录linux系统，并读取执行shell命令结果
 *
 * @author wanghonggang
 * 2018-10-30
 */
public class RedisInstall {

    private Session ftpsession;
    private Session execSession;



    /**
     * 登录linux系统
     */
    @Test
    public void installRedis() {
        try {
            Map<?, ?> map = YamlUtil.loadYaml("redis.yaml");
//            Map<?, ?> map = YamlUtil.loadYaml("hadoop_slaver.yaml");
            String baseDir = (String) YamlUtil.getProperty(map, "baseDir");
            String filePath = (String) YamlUtil.getProperty(map, "file_linux_cfg");
            String upLoadFile = (String) YamlUtil.getProperty(map, "upLoadFile");
            String upLoadFilePackage = (String) YamlUtil.getProperty(map, "upLoadFilePackage");

//			登录hadoop 的shell和ftp
            String user = (String) YamlUtil.getProperty(map, "app.sit.linux.user");
            String ip = (String) YamlUtil.getProperty(map, "app.sit.linux.ip");
            Integer sshPort = (Integer) YamlUtil.getProperty(map, "app.sit.linux.port");
            String password = (String) YamlUtil.getProperty(map, "app.sit.linux.password");
            String appPackage = (String) YamlUtil.getProperty(map, "app.sit.linux.app-package");
            String appHome = (String) YamlUtil.getProperty(map, "app.sit.linux.app-home");
            List<String> property = (List<String>) YamlUtil.getProperty(map, "app.sit.conf");
            String prefix = (String) YamlUtil.getProperty(map, "app.sit.prefix");
            String configPath = (String) YamlUtil.getProperty(map, "app.sit.config-path");
            boolean getFile = (boolean) YamlUtil.getProperty(map, "app.sit.get-file");
            PropertiesThreadLocalHolder.addProperties("file_linux_cfg", filePath);
            PropertiesThreadLocalHolder.addProperties("upLoadFile", upLoadFile);
            PropertiesThreadLocalHolder.addProperties("upLoadFilePackage", upLoadFilePackage);

            Login(user, ip, sshPort);
            execSession.setPassword(password);
            ftpsession.setPassword(password);
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
            String s3 = executeShell("mkdir -vp " + appHome);
            System.out.println("创建app目录 " + s3);
            sftp.cd(appHome + "../");
            /**
             * 将u盘的java上传到linux*******************************************************88
             */
            boolean mark = false;
            Vector ls = sftp.ls("./");
            for (Object str : ls) {
                ChannelSftp.LsEntry cl = (ChannelSftp.LsEntry) str;
                if (cl.getLongname().endsWith(appPackage)) {
                    System.out.println(str + "   redis 已经被安装");
                    mark = true;
                    break;
                }
            }
            if (!mark) {
                File javaFile = new File(baseDir + appPackage);
                sftp.put(new FileInputStream(javaFile), javaFile.getName());
            }

            /**
             * 解压缩
             */
            if (!mark) {
                String s2 = executeShell("tar -zxvf " + appHome + "../" + appPackage + "  -C " + appHome);
                System.out.println("解压redis_____________________" + s2);
                executeShell("mv  " + appHome + appPackage.replace(".tar", "").replace(".gz", "") + "  " + appHome + "redis");
            }




            String s1 = executeShell("cd "+appHome+"/redis \n ls");
            System.out.println("进入 redis"+s1);
            if(!mark){
                String s2 = executeShell("cd "+appHome+"redis/src \n make");
                System.out.println("-------------开始安装 make: "+s2);
                String makeInstall = executeShell("cd "+appHome+"redis/src \n make install PREFIX=/usr/local/redis ");
                System.out.println("--------------makeInstall: "+makeInstall);
                String cp = executeShell("cd "+appHome+"redis \n cp redis.conf /usr/local/redis/ ");
                System.out.println("------------cp redis config:  "+cp);
                 executeShell("cp /etc/rc.local  /usr/local/redis");
            }
//             executeShell("mv  /usr/local/redis/rc.local  /etc");


            FileManager fileManager = new FileManager();

            //下载配置
            for (String file : property) {
                try {
                    if (getFile) {
                        fileManager.getLinuxFileCategory(prefix, ip, user, password, 22, file, configPath, new HashMap() {{
                            put("file_linux_cfg", filePath);
                        }});
                    } else {

                        fileManager.upLinuxFileCategory(prefix, ip, user, password, 22, file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            executeShell("cp /usr/local/redis/bin/redis-server /usr/local/bin/\n" +
                    "cp /usr/local/redis/bin/redis-cli /usr/local/bin/\n");


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