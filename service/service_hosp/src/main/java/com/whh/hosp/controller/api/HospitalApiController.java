package com.whh.hosp.controller.api;

import com.whh.hosp.model.hosp.Hospital;
import com.whh.hosp.model.hosp.Schedule;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.DepartmentService;
import com.whh.hosp.service.HospitalService;
import com.whh.hosp.service.HospitalSetService;
import com.whh.hosp.service.ScheduleService;
import com.whh.hosp.vo.hosp.DepartmentVo;
import com.whh.hosp.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp/hospital")
public class HospitalApiController {

    @Resource
    private HospitalService hospitalService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private HospitalSetService hospitalSetService;

    //查询医院列表(条件查询带分页)
    @GetMapping("/{page}/{limit}")
    public Result findHospList(@PathVariable("page") Integer page,
                               @PathVariable("limit") Integer limit,
                               HospitalQueryVo hospitalQueryVo){
        Page<Hospital> hospitals = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(hospitals);
    }

    //根据医院名称查询(模糊查询)
    @GetMapping("findByHosName/{hosname}")
    public Result findByHosName(@PathVariable("hosname") String hosname) {
        List<Hospital> list = hospitalService.findByHosname(hosname);
        return Result.ok(list);
    }

    //根据医院编号获取所有科室信息
    @GetMapping("/department/{hoscode}")
    public Result index(@PathVariable("hoscode") String hoscode){
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }

    //根据医院编号获取预约挂号的详情信息
    @GetMapping("/findHospDetail/{hoscode}")
    public Result item(@PathVariable("hoscode") String hoscode){
        Map<String,Object> map = hospitalService.item(hoscode);
        return Result.ok(map);
    }

//===========前台预约挂号接口===============================================

    //获取可预约排班数据
    @GetMapping("/auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getBookingSchedule(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Integer limit,
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable("hoscode") String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable("depcode") String depcode) {
        return Result.ok(scheduleService.getBookingScheduleRule(page, limit, hoscode, depcode));
    }

    @ApiOperation(value = "获取排班数据")
    @GetMapping("/auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    public Result findScheduleList(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable("hoscode") String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable("depcode") String depcode,
            @ApiParam(name = "workDate", value = "排班日期", required = true)
            @PathVariable("workDate") String workDate) {
        return Result.ok(scheduleService.getDetailSchedule(hoscode, depcode, workDate));
    }


    //根据排班id获取排班数据
    @GetMapping("/getSchedule/{scheduleId}")
    public Result getSchedule(@PathVariable("scheduleId") String scheduleId) {
        Schedule schedule = scheduleService.getScheduleId(scheduleId);
        return Result.ok(schedule);
    }









}
