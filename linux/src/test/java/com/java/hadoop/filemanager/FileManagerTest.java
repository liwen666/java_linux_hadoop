package com.java.hadoop.filemanager;

import com.alibaba.fastjson.JSONObject;
import com.java.hadoop.linux.LinuxUtil;
import com.java.hadoop.linux.filecontroller.download.LinuxFileDomain;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.Cleanup;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;


public  class FileManagerTest {
    private Session ftpsession;
    private Session execSession;

    public static void main(String[] args) {
        System.out.println(1);
    }
    @Test
    public void getLinuxFileCategory() throws Exception {
        //对其他机器授权
        String ip = "192.168.42.210";
//        String ip = "192.168.42.211";
        String userName = "root";
        int port = 22;
        Login(userName, ip, port);
        String filePath = "/etc/sysconfig/";
        String fileName = "network";
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
        String pwd = LinuxUtil.executeShell("pwd", execSession);
        System.out.println(pwd);
        LinuxUtil.findFile(null, ip, userName, filePath, fileName, ftpsession);
        String fileCfg = "E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\filemanager\\file_linux_cfg.json";
        @Cleanup FileInputStream fis = new FileInputStream(fileCfg);
        byte[] bytes = new byte[1024];
        int read = fis.read(bytes);
        String s = new String(bytes, 0, read);
        List<LinuxFileMnangerDomain> linuxFileMnangerDomains = JSONObject.parseArray(s, LinuxFileMnangerDomain.class);
        LinuxFileMnangerDomain lfmd = null;
        boolean flag = false;
        for (LinuxFileMnangerDomain l : linuxFileMnangerDomains) {
            if (ip.equals(l.getIp())) {
                flag = true;
                lfmd = l;
            }
        }
        if (!flag) {
            lfmd = new LinuxFileMnangerDomain();
            lfmd.setIp(ip);
            linuxFileMnangerDomains.add(lfmd);
        }

        LinuxFileDomain lfd = new LinuxFileDomain();
        lfd.setFileName(fileName);
        lfd.setFilePath(filePath);
        Map<String, FileData> fileDataMap = lfmd.getFileDataMap();
        FileData fileData = fileDataMap.get(userName);
        if (fileData == null) {
            fileData = new FileData();
            fileData.setUserName(userName);
            fileDataMap.put(userName, fileData);
            List<LinuxFileDomain> linuxFileDomains = fileData.getLinuxFileDomains();
            linuxFileDomains.add(lfd);
        } else {
            List<LinuxFileDomain> linuxFileDomains = fileData.getLinuxFileDomains();
            if (!linuxFileDomains.contains(lfd)) {
                linuxFileDomains.add(lfd);
            }
        }

        String string = JSONObject.toJSON(linuxFileMnangerDomains).toString();
        System.out.println(string);
        @Cleanup FileOutputStream fileCfgStream = new FileOutputStream(fileCfg, false);
        fileCfgStream.write(string.getBytes());
        fileCfgStream.flush();

        ftpsession.disconnect();
        execSession.disconnect();


    }

    @Test
    public void upLinuxFileCategory() throws Exception {
        //对其他机器授权
//        String ip = "192.168.42.210";
        String ip = "192.168.42.211";
        String userName = "root";
        int port = 22;
        Login(userName, ip, port);
        String fileName = "network";
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
        String pwd = LinuxUtil.executeShell("pwd", execSession);
        ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
        sftp.connect();
        System.out.println(pwd);
        FileData fileData = LinuxUtil.findLinuxFileDomain(ip, userName);
        System.out.println(JSONObject.toJSON(fileData));
        List<LinuxFileDomain> linuxFileDomains = fileData.getLinuxFileDomains();
        String filePath = null;
        for (LinuxFileDomain lfd : linuxFileDomains) {
            if (fileName.equals(lfd.getFileName())) {
                filePath = lfd.getFilePath();
            }
        }
        if (null != filePath) {
            String packageBase = "com.java.hadoop.filemanager";
            String srcPackage = packageBase + "." + ip.replaceAll("\\.", "-") + "." + userName;
            Resource resource = LinuxUtil.findResource(srcPackage, fileName);
            System.out.println(resource);
            sftp.cd(filePath);
            sftp.put(resource.getInputStream(), fileName);

        }

        sftp.disconnect();
        ftpsession.disconnect();
        execSession.disconnect();


    }


    public void Login(String user, String host, int port) throws JSchException {
        JSch jsch = new JSch();
        execSession = jsch.getSession(user, host, port);
        ftpsession = jsch.getSession(user, host, port);


    }
}