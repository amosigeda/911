package com.care.sys.safearea.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class SafeArea extends PublicVoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6028218934433133201L;
	private int id;
	private String seriNo;
	private String userId;
	private String longitude;
	private String latitude;
	private Integer safe_range;
	private String area_name;
	private String area_effect_time;
	private Date create_time;
	private Date update_time;
	private String safeAddress;
	private String status;      //是否进这个区域,1表示进入,0表示正常,-1表示出去
    private String belongProject;
	
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeriNo() {
		return seriNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Integer getSafe_range() {
		return safe_range;
	}
	public void setSafe_range(Integer safe_range) {
		this.safe_range = safe_range;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getArea_effect_time() {
		return area_effect_time;
	}
	public void setArea_effect_time(String area_effect_time) {
		this.area_effect_time = area_effect_time;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getSafeAddress() {
		return safeAddress;
	}
	public void setSafeAddress(String safeAddress) {
		this.safeAddress = safeAddress;
	}
	public void setSeriNo(String seriNo) {
		this.seriNo = seriNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
