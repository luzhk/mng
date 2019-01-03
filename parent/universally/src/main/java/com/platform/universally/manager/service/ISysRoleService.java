package com.platform.universally.manager.service;

import java.util.List;

public interface ISysRoleService {

	List<String> listRoleCodesByUserCode(String username);

}
