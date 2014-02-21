package com.doomdagger.pojos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="area.findAll", query="select area from Area area"),
	@NamedQuery(name="area.findOneById", query="select area from Area area where area.id=:areaId")
})

@Entity
public class Area implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1199454941218580378L;
	@Id
	private String id;
	private String areaName;
	private int areaRemoteId;
	private double longitude;
	private double latitude;
	private double radius;
	
	
	
	public Area() {}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getAreaRemoteId() {
		return areaRemoteId;
	}
	public void setAreaRemoteId(int areaRemoteId) {
		this.areaRemoteId = areaRemoteId;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}


}
