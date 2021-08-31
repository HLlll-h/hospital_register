package com.whh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whh.hosp.dao.UserInfoDao;
import com.whh.hosp.enums.AuthStatusEnum;
import com.whh.hosp.exception.YyghException;
import com.whh.hosp.helper.JwtHelper;
import com.whh.hosp.model.user.UserInfo;
import com.whh.hosp.result.ResultCodeEnum;
import com.whh.hosp.service.UserInfoService;
import com.whh.hosp.vo.user.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whh.hosp.vo.user.UserAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;


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


}
