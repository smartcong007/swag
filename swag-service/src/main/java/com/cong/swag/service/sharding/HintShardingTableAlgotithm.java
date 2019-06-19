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
public class HintShardingTableAlgotithm implements HintShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
        return Lists.newArrayList("goods");
    }
}
