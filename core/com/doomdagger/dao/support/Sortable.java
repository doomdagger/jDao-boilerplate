package com.doomdagger.dao.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

/**
 * 提供排序支持
 * @author Li He
 *
 */
public class Sortable {
	
	private Map<String, SortOrder> sortMap = new HashMap<String, SortOrder>();
	
	private Sortable(){
		
	}
	
	private Sortable(String field, SortOrder order){
		sortMap.put(field, order);
	}
	
	/**
	 * 允许Instance对象传入第一对排序条件，指定字段名与排列顺序
	 * @param field 字段名
	 * @param order 排列顺序
	 * @return Sortable对象
	 */
	public static Sortable instance(String field, SortOrder order){
		Sortable sortable = new Sortable(field, order);
		return sortable;
	}
	
	/**
	 * 添加排序条件
	 * @param field 字段名
	 * @param order 排列顺序
	 * @return Sortable对象
	 */
	public Sortable and(String field, SortOrder order){
		this.sortMap.put(field, order);
		return this;
	}
	
	public Map<String, SortOrder> getSortInfo(){
		return sortMap;
	}
	
	/**
	 * 将Sortable对象转换为hibernate可以使用Order对象的集合
	 * @return Order对象集合
	 */
	public List<Order> toSort(){
		List<Order> list = new ArrayList<Order>();
		
		for(Map.Entry<String, SortOrder> entry : sortMap.entrySet()){
			Order order = null;
			if(entry.getValue()==SortOrder.ASCEND){
				order = Order.asc(entry.getKey());
			}else{
				order = Order.desc(entry.getKey());
			}
			
			list.add(order);
		}
		
		return list;
	}
}
