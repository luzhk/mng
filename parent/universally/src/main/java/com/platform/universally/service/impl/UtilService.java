package com.platform.universally.service.impl;

import com.platform.universally.mapper.SerialNoMapper;
import com.platform.universally.model.SerialNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.universally.service.IUtilService;


@Component("utilService")
public class UtilService implements IUtilService {
	
	@Autowired
	private SerialNoMapper serialNoMapper;
	
	private final static String INIT_USER_CODE = "0000000";

	/**
	 * 获取用户的编码
	 * @param moduleType 模块
	 * @param serialnoType 业务
	 */
	@Override
	public String getUserSerialNo(String moduleType, String serialnoType) {
		String serialNo = String.valueOf(getSeralNo(moduleType, serialnoType));
		StringBuffer sb = new StringBuffer(INIT_USER_CODE);
		sb.append(serialNo);
		String result = sb.substring((serialNo.length() - 1), sb.length());
		return result;
	}
	
	/**
	 * 根据序列号类型从数据中获取对应的数据类型
	 * @param moduleType
	 * @param serialnoType
	 * @return
	 */
	private Long getSeralNo(String moduleType, String serialnoType){
		SerialNo record = new SerialNo();
		record.setModuleType(moduleType);
		record.setSerialType(serialnoType);
		record.setSerialNo(0L);
		Long result = serialNoMapper.getSerialNo(record);
		return result;
	}

}
