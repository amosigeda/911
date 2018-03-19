package com.care.sys.feedbackinfo.domain;

import java.util.Date;
import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;
/**
 * �û�POJO
 * @author hepx
 * @date 2010-10-29 ����10:34:23
 */
public class FeedBackInfo extends PublicVoBean implements Serializable {

	private static final long serialVersionUID = 3606211640133561712L;
	
	public FeedBackInfo(){
	}
	private String feedbackstatus;
	private Integer id;
	private String contactWay;     //��ϵ��ʽ
	private String feedBackContent;//�������
	private Date dateTime;         //����ʱ��
	
	private String userName;
	private String procontent;
    private Date protime;
    public String getFeedbackstatus() {
		return feedbackstatus;
	}
	public void setFeedbackstatus(String feedbackstatus) {
		this.feedbackstatus = feedbackstatus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    private String belongProject;
	public String getProcontent() {
		return procontent;
	}
	public void setProcontent(String procontent) {
		this.procontent = procontent;
	}
	public Date getProtime() {
		return protime;
	}
	public void setProtime(Date protime) {
		this.protime = protime;
	}
	
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setContactWay(String contactWay){
		this.contactWay = contactWay;
	}
	public String getContactWay(){
		return this.contactWay;
	}

	public void setFeedBackContent(String feedBackContent){
		this.feedBackContent = feedBackContent;
	}
	public String getFeedBackContent(){
		return this.feedBackContent;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Date getDateTime() {
		return this.dateTime;
	}
}
