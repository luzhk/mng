package com.platform.universally.basemapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * jpa基础mapper
 * Created by Administrator on 2018/12/26.
 */
public interface MyBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
