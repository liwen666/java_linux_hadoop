package com.java.hadoop.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZooKeeperTest {
    public static Logger logger = LoggerFactory.getLogger(ZooKeeperTest.class);

    public static void main(String[] args) throws Exception {
//        ZooKeeper zk = new ZooKeeper("172.16.102.22:2181", 3000, new Watcher() {
//        ZooKeeper zk = new ZooKeeper("172.16.102.23:2181", 3000, new Watcher() {
//        ZooKeeper zk = new ZooKeeper("192.168.60.100:2181", 3000, new Watcher() {
        ZooKeeper zk = new ZooKeeper("10.0.22.87:2181", 3000, new Watcher() {
//        ZooKeeper zk = new ZooKeeper("172.16.101.12:2181", 3000, new Watcher() {
//        ZooKeeper zk = new ZooKeeper("192.168.137.111:2181", 3000, new Watcher() {
            public void process(WatchedEvent event) {
                // TODO Auto-generated method stub
                logger.debug(" receive event : " + event.getType().name());
            }
        });
        System.out.println("=========创建节点===========");
        if (null == zk.exists("/test", false)) {
            zk.create("/test", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("=============查看节点是否安装成功===============");
        System.out.println(new String(zk.getData("/test", false, null)));

        System.out.println("=========修改节点的数据==========");
        zk.setData("/test", "zNode2".getBytes(), -1);
        System.out.println("========查看修改的节点是否成功=========");
        System.out.println(new String(zk.getData("/test", false, null)));

        System.out.println("=======删除节点==========");
        zk.delete("/test", -1);
        System.out.println("==========查看节点是否被删除============");
        System.out.println("节点状态：" + zk.exists("/test", false));
        zk.close();
    }

    @Test
    public void testKafkaZk() throws Exception {
        ZooKeeper zk = new ZooKeeper("172.16.102.23:2181", 3000, null);
        ls("/");
    }

    /**
     * 列出指定path下所有孩子
     */
    public void ls(String path) throws Exception {
        ZooKeeper zk = new ZooKeeper("172.16.102.23:2181", 3000, new Watcher() {
            public void process(WatchedEvent event) {
                // TODO Auto-generated method stub
//                logger.debug(" receive event : " + event.getType().name());
            }
        });
        List<String> list = zk.getChildren(path, null);
        //判断是否有子节点
        if (list.isEmpty() || list == null) {
            return;
        }
        for (String s : list) {
            System.out.println(s);
            if(s.equals("JAVA_TOPIC")){
                System.out.println(path);

            }
            //判断是否为根目录
            if (path.equals("/")) {
                ls(path + s);
            } else {
                ls(path + "/" + s);
            }

        }
    }

    @Test
    public void getNode() throws Exception {
        ZooKeeper zk = new ZooKeeper("172.16.102.23:2181", 3000, new Watcher() {
            public void process(WatchedEvent event) {
                // TODO Auto-generated method stub
                logger.debug(" receive event : " + event.getType().name());
            }
        });

        System.out.println(new String(zk.getData("/brokers/topics/JAVA_TOPIC", false, null)));

    }
}