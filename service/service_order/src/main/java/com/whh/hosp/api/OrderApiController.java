package com.whh.hosp.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whh.hosp.model.order.OrderInfo;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.OrderService;
import com.whh.hosp.utils.AuthContextHolder;
import com.whh.hosp.vo.order.OrderQueryVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderApiController {


    @Resource
    private OrderService orderService;


    //生成挂号订单
    @PostMapping("/auth/submitOrder/{scheduleId}/{patientId}")
    public Result saveOrders(@PathVariable("scheduleId") String scheduleId,
                             @PathVariable("patientId") Long patientId){
        Long orderId = orderService.saveOrder(scheduleId,patientId);
        return Result.ok(orderId);
    }

    //根据订单id查询订单详情
    @GetMapping("/auth/getOrders/{orderId}")
    public Result getOrders(@PathVariable("orderId") String orderId) {
        OrderInfo orderInfo = orderService.getOrder(orderId);
        return Result.ok(orderInfo);
    }

    //订单列表（条件查询带分页）
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable("page") Long page,
                       @PathVariable("limit") Long limit,
                       OrderQueryVo orderQueryVo, HttpServletRequest request) {
        //设置当前用户id
        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        IPage<OrderInfo> pageModel =
                orderService.selectPage(pageParam,orderQueryVo);
        return Result.ok(pageModel);
    }

    //取消预约订单
    @GetMapping("/auth/cancelOrder/{orderId}")
    public Result cancerOrder(@PathVariable("orderId") Long orderId){
        Boolean isOrder = orderService.cancelOrder(orderId);
        return Result.ok(isOrder);
    }




}
