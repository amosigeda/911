package com.care.sys.locationinfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class LocationInfo extends PublicVoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;









	public LocationInfo(){
		
	}
	
	private Integer id;
	private String serieNo;
	private Integer battery;//����
	private String longitude;//����
	private String latitude;//γ��
	private String locationType;
	private Integer accuracy;//��ȷ��
	private Date uploadTime;
	private String changeLongitude;//ת����ľ���
	private String changeLatitude;//ת�����γ��
    private String belongProject;
	private String showType;  //显示类型,0表示不显示，1表示显示
	private Date endTime;     //超过范围后的时间
	private String fall;
	private Integer stepNo;
	private Integer rollNo;
	private Integer tAlarm;
	private Integer tStatus;
	private String cause;
	private String map;
	private String address;
	private Integer radius;
	private String imei;
	private String driverStatus;
	private String driverCount;
	private String deviceId;
	private String altitude;
	private String satellite;
	private String speed;
	private String cellId;
	private String lac;//位置区域码
	private String type;
	private String isGps;
	private String createDate;
	private String angle;
	private String groupsId;
	private String device;
	private String spstatus;
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getSpstatus() {
		return spstatus;
	}
	public void setSpstatus(String spstatus) {
		this.spstatus = spstatus;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Integer getStepNo() {
		return stepNo;
	}
	public void setStepNo(Integer stepNo) {
		this.stepNo = stepNo;
	}
	public Integer getRollNo() {
		return rollNo;
	}
	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}
	public Integer gettAlarm() {
		return tAlarm;
	}
	public void settAlarm(Integer tAlarm) {
		this.tAlarm = tAlarm;
	}
	public Integer gettStatus() {
		return tStatus;
	}
	public void settStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public String getChangeLongitude() {
		return changeLongitude;
	}
	public void setChangeLongitude(String changeLongitude) {
		this.changeLongitude = changeLongitude;
	}
	public String getChangeLatitude() {
		return changeLatitude;
	}
	public void setChangeLatitude(String changeLatitude) {
		this.changeLatitude = changeLatitude;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerieNo() {
		return serieNo;
	}
	public void setSerieNo(String serieNo) {
		this.serieNo = serieNo;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getFall(){
		return this.fall;
	}
	public void setFall(String fall) {
		// TODO Auto-generated method stub
		this.fall = fall;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getRadius() {
		return radius;
	}
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
	public String getDriverStatus() {
		return driverStatus;
	}
	public void setDriverStatus(String driverStatus) {
		this.driverStatus = driverStatus;
	}
	public String getDriverCount() {
		return driverCount;
	}
	public void setDriverCount(String driverCount) {
		this.driverCount = driverCount;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getAltitude() {
		return altitude;
	}
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	public String getSatellite() {
		return satellite;
	}
	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getLac() {
		return lac;
	}
	public void setLac(String lac) {
		this.lac = lac;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsGps() {
		return isGps;
	}
	public void setIsGps(String isGps) {
		this.isGps = isGps;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAngle() {
		return angle;
	}
	public void setAngle(String angle) {
		this.angle = angle;
	}
	public String getGroupsId() {
		return groupsId;
	}
	public void setGroupsId(String groupsId) {
		this.groupsId = groupsId;
	}
	

}
