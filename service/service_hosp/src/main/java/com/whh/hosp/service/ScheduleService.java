package com.whh.hosp.service;

import com.whh.hosp.model.hosp.Schedule;
import com.whh.hosp.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ScheduleService {

    //上传排班
    void save(Map<String, Object> paramMap);

    //查看排班
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班
    void remove(String hoscode, String hosScheduleId);
}
