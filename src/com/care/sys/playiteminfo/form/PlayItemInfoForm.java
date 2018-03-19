package com.care.sys.playiteminfo.form;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.playiteminfo.dao.PlayItemInfoDao;
import com.godoing.rose.http.PublicFormBean;
import com.godoing.rose.log.LogFactory;

public class PlayItemInfoForm extends PublicFormBean implements Serializable{
	
	public PlayItemInfoForm(){
		
	}
	private Integer id;
	private String serieNo;
	private String productModel;
	private String name;
	private String type;
	private String controlType;
	
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
