package com.whh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whh.hosp.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {


    /**
     * 根据parent_id查询子数据
     */
    List<Dict> findChildData(Long id);

    /**
     * dict表的excel导出
     */
    void exportDictData(HttpServletResponse response);

    /**
     * excel导入数据字典
     */
    void importDictData(MultipartFile file);

    /**
     * 根据dict_code和value查询
     */
    String getDictName(String dictCode, String value);

    /**
     * 根据dict_code获取下级节点
     */
    List<Dict> findByDictCode(String dictCode);
}
