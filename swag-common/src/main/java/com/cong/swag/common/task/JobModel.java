package com.cong.swag.common.task;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-19
 */
@Data
public class JobModel implements Serializable {

    private static final long serialVersionUID = 5469712418493396802L;

    @NotNull
    private String jobHandler;

    private Object jobContent;

    private Date jobCreateDate;

    private String jobDesc;
}
