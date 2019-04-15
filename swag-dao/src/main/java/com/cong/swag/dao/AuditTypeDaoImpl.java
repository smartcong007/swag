package com.cong.swag.dao;

import com.cong.swag.common.VO.AuditTypeVO;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-04-10
 */
@Repository("auditTypeDao")
public class AuditTypeDaoImpl implements AuditTypeDao {

    @Autowired
    SqlSessionTemplate sqlSession;

    @Override
    public List<AuditTypeVO> getAuditTypeList() {
        return this.sqlSession.selectList("auditTypeNameSpace.selectList");
    }
}
