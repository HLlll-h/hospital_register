package com.whh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whh.hosp.model.user.Patient;

import java.util.List;

public interface PatientService extends IService<Patient> {

    /**
     * 根据user_id查找patient
     */
    List<Patient> findAllUserId(Long userId);

    /**
     * 根据id获取就诊人信息(patient)
     */
    Patient getPatientId(Long id);
}
