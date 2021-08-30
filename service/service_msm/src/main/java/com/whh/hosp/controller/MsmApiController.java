package com.whh.hosp.controller;


import com.whh.hosp.result.Result;
import com.whh.hosp.service.MsmService;
import com.whh.hosp.utils.RandomUtil;
import io.swagger.annotations.OAuth2Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/msm")
public class MsmApiController {


    @Resource
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;


    //发送手机验证码
    @GetMapping("/send/{phone}")
    public Result sendCode(@PathVariable("phone") String phone){
        //从redis获取验证码 如果获取到 返回ok
        //key 手机号 value
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {//code不为空
            return Result.ok();
        }
        //如果从redis获取不到，
        // 生成验证码，
        code = RandomUtil.getSixBitRandom();
        //调用service方法，通过整合短信服务进行发送
        boolean isSend = msmService.send(phone,code);
        //生成验证码放到redis里面，设置有效时间
        if(isSend) {
            redisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.fail().message("发送短信失败");
        }
    }




}
