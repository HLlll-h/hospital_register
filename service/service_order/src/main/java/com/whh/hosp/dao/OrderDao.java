package com.whh.hosp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whh.hosp.model.order.OrderInfo;
import com.whh.hosp.vo.order.OrderCountQueryVo;
import com.whh.hosp.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao extends BaseMapper<OrderInfo> {


    //查询预约统计数据
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);

}
