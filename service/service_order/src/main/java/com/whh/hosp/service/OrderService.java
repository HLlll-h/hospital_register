package com.whh.hosp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whh.hosp.model.order.OrderInfo;
import com.whh.hosp.vo.order.OrderCountQueryVo;
import com.whh.hosp.vo.order.OrderQueryVo;

import java.util.Map;

public interface OrderService extends IService<OrderInfo> {


    //生成挂号订单
    Long saveOrder(String scheduleId, Long patientId);

    //根据订单id查询订单详情
    OrderInfo getOrder(String orderId);

    //订单列表（条件查询带分页）
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    //订单详情
    Map<String, Object> show(Long id);

    //取消预约订单
    Boolean cancelOrder(Long orderId);

    //就诊通知(就医提醒)
    void patientTips();

    //后台预约统计
    Map<String,Object> getCountMap(OrderCountQueryVo orderCountQueryVo );


}
