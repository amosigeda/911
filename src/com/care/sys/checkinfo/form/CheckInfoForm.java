package com.care.sys.checkinfo.form;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.godoing.rose.http.PublicFormBean;

public class CheckInfoForm extends PublicFormBean implements Serializable{
	
	private int id;
	private String packageName;
	private String versionName;
	private Integer versionCode;
	private String downloadPath;
	private String functionCap;
	private FormFile file1;
	private String belongProject;
	private String upUser;
	private String IDCode;
	private String upType;
	private String upPlatform;
	private String status;
	private String remarks;
	private String versionType;
	private String IDCodeId;
	private Date uploadTime;
	private String erWeiMa;
	
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getIDCodeId() {
		return IDCodeId;
	}
	public void setIDCodeId(String iDCodeId) {
		IDCodeId = iDCodeId;
	}
	public String getVersionType() {
		return versionType;
	}
	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}
	public String getUpUser() {
		return upUser;
	}
	public void setUpUser(String upUser) {
		this.upUser = upUser;
	}
	public String getIDCode() {
		return IDCode;
	}
	public void setIDCode(String iDCode) {
		IDCode = iDCode;
	}
	public String getUpType() {
		return upType;
	}
	public void setUpType(String upType) {
		this.upType = upType;
	}
	public String getUpPlatform() {
		return upPlatform;
	}
	public void setUpPlatform(String upPlatform) {
		this.upPlatform = upPlatform;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFunctionCap() {
		return functionCap;
	}
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public void setFunctionCap(String functionCap) {
		this.functionCap = functionCap;
	}
	public FormFile getFile1() {
		return file1;
	}
	public void setFile1(FormFile file1) {
		this.file1 = file1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public Integer getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public String getErWeiMa() {
		return erWeiMa;
	}
	public void setErWeiMa(String erWeiMa) {
		this.erWeiMa = erWeiMa;
	}
	

}
