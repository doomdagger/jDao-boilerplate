package com.doomdagger.dao.test;

import java.io.File;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.doomdagger.dao.impl.AreaDao;
import com.doomdagger.dao.support.CriteriaWrapper;
import com.doomdagger.dao.support.ProjectionWrapper;
import com.doomdagger.dao.support.Tuple;

public class DaoUnitTest {
//	@Test
//	public void addTest(){
//		char sep = File.separatorChar;
//		@SuppressWarnings("resource")
//		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebContent"+sep+"WEB-INF"+sep+"applicationContext.xml");
//
//		AreaDao areaDao = (AreaDao)ctx.getBean("areaDao");
//		
//		Area area = new Area();
//		area.setAreaName("吼吼");
//		area.setId(UUIDGenerator.randomUUID());
//		area.setRadius(Math.random()*1000);
//		areaDao.add(area);
//		
//	}
//	
//	@Test
//	public void findTest(){
//		char sep = File.separatorChar;
//		@SuppressWarnings("resource")
//		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebContent"+sep+"WEB-INF"+sep+"applicationContext.xml");
//
//		AreaDao areaDao = (AreaDao)ctx.getBean("areaDao");
//		
//		Tuple tuples = areaDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("id", "9009b898-d04d-48c7-81c4-bf10aad4e220")), ProjectionWrapper.instance("id","radius"));
//		
//		System.err.println(tuples.get(1));
//		
//		System.err.println(tuples.size());
//		for(Tuple tuple : tuples){
//			System.err.println(tuple.get(0));
//		}
//		
//	}
	
	@Test
	public void updateTest(){
		char sep = File.separatorChar;
		@SuppressWarnings("resource")
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebContent"+sep+"WEB-INF"+sep+"applicationContext.xml");

		AreaDao areaDao = (AreaDao)ctx.getBean("areaDao");
		
		//areaDao.updateMultiByParams(CriteriaWrapper.instance().and(Restrictions.eq("id", "'a5b17c11-a17e-4a04-b2eb-bd138dd297c0'")), UpdateWrapper.instance().inc("radius", 300));
		
		Tuple tuple = areaDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("areaName", "吼吼")), ProjectionWrapper.instance().fields("id","areaName"));
		
		for(Object object : tuple){
			System.err.println(object);
		}
		
	}
}
