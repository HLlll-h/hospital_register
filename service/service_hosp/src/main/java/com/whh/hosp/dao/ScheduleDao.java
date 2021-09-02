package com.whh.hosp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whh.hosp.model.hosp.Schedule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleDao extends BaseMapper<Schedule> {
}
