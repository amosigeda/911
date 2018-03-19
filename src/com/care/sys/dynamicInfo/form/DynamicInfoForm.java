package com.care.sys.dynamicInfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class DynamicInfoForm extends PublicFormBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String mName;
	private String superId;
	private String menuLeave;
	private String menuRank; 
	private Integer submenuNumber;
	private String effect;
	private Date addTime;
	private String describe;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	
	public String getSuperId() {
		return superId;
	}
	public void setSuperId(String superId) {
		this.superId = superId;
	}
	public String getMenuLeave() {
		return menuLeave;
	}
	public void setMenuLeave(String menuLeave) {
		this.menuLeave = menuLeave;
	}
	public String getMenuRank() {
		return menuRank;
	}
	public void setMenuRank(String menuRank) {
		this.menuRank = menuRank;
	}
	public Integer getSubmenuNumber() {
		return submenuNumber;
	}
	public void setSubmenuNumber(Integer submenuNumber) {
		this.submenuNumber = submenuNumber;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	

}
