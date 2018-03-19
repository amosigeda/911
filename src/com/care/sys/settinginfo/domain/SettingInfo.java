package com.care.sys.settinginfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class SettingInfo extends PublicVoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String serieNo;
	private Integer volume;
	private String map;
	private String fallOn;
	private String light;
	private String gps_on;
	private String light_sensor;
	private String fall;
	
	private String shutdown;
	private String repellent;
	private String heart;
    private String type;
    private String isOff;
    private String levels;
    private String degree;
    private String device;
	
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	public String getShutdown() {
		return shutdown;
	}
	public void setShutdown(String shutdown) {
		this.shutdown = shutdown;
	}
	public String getRepellent() {
		return repellent;
	}
	public void setRepellent(String repellent) {
		this.repellent = repellent;
	}
	public String getHeart() {
		return heart;
	}
	public void setHeart(String heart) {
		this.heart = heart;
	}
    private String belongProject;
	
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerieNo() {
		return serieNo;
	}
	public void setSerieNo(String serieNo) {
		this.serieNo = serieNo;
	}
	public Integer getVolume() {
		return volume;
	}
	public String getGps_on() {
		return gps_on;
	}
	public void setGps_on(String gps_on) {
		this.gps_on = gps_on;
	}
	public String getLight_sensor() {
		return light_sensor;
	}
	public void setLight_sensor(String light_sensor) {
		this.light_sensor = light_sensor;
	}
	public String getFall() {
		return fall;
	}
	public void setFall(String fall) {
		this.fall = fall;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getFallOn() {
		return fallOn;
	}
	public void setFallOn(String fallOn) {
		this.fallOn = fallOn;
	}
	public String getLight() {
		return light;
	}
	public void setLight(String light) {
		this.light = light;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsOff() {
		return isOff;
	}
	public void setIsOff(String isOff) {
		this.isOff = isOff;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	

}
