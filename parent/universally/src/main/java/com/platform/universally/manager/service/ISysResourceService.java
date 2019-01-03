package com.platform.universally.manager.service;

import java.util.List;

import com.platform.universally.manager.model.SysMenu;

public interface ISysResourceService {
	
	List<SysMenu> listSysMenusByRole(List<String> sysRole);
}
