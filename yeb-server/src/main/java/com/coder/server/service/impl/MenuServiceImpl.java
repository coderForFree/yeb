package com.coder.server.service.impl;

import com.coder.server.pojo.Admin;
import com.coder.server.pojo.Menu;
import com.coder.server.mapper.MenuMapper;
import com.coder.server.pojo.Role;
import com.coder.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuyuchao
 * @since 2021-12-14
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * Description: 通过用户ID查询用户列表
     * @return java.util.List<com.coder.server.pojo.Menu>
     */

    @Override
    public List<Menu> getMenusByAdminId() {
        //从全局变量中获取adminId
        Integer adminId = ((Admin) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis中获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
        //如果redis中的menus为空，用数据库查
        if (CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(adminId);
            //将数据添加到redis中
            valueOperations.set("menu_"+adminId,menus);
        }
        return menus;
    }

    @Override
    public List<Menu> getMenusByRoles() {
        return menuMapper.getMenusByRoles();
    }

}
