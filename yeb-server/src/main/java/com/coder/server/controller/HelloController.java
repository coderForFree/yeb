package com.coder.server.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @ApiOperation(value = "测试controller")
    @GetMapping("hello")
    public String hello(){
        return "hello";
    }

    @ApiOperation(value = "测试用户1基本资料栏获取时，角色权限过滤是否生效")
    @GetMapping("/employee/basic/access")
    public String access1(){
        return "/employee/basic/access";
    }

    @ApiOperation(value = "测试用户1高级资料栏获取时，角色权限过滤是否生效")
    @GetMapping("/employee/advanced/access")
    public String access2(){
        return "/employee/advanced/access";
    }


}
