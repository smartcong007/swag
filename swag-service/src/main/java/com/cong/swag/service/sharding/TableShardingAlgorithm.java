package com.cong.swag.service.sharding;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import java.util.Collection;

/**
 * @Description 基于精确分片算法实现的分表算法
 * @Author zheng cong
 * @Date 2019-04-04
 */
public class TableShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        for (String each:collection) {
            //根据分片键对应的值模3与表名尾部匹配
            if (each.endsWith(preciseShardingValue.getValue()%3+"")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
