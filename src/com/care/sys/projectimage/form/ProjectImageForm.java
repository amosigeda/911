package com.care.sys.projectimage.form;

import java.io.Serializable;
import com.godoing.rose.http.PublicFormBean;

public class ProjectImageForm extends PublicFormBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String projectId;
	private String downLoadPath1;
	private String downLoadPath2;
	private String downLoadPath3;
	private String downLoadPath4;
	private String downLoadPath5;
	private String downLoadStatus;
	private String hideLine;
	
	public ProjectImageForm() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDownLoadPath1() {
		return downLoadPath1;
	}

	public void setDownLoadPath1(String downLoadPath1) {
		this.downLoadPath1 = downLoadPath1;
	}

	public String getDownLoadPath2() {
		return downLoadPath2;
	}

	public void setDownLoadPath2(String downLoadPath2) {
		this.downLoadPath2 = downLoadPath2;
	}

	public String getDownLoadPath3() {
		return downLoadPath3;
	}

	public void setDownLoadPath3(String downLoadPath3) {
		this.downLoadPath3 = downLoadPath3;
	}

	public String getDownLoadPath4() {
		return downLoadPath4;
	}

	public void setDownLoadPath4(String downLoadPath4) {
		this.downLoadPath4 = downLoadPath4;
	}

	public String getDownLoadPath5() {
		return downLoadPath5;
	}

	public void setDownLoadPath5(String downLoadPath5) {
		this.downLoadPath5 = downLoadPath5;
	}

	public String getDownLoadStatus() {
		return downLoadStatus;
	}

	public void setDownLoadStatus(String downLoadStatus) {
		this.downLoadStatus = downLoadStatus;
	}

	public String getHideLine() {
		return hideLine;
	}

	public void setHideLine(String hideLine) {
		this.hideLine = hideLine;
	}
	
}
