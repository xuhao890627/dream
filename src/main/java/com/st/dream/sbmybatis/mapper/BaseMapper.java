package com.st.dream.sbmybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BaseMapper<T>  extends Mapper<T>, MySqlMapper<T>, InsertListMapper<T> {
}
