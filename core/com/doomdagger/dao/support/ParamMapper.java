package com.doomdagger.dao.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 为JPQL 与 HQL 提供构建映射项支持，快速构建key-value映射
 * @author Li He
 *
 */
public class ParamMapper {
	private List<String> paramNames;
	private List<Object> paramValues;
	
	private ParamMapper(){
		paramNames = new ArrayList<String>();
		paramValues = new ArrayList<Object>();
	}
	
	/**
	 * 构建一个ParamMapper对象，传入第一组key-value映射，静态方法
	 * @param key 字符串值
	 * @param value 任何类型的对象
	 * @return ParamMapper实体
	 */
	public static ParamMapper param(String key, Object value){
		ParamMapper paramMapper = new ParamMapper();
		paramMapper.add(key, value);
		return paramMapper;
	}
	
	/**
	 * 调用此方法继续向ParamMapper中增加键值对
	 * @param key 字符串值
	 * @param value 任何类型的对象
	 * @return ParamMapper实体
	 */
	public ParamMapper add(String key, Object value){
		paramNames.add(key);
		paramValues.add(value);
		return this;
	}

	/**
	 * 返回key集合
	 * @return key集合，字符串集合
	 */
	public List<String> getParamNames() {
		return paramNames;
	}

	/**
	 * 返回value集合
	 * @return value集合，对象集合
	 */
	public List<Object> getParamValues() {
		return paramValues;
	}

	/**
	 * 替代方法，除了可以返回key集合外，也可以返回key数组
	 * @return key数组，字符串数组
	 */
	public String[] getKeyArray(){
		return paramNames.toArray(new String[0]);
	}
	
	/**
	 * 替代方法，除了可以返回value集合外，也可以返回value数组
	 * @return value数组，对象数组
	 */
	public Object[] getValueArray(){
		return paramValues.toArray(new Object[0]);
	}
}
