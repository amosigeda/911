package com.care.sys.phoneinfo.form;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.godoing.rose.http.PublicFormBean;

public class PhoneInfoForm extends PublicFormBean implements Serializable{
	
	public PhoneInfoForm(){
		
	}
	private Integer id;
	private String serieNo;
	private String productModel;
	private String firmwareEdition;
	private String phone;
	private String alarmBellType;
	private Date inputTime;
	private Date uploadTime;
	private FormFile file1;
	private String status;
	private String belongProject;
	private String phoneManageId;
	private String relativeCallStatus;
	
	private String companyId;
    private String countNum;
    private String miniNum;
    private String maxNum;
	
    private String ip;
    private String devicePort;
    private String centerNum;
    private String assisantNum;
    private Float  reportedTime;
    private String deviceLanguage;
    private String timeZone;
    private Integer satelliteNum;
    private Float GSMSignal;
    private String LEDSwitch;
    
	public String getRelativeCallStatus() {
		return relativeCallStatus;
	}
	public void setRelativeCallStatus(String relativeCallStatus) {
		this.relativeCallStatus = relativeCallStatus;
	}
	public String getPhoneManageId() {
		return phoneManageId;
	}
	public void setPhoneManageId(String phoneManageId) {
		this.phoneManageId = phoneManageId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	public String getMiniNum() {
		return miniNum;
	}
	public void setMiniNum(String miniNum) {
		this.miniNum = miniNum;
	}
	public String getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public FormFile getFile1() {
		return file1;
	}
	public void setFile1(FormFile file1) {
		this.file1 = file1;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getAlarmBellType() {
		return alarmBellType;
	}
	public void setAlarmBellType(String alarmBellType) {
		this.alarmBellType = alarmBellType;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getFirmwareEdition() {
		return firmwareEdition;
	}
	public void setFirmwareEdition(String firmwareEdition) {
		this.firmwareEdition = firmwareEdition;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDevicePort() {
		return devicePort;
	}
	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort;
	}
	public String getCenterNum() {
		return centerNum;
	}
	public void setCenterNum(String centerNum) {
		this.centerNum = centerNum;
	}
	public String getAssisantNum() {
		return assisantNum;
	}
	public void setAssisantNum(String assisantNum) {
		this.assisantNum = assisantNum;
	}
	public Float getReportedTime() {
		return reportedTime;
	}
	public void setReportedTime(Float reportedTime) {
		this.reportedTime = reportedTime;
	}
	public String getDeviceLanguage() {
		return deviceLanguage;
	}
	public void setDeviceLanguage(String deviceLanguage) {
		this.deviceLanguage = deviceLanguage;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public Integer getSatelliteNum() {
		return satelliteNum;
	}
	public void setSatelliteNum(Integer satelliteNum) {
		this.satelliteNum = satelliteNum;
	}
	public Float getGSMSignal() {
		return GSMSignal;
	}
	public void setGSMSignal(Float gSMSignal) {
		GSMSignal = gSMSignal;
	}
	public String getLEDSwitch() {
		return LEDSwitch;
	}
	public void setLEDSwitch(String lEDSwitch) {
		LEDSwitch = lEDSwitch;
	}
	
	
}
