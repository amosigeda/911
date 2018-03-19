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

import net.sf.json.JSONArray;
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
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class doFindPasswordAction extends BaseAction {

	Log logger = LogFactory.getLog(doFindPasswordAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		ServiceBean instance = ServiceBean.getInstance();
		JSONObject json = new JSONObject();
		String user_id = "";
		String belongProject = "";
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			String user_phone = object.getString("user_phone");
			String type = object.getString("type");
			belongProject = object.has("belong_project") ? object
					.getString("belong_project") : "1";
			// String user_phone = request.getParameter("user_phone");
			// String type = request.getParameter("type");
			// belongProject = request.getParameter("belong_project");
			AppUserInfoFacade facade = instance.getAppUserInfoFacade();
			AppUserInfo vo = new AppUserInfo();

			vo.setCondition("user_name ='" + user_phone
					+ "' and belong_project ='" + belongProject + "'");
			List<DataMap> count = facade.getAppUserInfo(vo);
			Random random = new Random();
			StringBuffer randNumber = new StringBuffer();
			for (int i = 0; i < 4; i++) {
				String rand = String.valueOf(random.nextInt(10));
				randNumber.append(rand);
			}
			user_id = "0";
			if (type.equals("0")) {// ï¿½ï¿½Ö¤
				if (count.size() > 0) {
					result = Constant.SUCCESS_CODE;
				} else {
					result = Constant.FAIL_CODE;
				}
			} else if (type.equals("1")) {// ï¿½Ş¸ï¿½ï¿½ï¿½ï¿½ï¿½
				if (user_phone.contains("@")) {
					CreateMessage(getServerName(), randNumber.toString(),
							user_phone);
					logger.info(randNumber.toString());
					json.put("randNum", randNumber.toString());
					result = Constant.SUCCESS_CODE;
				} else if (count.size() > 0) {
					user_id = "" + count.get(0).getAt("id");
					String new_password = object.getString("new_password");
					vo.setPassword(new_password);
					int num = facade.updateAppUserInfo(vo);
					if (num > 0) {
						result = Constant.SUCCESS_CODE;
					}
				} else {
					result = Constant.FAIL_CODE;
				}
			}else if(type.equals("2")){

				user_id = "" + count.get(0).getAt("id");
				String new_password = object.getString("new_password");
				vo.setPassword(new_password);
				int num = facade.updateAppUserInfo(vo);
				if (num > 0) {
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
		}
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		insertVisit(href, belongProject, user_id, 0, start, new Date(), json
				.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	private void CreateMessage(String server, String rand, String user_phone)
			throws AddressException {
		String[] fromEmail = server.split(",");
		System.out.println(fromEmail[0] + "," + fromEmail[1]);
		String body = "Ç×°®µÄ"+user_phone+"Ğ¡»ï°é:<br>nbsp;nbsp;ÕâÊÇÄúÔÚÖĞÑ¶×¢²áµÄÑéÖ¤Âë£º"+rand+"£¬ÇëÔÚ60ÃëÄÚÇ°È¥ÑéÖ¤£¡Èô³¬¹ı60ÃëÇëµã»÷ÖØĞÂ·¢ËÍ¡£"; //ÕıÎÄÄÚ
        String subject = "ÖĞÑ¶×¢²áÑéÖ¤ÓÊ¼ş";
		logger.info(fromEmail[0] + fromEmail[1]);
		try {
			// ****************************åˆ›å»ºä¼šè¯***************************************
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.163.com");// å‘ä»¶äººä½¿ç”¨å‘é‚®ä»¶çš„ç”µå­ä¿¡ç®±æœåŠ¡å™¨
			props.put("mail.smtp.auth", "true");
			Session mailsession = Session.getInstance(props); // è·å¾—é»˜è®¤çš„sessionå¯¹è±¡
			mailsession.setDebug(true);

			// *****************************æ„é€ æ¶ˆï¿?**************************************
			MimeMessage msg = new MimeMessage(mailsession);

			InternetAddress from = new InternetAddress(fromEmail[0]);
			msg.setFrom(from);
			InternetAddress to = new InternetAddress(user_phone); // è®¾ç½®æ”¶ä»¶äººåœ°å€å¹¶è§„å®šå…¶ç±»å‹
			msg.setRecipient(Message.RecipientType.TO, to);
			msg.setSentDate(new Date()); // è®¾ç½®å‘ä¿¡æ—¶é—´
			msg.setSubject(subject); // è®¾ç½®ä¸»é¢˜
			msg.setText(body);
			msg.setContent(body, "text/html;charset=UTF-8"); // è®¾ç½® æ­£æ–‡

			Transport transport = mailsession.getTransport("smtp");
			transport.connect("smtp.163.com", fromEmail[0].split("@")[0],
					fromEmail[1]);// å‘é‚®ä»¶äººå¸æˆ·å¯†ç ,æ­¤å¤–æ˜¯æˆ‘çš„å¸æˆ·å¯†ç ï¼Œä½¿ç”¨æ—¶è¯·ä¿®æ”¹ï¿?
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			// *******************************å‘é€æ¶ˆï¿?*******************************
			// msg.writeTo(System.out);//ä¿å­˜æ¶ˆæ¯ å¹¶åœ¨æ§åˆ¶ï¿?æ˜¾ç¤ºæ¶ˆæ¯å¯¹è±¡ä¸­å±æ€§ä¿¡ï¿?
			// System.out.println("é‚®ä»¶å·²æˆåŠŸå‘é€åˆ° " + username);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);

		}
	}

	public String getServerName() throws Exception {
		String mailName = "", mailpass = "";
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader()
				.getResourceAsStream("server.properties"));
		mailName = pros.getProperty("mailname");
		logger.info(mailName);
		mailpass = pros.getProperty("mailpass");
		logger.info(mailpass);
		return mailName + "," + mailpass;
	}
}
