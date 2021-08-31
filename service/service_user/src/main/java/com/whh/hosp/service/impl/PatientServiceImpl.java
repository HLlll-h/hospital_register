package com.whh.hosp.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whh.hosp.client.DictFeignClient;
import com.whh.hosp.dao.PatientDao;
import com.whh.hosp.enums.DictEnum;
import com.whh.hosp.model.user.Patient;
import com.whh.hosp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientDao, Patient> implements PatientService {


    @Resource
    private PatientDao patientDao;

    @Autowired
    private DictFeignClient dictFeignClient;

    //根据user_id查找patient
    //获取就诊人列表
    @Override
    public List<Patient> findAllUserId(Long userId) {
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Patient> patientList = patientDao.selectList(wrapper);
        //通过OpenFeign服务调用使用数据字典的表
//        patientList.stream().forEach(item -> {
//            //其他参数封装
//            this.packPatient(item);
//        });
        return patientList;
    }

    //根据id获取就诊人信息
    @Override
    public Patient getPatientId(Long id) {
        Patient patient = patientDao.selectById(id);
//        return packPatient(patient);
        return patient;
    }


    //Patient对象里面其他参数封装
    private Patient packPatient(Patient patient) {
        //根据证件类型编码，获取证件类型具体指
        String certificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getCertificatesType());//联系人证件
        //联系人证件类型
        String contactsCertificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(),patient.getContactsCertificatesType());
        //省
        String provinceString = dictFeignClient.getName(patient.getProvinceCode());
        //市
        String cityString = dictFeignClient.getName(patient.getCityCode());
        //区
        String districtString = dictFeignClient.getName(patient.getDistrictCode());

        patient.getParam().put("certificatesTypeString", certificatesTypeString);
        patient.getParam().put("contactsCertificatesTypeString", contactsCertificatesTypeString);
        patient.getParam().put("provinceString", provinceString);
        patient.getParam().put("cityString", cityString);
        patient.getParam().put("districtString", districtString);
        patient.getParam().put("fullAddress", provinceString + cityString + districtString + patient.getAddress());
        return patient;
    }
}
