package com.cong.swag.common.util;

import com.cong.swag.common.VO.AuditTypeVO;
import java.util.List;
import java.util.Optional;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-04-10
 */
public class AuditTypeHolder {

    private static List<AuditTypeVO> auditTypeVOS;

    public static void initAuditTypeVOS(List<AuditTypeVO> typeVOS) {
        auditTypeVOS = typeVOS;
    }

    public static AuditTypeVO getByCode(String code) {
        if (auditTypeVOS == null) {
            throw new IllegalStateException();
        }
        Optional<AuditTypeVO> optional = auditTypeVOS.stream().filter((type) -> type.getCode().equals(code)).findAny();
        return optional.isPresent()?optional.get():null;
    }

}
