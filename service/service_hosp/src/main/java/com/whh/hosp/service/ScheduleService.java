package com.whh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whh.hosp.model.hosp.Schedule;
import com.whh.hosp.vo.hosp.ScheduleOrderVo;
import com.whh.hosp.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService extends IService<Schedule> {

    //上传排班
    void save(Map<String, Object> paramMap);

    //查看排班
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班
    void remove(String hoscode, String hosScheduleId);

    //根据医院编号和科室编号 查询排班的规则数据
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    //根据医院编号 科室编号 工作日期查询排班详细信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    //获取可预约的排班数据
    Map<String,Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    //根据排班id获取排班数据
    Schedule getScheduleId(String scheduleId);

    //根据排班id获取预约下单数据
    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    //更新排班数据
    void update(Schedule schedule);
}
