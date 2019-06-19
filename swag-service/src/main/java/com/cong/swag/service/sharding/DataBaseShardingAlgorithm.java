package com.cong.swag.service.sharding;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import java.util.Collection;

/**
 * @Description 基于精确分片算法实现的分库规则
 * @Author zheng cong
 * @Date 2019-04-04
 */
public class DataBaseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        for (String each:collection) {
            //根据分片键的值模2再+1与库名末尾匹配
            if (each.endsWith(preciseShardingValue.getValue()%2+1+"")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
