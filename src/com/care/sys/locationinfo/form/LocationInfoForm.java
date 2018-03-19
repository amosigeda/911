package com.care.sys.locationinfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class LocationInfoForm extends PublicFormBean implements Serializable{
	
	public LocationInfoForm(){
		
	}
	private Integer id;
	private String serieNo;
	private Integer battery;
	private String longitude;
	private String latitude;
	private String locationType;
	private Integer accuracy;
	private Date uploadTime;
	private String changeLongitude;//ת����ľ���
	private String changeLatitude;//ת�����γ��
	private String showType;  //显示类型
	private String fall;
	private Integer stepNo;
	private Integer rollNo;
	private Integer tAlarm;
	private Integer tStatus;
	private String spstasus;
	
	public String getSpstasus() {
		return spstasus;
	}
	public void setSpstasus(String spstasus) {
		this.spstasus = spstasus;
	}
	public Integer getStepNo() {
		return stepNo;
	}
	public void setStepNo(Integer stepNo) {
		this.stepNo = stepNo;
	}
	public Integer getRollNo() {
		return rollNo;
	}
	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}
	public Integer gettAlarm() {
		return tAlarm;
	}
	public void settAlarm(Integer tAlarm) {
		this.tAlarm = tAlarm;
	}
	public Integer gettStatus() {
		return tStatus;
	}
	public void settStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}
	public String getChangeLongitude() {
		return changeLongitude;
	}
	public void setChangeLongitude(String changeLongitude) {
		this.changeLongitude = changeLongitude;
	}
	public String getChangeLatitude() {
		return changeLatitude;
	}
	public void setChangeLatitude(String changeLatitude) {
		this.changeLatitude = changeLatitude;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
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
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
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
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getFall() {
		return fall;
	}
	public void setFall(String fall) {
		this.fall = fall;
	}
	

}
