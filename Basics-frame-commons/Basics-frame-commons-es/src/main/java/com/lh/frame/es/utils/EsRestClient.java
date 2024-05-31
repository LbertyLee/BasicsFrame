package com.lh.frame.es.utils;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lh.frame.es.basic.EsIndexInfo;
import com.lh.frame.es.basic.EsSearchRequest;
import com.lh.frame.es.basic.EsSourceData;
import com.lh.frame.es.config.EsClusterConfig;
import com.lh.frame.es.config.EsConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Component
@Slf4j
public class EsRestClient {

    /**
     * 用于存储RestHighLevelClient实例的映射，键为字符串类型，值为RestHighLevelClient类型。
     * 这允许在应用程序中以键值对的形式管理多个ES客户端实例。
     */
    public static Map<String, RestHighLevelClient> clientMap = new HashMap<>();

    /**
     * 通过@Resource注解自动注入的EsConfigProperties实例。
     * 该实例用于配置与Elasticsearch相关的属性，例如服务器地址、端口等。
     */
    @Resource
    private EsConfigProperties esConfigProperties;

    /**
     * RestHighLevelClient请求的通用选项。
     * 这些选项在所有的Elasticsearch请求中被共享，例如连接超时、读取超时等设置。
     * 它们通过在静态初始化块中创建来确保在任何请求发送之前被初始化。
     */
    private static final RequestOptions COMMON_OPTIONS;

    static {
        // 构建COMMON_OPTIONS实例，基于默认请求选项进行定制
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    /**
     * 初始化函数，用于在对象创建后立即执行初始化操作。
     * 该方法从配置中读取Elasticsearch集群的配置信息，尝试为每个配置的集群创建RestHighLevelClient，
     * 并将成功创建的客户端保存在一个映射中以供后续使用。
     * 该方法不接受参数且无返回值。
     */
    @PostConstruct
    public void initialize() {
        // 从配置属性中获取所有的Elasticsearch集群配置
        List<EsClusterConfig> esConfigs = esConfigProperties.getEsConfigs();
        for (EsClusterConfig esConfig : esConfigs) {
            // 打印日志，记录当前正在初始化的集群配置的名称和节点信息
            log.info("initialize.config.name:{},node:{}", esConfig.getName(), esConfig.getNodes());
            // 使用当前集群配置初始化RestHighLevelClient
            RestHighLevelClient restHighLevelClient = this.initRestClient(esConfig);
            if (!ObjectUtil.isEmpty(restHighLevelClient)) {
                // 如果客户端创建成功，则将其保存在映射中，以集群名称为键
                clientMap.put(esConfig.getName(), restHighLevelClient);
            }  else {
                // 如果客户端创建失败，记录错误日志
                log.error("config.name:{},node:{}.initError", esConfig.getName(), esConfig.getNodes());
            }
        }
    }

    /**
     * 初始化并创建一个RestHighLevelClient实例。
     * 该方法通过EsClusterConfig获取Elasticsearch集群的节点信息，然后创建RestHighLevelClient来连接这些节点。
     *
     * @param esClusterConfig 包含Elasticsearch集群配置信息的对象，如节点的IP和端口。
     * @return 返回初始化好的RestHighLevelClient实例，用于Elasticsearch的操作。
     */
    private RestHighLevelClient initRestClient(EsClusterConfig esClusterConfig) {
        // 将节点信息字符串分割成IP和端口的数组
        String[] ipPortArr = esClusterConfig.getNodes().split(",");
        List<HttpHost> httpHostList = new ArrayList<>(ipPortArr.length);
        // 遍历节点信息，构建HttpHost列表
        for (String ipPort : ipPortArr) {
            String[] ipPortInfo = ipPort.split(":");
            if (ipPortInfo.length == 2) {
                HttpHost httpHost = new HttpHost(ipPortInfo[0], NumberUtils.toInt(ipPortInfo[1]));
                httpHostList.add(httpHost);
            }
        }
        // 将HttpHost列表转换为数组
        HttpHost[] httpHosts = new HttpHost[httpHostList.size()];
        httpHostList.toArray(httpHosts);
        // 使用HttpHost数组构建RestClientBuilder，进而创建RestHighLevelClient实例
        RestClientBuilder builder = RestClient.builder(httpHosts);
        return new RestHighLevelClient(builder);
    }


    /**
     * 获取指定集群名称的RestHighLevelClient实例。
     *
     * @param clusterName 集群的名称，用于在clientMap中查找对应的RestHighLevelClient实例。
     * @return 返回与指定集群名称关联的RestHighLevelClient实例。如果找不到对应的实例，将返回null。
     */
    private static RestHighLevelClient getClient(String clusterName) {
        // 从clientMap中根据集群名称获取RestHighLevelClient实例
        return clientMap.get(clusterName);
    }

    /**
     * 将文档插入到指定的 Elasticsearch 索引中。
     *
     * @param esIndexInfo 包含索引名称和集群名称的 EsIndexInfo 对象。
     * @param esSourceData 包含文档数据和文档 ID 的 EsSourceData 对象。
     * @return 如果文档插入成功则返回 true，如果插入过程中发生异常则返回 false。
     */
    public static boolean insertDoc(EsIndexInfo esIndexInfo, EsSourceData esSourceData) {
        try {
            // 创建一个 IndexRequest 对象，设置索引名称、文档数据和文档 ID
            IndexRequest indexRequest = new IndexRequest(esIndexInfo.getIndexName());
            indexRequest.source(esSourceData.getData());
            indexRequest.id(esSourceData.getDocId());
            // 使用 Elasticsearch 客户端进行文档插入操作
            getClient(esIndexInfo.getClusterName()).index(indexRequest, COMMON_OPTIONS);
            return true;
        } catch (Exception e) {
            // 记录插入操作过程中发生的异常
            log.error("insertDoc.exception:{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 更新Elasticsearch中的文档。
     *
     * @param esIndexInfo 包含索引名称和集群名称的EsIndexInfo对象，用于指定要更新的文档所在的索引和集群。
     * @param esSourceData 包含文档ID和更新内容的EsSourceData对象，文档ID用于定位要更新的文档，更新内容则为文档的新版本数据。
     * @return 返回一个布尔值，若文档成功更新，则返回true；若更新过程中发生异常，则返回false。
     */
    public static boolean updateDoc(EsIndexInfo esIndexInfo, EsSourceData esSourceData) {
        try {
            // 构建更新请求，指定要更新的文档的索引名、ID和更新内容
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(esIndexInfo.getIndexName());
            updateRequest.id(esSourceData.getDocId());
            updateRequest.doc(esSourceData.getData());
            // 执行更新请求
            getClient(esIndexInfo.getClusterName()).update(updateRequest, COMMON_OPTIONS);
            return true;
        } catch (Exception e) {
            // 捕获并记录更新过程中的异常
            log.error("updateDoc.exception:{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 批量更新文档。
     *
     * @param esIndexInfo 包含ES索引信息的对象，如索引名称和集群名称。
     * @param esSourceDataList 要更新的文档数据列表，每个文档数据包含文档ID和文档内容。
     * @return 如果批量更新操作成功执行，则返回true；如果有任何更新失败，则返回false。
     */
    public static boolean batchUpdateDoc(EsIndexInfo esIndexInfo, List<EsSourceData> esSourceDataList) {
        try {
            boolean flag = false; // 用于标记是否执行了更新操作
            BulkRequest bulkRequest = new BulkRequest(); // 批量请求对象
            // 遍历文档数据列表，为每个非空文档ID的文档创建更新请求，并添加到批量请求中
            for (EsSourceData esSourceData : esSourceDataList) {
                String docId = esSourceData.getDocId();
                if (StringUtils.isNotBlank(docId)) {
                    UpdateRequest updateRequest = new UpdateRequest();
                    updateRequest.index(esIndexInfo.getIndexName()); // 设置索引名称
                    updateRequest.id(esSourceData.getDocId()); // 设置文档ID
                    updateRequest.doc(esSourceData.getData()); // 设置文档内容
                    bulkRequest.add(updateRequest);
                    flag = true; // 标记为已执行更新操作
                }
            }
            // 如果有更新请求，则执行批量更新
            if (flag) {
                BulkResponse bulk = getClient(esIndexInfo.getClusterName()).bulk(bulkRequest, COMMON_OPTIONS);
                // 检查是否有更新失败
                return !bulk.hasFailures();
            }
            // 批量更新成功
            return true;
        } catch (Exception e) {
            log.error("batchUpdateDoc.exception:{}", e.getMessage(), e); // 记录异常信息
        }
        // 发生异常，返回false
        return false;
    }

    /**
     * 删除指定索引下的所有文档。
     *
     * @param esIndexInfo 包含索引名称和集群名称的EsIndexInfo对象。
     * @return 如果文档删除成功，则返回true；如果删除过程中发生异常，则返回false。
     */
    public static boolean delete(EsIndexInfo esIndexInfo) {
        try {
            // 创建一个DeleteByQueryRequest对象，指定要删除的索引名称
            DeleteByQueryRequest deleteByQueryRequest =
                    new DeleteByQueryRequest(esIndexInfo.getIndexName());
            // 设置查询条件为匹配所有文档的查询
            deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
            // 执行删除操作，并获取删除结果
            BulkByScrollResponse response = getClient(esIndexInfo.getClusterName()).deleteByQuery(
                    deleteByQueryRequest, COMMON_OPTIONS
            );
            // 记录删除的文档数量
            long deleted = response.getDeleted();
            log.info("deleted.size:{}", deleted);
            return true;
        } catch (Exception e) {
            // 记录删除操作异常信息
            log.error("delete.exception:{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 删除指定文档
     * @param esIndexInfo Elasticsearch索引信息，包含索引名称和集群名称
     * @param docId 要删除的文档ID
     * @return 删除操作是否成功。成功返回true，失败返回false
     */
    public static boolean deleteDoc(EsIndexInfo esIndexInfo, String docId) {
        try {
            // 构建删除请求，指定索引名称和文档ID
            DeleteRequest deleteRequest = new DeleteRequest(esIndexInfo.getIndexName());
            deleteRequest.id(docId);
            // 执行删除操作
            DeleteResponse response = getClient(esIndexInfo.getClusterName()).delete(deleteRequest, COMMON_OPTIONS);
            // 记录删除操作的响应信息
            log.info("deleteDoc.response:{}", JSON.toJSONString(response));
            return true;
        } catch (Exception e) {
            // 记录删除操作过程中发生的异常
            log.error("deleteDoc.exception:{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据文档ID检查ES索引中是否存在该文档。
     *
     * @param esIndexInfo 包含ES索引名称和集群名称的EsIndexInfo对象。
     * @param docId 要检查的文档ID。
     * @return 如果文档存在，则返回true；否则返回false。
     */
    public static boolean isExistDocById(EsIndexInfo esIndexInfo, String docId) {
        try {
            // 创建GetRequest并设置要查询的文档ID
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            // 发送请求检查文档是否存在
            return getClient(esIndexInfo.getClusterName()).exists(getRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            // 记录异常信息
            log.error("isExistDocById.exception:{}", e.getMessage(), e);
        }
        // 如果有异常或其他原因，返回false表示文档不存在
        return false;
    }

    /**
     * 根据文档ID从指定的Elasticsearch索引中获取文档。
     *
     * @param esIndexInfo 包含Elasticsearch索引名称和集群名称的EsIndexInfo对象。
     * @param docId 要获取的文档的ID。
     * @return 如果找到文档，返回一个包含文档源数据的Map；如果未找到或发生异常，返回null。
     */
    public static Map<String, Object> getDocById(EsIndexInfo esIndexInfo, String docId) {
        try {
            // 创建GetRequest并设置索引名和文档ID
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            // 执行获取操作并获取响应
            GetResponse response = getClient(esIndexInfo.getClusterName()).get(getRequest, COMMON_OPTIONS);
            // 从响应中提取文档源数据并返回
            Map<String, Object> source = response.getSource();
            return source;
        } catch (Exception e) {
            // 记录异常信息
            log.error("isExistDocById.exception:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据文档ID从指定的Elasticsearch索引中获取文档。
     *
     * @param esIndexInfo 包含Elasticsearch索引名称和集群名称的索引信息对象。
     * @param docId 要获取的文档的ID。
     * @param fields 需要获取的字段数组，如果为null或空，则获取所有字段。
     * @return 返回一个包含文档字段和值的Map对象；如果获取失败，返回null。
     */
    public static Map<String, Object> getDocById(EsIndexInfo esIndexInfo, String docId, String[] fields) {
        try {
            // 创建GetRequest并设置索引名和文档ID
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            // 设置需要返回的字段
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, fields, null);
            getRequest.fetchSourceContext(fetchSourceContext);
            // 执行获取操作
            GetResponse response = getClient(esIndexInfo.getClusterName()).get(getRequest, COMMON_OPTIONS);
            // 解析并返回文档源信息
            return response.getSource();
        } catch (Exception e) {
            // 记录异常信息
            log.error("isExistDocById.exception:{}", e.getMessage(), e);
        }
        return null;
    }


    /**
     * 使用TermQuery进行搜索。
     *
     * @param esIndexInfo Elasticsearch索引信息，包含索引名称和集群名称。
     * @param esSearchRequest Elasticsearch搜索请求参数，包含查询条件、返回字段、分页信息、排序等。
     * @return SearchResponse 搜索结果响应对象，包含搜索结果、耗时等信息。
     */
    public static SearchResponse searchWithTermQuery(EsIndexInfo esIndexInfo, EsSearchRequest esSearchRequest) {
        try {
            // 构建查询条件
            BoolQueryBuilder bq = esSearchRequest.getBq();
            // 指定返回的字段
            String[] fields = esSearchRequest.getFields();
            // 设置搜索结果起始位置
            int from = esSearchRequest.getFrom();
            // 设置每页返回的文档数
            int size = esSearchRequest.getSize();
            // 设置Scroll搜索的保持时间
            Long minutes = esSearchRequest.getMinutes();
            // 是否需要Scroll方式搜索
            Boolean needScroll = esSearchRequest.getNeedScroll();
            // 指定排序字段
            String sortName = esSearchRequest.getSortName();
            // 指定排序顺序
            SortOrder sortOrder = esSearchRequest.getSortOrder();
            // 构建搜索源配置
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(bq);
            searchSourceBuilder.fetchSource(fields, null).from(from).size(size);
            // 如果存在高亮配置，则添加高亮设置
            if (Objects.nonNull(esSearchRequest.getHighlightBuilder())) {
                searchSourceBuilder.highlighter(esSearchRequest.getHighlightBuilder());
            }
            // 如果指定了排序字段，则添加排序设置
            if (StringUtils.isNotBlank(sortName)) {
                searchSourceBuilder.sort(sortName);
            }
            // 默认按照分数降序排序
            searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
            // 构建搜索请求
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.searchType(SearchType.DEFAULT);
            searchRequest.indices(esIndexInfo.getIndexName());
            searchRequest.source(searchSourceBuilder);
            // 如果需要Scroll方式搜索，设置Scroll选项
            if (needScroll) {
                Scroll scroll = new Scroll(TimeValue.timeValueMinutes(minutes));
                searchRequest.scroll(scroll);
            }
            // 执行搜索
            return getClient(esIndexInfo.getClusterName()).search(searchRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            // 记录异常信息
            log.error("searchWithTermQuery.exception:{}", e.getMessage(), e);
        }
        return null;
    }


    /**
     * 批量插入文档到指定的ES索引。
     *
     * @param esIndexInfo 包含ES索引信息的对象，如索引名称和集群名称。
     * @param esSourceDataList 要插入的文档数据列表，每个文档数据包含文档ID和文档内容。
     * @return 如果插入操作全部成功，则返回true；如果有任何插入操作失败，则返回false。
     */
    public static boolean batchInsertDoc(EsIndexInfo esIndexInfo, List<EsSourceData> esSourceDataList) {
        // 如果日志级别允许，输出信息日志
        if (log.isInfoEnabled()) {
            log.info("批量新增ES:{}", esSourceDataList.size());
            log.info("indexName:{}", esIndexInfo.getIndexName());
        }
        try {
            boolean flag = false; // 用于标记是否有文档被加入到批量插入请求中
            BulkRequest bulkRequest = new BulkRequest();
            // 遍历文档数据列表，为每个非空文档ID的文档创建索引请求，并加入到批量请求中
            for (EsSourceData source : esSourceDataList) {
                String docId = source.getDocId();
                if (StringUtils.isNotBlank(docId)) {
                    IndexRequest indexRequest = new IndexRequest(esIndexInfo.getIndexName());
                    indexRequest.id(docId);
                    indexRequest.source(source.getData());
                    bulkRequest.add(indexRequest);
                    flag = true;
                }
            }
            // 如果有文档被加入到批量请求中，则执行批量插入操作
            if (flag) {
                BulkResponse response = getClient(esIndexInfo.getClusterName()).bulk(bulkRequest, COMMON_OPTIONS);
                // 检查批量插入操作是否有失败的请求
                if (response.hasFailures()) {
                    return false;
                }
            }
        } catch (Exception e) {
            // 记录异常信息
            log.error("batchInsertDoc.error", e);
        }
        return true;
    }

    /**
     * 使用给定的查询和脚本，通过查询更新ES索引中的文档。
     *
     * @param esIndexInfo 包含索引名称和集群名称的ES索引信息对象。
     * @param queryBuilder 用于定义要更新的文档查询的构建器。
     * @param script 用于更新文档的脚本。
     * @param batchSize 批量处理的文档数量。
     * @return 总是返回true，表示操作已尝试执行。
     */
    public static boolean updateByQuery(EsIndexInfo esIndexInfo, QueryBuilder queryBuilder, Script script, int batchSize) {
        // 如果日志级别允许，记录开始更新查询的信息
        if (log.isInfoEnabled()) {
            log.info("updateByQuery.indexName:{}", esIndexInfo.getIndexName());
        }
        try {
            // 创建更新查询请求，并设置查询、脚本和批量大小
            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(esIndexInfo.getIndexName());
            updateByQueryRequest.setQuery(queryBuilder);
            updateByQueryRequest.setScript(script);
            updateByQueryRequest.setBatchSize(batchSize);
            updateByQueryRequest.setAbortOnVersionConflict(false); // 设置在版本冲突时不中止更新
            // 执行更新查询请求
            BulkByScrollResponse response = getClient(esIndexInfo.getClusterName()).updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
            // 检查是否有更新失败的操作
            List<BulkItemResponse.Failure> failures = response.getBulkFailures();
        } catch (Exception e) {
            // 如果发生异常，记录错误信息
            log.error("updateByQuery.error", e);
        }
        return true;
    }

    /**
     * 分词方法
     */
    public static List<String> getAnalyze(EsIndexInfo esIndexInfo, String text) throws Exception {
        List<String> list = new ArrayList<String>();
        Request request = new Request("GET", "_analyze");
        JSONObject entity = new JSONObject();
        entity.put("analyzer", "ik_smart");
        entity.put("text", text);
        request.setJsonEntity(entity.toJSONString());
        Response response = getClient(esIndexInfo.getClusterName()).getLowLevelClient().performRequest(request);
        JSONObject tokens = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        JSONArray arrays = tokens.getJSONArray("tokens");
        for (int i = 0; i < arrays.size(); i++) {
            JSONObject obj = JSON.parseObject(arrays.getString(i));
            list.add(obj.getString("token"));
        }
        return list;
    }


}
