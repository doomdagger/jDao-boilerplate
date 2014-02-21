package com.doomdagger.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.doomdagger.pojos.Area;

@Repository("areaDao")
public class AreaDao extends GenericDaoImpl<Area>{
	@Override
	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
