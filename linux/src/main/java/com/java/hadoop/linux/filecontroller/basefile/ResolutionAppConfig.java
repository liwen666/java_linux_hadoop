package com.java.hadoop.linux.filecontroller.basefile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.temp.common.base.util.PackageScanUtil;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ResolutionAppConfig {
    public static String getConfigstr(String appFlag) throws IOException {
        Resource[] resource = PackageScanUtil.findResource("com.java.hadoop.linux.filecontroller.basefile");
        String appConfig = null;
        for(Resource r:resource){
            String filename = r.getFilename();
            if("appconfig.json".equals(filename)){
                InputStream inputStream = r.getInputStream();
                byte [] cache = new byte[1024*100];
                int read = inputStream.read(cache);
                String configStr = new String(cache);
                System.out.println(configStr);
                JSONObject jsonObject = JSONObject.parseObject(configStr);

                appConfig = (String) jsonObject.get(appFlag);
            }

        }
       String a = " " +
               "export JAVA_HOME=/home/hadoop/java/jdk1.8.0_191 " +
               "export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar " +
               "export PATH=$PATH:$JAVA_HOME/bin";
        return appConfig;

    }
}
