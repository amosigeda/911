package com.care.sys.settinginfo.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

public class SettingInfoForm extends PublicFormBean implements Serializable{
	
	private Integer id;
	private String serieNo;
	private Integer volume;
	private String autoMute;
	private String powerOff;
	private String light;
	private String gps_on;
	private String light_sensor;
	private String fall;
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
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public String getAutoMute() {
		return autoMute;
	}
	public void setAutoMute(String autoMute) {
		this.autoMute = autoMute;
	}
	public String getPowerOff() {
		return powerOff;
	}
	public void setPowerOff(String powerOff) {
		this.powerOff = powerOff;
	}
	public String getLight() {
		return light;
	}
	public void setLight(String light) {
		this.light = light;
	}
	 

}
