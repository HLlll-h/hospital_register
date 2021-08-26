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
@CrossOrigin //解决跨域问题
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





}
