package com.cong.swag.common.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-24
 */
@Data
public class MqBodyDTO implements Serializable {

    private static final long serialVersionUID = -6534320202918442257L;

    private String msgType;

    private String origin;

    private Object data;

}
