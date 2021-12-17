package com.coder.server.service;

import com.coder.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coder.server.pojo.Menu;
import com.coder.server.pojo.RespBean;
import com.coder.server.pojo.Role;
import io.swagger.models.auth.In;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuyuchao
 * @since 2021-12-14
 */
public interface IAdminService extends IService<Admin> {
    /**
      * Description: 登录返回token服务类
      * @param username 用户名
      * @param password 密码
      * @param code
     * @param request 请求
     * @return com.coder.server.pojo.RespBean
      */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
      * Description: 根据用户名获取用户信息服务类
      * @param username 用户名
      * @return com.coder.server.pojo.Admin
      */
    Admin getAdminByUserName(String username);

    /**
      * Description: 根据用户ID获取角色列表
      * @param adminId
      * @return java.util.List<com.coder.server.pojo.Role>
      */
    List<Role> getRoles(Integer adminId);

}
