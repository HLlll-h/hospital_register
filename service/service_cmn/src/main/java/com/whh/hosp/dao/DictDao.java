package com.whh.hosp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whh.hosp.model.cmn.Dict;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictDao extends BaseMapper<Dict> {
}
