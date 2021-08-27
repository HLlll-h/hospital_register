package com.whh.hosp.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whh.hosp.dao.HospitalSetDao;
import com.whh.hosp.model.hosp.HospitalSet;
import com.whh.hosp.service.HospitalSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetDao, HospitalSet> implements HospitalSetService {

    //调dao 需autowired注入dao对象  MyBatis-Plus封装完成了  可不写

    //MyBatis-Plus不仅封装了Dao接口的方法，还封装了ServiceImpl的方法 帮我们写好了 都可不写

    @Resource
    private HospitalSetDao hospitalSetDao;


    //2.根据传递过来医院编码，查询数据库，查询签名sign_key
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }






}
