package com.java.hadoop.elasticsearch.estest;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
 
import java.io.IOException;
 
public class TestEs {
 
    /**
     * 测试es连接
     * @throws IOException
     */
    @Test
    public void testClient() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("172.16.101.12", 9200, "http")));
        GetRequest getRequest = new GetRequest(
                "_cat",//index
                "projectInfo", //type
                "8");//参数id
 
 
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
        client.close();
 
    }


    @Test
    public void wordpassword() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "123456"));  //es账号密码（默认用户名为elastic）
        RestHighLevelClient esClient =new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1",9200)
                ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).setMaxRetryTimeoutMillis(2000)
        );

    }

    @Test
    public void testSearchQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("172.16.101.12", 9200, "http")
                )
        );
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("sharing_content", "成果"));
        searchRequest.indices("project_info");
        searchRequest.types("projectInfo");
        searchRequest.searchType(SearchType.QUERY_THEN_FETCH);
 
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
 
        for (SearchHit hit : searchHits) {
            System.out.println(hit.getSourceAsString());
        }
 
 
 
    }
}