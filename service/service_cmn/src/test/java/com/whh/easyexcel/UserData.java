package com.whh.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 测试easyexcel使用
 */
@Data
public class UserData {

    @ExcelProperty(value = "用户编号",index = 0)
    private Integer uid;

    @ExcelProperty(value = "用户姓名",index = 1)
    private String name;

    public UserData(Integer uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public UserData() {
    }
}
