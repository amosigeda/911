package com.care.sys.userlogininfo.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.app.LoginUser;
import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.CommUtils;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.userlogininfo.domain.UserLoginInfo;
import com.care.sys.userlogininfo.domain.logic.UserLoginInfoFacade;
import com.care.sys.userlogininfo.form.UserLoginInfoForm;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class UserLoginInfoAction extends BaseAction {
	
	Log logger = LogFactory.getLog(UserLoginInfoAction.class);
	
	public ActionForward queryUserLoginInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//结果集
		PagePys pys = new PagePys();//页面属性
		DataList list = null; //返回页面List  用logic itrate获取。
		StringBuffer sb = new StringBuffer();//创建字符串容器
		UserLoginInfoFacade info = ServiceBean.getInstance().getUserLoginInfoFacade();//加载userApp工厂（取得user字典）
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			UserLoginInfoForm form = (UserLoginInfoForm) actionForm;
			UserLoginInfo vo = new UserLoginInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String userName = request.getParameter("userName");
			String belongProject = request.getParameter("belongProject");

			/*设置化排序字段*/
            form.setOrderBy("u.id"); 
            form.setSort("1"); 
            if(!projectInfoId.equals("0")){
				sb.append("u.belong_project in(" + projectInfoId + ")");
			}else{
				if(!"0".equals(companyInfoId)){
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
						sb.append("u.belong_project in(" + str + ")");
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
			    sb.append("substring(login_time,1,10) = '" + time1 + "'");
			}
			if(startTime != null && !"".equals(startTime)){				
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(login_time,1,10) >= '"+startTime+"'");
				request.setAttribute("fNow_date", startTime);
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(login_time,1,10) <= '"+endTime+"'");
				request.setAttribute("now_date", endTime);
			}
			if(userName != null && !"".equals(userName)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.user_name like '%"+userName+"%'");
			}	
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("u.belong_project='"+belongProject+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("belongProject", belongProject);
		    request.setAttribute("userName", userName);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getUserLoginInfoListByVo(vo);  
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			/* 设置化排序字段 */ 
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* 这里为管理页面，所以出错后跳转到系统默认页面 */
			if (e instanceof SystemException) { /* 对已知异常进行解析 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 对未知异常进行解析，并全部定义成未知异常 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("queryUserLoginInfo");
	}

}
