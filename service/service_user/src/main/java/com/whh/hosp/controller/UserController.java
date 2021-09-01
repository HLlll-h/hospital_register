package com.whh.hosp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whh.hosp.model.user.UserInfo;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.UserInfoService;
import com.whh.hosp.vo.user.UserInfoQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 后台用户管理模块
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {


    @Resource
    private UserInfoService userInfoService;

    //用户列表（条件查询带分页）
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Long page,
                       @PathVariable("limit") Long limit,
                       UserInfoQueryVo userInfoQueryVo) {
        Page<UserInfo> pageParam = new Page<>(page,limit);
        IPage<UserInfo> pageModel =
                userInfoService.selectPage(pageParam,userInfoQueryVo);
        return Result.ok(pageModel);
    }


    //用户锁定
    @GetMapping("/lock/{userId}/{status}")
    public Result lock(@PathVariable("userId") Long userId,
                       @PathVariable("status") Integer status) {
        userInfoService.lock(userId,status);
        return Result.ok();
    }


    //用户详情
    @GetMapping("/show/{userId}")
    public Result show(@PathVariable("userId") Long userId){
        Map<String,Object> map = userInfoService.show(userId);
        return Result.ok(map);
    }


    //认证审批(将authStatus从1改为2[通过]  -1[未通过])
    @GetMapping("approval/{userId}/{authStatus}")
    public Result approval(@PathVariable Long userId,@PathVariable Integer authStatus) {
        userInfoService.approval(userId,authStatus);
        return Result.ok();
    }



}
