package com.whh.hosp.service;

import com.whh.hosp.vo.msm.MsmVo;

public interface MsmService {

    //发送手机验证码
    boolean send(String phone, String code);



    //rabbitMQ使用发送短信(预约订单)
    boolean send(MsmVo msmVo);
}
