package com.cong.swag.service.sharding;

import com.google.common.collect.Lists;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;
import java.util.Collection;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-06-10
 */
public class HintShardingDatasourceAlgotithm implements HintShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> collection, ShardingValue shardingValue) {
        return Lists.newArrayList("sharding_db_001");
    }
}
