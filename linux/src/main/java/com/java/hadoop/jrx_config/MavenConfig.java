package com.java.hadoop.jrx_config;

import com.java.hadoop.filemanager.FileManager;

public class MavenConfig {
    public static void main(String[] args) throws Exception {
        String filePath="/home/jrxany/apache-maven-3.5.4/conf";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        fileManager.getLinuxFileCategory("172.16.102.24","jrxany","20191022",22,"settings.xml",filePath);



//        mvn dependency:get -DremoteRepositories=http://repo1.maven.org/maven2/ -DgroupId=junit -DartifactId=junit -Dversion=4.8.2
//        mvn dependency:get -DremoteRepositories=http://10.0.9.13:8081/nexus/content/groups/public  -DgroupId=jrx.tutorial -DartifactId=b01-simple-job -Dversion=1.0.0
//        mvn dependency:get  -DgroupId=jrx.batch.common -DartifactId=batch-common -Dversion=1.10.0-SNAPSHOT

//        mvn dependency:get  -DgroupId=jrx.batch.common -DartifactId=batch-common -Dversion=1.10.0-SNAPSHOT
//        mvn dependency:get  -DgroupId=jrx.tutorial -DartifactId=b01-simple-job -Dversion=1.0.0
        fileManager.upLinuxFileCategory("est_center_new", "172.16.102.24","jrxany","20191022",22,"settings.xml");
//
        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
