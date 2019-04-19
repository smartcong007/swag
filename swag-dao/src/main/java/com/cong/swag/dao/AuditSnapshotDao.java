package com.cong.swag.dao;

import com.cong.swag.common.VO.AuditInfoVO;
import com.cong.swag.common.VO.AuditSnapshotVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-04-10
 */
public interface AuditSnapshotDao {

    int insertSnapshot(AuditSnapshotVO auditSnapshotVO);

    List<AuditSnapshotVO> getByAuditType(String auditType);

    List<AuditSnapshotVO> getByOperation(String operation);

    List<AuditSnapshotVO> listAuditSnapshot(String idRef, String auditType);

    List<AuditSnapshotVO> listAuditUserOperate(String operatorId);

    List<AuditInfoVO> getSelfFishedAuditInfoList(@Param("operatorId") String operatorId, @Param("auditTypes") List<String> auditTypes);

}
