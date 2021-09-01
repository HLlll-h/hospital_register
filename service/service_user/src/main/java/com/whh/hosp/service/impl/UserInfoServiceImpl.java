package com.whh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whh.hosp.dao.UserInfoDao;
import com.whh.hosp.enums.AuthStatusEnum;
import com.whh.hosp.exception.YyghException;
import com.whh.hosp.helper.JwtHelper;
import com.whh.hosp.model.user.Patient;
import com.whh.hosp.model.user.UserInfo;
import com.whh.hosp.result.ResultCodeEnum;
import com.whh.hosp.service.PatientService;
import com.whh.hosp.service.UserInfoService;
import com.whh.hosp.vo.user.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whh.hosp.vo.user.UserAuthVo;
import com.whh.hosp.vo.user.UserInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Resource
    private PatientService patientService;

    //用户手机号登录
    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {
        //从loginVO获取输入的手机号和验证码
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();

        //判断手机号和验证码是否为空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        // 判断手机验证码和用户输入的验证码是否一致
        String redisCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(redisCode)) {//不一致
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }

        //绑定手机号码(微信登录部分，短信验证码登录不需要)
        UserInfo userInfo = null;
        if(!StringUtils.isEmpty(loginVo.getOpenid())) {
            userInfo = this.selectWxInfoOpenId(loginVo.getOpenid());
            if(null != userInfo) {
                userInfo.setPhone(loginVo.getPhone());
                this.updateById(userInfo);
            } else {
                throw new YyghException(ResultCodeEnum.DATA_ERROR);
            }
        }

        //如果userInfo为空 进行正常的手机短信验证码登录
        if (userInfo == null){
            //判断是否是第一次登录  是第一次登录的话 在数据库中创建
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("phone",phone);
            userInfo = userInfoDao.selectOne(wrapper);
            if(userInfo == null){//为空是第一次登录  在数据库中创建
                userInfo = new UserInfo();
                userInfo.setName("");
                userInfo.setPhone(phone);
                userInfo.setStatus(1);
                userInfoDao.insert(userInfo);
            }
        }

        //校验是否被禁用  status=0时被禁用 =1是可用
        if (userInfo.getStatus() == 0){
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //不是第一次 进行登录

        //返回登录信息

        //返回登录用户名

        //返回token信息
        //返回页面显示名称
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);
        //jwt生成token
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);
        return map;
    }

    //根据OpenId查询
    @Override
    public UserInfo selectWxInfoOpenId(String openid) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UserInfo userInfo = userInfoDao.selectOne(wrapper);
        return userInfo;
    }

    //用户认证
    @Override
    public void userAuth(long userId, UserAuthVo userAuthVo) {
        //根据用户id查询用户信息
        UserInfo userInfo = userInfoDao.selectById(userId);
        //设置认证信息
        //认证人姓名
        userInfo.setName(userAuthVo.getName());
        //其他认证信息
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //进行信息更新
        userInfoDao.updateById(userInfo);
    }


    //用户列表（条件查询带分页）
    @Override
    public IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo) {
        //UserInfoQueryVo获取条件值
        String name = userInfoQueryVo.getKeyword(); //用户名称
        Integer status = userInfoQueryVo.getStatus();//用户状态
        Integer authStatus = userInfoQueryVo.getAuthStatus(); //认证状态
        String createTimeBegin = userInfoQueryVo.getCreateTimeBegin(); //开始时间
        String createTimeEnd = userInfoQueryVo.getCreateTimeEnd(); //结束时间
        //对条件值进行非空判断
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(authStatus)) {
            wrapper.eq("auth_status",authStatus);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用mapper的方法
        IPage<UserInfo> pages = baseMapper.selectPage(pageParam, wrapper);
        //编号变成对应值封装
        pages.getRecords().stream().forEach(item -> {
            this.packageUserInfo(item);
        });
        return pages;
    }

    //用户锁定
    @Override
    public void lock(Long userId, Integer status) {
        if (status.intValue() == 0 || status.intValue() == 1){
            UserInfo userInfo = userInfoDao.selectById(userId);
            userInfo.setStatus(status);
            userInfoDao.updateById(userInfo);
        }
    }

    //用户详情
    @Override
    public Map<String, Object> show(Long userId) {
        Map<String,Object> map = new HashMap<>();
        UserInfo userInfo = userInfoDao.selectById(userId);
        UserInfo userInfo1 = packageUserInfo(userInfo);
        map.put("userInfo",userInfo1);
        //根据userId查询就诊人信息
        List<Patient> patientList = patientService.findAllUserId(userId);
        map.put("patientList",patientList);
        return map;
    }

    //认证审批(将authStatus从1改为2[通过]  -1[未通过])
    @Override
    public void approval(Long userId, Integer authStatus) {
        if (authStatus == 2 || authStatus == -1) { //-1审核未通过 2审核通过
            UserInfo userInfo = userInfoDao.selectById(userId);
            userInfo.setAuthStatus(authStatus);
            userInfoDao.updateById(userInfo);
        }
    }

    //编号变成对应值封装
    private UserInfo packageUserInfo(UserInfo userInfo) {
        //处理认证状态编码
        userInfo.getParam().put("authStatusString",AuthStatusEnum.getStatusNameByStatus(userInfo.getAuthStatus()));
        //处理用户状态 0  1
        String statusString = userInfo.getStatus().intValue()==0 ?"锁定" : "正常";
        userInfo.getParam().put("statusString",statusString);
        return userInfo;
    }


}
