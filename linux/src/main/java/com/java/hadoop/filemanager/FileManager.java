package com.java.hadoop.filemanager;

import com.alibaba.fastjson.JSONObject;
import com.java.hadoop.linux.LinuxUtil;
import com.java.hadoop.linux.filecontroller.download.LinuxFileDomain;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FileManager {

    private Session ftpsession;
    private Session execSession;

    public void getLinuxFileCategory(String dirPrefix, String ip, String userName, String passwd, int port, String fileName, String filePath) throws Exception {
        //对其他机器授权
        port = 22;
        Login(userName, ip, port);
        execSession.setPassword(passwd);
        ftpsession.setPassword(passwd);
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        execSession.setConfig("userauth.gssapi-with-mic", "no");
        ftpsession.setConfig("userauth.gssapi-with-mic", "no");
        // 连接超时
        execSession.connect(1000 * 15);
        ftpsession.connect(1000 * 15);
        String pwd = LinuxUtil.executeShell("pwd", execSession);
        System.out.println(pwd);
        LinuxUtil.findFile(dirPrefix, ip, userName, filePath, fileName, ftpsession);
        String fileCfg = System.getProperty("user.dir") + "\\linux\\src\\main\\java\\com\\java\\hadoop\\filemanager\\file_linux_cfg.json";
        @Cleanup FileInputStream fis = new FileInputStream(fileCfg);
        byte[] bytes = new byte[4024 * 1024];
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
        lfd.setDirPrefix(dirPrefix);
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
        @Cleanup FileOutputStream fileCfgStream = new FileOutputStream(fileCfg, false);
        fileCfgStream.write(string.getBytes());
        fileCfgStream.flush();
        LinuxUtil.findFile(dirPrefix, ip, userName, filePath, fileName, ftpsession);

        ftpsession.disconnect();
        execSession.disconnect();


    }

    public void upLinuxFileCategory(String dirPrefix, String ip, String userName, String passwd, int port, String fileName) throws Exception {
        //对其他机器授权
//        String ip = "192.168.42.210";
        port = 22;
        Login(userName, ip, port);
        execSession.setPassword(passwd);
        ftpsession.setPassword(passwd);
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        execSession.setConfig("userauth.gssapi-with-mic", "no");
        ftpsession.setConfig("userauth.gssapi-with-mic", "no");
        // 连接超时
        execSession.connect(1000 * 15);
        ftpsession.connect(1000 * 15);
        String pwd = LinuxUtil.executeShell("pwd", execSession);
        ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
        sftp.connect();
        System.out.println(pwd);
        FileData fileData = LinuxUtil.findLinuxFileDomain(ip, userName);
        List<LinuxFileDomain> linuxFileDomains = fileData.getLinuxFileDomains();
        String filePath = null;
        for (LinuxFileDomain lfd : linuxFileDomains) {
            if (fileName.equals(lfd.getFileName()) && (lfd.getDirPrefix() == null || lfd.getDirPrefix().equals(dirPrefix))) {
                filePath = lfd.getFilePath();
            }
        }
        if (null != filePath) {
            String packageBase = "com.java.hadoop.filemanager";
            String srcPackage = packageBase + "." + ip.replaceAll("\\.", "-") + "." + userName;
            if(StringUtils.isNoneEmpty(dirPrefix)){
                srcPackage=srcPackage+"."+dirPrefix;
            }
            Resource resource = LinuxUtil.findResource(srcPackage, fileName);
            System.out.println(resource);
            sftp.cd(filePath);
            sftp.put(resource.getInputStream(), fileName);

        }

        sftp.disconnect();
        ftpsession.disconnect();
        execSession.disconnect();


    }

    public void upLinuxByPath(String ip, String userName, String passwd, int port, String filePath, String fileName) throws Exception {
        //对其他机器授权
        port = 22;
        Login(userName, ip, port);
        execSession.setPassword(passwd);
        ftpsession.setPassword(passwd);
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        execSession.setConfig("userauth.gssapi-with-mic", "no");
        ftpsession.setConfig("userauth.gssapi-with-mic", "no");
        // 连接超时
        execSession.connect(1000 * 15);
        ftpsession.connect(1000 * 15);
        String pwd = LinuxUtil.executeShell("pwd", execSession);
        ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
        sftp.connect();
        sftp.cd(filePath);
        File file = new File( fileName);
        sftp.put(new FileInputStream(file), fileName);
        sftp.disconnect();
        ftpsession.disconnect();
        execSession.disconnect();


    }


    public void Login(String user, String host, int port) throws JSchException {
        JSch jsch = new JSch();
        execSession = jsch.getSession(user, host, port);
        ftpsession = jsch.getSession(user, host, port);


    }

    public static void main(String[] args) throws IOException {
//        File f = new File("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\filemanager\\192-168-42-210\\hadoop\\11.txt");
        File f = new File("E:\\github_program\\java_linux_hadoop\\src\\main\\java\\com\\java\\hadoop\\filemanager\\192-168-42-210\\hadoop\\core.txt");
        f.createNewFile();
    }
}
