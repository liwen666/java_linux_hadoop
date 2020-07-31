package com.java.hadoop.elasticsearch.estest;

//import 省略。。。

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class EsUtils {

    public static TransportClient connectionEs(){


//        Settings esSettings = Settings.builder()
//                .put("cluster.name", "elasticsearch")
//                .put("client.transport.sniff", true)
//                .build();
//        TransportClient client = null;
//        try {
//            client = new PreBuiltTransportClient(esSettings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("172.16.101.22"), 9200));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        System.out.println("ElasticsearchClient 连接成功");
//        return client;


        //设置集群的名字
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .put("client.transport.ignore_cluster_name", true)
                .build();

        /*//忽视连接集群时名字验证
        builder.put("client.transport.ignore_cluster_name", true);
        //ping 一个节点时等待时间 默认5秒
        builder.put("client.transport.ping_timeout", "5s");
        //多久采样 ping / 节点列表和连接
        builder.put("client.transport.nodes_sampler_interval", "5s");*/
//        - 172.16.101.41:9200
//                - 172.16.101.22:9200
//                - 172.16.101.12:9200
        try {
            //连接
            @SuppressWarnings("resource")
            TransportClient client = new PreBuiltTransportClient(settings)
//                                         .addTransportAddress(new TransportAddress(InetAddress.getByName("172.16.101.41"), 9200));
//                                         .addTransportAddress(new TransportAddress(InetAddress.getByName("172.16.101.22"), 9200));
                                         .addTransportAddress(new TransportAddress(InetAddress.getByName("172.16.101.12"), 9200));
            return client;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void indexApi(){
        TransportClient client = connectionEs();
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
            "}";

        List<DiscoveryNode> connectedNodes = client.connectedNodes();
        System.out.println(connectedNodes);


        IndexResponse response = client.prepareIndex("twitter", "tweet").setSource(json, XContentType.JSON).get();

        String index = response.getIndex();
        String type = response.getType();

        String id = response.getId();

        long version = response.getVersion();

        RestStatus status = response.status();

        System.out.println(index);
        System.out.println(type);
        System.out.println(id);
        System.out.println(version);
        System.out.println(status);

    }

    public static void main( String[] args )
    {
        EsUtils.indexApi();
        System.out.println( "Hello World!" );
    }
}