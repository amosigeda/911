package com.care.sys.shareinfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class ShareInfoForm extends PublicFormBean implements Serializable{
	
	public ShareInfoForm(){
		
	}
	
	private String userId;   //�û�id
	
	private String deviceId; //�豸id
	
	private String toUserId;  //���ĸ��û�����
	
	private String isPriority; //���ȼ�,0��ʾ������豸,1��ʾ�Լ������豸

	private Date shareDate;   //����ʱ��
	
	private String belongProject;
	
	public String getBelongProject() {
		return belongProject;
	}

	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIsPriority() {
		return isPriority;
	}

	public void setIsPriority(String isPriority) {
		this.isPriority = isPriority;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public Date getShareDate() {
		return shareDate;
	}

	public void setShareDate(Date shareDate) {
		this.shareDate = shareDate;
	}

}
