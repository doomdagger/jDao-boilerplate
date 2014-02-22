package com.doomdagger.dao.test;

import java.util.ArrayList;
import java.util.List;

import com.doomdagger.dao.support.UUIDGenerator;
import com.doomdagger.pojos.Area;
import com.doomdagger.pojos.Building;

public class MainTest {
	public static void main(String[] args){
		Area area = new Area();
		area.setAreaName("本部");
		area.setAreaRemoteId(123421);
		
		List<Building> buildings = new ArrayList<Building>();
		
		Building building = new Building();
		building.setArea(area);
		building.setBuildingName("实验楼");
		building.setBuildingRemoteId(12345);
		building.setId(UUIDGenerator.randomUUID());
		building.setLatitude(4392.21);
		building.setLongitude(2312);
		buildings.add(building);
		
		Building building2 = new Building();
		building2.setArea(area);
		building2.setBuildingName("实验楼2");
		building2.setBuildingRemoteId(2441);
		building2.setId(UUIDGenerator.randomUUID());
		building2.setLatitude(1234.21);
		building2.setLongitude(9789);
		buildings.add(building2);
		
		area.setBuildings(buildings);
		
		area.setId(UUIDGenerator.randomUUID());
		area.setLongitude(23241.21);
		
//		Map<String, Building> map = new HashMap<String, Building>();
//		map.put(building.getId(), building);
//		map.put(building2.getId(), building2);
//		
//		area.setMap(map);
		
		System.err.println(area.toJSON());
	}
}
