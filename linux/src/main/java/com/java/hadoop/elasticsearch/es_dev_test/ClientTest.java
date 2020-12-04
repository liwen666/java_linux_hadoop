package com.java.hadoop.elasticsearch.es_dev_test;

public class ClientTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//创建
		ElasticSearchClient esClient = new ElasticSearchClient();
		esClient.createIndex(); //创建索引库
//		esClient.setMappings();//设置mapping信息
//		esClient.setAddDOC1();//添加文档 方式1
//		esClient.setAddDOC2();//添加文档 方式2
		
		//查询
//		ESQueryClient client = new ESQueryClient();
//		client.queryById();
//		client.queryByTerm();
//		client.queryByQueryString();
	}

}