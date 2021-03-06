package com.doomdagger.dao.support;


import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

/**
 * 此类用户无需知晓，底层的拼接类，直接与框架交互
 * @author Li He
 *
 */
@SuppressWarnings("rawtypes")
public class QueryWrapper {
	private DetachedCriteria criteria;
	
	private QueryWrapper(Class cls){
		criteria = DetachedCriteria.forClass(cls);
	}
	
	public static QueryWrapper from(Class cls){
		return new QueryWrapper(cls);
	}
	
	public QueryWrapper join(String mappedField, String alias){
		criteria.createAlias(mappedField, alias);
		return this;
	}
	
	public QueryWrapper join(Map<String, String> propPair){
		for(Map.Entry<String, String> entry:propPair.entrySet()){
			join(entry.getKey(), entry.getValue());
		}
		return this;
	}
	
	public QueryWrapper addCriteria(CriteriaWrapper wrapper){
		if(wrapper!=null)
			criteria.add(wrapper.getCriteria());
		return this;
	}
	
	public QueryWrapper addProjection(ProjectionWrapper projectionWrapper){
		if(projectionWrapper!=null)
			criteria.setProjection(projectionWrapper.getProjectionList());
		return this;
	}
	
	public QueryWrapper addOrder(Sortable sortable){
		if(sortable!=null)
			for(Order order : sortable.toSort()){
				criteria.addOrder(order);
			}
		return this;
	}
	
	
	public DetachedCriteria getCriteria(){
		return criteria;
	}
}
