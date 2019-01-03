package com.platform.universally.manager.mapper;


import java.util.List;

import com.platform.universally.basemapper.MyBaseMapper;
import com.platform.universally.manager.model.SysUser;
//import com.platform.utils.basemapper.BaseReadMapper;
//import com.platform.utils.basemapper.BaseWriteMapper;
//import org.apache.ibatis.annotations.Mapper;


//@Mapper
public interface SysUserMapper extends MyBaseMapper<SysUser> {
    /*int deleteByPrimaryKey(Long id);
    int insert(SysUser record);
    int insertSelective(SysUser record);
    SysUser selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(SysUser record);
    int updateByPrimaryKey(SysUser record);*/
	
	List<SysUser> listSysUserByRecord(SysUser record);
}