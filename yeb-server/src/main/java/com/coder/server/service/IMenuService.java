package com.coder.server.service;

import com.coder.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coder.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuyuchao
 * @since 2021-12-14
 */
public interface IMenuService extends IService<Menu> {

    /**
     * Description: 通过用户ID查询用户列表
     * @return java.util.List<com.coder.server.pojo.Menu>
     */

    List<Menu> getMenusByAdminId();

    /**
      * Description: 通过用户权限查询菜单列表
      * @return java.util.List<com.coder.server.pojo.Role>
      */

    List<Menu> getMenusByRoles();
}
