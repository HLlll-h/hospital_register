package com.whh.hosp.controller;


import com.whh.hosp.model.cmn.Dict;
import com.whh.hosp.result.Result;
import com.whh.hosp.service.DictService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin //解决跨域问题
public class DictController {


    @Resource
    private DictService dictService;


    //根据parent_id查询子数据列表
    @GetMapping("/findChildData/{id}")
    public Result findChildData(@PathVariable("id") Long id){
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }


    //导出数据字典接口
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response){
        dictService.exportDictData(response);
    }

    //导入数据字典接口
    @PostMapping("/importData")
    public Result importDict(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();
    }


    //根据dict_code和value查询
    @GetMapping("/getName/{dictCode}/{value}")
    public String getName(@PathVariable("dictCode") String dictCode,
                          @PathVariable("value") String value){
//        System.out.println("===============================");
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    //根据value查询
    @GetMapping("/getName/{value}")
    public String getName(@PathVariable("value") String value){

        String dictName = dictService.getDictName("",value);
        return dictName;
    }

    //根据dict_code获取下级节点
    @GetMapping("/findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable("dictCode") String dictCode){
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }





}
