package com.java.hadoop.elasticsearch.es_dev_test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ESQueryClient {
	
	/**
	 * 简单的执行查询对象
	 * @param client
	 * @param builder
	 */
	private void search(TransportClient client , QueryBuilder builder) {
		//4  执行查询
		SearchResponse searchResponse = client.prepareSearch("index_client")
				.setTypes("content")
				.setQuery(builder)
				.setFrom(0) //起始行数
				.setSize(5)	//每一页显示的数量
				.get();
		
		//5 取查询结果
		SearchHits searchHits = searchResponse.getHits();
	
		System.out.println("查询到的总的结果数 = " + searchHits.getTotalHits());
	
		Iterator<SearchHit> iterator = searchHits.iterator();
		while(iterator.hasNext()) {
			SearchHit hit = iterator.next();
			System.out.println(hit.getSourceAsString());
			
			System.out.println("------获取文档属性------");
			Map<String, Object> map = hit.getSourceAsMap();
			System.out.println(map.get("content"));
		}
	}
	
	/**
	 * 设置高亮显示的查询
	 * @param client
	 * @param builder
	 * @param hightFiled
	 */
	private void search(TransportClient client , QueryBuilder builder, String hightFiled) {
		
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field(hightFiled); //设置需要高亮显示的域
		highlightBuilder.preTags("<em>"); //设置前缀
		highlightBuilder.postTags("</em>");//设置后缀
		
		//4  执行查询
		SearchResponse searchResponse = client.prepareSearch("index_client")
				.setTypes("content")
				.setQuery(builder)
				.setFrom(0) //起始行数
				.setSize(5)	//每一页显示的数量
				.highlighter(highlightBuilder)//设置高亮显示
				.get();
		
		//5 取查询结果
		SearchHits searchHits = searchResponse.getHits();
	
		System.out.println("查询到的总的结果数 = " + searchHits.getTotalHits());
	
		Iterator<SearchHit> iterator = searchHits.iterator();
		while(iterator.hasNext()) {
			SearchHit hit = iterator.next();
			System.out.println(hit.getSourceAsString());
			
			System.out.println("------获取文档属性------");
			Map<String, Object> map = hit.getSourceAsMap();
			System.out.println(map.get("content"));
			
			System.out.println("------获取高亮显示的数据------");
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			HighlightField field = highlightFields.get(hightFiled);
			Text[] fragments = field.getFragments();
			if(fragments != null) {
				System.out.println(fragments[0]);
			}
		}
	}

	/**
	 * 通过id来查询
	 * @throws UnknownHostException
	 */
	public void queryById() throws UnknownHostException {
		// 1 创建一个Settings对象，相当于一个配置信息，主要配置集群中的名称
		Settings settings = Settings.builder().put("cluster.name", "my-elasticsearch").build();
		// 2 创建一个客户端client对象 （单机版的暂时没问题，集群的有问题）
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		//3 创建一个查询对象
		QueryBuilder builder = QueryBuilders.idsQuery().addIds("1","2","3");
		
		search(client,builder);//执行查询
	}
	
	/**
	 * 通过Term来查询
	 * @throws UnknownHostException
	 */
	public void queryByTerm() throws UnknownHostException {
		// 1 创建一个Settings对象，相当于一个配置信息，主要配置集群中的名称
		Settings settings = Settings.builder().put("cluster.name", "my-elasticsearch").build();
		// 2 创建一个客户端client对象 （单机版的暂时没问题，集群的有问题）
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		//3 创建一个查询对象
		QueryBuilder builder = QueryBuilders.spanTermQuery("content", "伊拉克");
		
//		search(client,builder);//执行查询
		search(client,builder,"content");//执行高亮查询
	}
	
	/**
	 * 通过StringQuery来查询
	 * 先对需要查询的字符串进行分词，在查询
	 * @throws UnknownHostException
	 */
	public void queryByQueryString() throws UnknownHostException {
		// 1 创建一个Settings对象，相当于一个配置信息，主要配置集群中的名称
		Settings settings = Settings.builder().put("cluster.name", "my-elasticsearch").build();
		// 2 创建一个客户端client对象 （单机版的暂时没问题，集群的有问题）
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		//3 创建一个查询对象
		QueryBuilder builder = QueryBuilders.queryStringQuery("无人驾驶校车").defaultField("content");
		
		search(client,builder);//执行查询
	}
}