package com.java.hadoop.elasticsearch.estest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author hhbbz on 2019-03-05.
 * 在es6.x中，所有的tableCode都当作type字段来用，_type字段都以Constants的DEFAULT_ES_TYPE作默认值
 * @Explain: 通过RestClient操作es
 */
@Slf4j
@Getter
@Setter
public class ElasticSearchRestDataEngine  {

    private List<String> clusterUrl = Lists.newArrayList("10.0.8.11:9200");

    private RestHighLevelClient client;

    public ElasticSearchRestDataEngine() {

    }

    public ElasticSearchRestDataEngine(RestHighLevelClient client) {
        this.client = client;
    }

    protected ElasticSearchRestDataEngine(List<String> clusterUrl) {
        this.clusterUrl = clusterUrl;
        initialize(false);
    }

    /**
     * ElasticSearchConnection有相同代码，不确定以后是否要分开设置client属性，所以暂时会代码重复
     */
    protected void initialize(boolean force) {
        try {
            if (client == null || force) {
                HttpHost[] httpHostList = new HttpHost[clusterUrl.size()];

                if (!CollectionUtils.isEmpty(clusterUrl)) {
                    int i = 0;
                    for (String node : clusterUrl) {
                        String[] addr = node.split(":");
                        httpHostList[i] = (new HttpHost(addr[0], Integer.parseInt(addr[1]), "http"));
                        i++;
                    }
                }
                this.client = new RestHighLevelClient(RestClient
                        .builder(httpHostList)
                        .setRequestConfigCallback(config -> config
                                .setConnectTimeout(30000)
                                .setConnectionRequestTimeout(30000)
                                .setSocketTimeout(30000)
                        )
                        .setHttpClientConfigCallback(config -> config
                                .setMaxConnTotal(1000)
                                .setMaxConnPerRoute(1000)
                        )
                        .setMaxRetryTimeoutMillis(30000)
                        .setNodeSelector(NodeSelector.ANY));

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        ElasticSearchRestDataEngine elasticSearchRestDataEngine = new ElasticSearchRestDataEngine();
        elasticSearchRestDataEngine.initialize(false);
        RestHighLevelClient client = elasticSearchRestDataEngine.getClient();
        IndicesClient indices = client.indices();
        System.out.println(JSON.toJSONString(indices));
    }

}
