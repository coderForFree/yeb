package com.coder.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coder.server.config.security.component.JwtTokenUtil;
import com.coder.server.mapper.AdminMapper;
import com.coder.server.mapper.RoleMapper;
import com.coder.server.pojo.Admin;
import com.coder.server.pojo.RespBean;
import com.coder.server.pojo.Role;
import com.coder.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuyuchao
 * @since 2021-12-14
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
      * Description: 登录后返回token实现类
      * @param username 用户名
      * @param password 密码
      * @param code
     * @param request 请求
     * @return com.coder.server.pojo.RespBean
      */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {

        //从session中获取图形验证码
        String captcha = request.getSession().getAttribute("captcha").toString();

        //判断验证码是否为空或匹配不上
        if (StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码有误，请重新输入！");
        }

        //根据用户名获取 userDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //用户信息为空或者密码匹配失败
        if (Objects.isNull(userDetails) || !passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }

        //用户未启用
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员！");
        }

        //更新登录用户信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        //放入springSecurity全文中
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //根据userDetails拿到token令牌
        String token = jwtTokenUtil.generatedToken(userDetails);

        //返回token令牌和tokenHead头部信息
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

    /**
     * Description: 根据用户名获取用户信息实现类
     * @param username 用户名
     * @return com.coder.server.pojo.Admin
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(
                new QueryWrapper<Admin>()
                        .eq("username",username)
                        .eq("enabled",true));
    }

    /**
      * Description: 根据用户ID获取角色列表
      * @param adminId
      * @return java.util.List<com.coder.server.pojo.Role>
      */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

}
