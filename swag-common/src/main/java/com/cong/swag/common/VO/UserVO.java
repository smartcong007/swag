package com.cong.swag.common.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by zhengcong on 2018/5/27.
 */
@Getter
@Setter
@ToString
@ApiModel(value = "user model", description = "用户模型")
public class UserVO {

    @ApiModelProperty(name = "id", value = "用户id")
    private Integer id;

    @ApiModelProperty(name = "userId", value = "用户Id")
    private Integer userId;

    @ApiModelProperty(name = "name", value = "姓名")
    private String name;

    @ApiModelProperty(name = "age", value = "年龄")
    private Integer age;

    @ApiModelProperty(name = "logo", value = "头像")
    private String logo;

}
