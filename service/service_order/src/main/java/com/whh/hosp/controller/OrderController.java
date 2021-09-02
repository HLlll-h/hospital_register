package com.whh.hosp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whh.hosp.model.order.OrderInfo;
import com.whh.hosp.model.user.UserInfo;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.OrderService;
import com.whh.hosp.vo.order.OrderQueryVo;
import com.whh.hosp.vo.user.UserInfoQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 后台订单管理模块
 */
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderController {

    @Resource
    private OrderService orderService;

    //订单列表（条件查询带分页）
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Long page,
                       @PathVariable("limit") Long limit,
                       OrderQueryVo orderQueryVo) {
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        IPage<OrderInfo> pageModel =
                orderService.selectPage(pageParam,orderQueryVo);
        return Result.ok(pageModel);
    }

    //订单详情
    @GetMapping("/show/{id}")
    public Result show(@PathVariable("id") Long id){
        Map<String,Object> map = orderService.show(id);
        return Result.ok(map);
    }



}
