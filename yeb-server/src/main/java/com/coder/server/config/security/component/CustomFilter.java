package com.coder.server.config.security.component;

import com.coder.server.pojo.Menu;
import com.coder.server.pojo.Role;
import com.coder.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
  * Description: TODO 权限控制（根据url分析请求所需角色）
  * CreateTime: 2021/12/15 7:11 下午
  * Author: liuyuchao
  */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> menus = menuService.getMenusByRoles();
        if (Objects.nonNull(menus)){
            for (Menu menu : menus) {
                //判断请求url与菜单的url是否匹配
                if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                    //把菜单中的角色名存到字符串数组中返回
                    String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                    return SecurityConfig.createList(str);
                }
            }
        }
        //未匹配url默认登录即可访问（赋予ROLE_LOGIN角色）
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
