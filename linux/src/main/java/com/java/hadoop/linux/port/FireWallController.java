package com.java.hadoop.linux.port;

import com.java.hadoop.linux.LinuxShell;
import com.java.hadoop.linux.LinuxUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.temp.common.base.util.PackageScanUtil;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FireWallController {
    private List<String> store = new ArrayList<String>();
    private Session ftpsession;
    private Session execSession;

    @Test //查看linux系统的磁盘状态
    public void testLinux() {
        String ip = "192.168.42.210";
        String username = "root";
        String password = "root";
        LinuxShell.login(ip, 22, username, password);
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
        Resource[] resource = PackageScanUtil.findResource("com.java.hadoop.linux.port");
        for (Resource r : resource) {
            if (r.getFilename().endsWith(".sh")) {
                System.out.println(r.getFilename());
                sftp.put(r.getInputStream(), r.getFilename());
                sftp.chmod(777, r.getFilename());
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
        String s = LinuxUtil.executeShell("sh /home/hadoop/bash/fire-wall.sh \n", execSession);
        LinuxUtil.executeShell("service iptables save \n", execSession);
        System.out.println(s);
        //添加用户
        ftpsession.disconnect();
        execSession.disconnect();

    }

    //添加端口开放设置
    @Test
    public void modifyPortSet() throws Exception {
        Login("root", "192.168.42.210", 22);
        execSession.setPassword("root");
        execSession.setConfig("StrictHostKeyChecking", "no");
        execSession.connect();
        String s = LinuxUtil.executeShell("iptables -L INPUT -4n --line-numbers", execSession);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes("utf8"))));
        BufferedReader shReader = new BufferedReader(new InputStreamReader(new FileInputStream(LinuxUtil.findResource("com.java.hadoop.linux", "fire-wall.sh").getFile())));
        Set<String> port = new HashSet<String>();
        while (shReader.ready()) {
            String s1 = shReader.readLine().trim();
            if (s1.startsWith("iptables")) {
                port.add(s1.split(" ")[6]);
            }
        }

        while (br.ready()) {
            String s1 = br.readLine().trim();
            int i = s1.lastIndexOf(":");
            String num = s1.split(" ")[0];
            String port1 = s1.substring(i == -1 ? 0 : i + 1, s1.length());
            if (port.contains(port1)) {
                System.out.println(num + "   " + port1);
                addDletePort(num);
            }
        }
        //删除端口
        String num=null;
        while ((num=getPort())!=null){
            System.out.println(num);
            LinuxUtil.executeShell("iptables -D INPUT "+num,execSession);
        }
        //执行脚本添加端口
        br.close();
        shReader.close();
//        execShell();
        execSession.disconnect();
        ftpsession.disconnect();

    }

    private void addDletePort(String num) {
        store.add(num);
    }

    private String getPort() {
        if(store.size()>0){
            String s = store.get(store.size() - 1);
            store.remove(store.size() - 1);
            return s;
        }
        return null;
    }


    @Test
    public void shellExec() throws Exception {
        Login("root", "192.168.42.210", 22);
        execSession.setPassword("root");
        execSession.setConfig("StrictHostKeyChecking", "no");
        execSession.connect();
        String result = LinuxUtil.shellShell("java", execSession);
//        String result = LinuxUtil.shellShell("iptables -L INPUT -4n --line-numbers", execSession);
        System.out.println(result);
    }

    public void Login(String user, String host, int port) throws JSchException {
        JSch jsch = new JSch();
        execSession = jsch.getSession(user, host, port);
        ftpsession = jsch.getSession(user, host, port);


    }


}
