package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

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
import com.care.sys.feedbackinfo.domain.FeedBackInfo;
import com.godoing.rose.log.LogFactory;

public class FeedBackAction extends BaseAction{

	Log logger = LogFactory.getLog(FeedBackAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		String user_id = "";
		String belongProject = "";
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			user_id = object.getString("user_id");
			String user_feedback_content = object.getString("user_feedback_content");
			belongProject = object.getString("belong_project");
			
			if(user_id != null && !"".equals(user_id)){
				user_feedback_content = Constant.transCodingToUtf(user_feedback_content);
				
				FeedBackInfo feedBackInfo = new FeedBackInfo();
				feedBackInfo.setContactWay(user_id);
				feedBackInfo.setDateTime(new Date());
				feedBackInfo.setFeedBackContent(user_feedback_content);
				feedBackInfo.setBelongProject(belongProject);
				
				int count = ServiceBean.getInstance().getFeedBackInfoFacade().insertFeedBackInfo(feedBackInfo);
				if(count > 0){
					result = Constant.SUCCESS_CODE;
				}else{		
					result = Constant.FAIL_CODE;
				}
			}
			json.put("request", href);
			
		}catch(Exception e){
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
		json.put(Constant.RESULTCODE, result);
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());	
		return null;
	}
}
