package com.whh.hosp.service;

import com.whh.hosp.model.hosp.Hospital;
import com.whh.hosp.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface HospitalService {

    //上传医院接口
    void save(Map<String, Object> paramMap);

    //根据医院编号查询
    Hospital getByHoscode(String hoscode);

    //医院列表(条件查询带分页)
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    //更新医院上线状态
    void updateStatus(String id, Integer status);

    //根据id查询医院详情
    Map<String,Object> getHospById(String id);

    //根据医院编号得到医院名称
    String getHospName(String hoscode);
}
