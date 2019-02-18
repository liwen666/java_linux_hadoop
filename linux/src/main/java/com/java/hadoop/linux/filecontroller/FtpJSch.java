package com.java.hadoop.linux.filecontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.junit.Test;

public class FtpJSch {

    public static ChannelSftp sftp = null;

    //账号
    private static String user = "root";
    //主机ip
    private static String host = "192.168.42.210";
    //密码
    private static String password = "root";
    private static Session sshSession = null;
    private static Channel channel = null;
    //端口
    private static int port = 22;
    //上传地址
    private static String directory = "/home/liwen";
    //下载目录

    public static void getConnect() {
        try {
            JSch jsch = new JSch();

            //获取sshSession  账号-ip-端口
            sshSession = jsch.getSession(user, host, port);
            //添加密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");

            sshSession.setConfig(sshConfig);
            //开启sshSession链接
            sshSession.connect();
            //获取sftp通道
            channel = sshSession.openChannel("sftp");
            //开启
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeSftp() {
        sftp.quit();
        channel.disconnect();
        sshSession.disconnect();

    }

    /**
     * @param uploadFile 上传文件的路径
     * @return 服务器上文件名
     */
    public static String upload(String uploadFile) {
        File file = null;
        String fileName = null;
        try {
            sftp.cd(directory);
            file = new File(uploadFile);
            //获取随机文件名
            fileName = uploadFile.substring(uploadFile.lastIndexOf("\\") + 1, uploadFile.length());
//            fileName = UUID.randomUUID().toString() + file.getName().substring(file.getName().length() - 5);
            //文件名是 随机数加文件名的后5位
            sftp.put(new FileInputStream(file), fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file == null ? null : fileName;
    }

    @Test
    public void upLoadFile() {
        FtpJSch.getConnect();
        String upload = FtpJSch.upload("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\linux\\filecontroller\\profile");
        System.out.println(upload);
//        FtpJSch.closeSftp();
    }

    @Test
    public void listFile() throws SftpException {
        FtpJSch.getConnect();
        Vector ls = sftp.ls("/home/liwen/");
//		Vector ls = sftp.ls("/home/liwen");
//		ChannelSftp.LsEntrySelector
        for (Object str : ls) {
            System.out.println(str);
        }
    }

    @Test
    public void deleteFile() throws Exception {
        FtpJSch.getConnect();
        Vector ls = sftp.ls("/home/liwen/");
        for (Object str : ls) {
            System.out.println(str);
        }
        sftp.rm("/home/liwen/*");
        Vector ls1 = sftp.ls("/home/liwen/");
        for (Object str : ls1) {
            System.out.println(str);
        }
    }

    @Test
    public void download() {
        FtpJSch.getConnect();
        File f = new File("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\linux\\filecontroller\\download\\");
        f.mkdirs();
        try {
            sftp.cd("/home/liwen/");
            File file = new File("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\linux\\filecontroller\\download\\profile");
            System.out.println(file.exists());
            if (!file.exists()) {
                file.createNewFile();
            }
            sftp.get("profile", new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void download(String dir ,String fileName) {
        FtpJSch.getConnect();
//        File f = new File("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\linux\\filecontroller\\download\\");
//        f.mkdirs();
        try {
            sftp.cd(dir);
            File file = new File("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\linux\\filecontroller\\basefile\\base_"+fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            sftp.get(fileName, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void downLoadBaseFile(){
//        配置linux环境变量
//        download("/etc/","profile");
//        配置用户权限
//        download("/etc/","sudoers");


    }
}