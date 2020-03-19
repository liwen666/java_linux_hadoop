package com.java.hadoop.jrx_config;

import com.java.hadoop.filemanager.FileManager;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class BatchSchedule {


    public static void main(String[] args) throws Exception {
        String filePath="/home/jrxany/batch_schedule/batch_new/batch-scheduling-1.3.0-SNAPSHOT/classes";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        fileManager.getLinuxFileCategory("172.16.102.24","jrxany","20191022",22,"application.yaml",filePath);



//        mvn dependency:get -DremoteRepositories=http://repo1.maven.org/maven2/ -DgroupId=junit -DartifactId=junit -Dversion=4.8.2
//        mvn dependency:get -DremoteRepositories=http://10.0.9.13:8081/nexus/content/groups/public  -DgroupId=jrx.tutorial -DartifactId=b01-simple-job -Dversion=1.0.0
//        mvn dependency:get  -DgroupId=jrx.batch.common -DartifactId=batch-common -Dversion=1.10.0-SNAPSHOT

//        mvn dependency:get  -DgroupId=jrx.batch.common -DartifactId=batch-common -Dversion=1.10.0-SNAPSHOT
//        mvn dependency:get  -DgroupId=jrx.tutorial -DartifactId=b01-simple-job -Dversion=1.0.0
        fileManager.upLinuxFileCategory("est_center_new", "172.16.102.24","jrxany","20191022",22,"application.yaml");
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
