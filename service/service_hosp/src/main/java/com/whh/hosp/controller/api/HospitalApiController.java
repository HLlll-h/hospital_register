package com.whh.hosp.controller.api;

import com.whh.hosp.model.hosp.Hospital;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.DepartmentService;
import com.whh.hosp.service.HospitalService;
import com.whh.hosp.vo.hosp.DepartmentVo;
import com.whh.hosp.vo.hosp.HospitalQueryVo;
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









}
