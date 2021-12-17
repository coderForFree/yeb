package com.coder.server.mapper;

import com.coder.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coder.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuyuchao
 * @since 2021-12-14
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * Description: 通过用户ID查询用户列表
     * @param id
     * @return java.util.List<com.coder.server.pojo.Menu>
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
      * Description: 通过用户权限查询菜单列表
      * @return java.util.List<com.coder.server.pojo.Menu>
      */

    List<Menu> getMenusByRoles();
}
