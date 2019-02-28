package com.java.hadoop.filemanager;

import com.alibaba.fastjson.JSONObject;
import com.java.hadoop.linux.filecontroller.download.LinuxFileDomain;
import lombok.Data;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class LinuxFileMnangerDomain  {
    String ip;
    Map<String,FileData> fileDataMap = new HashMap<String, FileData>();

    public static void main(String[] args) {
        LinuxFileMnangerDomain lfmd = new LinuxFileMnangerDomain();
        lfmd.setIp("192.168.42.210");
        FileData fd = new FileData();
        fd.setUserName("root");
        LinuxFileDomain lfd = new LinuxFileDomain();
        lfd.setFileName("hosts");
        lfd.setFilePath("/etc/");
        fd.getLinuxFileDomains().add(lfd);
        lfmd.getFileDataMap().put("root",fd);


        System.out.println(JSONObject.toJSON(lfmd));
    }
@Test
    public  void arrayToJavaList() throws IOException {
        FileInputStream fis = new FileInputStream("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\filemanager\\spark.json");
        byte [] bytes = new byte[1024];
        int read = fis.read(bytes);
        String s = new String(bytes, 0, read);

        System.out.println(s);
        List<LinuxFileMnangerDomain> linuxFileMnangerDomains = JSONObject.parseArray(s,LinuxFileMnangerDomain.class);

    System.out.println(JSONObject.toJSON(linuxFileMnangerDomains));
    }

}
