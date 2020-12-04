package com.java.hadoop.elasticsearch.es_dev_test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ES客户端类
 * 
 * @author shiye
 *
 */
public class ElasticSearchClient {

	/**
	 *  创建索引
	 * @throws Exception
	 */
	public void createIndex() throws Exception {
		// 1 创建一个Settings对象，相当于一个配置信息，主要配置集群中的名称
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		// 2 创建一个客户端client对象 （单机版的暂时没问题，集群的有问题）
//		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		TransportClient client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("10.0.8.11"), 9200));

		// 3 使用client创建一个索引库
		client.admin().indices().prepareCreate("index_client").get();// 执行操作
		System.out.println("index_client 索引设置成功...");
		// 4 关闭client对象
		client.close();
	}

	/**
	 *  设置mapping信息
	 * @throws IOException
	 */
	public void setMappings() throws IOException {

		// 1 创建一个Settings对象，相当于一个配置信息，主要配置集群中的名称
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		// 2 创建一个客户端client对象 （单机版的暂时没问题，集群的有问题）
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("10.0.8.11"), 9200));

//		{
//	        "properties": {
//	            "content": {
//	                "type": "text",
//	                "analyzer": "ik_max_word",
//	                "search_analyzer": "ik_max_word"
//	            }
//	        }
//		}
		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()
					.startObject("properties")
						.startObject("content")
							.field("type", "text")
							.field("analyzer","ik_max_word")
							.field("search_analyzer","ik_max_word")
						.endObject()
					.endObject()
				.endObject();
		System.out.println(builder.toString());
		//使用client把mapping信息设置到索引库中
		client.admin().indices()
				.preparePutMapping("index_client") //设置要做映射的索引
				.setType("content")//设置要做映射的type
				.setSource(builder)//mapping信息
				.get();//执行操作
		System.out.println("mapping设置成功...");
		//管理客户端
		client.close();

	}
	
	/**
	 * 添加文档 方式一
	 * @throws IOException 
	 */
	public void setAddDOC1() throws IOException {
		// 1 创建一个Settings对象，相当于一个配置信息，主要配置集群中的名称
		Settings settings = Settings.builder().put("cluster.name", "my-elasticsearch").build();
		// 2 创建一个客户端client对象 （单机版的暂时没问题，集群的有问题）
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		
		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()
					.field("content","美国留给伊拉克的是个烂摊子吗")
				.endObject();
		System.out.println(builder.isPrettyPrint());
		//把文档对象添加到索引库
		client.prepareIndex()
				.setIndex("index_client") //设置索引名称
				.setType("content")	//设置type
				.setId("1")	//设置文档的id，如果不设置的话自动生成一个id
				.setSource(builder) //设置文档信息
				.get(); //执行操作
		System.out.println("文档添加成功...");
		//关闭客户端
		client.close();
	}
	
	/**
	 * 添加文档 方式二
	 * @throws IOException 
	 */
	public void setAddDOC2() throws IOException {
		// 1 创建一个Settings对象，相当于一个配置信息，主要配置集群中的名称
		Settings settings = Settings.builder().put("cluster.name", "my-elasticsearch").build();
		// 2 创建一个客户端client对象 （单机版的暂时没问题，集群的有问题）
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		
		//创建对象
		Content content = new Content();
		content.setContent("公安部：各地校车将享最高路权");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonDocument = objectMapper.writeValueAsString(content);
		System.out.println(jsonDocument);
		
		//使用client对象把文档写入索引库
		client.prepareIndex("index_client", "content")
				.setSource(jsonDocument,XContentType.JSON)
				.get();
		
		System.out.println("文档添加成功...");
		//关闭客户端
		client.close();
	}

}
