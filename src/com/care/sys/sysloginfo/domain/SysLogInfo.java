package com.care.sys.sysloginfo.domain;

import java.util.Date;
import java.io.Serializable;
import com.godoing.rose.http.PublicVoBean;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SysLogInfo extends PublicVoBean implements Serializable {

	private static final long serialVersionUID = -755661655719465936L;

	public SysLogInfo() {
	}

	private String userName;
	private String logs;
	private Date logDate;
	private Date outDate;
	private int id;
	private String ip;
	private Date updateDate;
	private String startTime;

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
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return this.startTime;
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
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	
}
