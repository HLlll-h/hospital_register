package com.whh.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {

    public static void main(String[] args) {
        //设置excel文件的路径
        String fileName = "F:\\01.xlsx";

        //构建数据集合
        List<UserData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserData data = new UserData(i,"傻逼"+i+"号");
            list.add(data);
        }

        //调用方法实现写操作
        EasyExcel.write(fileName,UserData.class).
                sheet("用户信息").doWrite(list);
    }
}
