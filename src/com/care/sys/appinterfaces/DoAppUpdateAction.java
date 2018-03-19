package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

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
import com.care.sys.checkinfo.domain.CheckInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class DoAppUpdateAction extends BaseAction {

	Log logger = LogFactory.getLog(DoAppUpdateAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			String user_id = object.has("user_id")?object.getString("user_id"):"-1";
			String version_code = object.getString("version_code");//版本号
			String belongProject = object.getString("belong_project");//项目编号
			String packages = object.has("pa")?object.getString("pa"):"-1";//包名
			
			//get   com.example.tabdemo
//			String user_id = request.getParameter("user_id");
//			String version_code = request.getParameter("version_code");
//			String belongProject = request.getParameter("belong_project");
//			String packages = request.getParameter("pa");
			
			
			CheckInfo vo = new CheckInfo();
			if(packages.equals("-1")){   //如果没有传包名
				vo.setCondition("belong_project ='"+belongProject+"' and status = '1' and version_type ='1' order by id desc limit 0,1");
			//查这个项目启用外部的
			}else{
			vo.setCondition("belong_project ='"+belongProject+"' and package_name ='"+packages+"' and status = '1' and version_type ='1' order by id desc limit 0,1");
			//	vo.setCondition("belong_project ='"+belongProject+"' and package_name ='"+packages+"' and status = '1' order by id desc limit 0,1");
			//查这个项目有这个包名启用外部
			}
			
			List<DataMap> apk_voList = ServiceBean.getInstance()
					.getCheckInfoFacade().getCheckInfo(vo);
			if (apk_voList.size() <= 0) {
				result = Constant.FAIL_CODE;
			} else {
				//都为版本类型为外部的
				String up_nei = "1";   //?????			
				String version_code_wai = ""+ apk_voList.get(0).getAt("version_code");   //版本号
				String func_wai = ""+apk_voList.get(0).getAt("function_cap");    //版本说明
				String download_wai = ""+apk_voList.get(0).getAt("download_path");  //下载地址
				String name_wai = ""+apk_voList.get(0).getAt("version_name");    //版本名称
				String up_type_wai = ""+apk_voList.get(0).getAt("up_type"); //上传类型
				
				if(packages.equals("-1")){   //如果这个包为-1
					vo.setCondition("belong_project ='"+belongProject+"' and status = '1' and version_type ='0' order by id desc limit 0,1");
				//就找这个项目内部的版本
				}else{
					vo.setCondition("belong_project ='"+belongProject+"' and package_name ='"+packages+"' and status = '1' and version_type ='0' order by id desc limit 0,1");
					//就找这个项目内部的版本带包名
				}
				apk_voList = ServiceBean.getInstance().getCheckInfoFacade().getCheckInfo(vo);
				String upUser = ""+apk_voList.get(0).getAt("up_user");  //上传用户
				String version_code_nei = ""+ apk_voList.get(0).getAt("version_code");   //版本号
				String func_nei = ""+apk_voList.get(0).getAt("function_cap");    //描述
				String download_nei = ""+apk_voList.get(0).getAt("download_path");  //下载地址
				String name_nei = ""+apk_voList.get(0).getAt("version_name");    //版本名称
				String up_type_nei = ""+apk_voList.get(0).getAt("up_type"); //上传类型
				
				if(!upUser.equals("-1")){   //上传的用户---
					String[] userArr = upUser.split(",");
					int length = userArr.length;
					for(int i=0;i<length;i++){
						String temp = userArr[i];
						if(temp.equals(user_id)){ //看有没有up_user为1
							up_nei = "0";   //如果 有up_nei就为0
							break;
						}
					}					
				}
				
				int temp_wai = Integer.valueOf(version_code_wai);
				int temp_nei = Integer.valueOf(version_code_nei);
				int cur_code = Integer.valueOf(version_code);
				
                if(up_nei.equals("1")){   //如果内部版本为1
                	if(user_id.equals("20") && cur_code <= temp_wai){   //??????
                		json.put("apk_version_code", temp_wai);
    					json.put("apk_version_name", name_wai);
    					json.put("download_url", download_wai);
    					json.put("apk_function_cap", func_wai);
    					json.put("up_type", up_type_wai);
    					result = Constant.SUCCESS_CODE;
                	}else if(cur_code < temp_wai){
                		json.put("apk_version_code", temp_wai);
    					json.put("apk_version_name", name_wai);
    					json.put("download_url", download_wai);
    					json.put("apk_function_cap", func_wai);
    					json.put("up_type", up_type_wai);
    					result = Constant.SUCCESS_CODE;
                	}
                	
				}else if(up_nei.equals("0") && user_id.equals("20")){
					if(temp_wai <= temp_nei && cur_code <= temp_nei){    //如果系统内部大于等于系统外部并且用户传的版本小于等于内部的
						json.put("apk_version_code", temp_nei);
						json.put("apk_version_name", name_nei);
						json.put("download_url", download_nei);
						json.put("apk_function_cap", func_nei);
						json.put("up_type", up_type_nei);
						result = Constant.SUCCESS_CODE;
					}else if(temp_nei <= temp_wai && cur_code <= temp_wai){  //如果服务器上内部版本小于等于服务器上外部版本并且用户的版本小于等于系统外部的
						json.put("apk_version_code", temp_wai);
						json.put("apk_version_name", name_wai);
						json.put("download_url", download_wai);
						json.put("apk_function_cap", func_wai);
						json.put("up_type", up_type_wai);
						result = Constant.SUCCESS_CODE;
					}else{
						result = Constant.FAIL_CODE;
					}
				}else if(up_nei.equals("0")){
					if(temp_wai < temp_nei && cur_code < temp_nei){    //如果系统外部的小于内部的并且用户上传的小于系统内部的
						json.put("apk_version_code", temp_nei);
						json.put("apk_version_name", name_nei);
						json.put("download_url", download_nei);
						json.put("apk_function_cap", func_nei);
						json.put("up_type", up_type_nei);
						result = Constant.SUCCESS_CODE;
					}else if(temp_nei < temp_wai && cur_code < temp_wai){  //如果系统内部的小于外部的并且用户传的小于系统外部的
						json.put("apk_version_code", temp_wai);
						json.put("apk_version_name", name_wai);
						json.put("download_url", download_wai);
						json.put("apk_function_cap", func_wai);
						json.put("up_type", up_type_wai);
						result = Constant.SUCCESS_CODE;
					}
				}else{					
					result = Constant.FAIL_CODE;
				}
			}
			json.put("request", href);
		} catch(Exception e){
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
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());
			
		return null;
	}
}
