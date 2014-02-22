package com.doomdagger.dao.support;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 * 投影查询支持，投影查询，查询返回部分字段
 * @author Li He
 *
 */
public class ProjectionWrapper {
	private ProjectionList projectionList;
	private ProjectionWrapper(){
		projectionList = Projections.projectionList();
	}
	
	/**
	 * 投影条件对象的构建，静态方法
	 * @return ProjectionWrapper对象
	 */
	public static ProjectionWrapper instance(){
		return new ProjectionWrapper();
	}
	
	/**
	 * 允许Instance方法传入Projection对象不定参数，快速构建ProjectionWrapper对象
	 * @param projection 
	 * @return ProjectionWrapper对象
	 */
	public static ProjectionWrapper instance(Projection... projection){
		ProjectionWrapper wrapper = new ProjectionWrapper();
		wrapper.add(projection);
		return wrapper;
	}
	
	public static ProjectionWrapper instance(String... fieldNames){
		ProjectionWrapper wrapper = new ProjectionWrapper();
		wrapper.fields(fieldNames);
		return wrapper;
	}
	
	//
	/**
	 * 方法传入String不定参数来指定投影查询返回的字段，快速构建ProjectionWrapper对象
	 * @param fieldNames 字段名称
	 * @return ProjectionWrapper对象
	 */
	public ProjectionWrapper fields(String... fieldNames){
		for(String fieldName : fieldNames){
			this.add(Projections.property(fieldName));
		}
		return this;
	}
	
	/**
	 * 方法传入Projection对象不定参数，快速构建ProjectionWrapper对象
	 * @param projections
	 * @return ProjectionWrapper对象
	 */
	public ProjectionWrapper add(Projection... projections){
		for(Projection projection : projections){
			projectionList.add(projection);
		}
		return this;
	}
	
	public ProjectionList getProjectionList(){
		return projectionList;
	}
}
