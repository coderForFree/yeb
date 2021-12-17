package com.coder.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
  * Description: TODO 用户登录实体类
  * CreateTime: 2021/12/14 5:53 下午
  * Author: liuyuchao
  */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AdminLoginParam对象", description="")
public class AdminLoginParam {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}
