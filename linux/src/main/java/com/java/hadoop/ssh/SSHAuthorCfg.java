package com.java.hadoop.ssh;

import com.java.hadoop.linux.LinuxUtil;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;

import static com.java.hadoop.linux.LinuxShell.executeShell;

public class SSHAuthorCfg {
    private Session ftpsession;
    private Session execSession;
    @Test
    public void getSSHfile() throws Exception {
        //对其他机器授权
        String ip = "192.168.42.211";
        Login("hadoop", ip, 22);
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
        String pwd = LinuxUtil.executeShell("pwd",execSession);
        System.out.println(pwd);
        LinuxUtil.sshFileCustomer("authorized_keys","/home/hadoop/.ssh/",ftpsession,"hadoop",ip);
        ftpsession.disconnect();
        execSession.disconnect();


    }
    @Test
    public void upSSHfile() throws Exception {
        //对其他机器授权
        String ip = "192.168.42.211";
        Login("hadoop", ip, 22);
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
        String pwd = LinuxUtil.executeShell("pwd",execSession);
        System.out.println(pwd);
        LinuxUtil.sshFileUploadCustomer("hadoop",ftpsession,ip);
        ftpsession.disconnect();
        execSession.disconnect();


    }



    public void Login(String user, String host, int port) throws JSchException {
        JSch jsch = new JSch();
        execSession = jsch.getSession(user, host, port);
        ftpsession = jsch.getSession(user, host, port);


    }
}
