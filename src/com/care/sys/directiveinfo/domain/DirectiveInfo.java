package com.care.sys.directiveinfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class DirectiveInfo extends PublicVoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 462928891321167542L;
	private Integer id;
	private String serie_no;
	private String distrub;
	private String mdistime;
	private String tdistime;
	private String wdistime;
	private String thdistime;
	private String fdistime;
	private String sdistime;
	private String sudistime;
	private String lowelectricity;
	private String isLow;
	private String clock;
	private String sleep;
	private String distrubChange;
	private String alarmChange;
	private String sleepChange;
	
    private String belongProject;
	
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public String getDistrubChange() {
		return distrubChange;
	}
	public void setDistrubChange(String distrubChange) {
		this.distrubChange = distrubChange;
	}
	public String getAlarmChange() {
		return alarmChange;
	}
	public void setAlarmChange(String alarmChange) {
		this.alarmChange = alarmChange;
	}
	public String getSleepChange() {
		return sleepChange;
	}
	public void setSleepChange(String sleepChange) {
		this.sleepChange = sleepChange;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerie_no() {
		return serie_no;
	}
	public void setSerie_no(String serie_no) {
		this.serie_no = serie_no;
	}
	public String getDistrub() {
		return distrub;
	}
	public void setDistrub(String distrub) {
		this.distrub = distrub;
	}
	public String getMdistime() {
		return mdistime;
	}
	public void setMdistime(String mdistime) {
		this.mdistime = mdistime;
	}
	public String getTdistime() {
		return tdistime;
	}
	public void setTdistime(String tdistime) {
		this.tdistime = tdistime;
	}
	public String getWdistime() {
		return wdistime;
	}
	public void setWdistime(String wdistime) {
		this.wdistime = wdistime;
	}
	public String getThdistime() {
		return thdistime;
	}
	public void setThdistime(String thdistime) {
		this.thdistime = thdistime;
	}
	public String getFdistime() {
		return fdistime;
	}
	public void setFdistime(String fdistime) {
		this.fdistime = fdistime;
	}
	public String getSdistime() {
		return sdistime;
	}
	public void setSdistime(String sdistime) {
		this.sdistime = sdistime;
	}
	public String getSudistime() {
		return sudistime;
	}
	public void setSudistime(String sudistime) {
		this.sudistime = sudistime;
	}
	public String getLowelectricity() {
		return lowelectricity;
	}
	public void setLowelectricity(String lowelectricity) {
		this.lowelectricity = lowelectricity;
	}
	public String getIsLow() {
		return isLow;
	}
	public void setIsLow(String isLow) {
		this.isLow = isLow;
	}
	public String getClock() {
		return clock;
	}
	public void setClock(String clock) {
		this.clock = clock;
	}
	public String getSleep() {
		return sleep;
	}
	public void setSleep(String sleep) {
		this.sleep = sleep;
	}
	
	
}
