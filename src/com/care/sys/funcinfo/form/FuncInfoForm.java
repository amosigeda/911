package com.care.sys.funcinfo.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class FuncInfoForm extends PublicFormBean implements Serializable {

	private static final long serialVersionUID = 7195638938059323124L;

	public FuncInfoForm() {
	}

	private String funcName;
	private String funcDo;
	private Long funcSort;
	private Long levels;
	private String statu;
	private String funcDesc;
	private String funcCode;
	private String superCode;

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncDo(String funcDo) {
		this.funcDo = funcDo;
	}

	public String getFuncDo() {
		return funcDo;
	}

	public void setFuncSort(Long funcSort) {
		this.funcSort = funcSort;
	}

	public Long getFuncSort() {
		return this.funcSort;
	}

	public void setLevels(Long levels) {
		this.levels = levels;
	}

	public Long getLevels() {
		return this.levels;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getStatu() {
		return this.statu;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public String getFuncDesc() {
		return this.funcDesc;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getFuncCode() {
		return this.funcCode;
	}

	public String getSuperCode() {
		return superCode;
	}

	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}
}
