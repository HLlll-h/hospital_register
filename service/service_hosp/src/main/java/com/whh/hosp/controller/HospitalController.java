package com.whh.hosp.controller;

import com.whh.hosp.client.DictFeignClient;
import com.whh.hosp.model.hosp.Hospital;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.HospitalService;
import com.whh.hosp.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin //解决跨域问题
public class HospitalController {

    @Resource
    private HospitalService hospitalService;

    @Autowired
    private DictFeignClient dictFeignClient;


    //医院列表(条件查询带分页)
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectHospPage(page,limit,hospitalQueryVo);
        List<Hospital> content = pageModel.getContent();
        long totalElements = pageModel.getTotalElements();

        return Result.ok(pageModel);
    }


    //更新医院上线状态
    @GetMapping("/updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable("id") String id,
                                   @PathVariable("status") Integer status){
        hospitalService.updateStatus(id,status);
        return Result.ok();
    }


    //查询医院详情信息
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable("id") String id){
        Map<String,Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }





}
