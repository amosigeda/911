package com.care.sys.sysregisterinfo.form;

import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class UserInfoForm extends PublicFormBean {

	private static final long serialVersionUID = 4665945856305925786L;
	
	private String userName;
	private String remark;
	private String userCode;
	private String passWrd;
	private String passWrd1;
	private Date createDate;
	private Date updateDate;
	private String groupCode;
	private int tag;
	private int id;
	private String code;  
	private String addUser;
	private String phoneNo;
	private String apply_status;
	private String companyId;
	private String isInApply;
	private String applyReason;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPassWrd() {
		return passWrd;
	}
	public void setPassWrd(String passWrd) {
		this.passWrd = passWrd;
	}
	public String getPassWrd1() {
		return passWrd1;
	}
	public void setPassWrd1(String passWrd1) {
		this.passWrd1 = passWrd1;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getApply_status() {
		return apply_status;
	}
	public void setApply_status(String apply_status) {
		this.apply_status = apply_status;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getIsInApply() {
		return isInApply;
	}
	public void setIsInApply(String isInApply) {
		this.isInApply = isInApply;
	}
	public String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}	
		
}
