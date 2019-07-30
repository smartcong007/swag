package com.cong.swag.common.dto;

import com.cong.swag.common.task.JobModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-22
 */
@Data
public class TaskDTO implements Serializable {


    private static final long serialVersionUID = 2925989733595191282L;

    @NotNull
    private String group;

    @NotNull
    private String name;

    @NotNull
    private JobModel jobModel;

    @NotNull
    private String cron;

}
