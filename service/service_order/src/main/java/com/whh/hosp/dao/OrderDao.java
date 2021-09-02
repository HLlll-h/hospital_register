package com.whh.hosp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whh.hosp.model.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao extends BaseMapper<OrderInfo> {
}
