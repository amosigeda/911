package com.care.sys.devicephoneinfo.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

public class DevicePhoneInfoForm extends PublicFormBean implements Serializable{
	
	private int id;
	private String devicePhone;
	private String deviceImsi;
	private String deviceName;
	private int belongProject;
	private String firm;
	private String country;
	
	
	public DevicePhoneInfoForm() {
		super();
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDevicePhone() {
		return devicePhone;
	}
	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}
	
	public String getDeviceImsi() {
		return deviceImsi;
	}

	public void setDeviceImsi(String deviceImsi) {
		this.deviceImsi = deviceImsi;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(int belongProject) {
		this.belongProject = belongProject;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}	
}
