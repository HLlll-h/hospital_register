package com.whh.hosp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whh.hosp.model.user.UserInfo;
import com.whh.hosp.vo.user.LoginVo;
import com.whh.hosp.vo.user.UserAuthVo;
import com.whh.hosp.vo.user.UserInfoQueryVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {

    //用户手机号登录
    Map<String, Object> loginUser(LoginVo loginVo);

    //根据OpenId查询
    UserInfo selectWxInfoOpenId(String openid);

    //用户认证
    void userAuth(long userId, UserAuthVo userAuthVo);

    //用户列表（条件查询带分页）
    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    //用户锁定
    void lock(Long userId, Integer status);

    //用户详情
    Map<String, Object> show(Long userId);

    //认证审批(将authStatus从1改为2[通过]  -1[未通过])
    void approval(Long userId, Integer authStatus);
}
