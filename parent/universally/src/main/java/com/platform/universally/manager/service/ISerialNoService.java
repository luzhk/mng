package com.platform.universally.manager.service;

/**
 * 生成学序列号
 * @author Administrator
 *
 */
public interface ISerialNoService {
	
	Long getSerialNo(String moduleType, String serialNoType);
}
