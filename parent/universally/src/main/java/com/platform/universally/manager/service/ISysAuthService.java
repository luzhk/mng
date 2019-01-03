package com.platform.universally.manager.service;

import java.util.List;

import com.platform.universally.manager.model.SysMenu;

public interface ISysAuthService {
	
	List<SysMenu> listSysMenusByRole(String RoleCode);

}
