package com.whh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whh.hosp.model.user.UserInfo;
import com.whh.hosp.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {

    //用户手机号登录
    Map<String, Object> loginUser(LoginVo loginVo);

    //根据OpenId查询
    UserInfo selectWxInfoOpenId(String openid);
}
