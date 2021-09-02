package com.whh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whh.hosp.model.order.OrderInfo;
import com.whh.hosp.model.order.PaymentInfo;

import java.util.Map;

/**
 * payment_info表
 */
public interface PaymentService extends IService<PaymentInfo> {



    //向支付记录表添加信息
    void savePaymentInfo(OrderInfo order, Integer status);

    //更新订单状态
    void paySuccess(String out_trade_no, Map<String, String> resultMap);
}
