package com.java.hadoop.linux;

import com.jcraft.jsch.*;
import com.temp.common.base.util.PackageScanUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashSet;

public class LinuxUtil {
    /**
     * 执行shell脚本
     *
     * @param command shell命令脚本
     * @return
     * @throws Exception
     */
    public static String executeShell(String command, Session execSession) throws Exception {

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

    public static String shellShell(String command, Session shellSession) {
        StringBuffer buffer = new StringBuffer();
        try {
            ChannelShell channel = (ChannelShell) shellSession.openChannel("shell");
            InputStream in = channel.getInputStream();
            channel.setPty(true);
            channel.connect();
//            System.out.println("exit-status: "+channel.getExitStatus());
            OutputStream os = channel.getOutputStream();
            os.write((command + "\r\n").getBytes());
            os.flush();
            InputStreamReader bis = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(bis);

            byte[] tmp = new byte[1024 * 10];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp);
                    if (i < 0) break;
                    String s = new String(tmp, 0, i);
                    buffer.append(s);
                    if (s.indexOf("--More--") >= 0) {
                        os.write((" ").getBytes());
                        os.flush();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
                if (!(in.available() > 0)) {
                    System.out.println(channel.isClosed() + "===channel===是否关闭======");
                    System.out.println(channel.getExitStatus());
                    System.out.println(channel.isConnected() + "==channel====是否连接======");
                    break;
                }
            }
            shellSession.disconnect();
            System.out.println(channel.isClosed() + "==channel====是否关闭连接");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static Resource findResource(String packageParten,String fileName) throws IOException {
        new LinkedHashSet();
        String packageSearchPath = "classpath*:" + packageParten.replaceAll("\\.", "/") + "/**/*";
        Resource[] resources = (new PathMatchingResourcePatternResolver()).getResources(packageSearchPath);
        for(Resource r:resources){
            if(r.getFilename().equals(fileName)){
                return r;
            }
        }
        return null;
    }

}
