package com.java.hadoop.linux.adduser;

import com.jcraft.jsch.*;
import com.temp.common.base.util.PackageScanUtil;
import org.springframework.core.io.Resource;

public class UserAddControl {
    private Session ftpsession;
    private Session execSession;

    public static void main(String[] args) throws Exception {
        UserAddControl userAddControl = new UserAddControl();
        String userName ="root";
        String password = "root";
        String host = "192.168.42.140";
        int port = 22;

        userAddControl.upLoadShell(userName,password,host,port);
    }
    public void upLoadShell(String userName,String password,String host,int port) throws Exception {
        Login(userName, host, port);

        execSession.setPassword(password);
        ftpsession.setPassword(password);
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        execSession.setConfig("StrictHostKeyChecking", "no");
        ftpsession.setConfig("StrictHostKeyChecking", "no");
        // 连接超时
        execSession.connect(1000 * 10);
        ftpsession.connect(1000 * 10);
        ChannelSftp sftp = (ChannelSftp) ftpsession.openChannel("sftp");
        //必须得连接，否则无法操作
        sftp.connect();
        try {
            sftp.mkdir("/root/bash");
        } catch (SftpException e) {
        }
        sftp.cd("/root/bash");
        Resource[] resource = PackageScanUtil.findResource("com.java.hadoop.linux.adduser");
        for(Resource r:resource){
            if(r.getFilename().endsWith(".sh")){
                System.out.println(r.getFilename());
                sftp.put(r.getInputStream(), r.getFilename());
                sftp.chmod(777,r.getFilename());
            }

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
