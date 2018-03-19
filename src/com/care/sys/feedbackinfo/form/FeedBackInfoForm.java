package com.care.sys.feedbackinfo.form;

import java.util.Date;
import java.io.Serializable;
import com.godoing.rose.http.PublicFormBean;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class FeedBackInfoForm extends PublicFormBean implements Serializable {

	private static final long serialVersionUID = -4230576721800974875L;

	public FeedBackInfoForm() {
	}
	private String feedbackstatus;
    private Integer id;
	private String contactWay;     //��ϵ��ʽ
	private String feedBackContent;//�������
	private Date dateTime;         //����ʱ��
	private String procontent;
	private Date protime;
	
	public String getFeedbackstatus() {
		return feedbackstatus;
	}
	public void setFeedbackstatus(String feedbackstatus) {
		this.feedbackstatus = feedbackstatus;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Integer getId() {
		return id;
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
