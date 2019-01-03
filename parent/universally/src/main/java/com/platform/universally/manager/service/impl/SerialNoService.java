package com.platform.universally.manager.service.impl;


import com.platform.universally.mapper.SerialNoMapper;
import com.platform.universally.model.SerialNo;
import com.platform.universally.manager.service.ISerialNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("makeSerialNoService")
public class SerialNoService implements ISerialNoService {
	
	@Autowired
	private SerialNoMapper baseMakeSerialNoMapper;

	@Override
	public Long getSerialNo(String moduleType, String serialNoType) {
		SerialNo record = new SerialNo();
		record.setModuleType(moduleType);
		record.setSerialType(serialNoType);
		return baseMakeSerialNoMapper.getSerialNo(record);
	}
}
