package com.care.sys.deviceactiveinfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class DeviceActiveInfoForm extends PublicFormBean implements Serializable{
	
	public DeviceActiveInfoForm(){
		
	}
	private int id;
	
	private String devicePhone;  //�豸�绰����
	
	private String deviceImei;   //�豸��imei��
	
	private String deviceName;   //�豸���
	
	private String deviceHead;   //�豸ͷ��,0��1Ů
	
	private String deviceSex;    //�豸�Ա�,Ĭ��18
	
	private String deviceAge;    //�豸����
	
	private String deviceHeight; //�豸���
	
	private String deviceWeight; //�豸����
	
	private String deviceDisable; //�豸�Ƿ񼤻�  0δ����,1�Ǽ���
	
	private Date deviceUpdateTime;   //�豸�ϴ�ʱ��

	private String userId;       //�󶨵��û�id
	
	private String listenType;   //�豸����״̬
	
	private Date first;
	  private String clock;
	    private String ring;
	    private String vicrate;
	    private String statu;
	    private String repeatTimes;
		
		public String getClock() {
			return clock;
		}

		public void setClock(String clock) {
			this.clock = clock;
		}

		public String getRing() {
			return ring;
		}

		public void setRing(String ring) {
			this.ring = ring;
		}

		public String getVicrate() {
			return vicrate;
		}

		public void setVicrate(String vicrate) {
			this.vicrate = vicrate;
		}

		public String getStatu() {
			return statu;
		}

		public void setStatu(String statu) {
			this.statu = statu;
		}

		public String getRepeatTimes() {
			return repeatTimes;
		}

		public void setRepeatTimes(String repeatTimes) {
			this.repeatTimes = repeatTimes;
		}
	
	public String getListenType() {
		return listenType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setListenType(String listenType) {
		this.listenType = listenType;
	}

	public Date getDeviceUpdateTime() {
		return deviceUpdateTime;
	}

	public void setDeviceUpdateTime(Date deviceUpdateTime) {
		this.deviceUpdateTime = deviceUpdateTime;
	}

	public String getDeviceDisable() {
		return deviceDisable;
	}

	public void setDeviceDisable(String deviceDisable) {
		this.deviceDisable = deviceDisable;
	}

	public String getDeviceWeight() {
		return deviceWeight;
	}

	public void setDeviceWeight(String deviceWeight) {
		this.deviceWeight = deviceWeight;
	}

	public String getDeviceHeight() {
		return deviceHeight;
	}

	public void setDeviceHeight(String deviceHeight) {
		this.deviceHeight = deviceHeight;
	}

	public String getDeviceAge() {
		return deviceAge;
	}

	public void setDeviceAge(String deviceAge) {
		this.deviceAge = deviceAge;
	}

	public String getDeviceSex() {
		return deviceSex;
	}

	public void setDeviceSex(String deviceSex) {
		this.deviceSex = deviceSex;
	}

	public String getDeviceHead() {
		return deviceHead;
	}

	public void setDeviceHead(String deviceHead) {
		this.deviceHead = deviceHead;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDevicePhone() {
		return devicePhone;
	}

	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}

	public Date getFirst() {
		return first;
	}

	public void setFirst(Date first) {
		this.first = first;
	}

}
