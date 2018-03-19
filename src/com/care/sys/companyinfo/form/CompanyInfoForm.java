package com.care.sys.companyinfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class CompanyInfoForm extends PublicFormBean implements Serializable{
	
	public CompanyInfoForm(){
		
	}
	private Integer id;
	private String companyNo;
	private String companyName;
	private Date addTime;
	private String remark;
	private String status;
	private String userName;
	
	private Integer relevanceId;
	private Integer companyId;//�м��Ŀͻ�ID
	private Integer channelId;//�м�������ID
	
	public Integer getRelevanceId() {
		return relevanceId;
	}
	public void setRelevanceId(Integer relevanceId) {
		this.relevanceId = relevanceId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}	
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
