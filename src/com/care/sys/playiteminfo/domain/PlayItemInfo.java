package com.care.sys.playiteminfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class PlayItemInfo extends PublicVoBean implements Serializable{
	
	private Integer id;
	private String serieNo;
	private String productModel;
	private String name;
	private String type;
	private String controlType;
	private String belongProject;
	
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public Integer getId() {
		return id;
	}
	public String getSerieNo() {
		return serieNo;
	}
	public String getProductModel() {
		return productModel;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getControlType() {
		return controlType;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setSerieNo(String serieNo) {
		this.serieNo = serieNo;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	

}
