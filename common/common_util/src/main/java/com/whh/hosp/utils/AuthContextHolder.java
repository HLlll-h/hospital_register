package com.whh.hosp.utils;

import com.whh.hosp.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取当前用户信息的工具类
 */
public class AuthContextHolder {

    //获取当前用户id
    public static long getUserId(HttpServletRequest request){
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取userId
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }

    //获取当前用户名称
    public static String getUserName(HttpServletRequest request){
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取userName
        String userName = JwtHelper.getUserName(token);
        return userName;
    }

}
