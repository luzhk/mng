package com.platform.universally.mapper;


import com.platform.universally.basemapper.MyBaseMapper;
import com.platform.universally.model.SerialNo;

public interface SerialNoMapper extends MyBaseMapper<SerialNo> {
	
    /*int deleteByPrimaryKey(Long id);
    int insert(BaseMakeSerialNo record);
    int insertSelective(BaseMakeSerialNo record);
    BaseMakeSerialNo selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(BaseMakeSerialNo record);
    int updateByPrimaryKey(BaseMakeSerialNo record);*/
	
	Long getSerialNo(SerialNo record);
}