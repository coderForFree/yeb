package com.coder.server.config.security.component;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* Description: token工具类
* CreateTime: 2021/12/14 2:44 下午
* Author: liuyuchao
*/
@Component
@Slf4j
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
      * Description: 根据用户信息生成token
      * @param userDetails
      * @return java.lang.String
      */
    public String generatedToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generatedToken(claims);
    }

    /**
      * Description: 判断token是否有效
      * @param token
      * @param userDetails
      * @return boolean
      */

    public boolean validateToken(String token,UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return Objects.equals(username,userDetails.getUsername());
    }

    /**
     * Description: 根据token获取登录用户名
     * @param token
     * @return java.lang.String
     */
    public String getUsernameFromToken(String token){
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            log.error("根据token获取登录用户名 -> {} 获取失败",token,e);
        }
        return username;
    }

    /**
     * Description: 刷新token
     * @param token
     * @return java.lang.String
     */

    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generatedToken(claims);
    }

    /**
      * Description: 判断token是否可以刷新
      * @param token
      * @return boolean
      */

    public boolean canRefresh(String token){
        return !isTokenExpried(token);
    }

    /**
      * Description: 判断token是否失效
      * @param token
      * @return boolean
      */

    private boolean isTokenExpried(String token) {
        Date expritedDate = getExpriedDateFromToken(token);
        return expritedDate.before(new Date());
    }

    /**
      * Description: 根据token获取失效时间
      * @param token
      * @return java.util.Date
      */

    private Date getExpriedDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
      * Description: 根据claims生成token
      * @param claims
      * @return java.lang.String
      */

    private String generatedToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generatedExpiration())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
      * Description: 生成token失效时间
      * @return java.util.Date
      */

    private Date generatedExpiration() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * Description: 根据token获取荷载
     * @param token
     * @return io.jsonwebtoken.Claims
     */

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("根据token获取claims -> {} 获取失败",token,e);
        }
        return claims;
    }
}
