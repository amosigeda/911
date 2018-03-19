package com.care.sys.checkinfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class CheckInfo extends PublicVoBean implements Serializable{
	
	private int id;
	private String packageName;//包名
	private String versionName;//版本名称
	private Integer versionCode;//版本号
	private String downloadPath;//下载路径
	private String functionCap;//版本说明
	private String belongProject;//项目
	private String upUser;//可下载用户
	private String IDCode;//识别码
	private String upType;//升级类型 0正常，1强制，2建议
	private String upPlatform;//上传平台0安卓
	private String status;//状态0禁用1启用
	private String remarks;//备注
	private String versionType;//版本类型0内部，1外部
	private String IDCodeId;//识别码表ID
	private Date uploadTime;//上传时间
	private String erWeiMa;//二维码
	
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
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public String getFunctionCap() {
		return functionCap;
	}
	public void setFunctionCap(String functionCap) {
		this.functionCap = functionCap;
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
