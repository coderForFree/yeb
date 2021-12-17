package com.coder.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * Description: TODO 公共返回对象
  * CreateTime: 2021/12/14 4:52 下午
  * Author: liuyuchao
  */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    //状态码
    private long code;
    //返回信息
    private String message;
    //返回对象
    private Object obj;

    //成功返回结果
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }

    //成功返回结果
    public static RespBean success(String message,Object obj){
        return new RespBean(200,message,obj);
    }

    //失败返回结果
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }

    //失败返回结果
    public static RespBean error(String message,Object obj){
        return new RespBean(500,message,obj);
    }
}
