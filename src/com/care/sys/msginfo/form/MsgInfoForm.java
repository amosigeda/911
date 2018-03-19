package com.care.sys.msginfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class MsgInfoForm extends PublicFormBean implements Serializable{
	
	public MsgInfoForm(){
		
	}

	private String fromId;   //�û�id
	
	private String toId; //�豸id
	
	private String isHandler; //�Ƿ���,0��δ����,1��ʾ�Ѵ���

	private String msgLevel;  //��Ϣ���ȼ�
	
	private String msgContent; //��Ϣ����
	
	private Date msgHandlerDate; //��Ϣ����ʱ��
	
	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getIsHandler() {
		return isHandler;
	}

	public void setIsHandler(String isHandler) {
		this.isHandler = isHandler;
	}

	public String getMsgLevel() {
		return msgLevel;
	}

	public void setMsgLevel(String msgLevel) {
		this.msgLevel = msgLevel;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Date getMsgHandlerDate() {
		return msgHandlerDate;
	}

	public void setMsgHandlerDate(Date msgHandlerDate) {
		this.msgHandlerDate = msgHandlerDate;
	}
}
