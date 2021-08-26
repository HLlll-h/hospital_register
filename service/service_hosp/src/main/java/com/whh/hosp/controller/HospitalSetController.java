package com.whh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whh.hosp.common.utils.MD5;
import com.whh.hosp.exception.YyghException;
import com.whh.hosp.model.hosp.HospitalSet;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.HospitalSetService;
import com.whh.hosp.vo.hosp.HospitalSetQueryVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin//可以允许跨域访问
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Resource
    private HospitalSetService hospitalSetService;


    //测试 查询hospital_set表中所有记录
    @GetMapping("/fingAll")
    public Result findAll(){
        List<HospitalSet> list = hospitalSetService.list();
//        try {
//            int a = 10 / 0;
//        } catch (Exception e) {
//            throw new YyghException("失败",201);
//        }

        return Result.ok(list);
    }

    //删除一条记录---逻辑删除
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Integer id){
        boolean flag = hospitalSetService.removeById(id);
        if (flag == true){
            return Result.ok();
        }
        return Result.fail();
    }

    //条件查询[支持模糊查询]带分页
    @PostMapping("/findPage/{current}/{limit}")//@RequestBody注解只能使用PostMapping
    public Result findPageHospSet(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        //创建page对象 传递当前页 没页记录数
        Page<HospitalSet> page = new Page<>(current,limit);
        //构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();//医院名称
        String hoscode = hospitalSetQueryVo.getHoscode();//医院编号
        if(!StringUtils.isEmpty(hosname)) {//hosname不为空
            wrapper.like("hosname",hospitalSetQueryVo.getHosname());
        }
        if(!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
        }

        //调用方法实现分页查询
        Page<HospitalSet> hospitalSets = hospitalSetService.page(page, wrapper);

        //返回结果
        return Result.ok(hospitalSets);
    }

    //hospital_set表中增加一条数据
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if(save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    //根据id获取hospital_set表中一条数据
    @GetMapping("/getHospSet/{id}")
    public Result getHospSet(@PathVariable("id") long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //修改hospital_set表中一条数据
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        //@RequestBody可加可不加  加了在swagger中会将整个对象合在一起 方便传参
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag) {//为true 修改成功
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //批量删除hospital_set表中数据
    @DeleteMapping("/batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList){
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    //hospital_set表设置锁定和解锁
    // is_deleted为0时表示存在 为1表示被逻辑删除
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable("id") Long id,
                                  @PathVariable("status") Integer status){
        //根据id查询记录
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //进行修改
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    //发送签名秘钥
    @PutMapping("/sendKey/{id}")
    public Result lockHospitalSet1(@PathVariable("id") Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //发送短信
        return Result.ok();
    }






}
