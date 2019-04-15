package com.cong.swag.common.VO;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-04-11
 */
@Getter
@Setter
@ToString
public class AuditInfoVO {

    private String id;
    private String auditType;
    private String idRef;
    private String shopCode;
    private String model;
    private Integer sequenceNumber;
    private String applicantId;
    private String applicantName;
    private String auditStatus;
    private String link;
    private Date createDate;
    private Date updateDate;
    private Integer auditTypeId;
    private String auditConfigId;
    private String refStatusDesc;
    private String env;
    private Integer auditPlatform;

}
