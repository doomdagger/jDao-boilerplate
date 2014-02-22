package com.doomdagger.dao.support;

import java.util.UUID;

/**
 * 生成随机UUID，可以作为主键
 * @author apple
 *
 */
public class UUIDGenerator {
	
	/**
	 * 随机UUID生成器
	 * @return 返回字符串
	 */
	public static String randomUUID(){
		return UUID.randomUUID().toString();
	}
}
