package com.doomdagger.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.doomdagger.dao.GenericDao;
import com.doomdagger.dao.support.CriteriaWrapper;
import com.doomdagger.dao.support.Pageable;
import com.doomdagger.dao.support.ParamMapper;
import com.doomdagger.dao.support.ProjectionWrapper;
import com.doomdagger.dao.support.QueryWrapper;
import com.doomdagger.dao.support.Sortable;
import com.doomdagger.dao.support.Tuple;
import com.doomdagger.dao.support.UpdateWrapper;

@SuppressWarnings({"unchecked","rawtypes"})
public class GenericDaoImpl<T> implements GenericDao<T>{
	private Class<T> cls;
	protected HibernateTemplate hibernateTemplate;
	
	
	/**
	 * Default constructor. 构造函数不传参，但是很重要，为继承的子类抽出泛型的Class对象，以便于 传给DAO方法
	 */
	public GenericDaoImpl() {
		Class clazz = getClass();

		while (clazz != Object.class) {
			Type t = clazz.getGenericSuperclass();
			if (t instanceof ParameterizedType) {
				Type[] args = ((ParameterizedType) t).getActualTypeArguments();
				if (args[0] instanceof Class) {
					cls = ((Class<T>) args[0]);
					break;
				}
			}
			clazz = clazz.getSuperclass();
		}		

	}
	
	
	
	
	@Override
	public void add(T paramT) {
		hibernateTemplate.save(paramT);
	}

	@Override
	public void addMulti(Collection<T> paramTs) {
		for(T paramT : paramTs){
			add(paramT);
		}
	}

	@Override
	public void update(T paramT) {
		hibernateTemplate.update(paramT);
	}

	@Override
	public int updateFirstByParams(CriteriaWrapper criteriaWrapper,
			UpdateWrapper UpdateWrapper) {
		return 0;
	}

	@Override
	public int updateFirstById(String id, UpdateWrapper UpdateWrapper) {
		CriteriaWrapper criteriaWrapper = CriteriaWrapper.instance().and(Restrictions.eq("id", id));
		return wrapBatchUpdate(criteriaWrapper, UpdateWrapper);
	}

	@Override
	public int updateMultiByParams(CriteriaWrapper criteriaWrapper,
			UpdateWrapper UpdateWrapper) {
		return wrapBatchUpdate(criteriaWrapper, UpdateWrapper);

	}


	@Override
	public void delete(T paramT) {
		hibernateTemplate.delete(paramT);
	}

	//OK!!!
	@Override
	public void deleteByParams(CriteriaWrapper criteriaWrapper) {
		getSession().createQuery("delete from "+cls.getSimpleName()+" where "+criteriaWrapper.getCriteria().toString()).executeUpdate();
	}

	@Override
	public T findOneById(String id) {
		return hibernateTemplate.get(cls, id);
	}

	
	@Override
	public List<T> findAll() {
		return hibernateTemplate.loadAll(cls);
	}


	@Override
	public Tuple findOneProjectedById(String id, ProjectionWrapper projectionWrapper) {
		return wrapQueryProjectedOne(CriteriaWrapper.instance().and(Restrictions.eq("id", id)), projectionWrapper, null);
	}




	@Override
	public T findOneByParams(CriteriaWrapper criteriaWrapper) {
		return wrapQueryOne(criteriaWrapper, null, null);
	}




	@Override
	public Tuple findOneProjectedByParams(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper) {
		return wrapQueryProjectedOne(criteriaWrapper, projectionWrapper, null);

	}




	@Override
	public List<T> findByParams(CriteriaWrapper criteriaWrapper) {
		return wrapQueryList(criteriaWrapper, null, null, null);

	}




	@Override
	public List<T> findByParamsInOrder(CriteriaWrapper criteriaWrapper,
			Sortable sortable) {
		return wrapQueryList(criteriaWrapper, null, sortable, null);

	}




	@Override
	public List<T> findByParamsInPage(CriteriaWrapper criteriaWrapper,
			Pageable pageable) {
		return wrapQueryList(criteriaWrapper, null, null, pageable);

	}




	@Override
	public List<T> findByParamsInPageInOrder(CriteriaWrapper criteriaWrapper,
			Pageable pageable, Sortable sortable) {
		return wrapQueryList(criteriaWrapper, null, sortable, pageable);
	}




	@Override
	public List<Tuple> findProjectedByParams(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper) {
		return wrapTuple(wrapQueryList(criteriaWrapper, projectionWrapper, null, null));

	}




	@Override
	public List<Tuple> findProjectedByParamsInOrder(
			CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		return wrapTuple(wrapQueryList(criteriaWrapper, projectionWrapper, sortable, null));

	}




	@Override
	public List<Tuple> findProjectedByParamsInPage(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable) {
		return wrapTuple(wrapQueryList(criteriaWrapper, projectionWrapper, null, pageable));

	}




	@Override
	public List<Tuple> findProjectedByParamsInPageInOrder(
			CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable,
			Sortable sortable) {
		return wrapTuple(wrapQueryList(criteriaWrapper, projectionWrapper, sortable, pageable));
	}




	@Override
	public List<T> findByIds(String... ids) {
		return wrapQueryList(CriteriaWrapper.instance().and(Restrictions.in("id", ids)), null, null, null);
	}




	@Override
	public List<T> findByIdsInOrder(Sortable sortable, String... ids) {
		return wrapQueryList(CriteriaWrapper.instance().and(Restrictions.in("id", ids)), null, sortable, null);
	}




	@Override
	public List<T> findAllInOrder(Sortable sortable) {
		return wrapQueryList(null, null, sortable, null);
	}




	@Override
	public List<T> findAllInPage(Pageable pageable) {
		return wrapQueryList(null, null, null, pageable);

	}




	@Override
	public List<T> findAllInPageInOrder(Pageable pageable, Sortable sortable) {
		return wrapQueryList(null, null, sortable, pageable);

	}




	@Override
	public List<Tuple> findProjectedAll(String... fields) {
		return wrapTuple(wrapQueryList(null, ProjectionWrapper.instance().fields(fields), null, null));
	}




	@Override
	public List<Tuple> findProjectedAllInOrder(Sortable sortable, String... fields) {
		return wrapTuple(wrapQueryList(null, ProjectionWrapper.instance().fields(fields), sortable, null));

	}




	@Override
	public List<Tuple> findProjectedAll(ProjectionWrapper projectionWrapper) {
		return wrapTuple(wrapQueryList(null, projectionWrapper, null, null));

	}




	@Override
	public List<Tuple> findProjectedAllInOrder(ProjectionWrapper projectionWrapper,
			Sortable sortable) {
		return wrapTuple(wrapQueryList(null, projectionWrapper, sortable, null));

	}




	@Override
	public List<Tuple> findProjectedAllInPage(Pageable pageable, String... fields) {
		return wrapTuple(wrapQueryList(null, ProjectionWrapper.instance().fields(fields), null, pageable));
	}




	@Override
	public List<Tuple> findProjectedAllInPageInOrder(Pageable pageable,
			Sortable sortable, String... fields) {
		return wrapTuple(wrapQueryList(null, ProjectionWrapper.instance().fields(fields), sortable, pageable));
	}




	@Override
	public List<Tuple> findProjectedAllInPage(Pageable pageable,
			ProjectionWrapper projectionWrapper) {
		return wrapTuple(wrapQueryList(null, projectionWrapper, null, pageable));

	}

	@Override
	public List<Tuple> findProjectedAllInPageInOrder(Pageable pageable,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		return wrapTuple(wrapQueryList(null, projectionWrapper, sortable, pageable));
	}
	

	@Override
	public List<T> findByJoinedParams(Map<String, String> propPair, CriteriaWrapper criteriaWrapper) {
		QueryWrapper queryWrapper = QueryWrapper.from(cls);
		for(Map.Entry<String, String> entry:propPair.entrySet()){
			queryWrapper.join(entry.getKey(), entry.getValue());
		}
		queryWrapper.addCriteria(criteriaWrapper);
		return hibernateTemplate.findByCriteria(queryWrapper.getCriteria());
	}

	@Override
	public T findOneByJoinedParams(Map<String, String> propPair, CriteriaWrapper criteriaWrapper) {
		QueryWrapper queryWrapper = QueryWrapper.from(cls);
		for(Map.Entry<String, String> entry:propPair.entrySet()){
			queryWrapper.join(entry.getKey(), entry.getValue());
		}
		queryWrapper.addCriteria(criteriaWrapper);
		List<T> list = hibernateTemplate.findByCriteria(queryWrapper.getCriteria(),0,1);
		if(list==null||list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	@Override
	public List findByNamedQuery(String queryName, ParamMapper paramMapper) {
		return hibernateTemplate.findByNamedQueryAndNamedParam(queryName, paramMapper.getKeyArray(), paramMapper.getValueArray());
	}
	@Override
	public Class<T> getParameterizedClass() {
		return cls;
	}

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	private int wrapBatchUpdate(CriteriaWrapper criteriaWrapper, UpdateWrapper updateWrapper){
		return getSession().createQuery(updateWrapper.getUpdate(cls, criteriaWrapper)).executeUpdate();
	}
	
	private List wrapQueryList(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable, Pageable pageable){
		DetachedCriteria detachedCriteria = QueryWrapper.from(cls).addCriteria(criteriaWrapper).addProjection(projectionWrapper).addOrder(sortable).getCriteria();
		if(pageable==null){
			return hibernateTemplate.findByCriteria(detachedCriteria); 
		}else{
			return hibernateTemplate.findByCriteria(detachedCriteria, pageable.getOffset(), pageable.getPageSize());
		}
	}
	
	private List<Tuple> wrapTuple(List rawList){
		List<Tuple> tuples = new ArrayList<Tuple>();
		for(Object object : rawList){
			tuples.add(new Tuple(object));
		}
		return tuples;
	}

	private T wrapQueryOne(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable){
		List<T> list =  wrapQueryList(criteriaWrapper, projectionWrapper, sortable, Pageable.inPage(0,1));
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	private Tuple wrapQueryProjectedOne(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable){
		List list =  wrapQueryList(criteriaWrapper, projectionWrapper, sortable, Pageable.inPage(0,1));
		if(list!=null&&list.size()!=0){
			return new Tuple((Object[])list.get(0));
		}else{
			return null;
		}
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate){
		this.hibernateTemplate = hibernateTemplate;
	}


	public Session getSession() {
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}



	@Override
	public int updateMultiByIds(Collection<String> ids,
			UpdateWrapper UpdateWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int upsert(T paramT) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int upsertMulti(Collection<T> paramTs) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<T> findByJoinedParamsInPage(Map<String, String> propPair,
			CriteriaWrapper criteriaWrapper, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<T> findByJoinedParamsInOrder(Map<String, String> propPair,
			CriteriaWrapper criteriaWrapper, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<T> findByJoinedParamsInPageInOrder(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Tuple> findProjectedByJoinedParams(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Tuple> findProjectedByJoinedParamsInPage(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Tuple> findProjectedByJoinedParamsInOrder(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Tuple> findProjectedByJoinedParamsInPageInOrder(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable,
			Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}




}
