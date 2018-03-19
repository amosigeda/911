package com.care.sys.msginfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class MsgInfo extends PublicVoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1182518760078612839L;

	private String fromId;   //�û�id
	
	private String toId; //�豸id
	
	private String isHandler; //�Ƿ���,0��δ����,1��ʾ�Ѵ���

	private String msgLevel;  //��Ϣ���ȼ�
	
	private String msgContent; //��Ϣ����
	
	private Date msgHandlerDate; //��Ϣ����ʱ��
	
    private String belongProject;
    
    private Date msgOccurDate;  //ZST@20151111
	
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
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
	public Date getMsgOccurDate() {
		return msgOccurDate;
	}
	public void setMsgOccurDate(Date msgOccurDate) {
		this.msgOccurDate = msgOccurDate;
	}
	
	
}
