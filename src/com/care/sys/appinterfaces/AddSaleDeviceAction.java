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
import com.care.sys.saleinfo.domain.SaleInfo;
import com.care.sys.saleinfo.domain.logic.SaleInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class AddSaleDeviceAction extends BaseAction{
	
	Log logger = LogFactory.getLog(AddSaleDeviceAction.class);
	
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

			SaleInfoFacade sf=mIntances.getSaleInfoFacade();

			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());

			String userName = object.getString("user_name");
			String mac = object.getString("mac");//唯一码
			//String belongProject = object.has("belong_project")?object.getString("belong_project"):"0";
			String belongProject = object.getString("belong_project");
			String imsi=object.getString("imsi");
			String phoneNumber=object.getString("phone_number");
			String phoneModel=object.getString("phone_model");
			String sysVersion=object.getString("sys_version");
			String appVersion=object.getString("app_version");
			String deviceType=object.getString("device_type");
			
            SaleInfo so=new SaleInfo();
			if(!mac.equals("")&&mac!=null){
				so.setCondition("imei ='"+mac+"'");
			   	
				List<DataMap> list = sf.getSaleInfo(so);
				if(list.size()>0){
					result = Constant.FAIL_CODE;
					//json.put("resultCode",Constant.FAIL_CODE );
				}else{
					so.setAddTime(new Date()+"");
					so.setBelongProject(belongProject);
					so.setUserName(userName);
					so.setImei(mac);
					so.setImsi(imsi);
					so.setPhone(phoneNumber);
					so.setPhoneModel(phoneModel);
					so.setSysVersion(sysVersion);
					so.setAppVersion(appVersion);
					so.setDeviceType(deviceType);
					so.setDateTime(new Date());
					int count=sf.insertSaleInfo(so);
					if(count>0){
						result = Constant.SUCCESS_CODE;
						//json.put(Constant.SUCCESS_CODE, sb.toString());
					}
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
