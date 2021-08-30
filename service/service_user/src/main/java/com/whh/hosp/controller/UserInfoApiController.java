package com.whh.hosp.controller;


import com.whh.hosp.result.Result;
import com.whh.hosp.service.UserInfoService;
import com.whh.hosp.vo.user.LoginVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Resource
    private UserInfoService userInfoService;


    //用户手机号登录接口
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String,Object> info =  userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }




}
