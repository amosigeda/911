package com.care.sys.deviceLogin.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class DeviceLogin extends PublicVoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4194801717239053909L;

	private int id;

	private String deivceImei;

	private String devicePhone;

	private String deviceStatus;

	private Date dateTime;

	private String belongProject;
	
	private String deviceVersion;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeivceImei() {
		return deivceImei;
	}

	public void setDeivceImei(String deivceImei) {
		this.deivceImei = deivceImei;
	}

	public String getDevicePhone() {
		return devicePhone;
	}

	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getBelongProject() {
		return belongProject;
	}

	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
}
