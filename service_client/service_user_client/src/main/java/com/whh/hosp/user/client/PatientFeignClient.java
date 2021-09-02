package com.whh.hosp.user.client;


import com.whh.hosp.model.user.Patient;
import com.whh.hosp.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient(value = "service-user")//调用service-user模块
@Repository//防止调用端注入时报错
public interface PatientFeignClient {


    //根据就诊人id获取就诊人信息
    @GetMapping("/api/user/patient/inner/get/{id}")
    public Patient getPatientOrder(@PathVariable("id") Long id);


    //获取就诊人列表
    @GetMapping("/api/user/patient/inner/findAll/{userId}")
    public List<Patient> getAllPatient(@PathVariable("orderId") Long userId);




}
