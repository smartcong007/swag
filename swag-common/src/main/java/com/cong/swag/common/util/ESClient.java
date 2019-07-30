package com.cong.swag.common.util;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-25
 */
public class ESClient {

    /**
     * logger
     */
//    private static final Logger LOGGER = LoggerFactory.getLogger(ESClient.class);
//
//    private static class RestClientHolder {
//
//        private final static RestHighLevelClient CLIENT = new RestHighLevelClient(
//            RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"), new HttpHost("127.0.0.1", 9201, "http"),
//                new HttpHost("127.0.0.1", 9202, "http"))
//        );
//    }
//
//    public static RestHighLevelClient getClient() {
//        return RestClientHolder.CLIENT;
//    }
//
//    /**
//     * 从es读取数据
//     * @param index 索引
//     * @param type type
//     * @param id id
//     * @return 已json的字符串格式返回数据
//     */
//    public static String getData(String index, String type, String id) {
//        try (RestHighLevelClient client = getClient();) {
//            GetRequest request = new GetRequest(index, type, id);
//            GetResponse response = client.get(request);
//            if (response != null && response.isExists()) {
//                LOGGER.info("查询搜索引擎 index: {},type: {}, id: {}", response.getIndex(), response.getType(),
//                    response.getId());
//                return response.getSourceAsString();
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException("搜索引擎查询异常：" + e.getMessage());
//        }
//    }

}
