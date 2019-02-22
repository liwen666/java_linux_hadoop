package com.java.hadoop.linux.filecontroller.download;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.hadoop.linux.LinuxUtil;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DownLoadAndUpLoadFile {
    private Session ftpsession;
    private Session execSession;


    @Test
    public void sshMasterFileDownload() throws Exception {
//        登录root 的shell和ftp
        Login("root", "192.168.42.210", 22,"root");

        //下载root 用户文件
        List <LinuxFileDomain> linuxFileDomains=findLinuxFile("root");
        for(LinuxFileDomain l :linuxFileDomains){
            LinuxUtil.download(l.getFileName(),l.getFilePath(),ftpsession,"root");

        }

        ftpsession.disconnect();
        execSession.disconnect();
    }
    @Test
    public void sshMasterFileUpload() throws Exception {
//        登录root 的shell和ftp
        Login("root", "192.168.42.210", 22,"root");
//        上传文件
        List <LinuxFileDomain> linuxFileDomains=findLinuxFile("root");
        for(LinuxFileDomain l :linuxFileDomains){
            //跳过特殊文件
            if(l.getFileName().equals("sshd_config")){
            continue;
            }
            Resource resource = LinuxUtil.findResource("com.java.hadoop.linux.filecontroller.download", l.getFileName());
            LinuxUtil.upLoadFile(resource,ftpsession,l.getFilePath());
        }
        ftpsession.disconnect();
        execSession.disconnect();
    }

    private List<LinuxFileDomain> findLinuxFile(String userName) throws IOException {
        Resource resource = LinuxUtil.findResource("com.java.hadoop.linux.filecontroller.download", "file_cfg.json");
        InputStream inputStream = resource.getInputStream();
        byte []cache = new byte[1024];
        inputStream.read(cache);
        String s = new String(cache);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String files = jsonObject.get(userName).toString();
        List<LinuxFileDomain> linuxFileDomains = JSONObject.parseArray(files, LinuxFileDomain.class);
        return linuxFileDomains;
    }

    private void Login(String user, String host, int port,String pass) throws JSchException {
        JSch jsch = new JSch();
        execSession = jsch.getSession(user, host, port);
        ftpsession = jsch.getSession(user, host, port);
        execSession.setPassword(pass);
        ftpsession.setPassword(pass);
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        // 连接超时
        execSession.connect(1000 * 10);
        ftpsession.connect(1000 * 10);


    }

    @Test
    public void readFileCfg() throws Exception {
        Resource resource = LinuxUtil.findResource("com.java.hadoop.linux.filecontroller.download", "file_cfg.json");
        InputStream inputStream = resource.getInputStream();
        byte []cache = new byte[1024];
        inputStream.read(cache);
        String s = new String(cache);
        System.out.println(s);

        JSONArray objects = JSONObject.parseArray(s);
        JSONObject jsonObject = objects.getJSONObject(0);
        System.out.println(jsonObject.get("fileName"));
        inputStream.close();
        List<LinuxFileDomain> lists = new ArrayList<LinuxFileDomain>();
        LinuxFileDomain lfd = new LinuxFileDomain();
        lfd.setFileName("hosts");lfd.setFilePath("/etc/");
        lists.add(lfd);
        Object o = JSONObject.toJSON(lists);
        System.out.println(o.toString());
        List<LinuxFileDomain> linuxFileDomains = JSONObject.parseArray(s, LinuxFileDomain.class);
        for(LinuxFileDomain l :linuxFileDomains){
            System.out.println(l.toString());
        }

    }

    @Test
    public void dir() throws Exception {
        System.out.println(System.getProperty("user.dir"));
        String path=System.getProperty("user.dir");
        System.out.println(path);

    }
}
