package com.coder.server.config.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
  * Description: TODO 权限控制（判断用户角色）
  * CreateTime: 2021/12/15 7:58 下午
  * Author: liuyuchao
  */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            //当前url所需的角色
            String needRole = configAttribute.getAttribute();
            //判断角色是否登录即可访问
            if ("ROLE_LOGIN".equalsIgnoreCase(needRole)){
                //判断访问权限是否为匿名（==没登录）
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("用户尚未登录！");
                }else {
                    return;
                }
            }
            //判断用户角色是否为url所需
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("用户权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
