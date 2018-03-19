package com.care.sys.sysloginfo.form;

import java.util.Date;
import java.io.Serializable;
import com.godoing.rose.http.PublicFormBean;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SysLogInfoForm extends PublicFormBean implements Serializable {

	private static final long serialVersionUID = -4007666243643679615L;

	public SysLogInfoForm() {
	}

	private String userName;
	private String logs;
	private Date logDate;
	private int id;
	private String tag;
	private String ip;
	private Date updateDate;
	private Date startTime;

	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIp() {
		return this.ip;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return this.tag;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public String getLogs() {
		return this.logs;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Date getLogDate() {
		return this.logDate;
	}
	
	public void setLogId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
