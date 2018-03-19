package com.care.common.config;

import org.springframework.beans.factory.BeanFactory;

import com.care.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.care.sys.channelinfo.domain.logic.ChannelInfoFacade;
import com.care.sys.checkinfo.domain.logic.CheckInfoFacade;
import com.care.sys.companyinfo.domain.logic.CompanyInfoFacade;
import com.care.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.devicephoneinfo.domain.logic.DevicePhoneInfoFacade;
import com.care.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.care.sys.dynamicInfo.domain.logic.DynamicInfoFacade;
import com.care.sys.feedbackinfo.domain.logic.FeedBackInfoFacade;
import com.care.sys.funcinfo.domain.logic.FuncInfoFacade;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.care.sys.monitorinfo.domain.logic.MonitorInfoFacade;
import com.care.sys.msginfo.domain.logic.MsgInfoFacade;
import com.care.sys.phonecountryinfo.domain.logic.PhoneCountryInfoFacade;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.care.sys.playiteminfo.domain.logic.PlayItemInfoFacade;
import com.care.sys.projectimage.domain.logic.ProjectImageFacade;
import com.care.sys.projectinfo.domain.logic.ProjectInfoFacade;
import com.care.sys.relativecallinfo.domain.logic.RelativeCallInfoFacade;
import com.care.sys.rolefuncinfo.domain.logic.RoleFuncInfoFacade;
import com.care.sys.roleinfo.domain.logic.RoleInfoFacade;
import com.care.sys.safearea.domain.logic.SafeAreaFacade;
import com.care.sys.saleinfo.domain.logic.SaleInfoFacade;
import com.care.sys.settinginfo.domain.logic.SettingInfoFacade;
import com.care.sys.shareinfo.domain.logic.ShareInfoFacade;
import com.care.sys.sysloginfo.domain.logic.SysLogInfoFacade;
import com.care.sys.sysregisterinfo.domain.logic.SysRegisterInfoFacade;
import com.care.sys.userinfo.domain.logic.UserInfoFacade;
import com.care.sys.userlogininfo.domain.logic.UserLoginInfoFacade;
import com.godoing.rose.spring.SpringUtils;

public class ServiceBean {

	static BeanFactory factory;
	private static ServiceBean instance;
	
	static UserInfoFacade userInfoFacade;
	static FuncInfoFacade  funcInfoFacade;
    static RoleInfoFacade roleInfoFacade;
    static RoleFuncInfoFacade roleFuncInfoFacade;
    static SysLogInfoFacade sysLogInfoFacade;
    static MonitorInfoFacade monitorInfoFacade;
	static PhoneInfoFacade phoneInfoFacade;
	static PlayItemInfoFacade playItemInfoFacade;
	static SettingInfoFacade settingInfoFacade;
	static RelativeCallInfoFacade relativeCallInfoFacade;
	static AppUserInfoFacade appUserInfoFacade;
	static LocationInfoFacade locationInfoFacade;
	static DeviceActiveInfoFacade deviceActiveInfoFacade;
	static SafeAreaFacade safeAreaFacade;
	static FeedBackInfoFacade feedbackInfoFacade;
	static CheckInfoFacade checkInfoFacade;
	static ShareInfoFacade shareInfoFacade;
	static MsgInfoFacade msgInfoFacade;
	static DirectiveInfoFacade directiveInfoFacade;
	static CompanyInfoFacade companyInfoFacade;
    static ChannelInfoFacade channelInfoFacade;
    static ProjectInfoFacade projectInfoFacade;
    static SaleInfoFacade saleInfoFacade;
    static UserLoginInfoFacade userLoginInfoFacade;
    static DeviceLoginFacade deviceLoginFacade;
    static SysRegisterInfoFacade registerInfoFacade;
    static DevicePhoneInfoFacade devicePhoneInfoFacade;
    static PhoneCountryInfoFacade phoneCountryInfoFacade;
    static ProjectImageFacade projectImageFacade;
    static DynamicInfoFacade dynamicInfoFacade;
	public static ServiceBean getInstance() {
		if (instance == null) {
			instance = new ServiceBean();
			factory = SpringUtils.getBeanFactory(); 
		}		
		userInfoFacade = (UserInfoFacade)factory.getBean("userInfoFacade");
		funcInfoFacade = (FuncInfoFacade)factory.getBean("funcInfoFacade");
		roleInfoFacade = (RoleInfoFacade)factory.getBean("roleInfoFacade");
		roleFuncInfoFacade = (RoleFuncInfoFacade)factory.getBean("roleFuncInfoFacade");
		sysLogInfoFacade = (SysLogInfoFacade)factory.getBean("sysLogInfoFacade");
		monitorInfoFacade = (MonitorInfoFacade)factory.getBean("monitorInfoFacade");
		phoneInfoFacade = (PhoneInfoFacade)factory.getBean("phoneInfoFacade");
		playItemInfoFacade = (PlayItemInfoFacade)factory.getBean("playItemInfoFacade");
		settingInfoFacade = (SettingInfoFacade)factory.getBean("settingInfoFacade");
		relativeCallInfoFacade = (RelativeCallInfoFacade)factory.getBean("relativeCallInfoFacade");
		appUserInfoFacade = (AppUserInfoFacade)factory.getBean("appUserInfoFacade");
		locationInfoFacade = (LocationInfoFacade)factory.getBean("locationInfoFacade");
		deviceActiveInfoFacade = (DeviceActiveInfoFacade)factory.getBean("deviceActiveInfoFacade");
		safeAreaFacade = (SafeAreaFacade)factory.getBean("safeAreaFacade");
		feedbackInfoFacade = (FeedBackInfoFacade)factory.getBean("feedBackInfoFacade");
		checkInfoFacade = (CheckInfoFacade)factory.getBean("checkInfoFacade");
		shareInfoFacade = (ShareInfoFacade)factory.getBean("shareInfoFacade");
		msgInfoFacade = (MsgInfoFacade)factory.getBean("msgInfoFacade");
		directiveInfoFacade = (DirectiveInfoFacade)factory.getBean("directiveInfoFacade");
		monitorInfoFacade = (MonitorInfoFacade)factory.getBean("monitorInfoFacade");
		companyInfoFacade = (CompanyInfoFacade)factory.getBean("companyInfoFacade");
		channelInfoFacade = (ChannelInfoFacade)factory.getBean("channelInfoFacade");
		projectInfoFacade = (ProjectInfoFacade)factory.getBean("projectInfoFacade");
		saleInfoFacade = (SaleInfoFacade)factory.getBean("saleInfoFacade");
		userLoginInfoFacade = (UserLoginInfoFacade)factory.getBean("userLoginInfoFacade");
		deviceLoginFacade = (DeviceLoginFacade)factory.getBean("deviceLoginFacade");
		registerInfoFacade = (SysRegisterInfoFacade)factory.getBean("registerInfoFacade");
		devicePhoneInfoFacade = (DevicePhoneInfoFacade) factory.getBean("devicePhoneInfoFacade");
		phoneCountryInfoFacade = (PhoneCountryInfoFacade)factory.getBean("phoneCountryInfoFacade");
		projectImageFacade = (ProjectImageFacade)factory.getBean("projectImageFacade");
		dynamicInfoFacade=(DynamicInfoFacade)factory.getBean("dynamicInfoFacade");
				
		return instance;
	}
	public UserInfoFacade getUserInfoFacade() {
		return userInfoFacade;
	}
	public FuncInfoFacade getFuncInfoFacade() {
		return funcInfoFacade;
	}
	public RoleFuncInfoFacade getRoleFuncInfoFacade() {
		return roleFuncInfoFacade;
	}
	public RoleInfoFacade getRoleInfoFacade() {
		return roleInfoFacade;
	}
	public SysLogInfoFacade getSysLogInfoFacade() {
		return sysLogInfoFacade;
	}
	public MonitorInfoFacade getMonitorInfoFacade() {
		return monitorInfoFacade;
	}
	public PhoneInfoFacade getPhoneInfoFacade() {
		return phoneInfoFacade;
	}
	public PlayItemInfoFacade getPlayItemInfoFacade() {
		return playItemInfoFacade;
	}
	public SettingInfoFacade getSettingInfoFacade() {
		return settingInfoFacade;
	}
	public RelativeCallInfoFacade getRelativeCallInfoFacade() {
		return relativeCallInfoFacade;
	}	
	public AppUserInfoFacade getAppUserInfoFacade() {
		return appUserInfoFacade;
	}
	public LocationInfoFacade getLocationInfoFacade() {
		return locationInfoFacade;
	}
	public DeviceActiveInfoFacade getDeviceActiveInfoFacade() {
		return deviceActiveInfoFacade;
	}
	public SafeAreaFacade getSafeAreaFacade() {
		return safeAreaFacade;
	}
	public FeedBackInfoFacade getFeedBackInfoFacade() {
		return feedbackInfoFacade;
	}
	public ShareInfoFacade getShareInfoFacade(){
		return shareInfoFacade;
	}
	public MsgInfoFacade getMsgInfoFacade(){
		return msgInfoFacade;
	}
	public FeedBackInfoFacade getFeedbackInfoFacade() {
		return feedbackInfoFacade;
	}
	public CheckInfoFacade getCheckInfoFacade() {
		return checkInfoFacade;
	}
	public DirectiveInfoFacade getDirectiveInfoFacade() {
		return directiveInfoFacade;
	}
	public CompanyInfoFacade getCompanyInfoFacade() {
		return companyInfoFacade;
	}
	public ChannelInfoFacade getChannelInfoFacade() {
		return channelInfoFacade;
	}
	public ProjectInfoFacade getProjectInfoFacade() {
		return projectInfoFacade;
	}
	public static SaleInfoFacade getSaleInfoFacade() {
		return saleInfoFacade;
	}
	public UserLoginInfoFacade getUserLoginInfoFacade() {
		return userLoginInfoFacade;
	}
	public DeviceLoginFacade getDeviceLoginFacade() {
		return deviceLoginFacade;
	}
	
	public SysRegisterInfoFacade getRegisterInfoFacade(){
		return registerInfoFacade;
	}
	
	public DevicePhoneInfoFacade getDevicePhoneInfoFacade(){
		return devicePhoneInfoFacade;
	}
	
	public PhoneCountryInfoFacade getPhoneCountryInfoFacade(){
		return phoneCountryInfoFacade;
	}
	
	public ProjectImageFacade getProjectImageFacade(){
		return projectImageFacade;
	}
	
	public DynamicInfoFacade getDynamicInfoFacade(){
		return dynamicInfoFacade;
	}
	
}
