package com.care.sys.deviceLogin.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.care.sys.deviceLogin.domain.DeviceLogin;
import com.care.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.care.sys.deviceLogin.form.DeviceLoginForm;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class DeviceLoginAction extends BaseAction{

	Log logger = LogFactory.getLog(DeviceLoginAction.class);
	
	public ActionForward queryDeviceLogin(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer("");
		DeviceLoginFacade deviceLogin = ServiceBean.getInstance().getDeviceLoginFacade();
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			DeviceLoginForm form = (DeviceLoginForm)actionForm;
			DeviceLogin vo = new DeviceLogin();
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String serieNo = request.getParameter("serieNo");
			String belongProject = request.getParameter("belongProject");
			String status = request.getParameter("status");
			String loginNum1 = request.getParameter("loginNum1");
			String loginNum2 = request.getParameter("loginNum2");
			
			
			if(!projectInfoId.equals("0")){
				sb.append("belong_project in(" + projectInfoId + ")");
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
						sb.append("belong_project in(" + str + ")");
					}					
				}
			}
			if(startTime == null && endTime == null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String time1 = format.format(new Date());
				
				request.setAttribute("fNow_date", time1);
			    request.setAttribute("now_date", time1);
			    if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10) = '" + time1 + "'");
			}
			if(startTime != null && !"".equals(startTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10) >= '"+startTime+"'");
				request.setAttribute("fNow_date", startTime);
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10) <= '"+endTime+"'");
				request.setAttribute("now_date", endTime);
			}
			
			if(serieNo != null && !"".equals(serieNo)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("device_imei like'%"+serieNo+"%'");
			}
			if(status != null && !"".equals(status)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("device_status ='"+status+"'");
				request.setAttribute("status", CommUtils.getStatusSelect(
						"status", Integer.parseInt(status)));
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("belong_project='"+belongProject+"'");
			}
			
			/*
			 * 正则表达式判断登录次数输入的是否为数字
			 * ZST@20151103
			 */
			try{
					Integer num1 = Integer.valueOf(loginNum1);
					Integer num2 = Integer.valueOf(loginNum2);
			}catch(Exception e){
				System.out.println("登录次数必须为整数。");
			}
						
			if(loginNum1 != null && !"".equals(loginNum1)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("s.count_num >='"+loginNum1+"'");
			}
			if(loginNum2 != null && !"".equals(loginNum2)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("s.count_num <='"+loginNum2+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			form.setOrderBy("id"); 
            form.setSort("1"); 
            
            BeanUtils.copyProperties(vo,form);			
            vo.setCondition(sb.toString());
         	list = deviceLogin.getDataDeviceLoginListByVo(vo); 
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
            		    
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("belongProject", belongProject);
		    request.setAttribute("loginNum1", loginNum1);
		    request.setAttribute("loginNum2", loginNum2);
		}catch (Exception e) { 
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
		return mapping.findForward("queryDeviceLogin");
	}
	
}
