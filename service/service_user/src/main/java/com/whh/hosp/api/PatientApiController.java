package com.whh.hosp.api;

import com.whh.hosp.model.user.Patient;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.PatientService;
import com.whh.hosp.utils.AuthContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {


    @Resource
    private PatientService patientService;

    //获取就诊人列表
    @GetMapping("/auth/findAll")
    public Result findAll(HttpServletRequest request) {
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        //一个userId可能有多个Patient
        List<Patient> list = patientService.findAllUserId(userId);
        return Result.ok(list);
    }

    //添加就诊人
    @PostMapping("/auth/save")
    public Result savePatient(@RequestBody Patient patient,HttpServletRequest request){
        //获取当前登录用户的id
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        patientService.save(patient);
        return Result.ok();
    }

    //根据id获取就诊人信息
    @GetMapping("/auth/get/{id}")
    public Result getPatient(@PathVariable("id") Long id) {
        Patient patient = patientService.getPatientId(id);
        return Result.ok(patient);
    }

    //修改就诊人信息
    @PostMapping("/auth/update")
    public Result updatePatient(@RequestBody Patient patient) {
        patientService.updateById(patient);
        return Result.ok();
    }

    //删除就诊人信息
    @DeleteMapping("/auth/remove/{id}")
    public Result removePatient(@PathVariable("id") Long id) {
        patientService.removeById(id);
        return Result.ok();
    }

    //根据就诊人id获取就诊人信息(服务调用使用)
    @GetMapping("/inner/get/{id}")
    public Patient getPatientOrder(@PathVariable("id") Long id){
        Patient patient = patientService.getPatientId(id);
        return patient;
    }

    //获取就诊人列表(服务调用使用)
    @GetMapping("/inner/findAll/{userId}")
    public List<Patient> getAllPatient(@PathVariable("orderId") Long userId) {
        //一个userId可能有多个Patient
        List<Patient> list = patientService.findAllUserId(userId);
        return list;
    }






}
