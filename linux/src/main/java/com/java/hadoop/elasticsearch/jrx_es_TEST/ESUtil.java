package com.java.hadoop.elasticsearch.jrx_es_TEST;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import jrx.anyest.engine.base.enums.common.Operator;
import jrx.anyest.engine.base.enums.strategy.rule.RuleRelation;
import jrx.anyest.engine.base.exception.DataException;
import jrx.anyest.engine.base.model.QueryItem;
import jrx.anyest.engine.base.model.RangeItem;
import jrx.anyest.engine.base.model.SortItem;
import jrx.anyest.engine.base.utils.constant.DataSearchConstants;
import jrx.anyest.engine.bridge.constants.DataCacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static jrx.anyest.engine.base.utils.Constants.DEFAULT_ES_TYPE;
import static jrx.anyest.engine.base.utils.Constants.ES_TYPE;


@Slf4j
public class ESUtil {


    public static final Integer DEFAULT_SIZE = 20;
    /**
     * 构造QueryBuilder
     *
     * @param queryItems
     * @return
     */
    public static BoolQueryBuilder genQueryBuilder(List<QueryItem> queryItems, RuleRelation relation) {
        // 构造全文或关系查询
        BoolQueryBuilder builder1 = QueryBuilders.boolQuery();

        if (CollectionUtils.isNotEmpty(queryItems)) {
            for (QueryItem qi : queryItems) {
                String k = qi.getKey();
                Object v = qi.getValue();

                switch (qi.getOperate()) {
                    case EQ:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery(k, v)));
                        } else {
                            builder1.must(QueryBuilders.matchPhraseQuery(k, v));
                        }
                        break;
                    case NEQ:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.matchPhraseQuery(k, v)));
                        } else {
                            builder1.mustNot(QueryBuilders.matchPhraseQuery(k, v));
                        }
                        break;
                    case GT:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(k).gt(v)));
                        } else {
                            builder1.must(QueryBuilders.rangeQuery(k).gt(v));

                        }
                        break;
                    case GE:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(k).gte(v)));
                        } else {
                            builder1.must(QueryBuilders.rangeQuery(k).gte(v));
                        }
                        break;
                    case LT:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(k).lt(v)));

                        } else {
                            builder1.must(QueryBuilders.rangeQuery(k).lt(v));
                        }
                        break;
                    case LE:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(k).lte(v)));
                        } else {
                            builder1.must(QueryBuilders.rangeQuery(k).lte(v));
                        }
                        break;
                    case IN:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery(k, v.toString().split(","))));
                        } else {
                            builder1.must(QueryBuilders.termsQuery(k, v.toString().split(",")));
                        }
                        break;
                    case NOTIN:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.termsQuery(k, v.toString().split(","))));
                        } else {
                            builder1.mustNot(QueryBuilders.termsQuery(k, v.toString().split(",")));
                        }
                        break;
                    case LIKE:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().must(QueryBuilders.wildcardQuery(k, "*" + v + "*")));

                        } else {
                            builder1.must(QueryBuilders.wildcardQuery(k, "*" + v + "*"));
                        }
                        break;
                    case NOTLIKE:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.wildcardQuery(k, "*" + v + "*")));
                        } else {
                            builder1.mustNot(QueryBuilders.wildcardQuery(k, "*" + v + "*"));
                        }
                        break;
                    case NULL:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(k)));
                        } else {
                            builder1.mustNot(QueryBuilders.existsQuery(k));
                        }
                        break;
                    case NONULL:
                        if (relation == RuleRelation.or) {
                            builder1.should(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(k)));
                        } else {
                            builder1.must(QueryBuilders.existsQuery(k));
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return builder1;
    }


    /**
     * 读取返回数据中的hits
     *
     * @param response
     * @param fields
     * @return
     */
    public static List<Map<String, Object>> getHits(SearchResponse response, List<String> fields) {
        List<Map<String, Object>> mapList = Lists.newArrayList();

        if(response == null){
            return mapList;
        }
        // 取值
        SearchHits hits = response.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit hit = iterator.next();
            Map<String, Object> fieldMap = hit.getSourceAsMap();
            fieldMap.put("index", hit.getIndex());
            //这个是es默认_type，跟type字段不一样
            fieldMap.put("_type", hit.getType());
            fieldMap.put("id", hit.getId());
            mapList.add(CollectionUtils.isNotEmpty(fields) ? matchKeyMap(fields, fieldMap) : fieldMap);
        }
        return mapList;
    }

    public static List<Map<String, Object>> getSqlHits(Response response, List<String> columnsList) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        try {
            //解析sql查询回来的数据
            /**
             * originMap格式：{
             *     "columns": [
             *         {"name": "author",       "type": "text"},
             *         {"name": "name",         "type": "text"},
             *         {"name": "page_count",   "type": "short"},
             *         {"name": "release_date", "type": "date"}
             *     ],
             *     "rows": [
             *         ["Peter F. Hamilton",  "Pandora's Star",       768, "2004-03-02T00:00:00.000Z"],
             *         ["Vernor Vinge",       "A Fire Upon the Deep", 613, "1992-06-01T00:00:00.000Z"],
             *         ["Frank Herbert",      "Dune",                 604, "1965-06-01T00:00:00.000Z"],
             *         ["Alastair Reynolds",  "Revelation Space",     585, "2000-03-15T00:00:00.000Z"],
             *         ["James S.A. Corey",   "Leviathan Wakes",      561, "2011-06-02T00:00:00.000Z"]
             *     ],
             * }
             */
            if(response.getEntity() == null){
                return mapList;
            }
            Map<String, Object> originMap = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));

            JSONArray array = (JSONArray) originMap.get(DataSearchConstants.ROWS);
            array.forEach(childrenObject -> {
                Map<String, Object> resolveMap = new HashMap<>();
                resolveMap.put(DataSearchConstants.COLUMNS, originMap.get(DataSearchConstants.COLUMNS));
                resolveMap.put(DataSearchConstants.ROWS, childrenObject);
                mapList.add(CollectionUtils.isNotEmpty(columnsList) ? matchKeyMap(columnsList, resolveMap) : resolveMap);
            });
            // 取值
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return mapList;
    }

    /**
     * 匹配返回字段
     *
     * @param keys 需要返回的字段
     * @param map
     * @return
     */
    public static Map<String, Object> matchKeyMap(List<String> keys, Map<String, Object> map) {
        if (CollectionUtils.isEmpty(keys)) {
            return map;
        }

        Map<String, Object> retMap = new HashMap<String, Object>();
        for (String key : keys) {
            retMap.put(key, map.get(key));
        }
        //type是一样要匹配返回的
        retMap.put(ES_TYPE, map.get(ES_TYPE));
        return retMap;
    }

    /**
     * 抽取columns参数
     *
     * @param columns
     * @return
     */
    public static List<String> getColumns(String columns) {
        if (StringUtils.isNotEmpty(columns)) {
            return Lists.newArrayList(columns.split(","));
        }
        return null;
    }

    /**
     * 构造排序参数列表
     *
     * @param params
     * @return
     */
    public static List<SortItem> getSortItem(Map<String, Object> params){
        List<SortItem> sortItems = Lists.newArrayList();
        if (params.containsKey(DataSearchConstants.SORTS)) {

            String data = params.get(DataSearchConstants.SORTS).toString();
            try {
                sortItems = JSONArray.parseArray(data, SortItem.class);
            } catch (DataException e) {
                log.error("SortItem类型转换错误", e);
                throw e;
            }
            params.remove(DataSearchConstants.SORTS);
        }
        return sortItems;
    }

    /**
     * 构造条件参数列表
     *
     * @param params
     * @return
     * @throws DataException
     */
    public static List<QueryItem> getQueryItem(Map<String, Object> params){
        List<QueryItem> queryItems = Lists.newArrayList();
        if (params.containsKey(DataSearchConstants.PARAMS)) {

            String data = params.get(DataSearchConstants.PARAMS).toString();
            try {
                queryItems = JSONArray.parseArray(data, QueryItem.class);
            } catch (DataException e) {
                log.error("QueryItem类型转换错误", e);
                throw e;
            }
            params.remove(DataSearchConstants.PARAMS);
        }
        return queryItems;
    }

    /**
     * 获取参数中的range
     *
     * @param params
     * @return
     */
    public static List<RangeItem> getRangeItems(Map<String, Object> params) {
        List<RangeItem> rangeItems = Lists.newArrayList();
        if (params.containsKey(DataSearchConstants.RANGES)) {

            String data = params.get(DataSearchConstants.RANGES).toString();
            try {
                rangeItems = JSONArray.parseArray(data, RangeItem.class);
            } catch (DataException e) {
                log.error("RangeItem类型转换错误", e);
                throw e;
            }
            params.remove(DataSearchConstants.RANGES);
        }
        return rangeItems;
    }

    /**
     * 请求参数转为queryItem，统一处理
     *
     * @param params
     * @return
     */
    public static List<QueryItem> toQueryItem(Map<String, Object> params) {
        List<QueryItem> queryItems = Lists.newArrayList();
        if (params != null && !params.isEmpty()) {

            for(Map.Entry<String, Object> entry : params.entrySet()){
                //查询缓存数据，需要大于缓存时间
                if(entry.getKey().endsWith(DataCacheConstants.FIELD_CACHE_TIME)){
                    queryItems.add(new QueryItem(entry.getKey(), Operator.GE, entry.getValue()));
                }else {
                    queryItems.add(new QueryItem(entry.getKey(), Operator.EQ, entry.getValue()));
                }
            }
        }
        return queryItems;
    }

    /**
     * 获取参数中的_type
     *
     * @param params
     * @return
     */
    public static String getESType(Map<String, Object> params) {
        String type = null;
        if (params.containsKey(ES_TYPE)) {
            type = params.get(ES_TYPE).toString();
        }
        return type;
    }

    /**
     * 配置查询条件
     *
     * @param index      索引
     * @param type       类型
     * @param from       偏移量
     * @param size       结果大小
     * @param queryItems 查询列
     * @param sortItems  排序列
     * @return
     */
    public static SearchRequest getSearchRequest(String index, String type, int from, int size,
                                                 List<QueryItem> queryItems, List<SortItem> sortItems) {
        return getSearchRequest(index, type, from, size, null, queryItems, sortItems, null);
    }


    /**
     * 配置查询条件
     * @param index
     * @param type
     * @param from
     * @param size
     * @param rangeItems
     * @param queryItems
     * @param sortItems
     * @param fields
     * @return
     */
    public static SearchRequest getSearchRequest(String index, String type, int from, int size,
                                                 List<RangeItem> rangeItems, List<QueryItem> queryItems,
                                                 List<SortItem> sortItems, List<String> fields) {
        // 构造精确查询(条件与关系)
        BoolQueryBuilder builder2 = genQueryBuilder(queryItems, RuleRelation.and);

        //必查type字段
        if(StringUtils.isNotEmpty(type)){
            builder2.must(QueryBuilders.matchPhraseQuery(ES_TYPE, type));
        }

        // 构造范围查询
        RangeQueryBuilder builder3 = null;
        if (CollectionUtils.isNotEmpty(rangeItems)) {
            for (RangeItem item : rangeItems) {
                builder3 = QueryBuilders.rangeQuery(StringUtils.trim(String.valueOf(item.getKey())))
                        .from(item.getFrom()).to(item.getTo());
            }
        }

        // 构造排序参数
        SortBuilder builder4 = null;
        if (CollectionUtils.isNotEmpty(sortItems)) {
            for (SortItem item : sortItems) {
                builder4 = SortBuilders.fieldSort(item.getKey())
                        .order(SortOrder.ASC.toString().equals(item.getOrder().name()) ? SortOrder.ASC : SortOrder.DESC);
            }
        }

        SearchRequest sr = new SearchRequest();
        sr.searchType(SearchType.DFS_QUERY_THEN_FETCH).indices(index).types(DEFAULT_ES_TYPE);

        SearchSourceBuilder ssb = new SearchSourceBuilder().explain(true);

        //不分页传0
        if (from != 0 ) {
            ssb.from(from * size);
        }
        if(size != 0){
            ssb.size(size);
        }

        //含范围查询和排序
        if (builder3 != null && builder4 != null) {
            sr.source(ssb.query(builder2.filter(builder3)).sort(builder4));
        //不含排序
        } else if (builder3 != null) {
            sr.source(ssb.query(builder2.filter(builder3)));
        //不含范围查询
        } else if (builder4 != null) {
            sr.source(ssb.query(builder2).sort(builder4));
        //只有条件查询(默认必须有)
        } else {
            sr.source(ssb.query(builder2));
        }

        return sr;
    }

    /**
     * 配置查询条件
     * @param index
     * @param type
     * @param rangeItems
     * @param queryItems
     * @param sortItems
     * @return
     */
    public static CountRequest getCountRequest(String index,
                                               String type,
                                               List<RangeItem> rangeItems,
                                               List<QueryItem> queryItems,
                                               List<SortItem> sortItems) {
        // 构造精确查询(条件与关系)
        BoolQueryBuilder builder2 = genQueryBuilder(queryItems, RuleRelation.and);

        //必查type字段
        if(StringUtils.isNotEmpty(type)){
            builder2.must(QueryBuilders.matchPhraseQuery(ES_TYPE, type));
        }

        // 构造范围查询
        RangeQueryBuilder builder3 = null;
        if (CollectionUtils.isNotEmpty(rangeItems)) {
            for (RangeItem item : rangeItems) {
                builder3 = QueryBuilders.rangeQuery(StringUtils.trim(String.valueOf(item.getKey())))
                        .from(item.getFrom()).to(item.getTo());
            }
        }

        // 构造排序参数
        SortBuilder builder4 = null;
        if (CollectionUtils.isNotEmpty(sortItems)) {
            for (SortItem item : sortItems) {
                builder4 = SortBuilders.fieldSort(item.getKey())
                        .order(SortOrder.ASC.toString().equals(item.getOrder().name()) ? SortOrder.ASC : SortOrder.DESC);
            }
        }

        CountRequest sr = new CountRequest(index);

        SearchSourceBuilder ssb = new SearchSourceBuilder();

        //含范围查询和排序
        if (builder3 != null && builder4 != null) {
            sr.source(ssb.query(builder2.filter(builder3)).sort(builder4));
            //不含排序
        } else if (builder3 != null) {
            sr.source(ssb.query(builder2.filter(builder3)));
            //不含范围查询
        } else if (builder4 != null) {
            sr.source(ssb.query(builder2).sort(builder4));
            //只有条件查询(默认必须有)
        } else {
            sr.source(ssb.query(builder2));
        }

        return sr;
    }
}
