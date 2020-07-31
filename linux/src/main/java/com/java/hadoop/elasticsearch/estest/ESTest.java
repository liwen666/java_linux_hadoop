package com.java.hadoop.elasticsearch.estest;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESTest {
    private static RestHighLevelClient esClient = null;
    static {
        //初始化ES操作客户端
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "NgzufV5HJ9qwsGBSIdwE"));  //es账号密码
        esClient =new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("172.16.101.41",9200)
                ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).setMaxRetryTimeoutMillis(2000)
        );
    }
    public static void main(String[] args) throws Exception{
        index();
    }
 
    static void  index() throws Exception{
//        esClient.
        GetRequest getRequest = new GetRequest("_cat","indices","*");
        GetResponse response = esClient.get(getRequest);
        System.out.println(response.getId());
    }
}
