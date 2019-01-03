package com.platform.universally.manager.service;

import com.platform.universally.manager.model.SysUser;

public interface ISysUserService {

	SysUser getSysUserByUserName(String userName);
	
	int saveSysUser(SysUser sysUser);

}
