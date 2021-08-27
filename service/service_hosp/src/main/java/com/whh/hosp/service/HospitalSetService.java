package com.whh.hosp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.whh.hosp.model.hosp.HospitalSet;


public interface HospitalSetService extends IService<HospitalSet> {


    /**
     *2.根据传递过来医院编码，查询数据库，查询签名
     */
    String getSignKey(String hoscode);
}
