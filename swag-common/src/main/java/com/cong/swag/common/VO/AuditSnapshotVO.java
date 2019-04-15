package com.cong.swag.common.VO;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-04-10
 */
@Getter
@Setter
@ToString
public class AuditSnapshotVO {

    private String id;

    private String auditType;

    private String idRef;

    private String operatorId;

    private String operatorName;

    private String operationName;

    private String operation;

    private String shopCode;

    private String remark;

    private Date operateDate;

    private Integer auditTypeId;

    private String auditConfigId;

    private String auditInfoId;

    private Integer timeFlag;

    private Integer sequenceNumber;

    private String operationType;

}
