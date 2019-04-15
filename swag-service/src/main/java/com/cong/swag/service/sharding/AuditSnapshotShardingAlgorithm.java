package com.cong.swag.service.sharding;

import com.cong.swag.common.VO.AuditTypeVO;
import com.cong.swag.common.util.AuditTypeHolder;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import java.util.Collection;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-04-09
 */
public class AuditSnapshotShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String auditType = preciseShardingValue.getValue();
        AuditTypeVO typeVO = AuditTypeHolder.getByCode(auditType);
        for (String each:collection) {
            if (each.endsWith(typeVO.getShopType()+typeVO.getId()%2)) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
