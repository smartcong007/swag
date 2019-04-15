package com.cong.swag.dao;

import com.cong.swag.common.VO.AuditInfoVO;
import com.cong.swag.common.VO.AuditSnapshotVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-04-10
 */
@Repository("auditSnapshotDao")
public class AuditSnapshotDaoImpl implements AuditSnapshotDao {

    @Autowired
    SqlSessionTemplate sqlSession;

    @Override
    public int insertSnapshot(AuditSnapshotVO auditSnapshotVO) {
        return this.sqlSession.insert("auditSnapshotNameSpace.insertSnapshot", auditSnapshotVO);
    }

    @Override
    public List<AuditSnapshotVO> getByAuditType(String auditType) {
        return this.sqlSession.selectList("auditSnapshotNameSpace.getByAuditType", auditType);
    }

    @Override
    public List<AuditSnapshotVO> getByOperation(String operation) {
        return this.sqlSession.selectList("auditSnapshotNameSpace.getByOperation", operation);
    }

    @Override
    public List<AuditSnapshotVO> listAuditSnapshot(String idRef, String auditType) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("idRef", idRef);
        params.put("auditType", auditType);
        return this.sqlSession.selectList("auditSnapshotNameSpace.listAuditSnapshot", params);
    }

    @Override
    public List<AuditSnapshotVO> listAuditUserOperate(String operatorId) {
        return this.sqlSession.selectList("auditSnapshotNameSpace.listAuditUserOperate",operatorId);
    }

    @Override
    public List<AuditInfoVO> getSelfFishedAuditInfoList(String operatorId, List<String> auditTypes) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("operatorId", operatorId);
        params.put("auditTypes", auditTypes);
        return this.sqlSession.selectList("auditSnapshotNameSpace.getSelfFishedAuditInfoList", params);
    }
}
