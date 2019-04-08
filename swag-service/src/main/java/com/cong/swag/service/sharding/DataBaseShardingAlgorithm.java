package com.cong.swag.service.sharding;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import java.util.Collection;

/**
 * @Description 分库规则
 * @Author zheng cong
 * @Date 2019-04-04
 */
public class DataBaseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        for (String each:collection) {
            if (each.endsWith(preciseShardingValue.getValue()%2+1+"")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
