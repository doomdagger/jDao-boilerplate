package com.doomdagger.dao.support;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 * 封装查询操作，底层仍采用Hibernate的Restrictions对象
 * @author Li He
 *
 */
public class CriteriaWrapper {
	
	//以Conjunction为Criteria的容器，这是一个大得and
	private Conjunction conjunction;
	
	private CriteriaWrapper(){
		conjunction = Restrictions.conjunction();
	}
	
	/**
	 * 实例化一个CriteriaWrapper，构造函数为私有，此方法为唯一的实例化方法
	 * @return 一个CriteriaWrapper对象
	 */
	public static CriteriaWrapper instance(){
		return new CriteriaWrapper();
	}
	
	/**
	 * 允许在Instance静态方法中传入多个Criterion，
	 * 这些Criterion将会默认以and联接。
	 * @param criterion 谓词条件
	 * @return 一个CriteriaWrapper对象
	 */
	public static CriteriaWrapper instance(Criterion... criterion){
		CriteriaWrapper wrapper = new CriteriaWrapper();
		wrapper.and(criterion);
		return wrapper;
	}
	
	/**
	 * 请只是用Restrictions来获取Criterion，Projections与Example等见其他类
	 * 这些Criterion将会以and关系联接
	 * @param criterions 谓词条件
	 * @return 返回CriteriaWrapper对象本身
	 */
	public CriteriaWrapper and(Criterion... criterions){
		Conjunction conjunction = Restrictions.conjunction();
		for(Criterion criterion : criterions){
			conjunction.add(criterion);
		}
		this.conjunction.add(conjunction);
		return this;
	}
	
	/**
	 * 请只是用Restrictions来获取Criterion，Projections与Example等见其他类
	 * 这些Criterion将会以or关系联接
	 * @param criterions 谓词条件
	 * @return 返回CriteriaWrapper对象本身
	 */
	public CriteriaWrapper or(Criterion... criterions){
		Disjunction disjunction = Restrictions.disjunction();
		for(Criterion criterion : criterions){
			disjunction.add(criterion);
		}
		conjunction.add(disjunction);
		return this;
	}
	
	/**
	 * 静态方法，以and形式联接多个CriteriaWrapper，
	 * 多个CriteriaWrapper中的conjunction对象将会以and形式联接
	 * @param criteriaWrappers wrapper对象
	 * @return 一个全新的CriteriaWrapper对象
	 */
	public static CriteriaWrapper and(CriteriaWrapper... criteriaWrappers){
		CriteriaWrapper criteriaWrapper = new CriteriaWrapper();
		for(CriteriaWrapper cri : criteriaWrappers){
			criteriaWrapper.getCriteria().add(cri.getCriteria());
		}
		return criteriaWrapper;
	}
	
	/**
	 * 静态方法，以or形式联接多个CriteriaWrapper，
	 * 多个CriteriaWrapper中的conjunction对象将会以or形式联接 
	 * @param criteriaWrappers
	 * @return
	 */
	public static CriteriaWrapper or(CriteriaWrapper... criteriaWrappers){
		CriteriaWrapper criteriaWrapper = new CriteriaWrapper();
		Disjunction disjunction = Restrictions.disjunction();
		for(CriteriaWrapper cri : criteriaWrappers){
			disjunction.add(cri.getCriteria());
		}
		criteriaWrapper.getCriteria().add(disjunction);
		return criteriaWrapper;
	}
	
	public Conjunction getCriteria(){
		return conjunction;
	}
}
