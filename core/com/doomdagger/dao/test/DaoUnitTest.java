package com.doomdagger.dao.test;

import java.io.File;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.doomdagger.dao.impl.AreaDao;
import com.doomdagger.dao.support.UUIDGenerator;
import com.doomdagger.pojos.Area;

public class DaoUnitTest {
	@Test
	public void addTest(){
		char sep = File.separatorChar;
		@SuppressWarnings("resource")
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebContent"+sep+"WEB-INF"+sep+"applicationContext.xml");

		AreaDao areaDao = (AreaDao)ctx.getBean("areaDao");
		
		Area area = new Area();
		area.setAreaName("哈哈");
		area.setId(UUIDGenerator.randomUUID());
		area.setRadius(234.544);
		areaDao.add(area);
		
	}
}
