package com.platform.universally.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.platform.universally.manager.mapper.SysUserMapper;
import com.platform.universally.manager.model.SysUser;
import com.platform.universally.manager.service.ISysUserService;

@Service("sysUserService")
public class SysUserService implements ISysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser getSysUserByUserName(String userName) {
		SysUser record = new SysUser();
		record.setUserName(userName);
		List<SysUser> sysUsers = sysUserMapper.listSysUserByRecord(record);
		if (CollectionUtils.isEmpty(sysUsers)) {
			return null;
		} else {
			return sysUsers.get(0);
		}
	}

	@Override
	public int saveSysUser(SysUser sysUser) {
		return sysUserMapper.insertSelective(sysUser);
	}
}
