package com.care.sys.interfaces;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.care.sys.settinginfo.domain.logic.SettingInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class DeviceFallAction extends BaseAction {

	Log logger = LogFactory.getLog(DeviceFallAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");		
		Date start = new Date();
		JSONObject json = new JSONObject();
		String serieNo = request.getParameter("serie_no");
		String fallTag = request.getParameter("fall");
		String belongProject = request.getParameter("b_g");
		SettingInfo vo = new SettingInfo();
		StringBuffer sb = new StringBuffer();
		
		if(fallTag == null){
			fallTag = "0";  //?????,???????
		}
		if(serieNo != null && !"".equals(serieNo)){
			sb.append("serie_no = '"+serieNo+"'");
		}
		if(belongProject != null && !"".equals(belongProject)){
			if(sb.length() > 0){
				sb.append(" and ");
			}
			sb.append("belong_project='"+belongProject+"'");
			
			vo.setCondition(sb.toString());
			SettingInfoFacade facade = ServiceBean.getInstance().getSettingInfoFacade();
			try{
			    List<DataMap> list = facade.getSettingInfo(vo);
				if(list.size() > 0) {
					vo.setFall(fallTag);
					facade.updateSettingInfo(vo);
				   json.put("resultCode", 1);
				}else {
					vo.setFall(fallTag);
					vo.setSerieNo(serieNo);
					vo.setLight("0");
					vo.setHeart("0");
					vo.setBelongProject(belongProject);
					facade.insertSettingInfo(vo);
					json.put("resultCode", 1);
				}
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei ='"+serieNo+"' and belong_project='"+belongProject+"'");
				List<DataMap> deviceList = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
				String user_id = "-1";
				boolean isFall = false;   //?????????
				if(deviceList.size() > 0){
					user_id = ""+deviceList.get(0).getAt("user_id");
				}
				if(!user_id.equals("-1")){
					String msgContent = "6@" + serieNo + "@" + "0"; //???????
					StringBuffer condition = new StringBuffer();
					condition.append("(msg_content ='5@"+serieNo+"@0'");
					condition.append(" or ");
					condition.append("msg_content ='6@"+serieNo+"@0')");
					MsgInfo msgInfo = new MsgInfo();
					
					msgInfo.setToId(user_id);
					msgInfo.setFromId(user_id);
					msgInfo.setIsHandler("0"); // ??????
					msgInfo.setMsgLevel("1"); // ??????
					msgInfo.setMsgHandlerDate(new Date());
					msgInfo.setBelongProject(belongProject);
					if(fallTag.equals("1")){   //????????			
						msgContent = "5@" + serieNo + "@" + "0"; 
					}
					msgInfo.setCondition(condition.toString()+" and to_id ='"+user_id+"' and is_handler ='0' ");   //????????,?????????????????????,??????????????,????????
					msgInfo.setOrderBy("id");
					msgInfo.setSort("1");
					msgInfo.setFrom(0);
					msgInfo.setPageSize(1);
					msgInfo.setMsgOccurDate(new Date());
					List<DataMap> temp = ServiceBean.getInstance().getMsgInfoFacade().getMsgInfo(msgInfo);
					if(temp.size() > 0){
						String tempContent = ""+temp.get(0).getAt("msg_content");   //??????????????
						if(!msgContent.equals(tempContent)){
							isFall = true;
						}			
					}
					msgInfo.setMsgContent(msgContent);
					if(isFall){
						ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);
					}			
				}
				
			}catch(Exception e){
				e.printStackTrace();
				json.put("resultCode", -1);
			}	
		}else{
			json.put("resultCode", 0);
		}
		String href= request.getServletPath();
		insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		
		return null;
	}

}
