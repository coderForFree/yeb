package com.coder.server.controller;

import com.coder.server.pojo.Admin;
import com.coder.server.pojo.AdminLoginParam;
import com.coder.server.pojo.RespBean;
import com.coder.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
  * Description: TODO 用户登录控制类
  * CreateTime: 2021/12/14 5:59 下午
  * Author: liuyuchao
  */
@Api(tags = "用户登录控制类")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    @ApiOperation(value = "获取登录用户详细信息")
    @PostMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (null == principal){
            return null;
        }
        //获取用户名(出于用户信息安全不获取密码)
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        //角色权限
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }

    @ApiOperation(value = "退出登录(前端删除token，后端返回成功信息(code))")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功");
    }
}
