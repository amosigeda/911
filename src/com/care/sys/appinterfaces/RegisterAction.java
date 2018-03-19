package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.care.sys.saleinfo.domain.SaleInfo;
import com.care.utils.IPSeeker;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class RegisterAction extends BaseAction{
	
	Log logger = LogFactory.getLog(RegisterAction.class);
	
	@SuppressWarnings("finally")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		ServiceBean mIntances = ServiceBean.getInstance();

		try {
			AppUserInfoFacade mAppUserInfoFacade = mIntances
					.getAppUserInfoFacade();

			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());

			String user_name = object.getString("user_name");
			String belongProject = object.has("belong_project")?object.getString("belong_project"):"0";
			String type = object.has("type") ? object.getString("type") : "1";// 0验证，1注册
			AppUserInfo userInfo = new AppUserInfo();
			if(belongProject.equals("0")){  //动态项目获取
				userInfo.setCondition("user_name='" + user_name +"'");
			}else{
				userInfo.setCondition("user_name='" + user_name
						+ "' and belong_project ='" + belongProject + "'");
			}
			
			List<DataMap> count = mAppUserInfoFacade.getAppUserInfo(userInfo);
			Random random = new Random();
			StringBuffer randNumber = new StringBuffer();
			for (int i = 0; i < 4; i++) {
				String rand = String.valueOf(random.nextInt(10));
				randNumber.append(rand);
			}
			if (type.equals("0")) {
				if (count.size() > 0) {
					result = Constant.SUCCESS_CODE;
				}

			} else if (type.equals("1")) {
				logger.info(user_name);
				if (user_name.contains("@")) {
					 CreateMessage(getServerName(),randNumber.toString(),user_name);
					 logger.info(randNumber.toString());
					 json.put("randNum",randNumber.toString());
					 result = Constant.SUCCESS_CODE;
				}else{

					String user_password = object.getString("user_password");			
					String user_imei = object.has("user_imei")?object.getString("user_imei"):"000000000000000";
//					String user_sdk = object.getString("user_sdk");
					String user_imsi = object.getString("user_imsi");
					String user_phone = object.getString("user_phone");
					String phone_version = object.getString("phone_version");  
					String phone_model = object.getString("phone_model");
					String app_version = object.getString("app_version");
								
					Date date = new Date();			
					
					if(count.size() <= 0){
						userInfo.setHead("0");
						userInfo.setAge("2015-12-12");
						userInfo.setNickName(user_name);
						userInfo.setPassword(user_password);
						userInfo.setCreateTime(date);
						userInfo.setLastLoginTime(date);
						userInfo.setSex("0"); 
						userInfo.setStatus("1");
						userInfo.setUserHeight("160");
						userInfo.setUserName(user_name);
						userInfo.setUserWeight("70");
						userInfo.setBelongProject(belongProject);    //动态项目时,为0
						userInfo.setBindCount("0");
						
						mAppUserInfoFacade.insertAppUserInfo(userInfo);
						
						List<DataMap> mList = mAppUserInfoFacade.getAppUserInfo(userInfo);
						String user_id = ""+mList.get(0).getAt("id");
						json.put("user_id", user_id);
						
						String dir = request.getSession(true).getServletContext().getRealPath("/qqrwy"); //找到文件夹
						String fileName = request.getSession(true).getServletContext().getRealPath("QQWry.Dat"); //找到文件的名字
				 	    IPSeeker ipSeeker = new IPSeeker(fileName,dir);   //ip数据库的地址和要存储的地址  
						String ip = request.getRemoteAddr();
						String province = ipSeeker.getIPLocation(ip).getCountry();
						 
						SaleInfo saleInfo = new SaleInfo();
						saleInfo.setUserName(user_name);
						saleInfo.setImei(user_imei);
						saleInfo.setImsi(user_imsi);
						saleInfo.setPhone(user_phone);
						saleInfo.setPhoneModel(phone_model);
						saleInfo.setSysVersion(phone_version);
						saleInfo.setDateTime(new Date());
						saleInfo.setIp(ip);
						saleInfo.setProvince(province);
						saleInfo.setBelongProject(belongProject);
						saleInfo.setAppVersion(app_version);
						
						ServiceBean.getInstance().getSaleInfoFacade().insertSaleInfo(saleInfo);
						
						insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
						result = Constant.SUCCESS_CODE;
					}
				
				}
			}else if(type.equals("2")){
				String user_password = object.getString("user_password");			
				String user_imei = object.has("user_imei")?object.getString("user_imei"):"000000000000000";
//				String user_sdk = object.getString("user_sdk");
				String user_imsi = object.getString("user_imsi");
				String user_phone = object.getString("user_phone");
				String phone_version = object.getString("phone_version");  
				String phone_model = object.getString("phone_model");
				String app_version = object.getString("app_version");
							
				Date date = new Date();			
				
				if(count.size() <= 0){
					userInfo.setHead("0");
					userInfo.setAge("2015-12-12");
					userInfo.setNickName(user_name);
					userInfo.setPassword(user_password);
					userInfo.setCreateTime(date);
					userInfo.setLastLoginTime(date);
					userInfo.setSex("0"); 
					userInfo.setStatus("1");
					userInfo.setUserHeight("160");
					userInfo.setUserName(user_name);
					userInfo.setUserWeight("70");
					userInfo.setBelongProject(belongProject);
					userInfo.setBindCount("0");
					
					mAppUserInfoFacade.insertAppUserInfo(userInfo);
					
					List<DataMap> mList = mAppUserInfoFacade.getAppUserInfo(userInfo);
					String user_id = ""+mList.get(0).getAt("id");
					json.put("user_id", user_id);
					
					String dir = request.getSession(true).getServletContext().getRealPath("/qqrwy"); //找到文件夹
					String fileName = request.getSession(true).getServletContext().getRealPath("QQWry.Dat"); //找到文件的名字
			 	    IPSeeker ipSeeker = new IPSeeker(fileName,dir);   //ip数据库的地址和要存储的地址  
					String ip = request.getRemoteAddr();
					String province = ipSeeker.getIPLocation(ip).getCountry();
					 
					SaleInfo saleInfo = new SaleInfo();
					saleInfo.setUserName(user_name);
					saleInfo.setImei(user_imei);
					saleInfo.setImsi(user_imsi);
					saleInfo.setPhone(user_phone);
					saleInfo.setPhoneModel(phone_model);
					saleInfo.setSysVersion(phone_version);
					saleInfo.setDateTime(new Date());
					saleInfo.setIp(ip);
					saleInfo.setProvince(province);
					saleInfo.setBelongProject(belongProject);
					saleInfo.setAppVersion(app_version);
					
					ServiceBean.getInstance().getSaleInfoFacade().insertSaleInfo(saleInfo);
					
					insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
					result = Constant.SUCCESS_CODE;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb.append(resultSb);

			logger.error(e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb.toString());
		} finally {
			json.put("request", href);
			json.put(Constant.RESULTCODE, result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
			return null;
		}
	}

	private void CreateMessage(String server,String rand,String user_name)throws AddressException {   
        String[] fromEmail=server.split(",");
        String body = "亲爱的"+user_name+"小伙伴:<br>nbsp;nbsp;这是您在中讯注册的验证码："+rand+"，请在60秒内前去验证！若超过60秒请点击重新发送。"; //正文内
        String subject = "中讯注册验证邮件";
        logger.info(fromEmail[0]+fromEmail[1]);
        try {
            //****************************创建会话***************************************
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.163.com");//发件人使用发邮件的电子信箱服务器
            props.put("mail.smtp.auth","true");
            Session mailsession = Session.getInstance(props); //获得默认的session对象
            mailsession.setDebug(true);
            
             //*****************************构造消息***************************************
            MimeMessage msg = new MimeMessage(mailsession);

            InternetAddress from=new InternetAddress(fromEmail[0]);
            msg.setFrom(from);
            InternetAddress to=new InternetAddress(user_name); //设置收件人地址并规定其类型
            msg.setRecipient(Message.RecipientType.TO,to);
            msg.setSentDate(new Date()); //设置发信时间
            msg.setSubject(subject); //设置主题
            msg.setText(body);
            msg.setContent(body,"text/html;charset=UTF-8"); //设置 正文
          
            Transport transport=mailsession.getTransport("smtp");
            transport.connect("smtp.163.com",fromEmail[0].split("@")[0],fromEmail[1]);//发邮件人帐户密码,此外是我的帐户密码，使用时请修改。
            transport.sendMessage(msg,msg.getAllRecipients());
            transport.close();
            //*******************************发送消息********************************
//            msg.writeTo(System.out);//保存消息 并在控制台 显示消息对象中属性信息
//            System.out.println("邮件已成功发送到 " + username);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
         
        }
}

	public String getServerName() throws Exception {
		String mailName = "",mailpass = "";
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
		mailName = pros.getProperty("mailname");
		logger.info(mailName);
		mailpass = pros.getProperty("mailpass");
		logger.info(mailpass);
		return mailName+","+mailpass;
	}
}
