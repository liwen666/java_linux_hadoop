package com.java.hadoop.linux;

import com.alibaba.fastjson.JSONObject;
import com.java.hadoop.linux.filecontroller.FtpJSch;
import com.java.hadoop.linux.filecontroller.download.LinuxFileDomain;
import com.jcraft.jsch.*;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.List;

import static com.java.hadoop.linux.filecontroller.FtpJSch.sftp;

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

    public static String upLoadFile(Resource resource, Session shellSession, String directory) throws JSchException {
        ChannelSftp sftp = (ChannelSftp) shellSession.openChannel("sftp");
        sftp.connect();
        try {
            sftp.cd(directory);
            //获取随机文件名
            //文件名是 随机数加文件名的后5位
            sftp.put(resource.getInputStream(), resource.getFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resource.getFilename() == null ? null : resource.getFilename();
    }

    public static void download(String fileName,String directory, Session shellSession,String authDir) throws JSchException {
        ChannelSftp sftp = (ChannelSftp) shellSession.openChannel("sftp");
        Object o = System.getProperties().get("user.dirs");
        String basePath = System.getProperty("user.dir");
        String dir=basePath+"\\src\\main\\java\\com\\java\\hadoop\\linux\\filecontroller\\download\\"+authDir;
            File f = new File(dir);
            f.mkdirs();
        try {
            sftp.connect();
            sftp.cd(directory);
            File file = new File(dir+"/"+fileName);
            System.out.println(file.exists());
            if (!file.exists()) {
                file.createNewFile();
            }
            sftp.get(fileName, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void userFileDownload(String userName,Session ftpsession) throws Exception {
        //下载 用户文件
        List<LinuxFileDomain> linuxFileDomains=findLinuxFile(userName);
        for(LinuxFileDomain l :linuxFileDomains){
            LinuxUtil.download(l.getFileName(),l.getFilePath(),ftpsession,userName);
        }
    }

    public static void userFileUpload(String userName,Session ftpsession) throws Exception {
//        上传文件
        List <LinuxFileDomain> linuxFileDomains=findLinuxFile(userName);
        for(LinuxFileDomain l :linuxFileDomains){
            Resource resource = LinuxUtil.findResource("com.java.hadoop.linux.filecontroller.download", l.getFileName());
            LinuxUtil.upLoadFile(resource,ftpsession,l.getFilePath());
        }
    }
    public static List<LinuxFileDomain> findLinuxFile(String userName) throws IOException {
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

}
