package com.java.hadoop.linux.bashexec;

import com.java.hadoop.linux.LinuxShell;
import com.java.hadoop.linux.filecontroller.FtpJSch;
import com.temp.common.base.util.PackageScanUtil;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Vector;

/**
 * @author ：dongbl
 * @version ：
 * @Description：
 * @date ：9:19 2017/11/14
 */
public class TestBash {
    String ip="192.168.42.210";
    String username="root";
    String password="root";

    /**
     * 创建unix脚本文件
     * @throws Exception
     */
    @Test
    public void createShFile() throws Exception {
        File file = new File("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\linux\\bashexec\\bashfile\\test3.sh");
        if(!file.exists()){
            file.createNewFile();
        }
        String commond = "#!/bin/sh\n" +
                "cd /home/liwen\n" +
                "mkdir shell_tut\n" +
                "cd shell_tut\n" +
                "for ((i=0; i<10; i++)); do\n" +
                " touch test_$i.txt\n" +
                "done\n";
        char[] chars = commond.toCharArray();
        FileWriter fw = new FileWriter(file);
        fw.write(chars);
        fw.close();
    }


    @Test
    /**
     * 因为文件格式的原因上传的文件无法执行
     */
    public void upLoadShell() throws Exception {
        FtpJSch.getConnect();
        FtpJSch.sftp.cd("/home/liwen");
        Resource[] resource = PackageScanUtil.findResource("com.java.hadoop.linux.bashexec.bashfile");
        for(Resource r:resource){
            r.getFile();
            FtpJSch.sftp.put(r.getInputStream(), r.getFilename());
        }
    }
    @Test
    public void execShell() throws Exception {
        LinuxShell.login(ip, 22, username,password);
        String result = LinuxShell.executeShell("sh /home/liwen/test1.sh");
        System.out.println(result);
        LinuxShell.close();
    }

    @Test
    public void downloadBash() throws Exception {
        FtpJSch.getConnect();
        try {
            FtpJSch.sftp.cd("/home/liwen");
            File file = new File("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\linux\\bashexec\\bashfile\\test1.sh");
            if (!file.exists()) {
                file.createNewFile();
            }
            FtpJSch.sftp.get("test1.sh", new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearFile() throws Exception {
        FtpJSch.getConnect();
        Vector ls = FtpJSch.sftp.ls("/home/liwen/");
        for (Object str : ls) {
            System.out.println(str);
        }
        FtpJSch.sftp.rm("/home/liwen/*");
        Vector ls1 = FtpJSch.sftp.ls("/home/liwen/");
        for (Object str : ls1) {
            System.out.println(str);
        }
    }

    @Test
    public void deleteFile() throws Exception {
    }
}