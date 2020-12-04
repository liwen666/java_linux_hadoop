package com.java.hadoop.elasticsearch.jrx_es_TEST;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import jrx.anyest.engine.base.enums.common.Operator;
import jrx.anyest.engine.base.model.QueryItem;
import jrx.anyest.engine.base.model.RangeItem;
import jrx.anyest.engine.base.model.SortItem;
import jrx.anyest.engine.bridge.utils.ESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Before;
import org.junit.Test;
import org.python.google.common.collect.Maps;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

import static jrx.anyest.engine.base.utils.Constants.*;
import static jrx.anyest.engine.base.utils.Constants.ES_ID;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2020/12/4  14:39
 */
@Slf4j
public class ES_TEST {
    RestHighLevelClient client ;

    @Before
    public void before() {

        String[] clusterUrl = new String[]{"10.0.8.11:9200"};
        HttpHost[] httpHostList = new HttpHost[clusterUrl.length];
        if (!ArrayUtils.isEmpty(clusterUrl)) {
            int i = 0;
            for (String node : clusterUrl) {
                String[] addr = node.split(":");
                httpHostList[i] = (new HttpHost(addr[0], Integer.parseInt(addr[1]), "http"));
                i++;
            }
        }

        String esUsername = "nvl";
        String esPassword = "nvl";
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esUsername, esPassword));  //es账号密码
        client = new RestHighLevelClient(RestClient
                .builder(httpHostList)
                .setRequestConfigCallback(config -> config
                        .setConnectTimeout(180000)
                        .setConnectionRequestTimeout(180000)
                        .setSocketTimeout(180000)
                )
                .setHttpClientConfigCallback(config -> {
                            config.setMaxConnTotal(1000)
                                    .setMaxConnPerRoute(1000);
                            if (Strings.isNotBlank(esUsername) && !"nvl".equals(esUsername)
                                    || Strings.isNotBlank(esPassword) && !"nvl".equals(esPassword)) {
                                config.setDefaultCredentialsProvider(credentialsProvider);
                            }
                            return config;
                        }
                )
                .setMaxRetryTimeoutMillis(180000)
                .setNodeSelector(NodeSelector.ANY));
        log.info("创建ES连接:{}", Arrays.toString(clusterUrl));

    }

    @Test
    public void query() {


        String index = "abcdefg";
        String type = null;
        int from = 0;
        int size = 20;
        List<RangeItem> rangeItems = Lists.newArrayList();
        List<QueryItem> queryItems = Lists.newArrayList();
        List<SortItem> sortItems = Lists.newArrayList();
//        queryItems.add(new QueryItem("version", Operator.EQ, 1));
//        queryItems.add(new QueryItem("publishModel", Operator.EQ, "ONLINE"));
//        sortItems.add(new SortItem("startTime", SortItem.SortOrder.DESC));
        List<String> fields = new ArrayList<>();
        SearchRequest sr = ESUtil.getSearchRequest(index, type, from, size, rangeItems, queryItems, sortItems, fields);
        if (log.isDebugEnabled()) {
            log.debug(JSON.toJSONString(sr.source().toString()));
        }
        SearchResponse response = null;
        try {
            response = client.search(sr, RequestOptions.DEFAULT);
            // 取值
            List<Map<String, Object>> mapList = Lists.newArrayList();
            SearchHits hits = response.getHits();
            Iterator<SearchHit> iterator = hits.iterator();
            while (iterator.hasNext()) {
                SearchHit hit = iterator.next();
                Map<String, Object> fieldMap = hit.getSourceAsMap();
                fieldMap.put("index", hit.getIndex());
                //这个是es默认_type，跟type字段不一样
                fieldMap.put("_type", hit.getType());
                fieldMap.put("_id", hit.getId());
//                mapList.add(CollectionUtils.isNotEmpty(fields) ? matchKeyMap(fields, fieldMap) : fieldMap);
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("查询es数据结果"+JSON.toJSONString(fieldMap));
                System.out.println("-----------------------------------------------------------------------");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save() {
        List<Map<String, Object>> data = Lists.newArrayList();
        ConcurrentMap<String, Object> newConcurrentMap = Maps.newConcurrentMap();
        newConcurrentMap.put("test1",100000);
        data.add(newConcurrentMap);
        String sourceCode="abcdefg";
        String tableCode="table_test";
        log.info("es save start, sourceCode = {}, tableCode = {}, total = {}", "abcdefg", "table_test", data.size());
        IndexRequest indexRequest = new IndexRequest("abcdefg", DEFAULT_ES_TYPE);

        data.forEach(item -> {
            //设置type字段
            if (StringUtils.isNotEmpty(tableCode)) {
                item.put(ES_TYPE, tableCode);
            }
            // 检索item 存在的java.sql.timestamp类型
            indexRequest.source(JSON.toJSONString(item), XContentType.JSON);
            indexRequest.timeout(TimeValue.timeValueSeconds(30));
            try {
                this.client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });

        log.info("es save end, sourceCode = {}, tableCode = {}, total = {}", sourceCode, tableCode, data.size());
    }


    @Test
    public void update() {
        ConcurrentMap<String, Object> data = Maps.newConcurrentMap();
        data.put("test1","100232039");
        data.put("_id","Mie3LHYBkijBhaCL1cG8");

        String id = data.get(ES_ID).toString();
        data.remove(ES_ID);
        //不能存在_type
        data.remove(ES_OLD_TYPE);
        //update a document
        UpdateRequest updateRequest = new UpdateRequest("abcdefg", DEFAULT_ES_TYPE, id)
                .doc(data, XContentType.JSON);
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.put(ES_ID, updateResponse.getId());
        System.out.println(JSON.toJSONString(data));
    }

    @Test
    public void delete() {
        DeleteRequest request = new DeleteRequest("abcdefg", DEFAULT_ES_TYPE, "Oye3LHYBkijBhaCLbcA6");
        try {
            DeleteResponse delete = this.client.delete(request, RequestOptions.DEFAULT);
            System.out.println(delete.getResult());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }


    /**
     * 反欺诈还款事件
     *
     */
    @Test
    public void name() {
        String index = "3_ev10187";
        String type = null;
        int from = 0;
        int size = 20;
        List<RangeItem> rangeItems = Lists.newArrayList();
        List<QueryItem> queryItems = Lists.newArrayList();
        List<SortItem> sortItems = Lists.newArrayList();
//        queryItems.add(new QueryItem("version", Operator.EQ, 1));
//        queryItems.add(new QueryItem("publishModel", Operator.EQ, "ONLINE"));
        queryItems.add(new QueryItem("eventId", Operator.EQ, "160698573410660852971"));
//        queryItems.add(new QueryItem("publishModel", Operator.EQ, "ONLINE"));
//        sortItems.add(new SortItem("startTime", SortItem.SortOrder.DESC));
        List<String> fields = new ArrayList<>();
        SearchRequest sr = ESUtil.getSearchRequest(index, type, from, size, rangeItems, queryItems, sortItems, fields);
        if (log.isDebugEnabled()) {
            log.debug(JSON.toJSONString(sr.source().toString()));
        }
        SearchResponse response = null;
        try {
            response = client.search(sr, RequestOptions.DEFAULT);
            // 取值
            List<Map<String, Object>> mapList = Lists.newArrayList();
            SearchHits hits = response.getHits();
            Iterator<SearchHit> iterator = hits.iterator();
            while (iterator.hasNext()) {
                SearchHit hit = iterator.next();
                Map<String, Object> fieldMap = hit.getSourceAsMap();
                fieldMap.put("index", hit.getIndex());
                //这个是es默认_type，跟type字段不一样
                fieldMap.put("_type", hit.getType());
                fieldMap.put("_id", hit.getId());
//                mapList.add(CollectionUtils.isNotEmpty(fields) ? matchKeyMap(fields, fieldMap) : fieldMap);
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("查询es数据结果"+JSON.toJSONString(fieldMap));
                System.out.println("-----------------------------------------------------------------------");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
