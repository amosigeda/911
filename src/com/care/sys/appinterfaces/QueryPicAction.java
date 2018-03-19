package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.List;

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
import com.care.sys.projectimage.domain.ProjectImage;
import com.care.sys.projectimage.domain.logic.ProjectImageFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class QueryPicAction extends BaseAction {
	Log logger = LogFactory.getLog(QueryPicAction.class);
	
	public ActionForward execute(ActionMapping mapping,ActionForm actionForm,
								 HttpServletRequest request,HttpServletResponse response) 
										 throws Exception{
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String href = request.getServletPath();
		String user_id = "";
		String pic_url1 = "";
		String pic_url2 = "";
		String pic_url3 = "";
		String pic_url4 = "";
		String pic_url5 = "";
		String belongProject = "0";
		Date start = new Date();
		List<DataMap> list = null;
		ProjectImage vo = new ProjectImage();
		ProjectImageFacade projectImageFacade = ServiceBean.getInstance().getProjectImageFacade();
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String onLine = "";
			while((onLine=reader.readLine())!=null){
				sb.append(onLine);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
		
			user_id = object.getString("user_id");
			belongProject = object.getString("b_g");
			json.put("request",href);
			
			vo.setCondition("p.id="+belongProject+" and pi.downLoadStatus='1'");
			list = projectImageFacade.getProjectImage(vo);
			if(list.isEmpty()){
				result = Constant.FAIL_CODE; //0为失败
			}else{
				JSONObject jsonObject = new JSONObject();
				pic_url1 = list.get(0).getAt("downloadpath1").toString();
				pic_url2 = list.get(0).getAt("downloadpath2").toString();
				pic_url3 = list.get(0).getAt("downloadpath3").toString();
				pic_url4 = list.get(0).getAt("downloadpath4").toString();
				pic_url5 = list.get(0).getAt("downloadpath5").toString();
				
				if(pic_url1!=null && !"".equals(pic_url1)){
					jsonObject.put("pic_url", pic_url1);
					jsonArray.add(jsonObject);
				}
				if(pic_url2!=null && !"".equals(pic_url2)){
					jsonObject.put("pic_url", pic_url2);
					jsonArray.add(jsonObject);
				}
				if(pic_url3!=null && !"".equals(pic_url3)){
					jsonObject.put("pic_url", pic_url3);
					jsonArray.add(jsonObject);
				}
				if(pic_url4!=null && !"".equals(pic_url4)){
					jsonObject.put("pic_url", pic_url4);
					jsonArray.add(jsonObject);
				}
				if(pic_url5!=null && !"".equals(pic_url5)){
					jsonObject.put("pic_url", pic_url5);
					jsonArray.add(jsonObject);
				}
				json.put("pic_url", jsonArray);
				result = Constant.SUCCESS_CODE;//1为成功
					
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();
			while(cause !=null){
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb.append(resultSb);
			logger.error(e);
			result = Constant.EXCEPTION_CODE; //-1为异常
			json.put(Constant.RESULTCODE, sb.toString());		
		}
		
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		
		return null;
		
	}
}
