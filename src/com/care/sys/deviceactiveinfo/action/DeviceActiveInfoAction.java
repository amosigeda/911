package com.care.sys.deviceactiveinfo.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.app.LoginUser;
import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.CommUtils;
import com.care.common.lang.Constant;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.deviceactiveinfo.form.DeviceActiveInfoForm;
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.safearea.domain.SafeArea;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class DeviceActiveInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(DeviceActiveInfoAction.class);
	
	public ActionForward queryDeviceActiveInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			
			DeviceActiveInfoForm form = (DeviceActiveInfoForm) actionForm;
			DeviceActiveInfo vo = new DeviceActiveInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String deviceImei = request.getParameter("deviceImei");
			String serieNo = request.getParameter("serieNo");
			String phoneNumber = request.getParameter("phoneNumber");
			String belongProject = request.getParameter("belongProject");
			String userName = request.getParameter("userName");
			String userId = request.getParameter("user_id");
			String phoneManageId = request.getParameter("phoneManageId");
			
			/*���û������ֶ�*/
            form.setOrderBy("device_update_time"); 
            form.setSort("1"); 
            
            sb.append("device_disable = '1'");
            if(!projectInfoId.equals("0")){
				sb.append("  and d.belong_project in(" + projectInfoId + ")");
			}else{
				if(!companyInfoId.equals("0")){
					ProjectInfo pro = new ProjectInfo();
					pro.setCondition("company_id in(" + companyInfoId + ")");
					List<DataMap> proList = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
					if(proList.size() > 0){
						int num = proList.size();
						String str = "";
						for(int i=0; i<num; i++){
							Integer id = (Integer)proList.get(i).getAt("id");
							str += String.valueOf(id);
							if(i != num-1){
								str += ",";
							}
						}
						sb.append(" and d.belong_project in(" + str + ")");
					}					
				}
			}
			if(startTime != null && !"".equals(startTime)){
				sb.append(" and substring(device_update_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){				
				sb.append(" and substring(device_update_time,1,10) <= '"+endTime+"'");
			}
			if(deviceImei != null && !"".equals(deviceImei)){				
				sb.append(" and device_imei='"+deviceImei+"'");
			}
			if(serieNo != null && !"".equals(serieNo)){
				sb.append(" and device_imei like '%"+serieNo+"%'");
			}
			if(phoneNumber != null && !"".equals(phoneNumber)){				
				sb.append(" and device_phone like '%"+phoneNumber+"%'");
			}
			if(userId != null && !"".equals(userId)){
				sb.append(" and user_id = '"+userId+"'");
			}
			if(phoneManageId != null && !"".equals(phoneManageId)){
				sb.append(" and ph.phone_manage_id='" + phoneManageId + "'");
			}
			if(belongProject != null && !"".equals(belongProject)){
				vo.setBelongProject("d.belong_project = '"+belongProject+"'");
			}
			
			if(userName != null && !"".equals(userName)){
				vo.setUserName("a.user_name like'%"+userName+"%'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("deviceImei", deviceImei);
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("phoneNumber", phoneNumber);
		    request.setAttribute("belongProject", belongProject);
		    request.setAttribute("userName", userName);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getDeviceActiveInfoListByVo(vo); 
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* ����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ�� */
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("queryDeviceActiveInfo");
	}
	
	public ActionForward queryDeviceActiveCount(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			DeviceActiveInfoForm form = (DeviceActiveInfoForm) actionForm;
			DeviceActiveInfo vo = new DeviceActiveInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");		

			/*���û������ֶ�*/
            form.setOrderBy("device_update_time"); 
            form.setSort("1"); 
          
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(device_update_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(device_update_time,1,10) <= '"+endTime+"'");
			}
			
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getDeviceActiveInfoGroupByTime(vo);
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* ����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ�� */
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("queryDeviceActiveCount");
	}
	
	public ActionForward queryDeviceActiveHistory(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		try {
			DeviceActiveInfoForm form = (DeviceActiveInfoForm) actionForm;
			DeviceActiveInfo vo = new DeviceActiveInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String serieNo = request.getParameter("serieNo");
			String belongProject = request.getParameter("belongProject");
			String bindStatus = request.getParameter("bindStatus");
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
            form.setOrderBy("date_time"); 
            form.setSort("1"); 
          
            if(!projectInfoId.equals("0")){
				sb.append("d.belong_project in(" + projectInfoId + ")");
			}else{
				if(!companyInfoId.equals("0")){
					ProjectInfo pro = new ProjectInfo();
					pro.setCondition("company_id in(" + companyInfoId + ")");
					List<DataMap> proList = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
					if(proList.size() > 0){
						int num = proList.size();
						String str = "";
						for(int i=0; i<num; i++){
							Integer id = (Integer)proList.get(i).getAt("id");
							str += String.valueOf(id);
							if(i != num-1){
								str += ",";
							}
						}
						sb.append("d.belong_project in(" + str + ")");
					}					
				}
			}
			if(startTime != null && !"".equals(startTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10) <= '"+endTime+"'");
			}
			if(serieNo != null && !"".equals(serieNo)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("d.serie_no like '%" + serieNo + "%'");
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("d.belong_project = '" + belongProject + "'");
			}
			if(bindStatus != null && !"".equals(bindStatus)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("d.status ='"+bindStatus+"'");
				request.setAttribute("bindStatus", CommUtils.getBindStatus(
						"bindStatus", Integer.parseInt(bindStatus)));
			}
			
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("belongProject", belongProject);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getDeviceActiveHistoryListByVo(vo);
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);
			if (e instanceof SystemException) {
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else {
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("queryDeviceActiveHistory");
	}
	
	public ActionForward deleteDeviceActive(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		 
		Result result = new Result();
		try{
			
			String userId = request.getParameter("user_id");
			String deviceImei = request.getParameter("device_imei");
			String belongProject = request.getParameter("belong_project");			
			
			ServiceBean instance = ServiceBean.getInstance();
			DeviceActiveInfo vo = new DeviceActiveInfo();
			RelativeCallInfo relativeCallInfo = new RelativeCallInfo();
			SafeArea safeArea = new SafeArea();
			SettingInfo settingInfo = new SettingInfo();
			ShareInfo shareInfo = new ShareInfo();
			
			vo.setDeviceDisable("0");//鎶婄姸鎬佺疆涓�
			vo.setDeviceAge("2015-12-12");
			vo.setDeviceHead("0");
			vo.setDeviceHeight("170");
			vo.setDeviceName("0");
			vo.setDevicePhone("0");
			vo.setDeviceSex("0");
			vo.setDeviceWeight("170");
			vo.setUserId("0");	
			
			vo.setCondition("user_id='"+userId+"' and device_imei='"+deviceImei+"' and belong_project='"+belongProject+"'");		
			instance.getDeviceActiveInfoFacade().updateDeviceActiveInfo(vo);
			
			relativeCallInfo.setCondition("user_id ='"+userId+"' and serie_no ='"+deviceImei+"' and belong_project='"+belongProject+"'");
			instance.getRelativeCallInfoFacade().deleteRelativeCallInfo(relativeCallInfo);
			
			safeArea.setCondition("user_id ='"+userId+"' and seri_no ='"+deviceImei+"' and belong_project='"+belongProject+"'");
			instance.getSafeAreaFacade().deleteSafeArea(safeArea);
			
			settingInfo.setCondition("serie_no ='"+deviceImei+"' and belong_project='"+belongProject+"'");
			instance.getSettingInfoFacade().deleteSettingInfo(settingInfo);
			
			shareInfo.setCondition("user_id ='"+userId+"' and device_imei='"+deviceImei+"' and belong_project='"+belongProject+"'");
			instance.getShareInfoFacade().deleteShareInfo(shareInfo);
			
			String deletePath = request.getSession(true).getServletContext().getRealPath(Constant.DEVICE_SAVE) + deviceImei;
			Constant.deleteFile(deletePath);
			
			PhoneInfo phoneInfo = new PhoneInfo();
			phoneInfo.setStatus("3");   //3琛ㄧず瑙ｇ粦
			phoneInfo.setCondition("serie_no='"+deviceImei+"' and belong_project='"+belongProject+"'");
			instance.getPhoneInfoFacade().updatePhoneInfo(phoneInfo);
			
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setToId(userId);
			msgInfo.setFromId(userId);
			msgInfo.setIsHandler("0"); // 未处理
			msgInfo.setMsgLevel("1"); // 级别较高
			msgInfo.setMsgContent("1@" + deviceImei + "@" + "0");
			msgInfo.setMsgHandlerDate(new Date());
			msgInfo.setBelongProject(belongProject);
			msgInfo.setMsgOccurDate(new Date());
			
			ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);
			
			DeviceActiveInfoFacade facade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
			DeviceActiveInfo da = new DeviceActiveInfo();
			da.setCondition("serie_no='"+deviceImei+"' and belong_project='"+belongProject+"' and status='1' order by id desc");
			List<DataMap> das = facade.getDeviceActiveHistory(da);
			if(das.size() > 0){
				Integer id = (Integer)das.get(0).getAt("id");
				String unbindTime = (String)das.get(0).getAt("unbind_time");
				if(unbindTime == null || unbindTime.equals("")){
					da.setUnbindTime(new Date());
					da.setCondition("id='"+id+"'");
					facade.updateDeviceActiveHistory(da);
				}
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DeviceActiveInfo v = new DeviceActiveInfo();
			v.setDeviceImei(deviceImei);
			v.setBelongProject(belongProject);
			v.setUserId(userId);
			v.setDeviceDisable("0");
			//v.setDeviceUpdateTime(formatter.format(new Date()));
			v.setDeviceUpdateTime(new Date());
			facade.insertDeviceActiveHistory(v);
			result.setBackPage(HttpTools.httpServletPath(request,
			"queryDeviceActiveInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
				"queryDeviceActiveInfo"));
			if (e instanceof SystemException) { /* 对已知异常进行解析 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 对未知异常进行解析，并全部定义成未知异常 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	public ActionForward downloadImg(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Result result = new Result();
		boolean isOnLine = true;
		try {
			String downloadUrl = request.getParameter("download");
			String dir = request.getSession(true).getServletContext()
					.getRealPath("/upload");
			String apkname = CommUtils.getSubStr(downloadUrl,3);
			File f = new File(dir + "/" + apkname);
			if (!f.exists()) {
				throw new SystemException("fail", "noIconPath");
			}

			FileInputStream fin = new FileInputStream(f);

			BufferedInputStream br = new BufferedInputStream(fin);
			byte[] buf = new byte[1024 * 8];
			int len = 0;

			response.reset();
			if (!isOnLine) {
				URL u = new URL("file:///" + dir);
				response.setContentType(u.openConnection().getContentType());
				response.setHeader("Content-Disposition", "inline; filename="
						+ apkname);
				// �ļ���Ӧ�ñ����UTF-8
			} else { // �����ط�ʽ
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition",
						"attachment; filename=" + f.getName());
			}
			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryDeviceActiveInfo"));
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	public ActionForward queryAddFriendsInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		try{
			DeviceActiveInfoForm form = (DeviceActiveInfoForm)actionForm;
			
			DeviceActiveInfo vo = new DeviceActiveInfo(); 	
			vo.setCondition(sb.toString());
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getAddFriends(vo);
			BeanUtils.copyProperties(pys, form);  
			pys.setCounts(list.getTotalSize());
		
			/*���û������ֶ�*/
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ��*/
			if(e instanceof SystemException){/*����֪�쳣���н���*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys); 
			request.setAttribute("result", result);
		}
		return mapping.findForward("queryAddFriendsInfo");
	}
	
	public ActionForward queryCallInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		try{
			DeviceActiveInfoForm form = (DeviceActiveInfoForm)actionForm;
			
			DeviceActiveInfo vo = new DeviceActiveInfo(); 	
			vo.setCondition(sb.toString());
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getCallInfo(vo);
			BeanUtils.copyProperties(pys, form);  
			pys.setCounts(list.getTotalSize());
		
			/*���û������ֶ�*/
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ��*/
			if(e instanceof SystemException){/*����֪�쳣���н���*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys); 
			request.setAttribute("result", result);
		}
		return mapping.findForward("queryCallInfo");
	}
	public ActionForward queryTalkGroup(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		try{
			DeviceActiveInfoForm form = (DeviceActiveInfoForm)actionForm;
			
			DeviceActiveInfo vo = new DeviceActiveInfo(); 	
			vo.setCondition(sb.toString());
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getTalkGroup(vo);
			BeanUtils.copyProperties(pys, form);  
			pys.setCounts(list.getTotalSize());
		
			/*���û������ֶ�*/
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ��*/
			if(e instanceof SystemException){/*����֪�쳣���н���*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys); 
			request.setAttribute("result", result);
		}
		return mapping.findForward("queryTalkGroup");
	}
	
	public ActionForward querySportInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		try{
			DeviceActiveInfoForm form = (DeviceActiveInfoForm)actionForm;
			
			DeviceActiveInfo vo = new DeviceActiveInfo(); 	
			vo.setCondition(sb.toString());
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getSportInfo(vo);
			BeanUtils.copyProperties(pys, form);  
			pys.setCounts(list.getTotalSize());
		
			/*���û������ֶ�*/
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ��*/
			if(e instanceof SystemException){/*����֪�쳣���н���*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys); 
			request.setAttribute("result", result);
		}
		return mapping.findForward("querySportInfo");
	}
	
	public ActionForward queryMediaInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		try{
			DeviceActiveInfoForm form = (DeviceActiveInfoForm)actionForm;
			
			DeviceActiveInfo vo = new DeviceActiveInfo(); 	
			vo.setCondition(sb.toString());
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getMediaInfo(vo);
			BeanUtils.copyProperties(pys, form);  
			pys.setCounts(list.getTotalSize());
		
			/*���û������ֶ�*/
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ��*/
			if(e instanceof SystemException){/*����֪�쳣���н���*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys); 
			request.setAttribute("result", result);
		}
		return mapping.findForward("queryMediaInfo");
	}
}
