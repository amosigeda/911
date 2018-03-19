package com.care.sys.userinfo.action;

import java.io.IOException;
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
import com.care.sys.companyinfo.domain.CompanyInfo;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.roleinfo.domain.RoleInfo;
import com.care.sys.userinfo.domain.UserInfo;
import com.care.sys.userinfo.domain.logic.UserInfoFacade;
import com.care.sys.userinfo.form.UserInfoForm;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.MD5;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class UserInfoAction extends BaseAction {
	Log logger = LogFactory.getLog(UserInfoAction.class);

	public ActionForward queryUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
		String groupCode = loginUser.getGroupCode();
		String userCode = loginUser.getUserCode();
		String href= request.getServletPath();
		Date start = new Date();		
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		int roleId = 0;
		StringBuffer sb = new StringBuffer();
		UserInfoFacade info = ServiceBean.getInstance().getUserInfoFacade();
		try {
			UserInfoForm form = (UserInfoForm) actionForm;
			UserInfo vo = new UserInfo();
			request.setAttribute("e",form.getUserCode());
			request.setAttribute("e2",form.getUserName());
			
			List<DataMap> rlist = ServiceBean.getInstance().getRoleInfoFacade()
		    	.getRoleInfo(new RoleInfo());
			request.getSession().setAttribute("roleList", rlist);

			if (request.getParameter("roleId") != null
					&& !"".equals(request.getParameter("roleId"))) {
				roleId = Integer.valueOf(request.getParameter("roleId"));
			}
			request.setAttribute("roleList", CommUtils.getPrintRegisterSelect(
					rlist, "roleId", "roleName", "id", roleId));
			if (roleId != 0) {
				if (sb.length() > 0) {
					sb.append(" and ");
				}
				sb.append(" r.id=" + roleId);
			}
			form.setOrderBy("groupCode");
			form.setSort("0");
			vo.setCondition(sb.toString());
			BeanUtils.copyProperties(vo, form);
			/*
			if("1".equals(groupCode)){
				vo.setCondition("userCode ='" + userCode + "'");
			}else if("2".equals(groupCode)){
				vo.setCondition("userCode ='" + userCode +"' OR addUser ='"+ userCode+"'");
			}
			*/
			if("2".equals(groupCode)){
				vo.setCondition("userCode ='"+userCode+"' or addUser ='" +userCode+"'");
			}
			list = info.getDataUserInfoListByVo(vo);
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
		return mapping.findForward("queryUserInfo");
	}

	public ActionForward initInsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String groupCode = request.getParameter("groupCode");
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
		String roleName = loginUser.getGroupCode();
	
		RoleInfo roleinfo = new RoleInfo();
		if("2".equals(roleName)){
			roleinfo.setCondition("roleDesc !='admin' and roleDesc !='manager'");
		}else if("1".equals(roleName)){
			roleinfo.setCondition("roleDesc !='admin'");
		}
	
		List<DataMap> rlist = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(roleinfo);
		
		String s = this.getCompanyAndProject();		
		request.setAttribute("companyList", s);
		request.setAttribute("roleList", CommUtils.getPrintSelect(rlist,"groupCode", "roleName", "id", groupCode, 1));
		return mapping.findForward("insertUserInfo");
	}

	public void validateInsert(UserInfoForm form) throws SystemException {
		UserInfo vo = new UserInfo();
	
		vo.setCondition("userCode = '"+form.getUserCode()+"'");
		if (ServiceBean.getInstance().getUserInfoFacade().getUserInfo(
				vo).size() > 0) {
			throw new SystemException("fail", "userCodeError");
		}
	}

	public ActionForward insertUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		
		try {
			RoleInfo roleInfo = new RoleInfo();
			String roleDesc = "";
			UserInfoForm form = (UserInfoForm) actionForm; 
			validateInsert(form); 
			UserInfo vo = new UserInfo();
			BeanUtils.copyProperties(vo, form); 
			String[] companyIds = request.getParameterValues("companyId");			
			
			if(companyIds != null){
				String companyId = "";
				int l_com = companyIds.length;
				for(int i=0; i<l_com; i++){
					companyId += companyIds[i];
					if(i != l_com-1){
						companyId += ",";
					}
				}
				vo.setCompanyId(companyId);
			}else{
				vo.setCompanyId("0");
			}
			String[] projectIds = request.getParameterValues("projectId");
			
			if(projectIds != null){
				String projectId = "";
				int l_pro = projectIds.length;
				for(int i=0; i<l_pro; i++){
					projectId += projectIds[i];
					if(i != l_pro-1){
						projectId += ",";
					}
				}
				vo.setProjectId(projectId);
			}else{
				vo.setProjectId("0");
			}
			
			
			vo.setCreateDate(new Date());  
			vo.setUpdateDate(new Date());
			vo.setUserName(form.getUserCode());
			vo.setPassWrd1(vo.getPassWrd());
			vo.setPassWrd(MD5.MD5(vo.getPassWrd()));

			roleInfo.setCondition("id ="+form.getGroupCode());
			List<DataMap> list= ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(roleInfo);
			if(!list.isEmpty()){
				roleDesc = list.get(0).getAt("roleDesc").toString();
			}
				
			vo.setCode(roleDesc);		
			ServiceBean.getInstance().getUserInfoFacade()
					.insertUserInfo(vo);  
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("inserts");  
			result.setResultType("success"); 
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"initInsert"));
			if (e instanceof SystemException) {
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { 
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}

	public ActionForward initUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String code = request.getParameter("userCode");
		UserInfo vo = new UserInfo();
		
		vo.setCondition("userCode ='"+code+"'");
		List<DataMap> list = ServiceBean.getInstance().getUserInfoFacade()
				.getUserInfo(vo); 
		if (list == null || list.size() == 0) {
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("userInfo", list.get(0)); 

		String groupCode = request.getParameter("groupCode");
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
		String roleName = loginUser.getGroupCode();
	
		RoleInfo roleinfo = new RoleInfo();
		if("2".equals(roleName)){
			roleinfo.setCondition("roleDesc !='admin' and roleDesc !='manager'");
		}else if("1".equals(roleName)){
			roleinfo.setCondition("roleDesc !='admin'");
		}
	
//		List<DataMap> rlist = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(roleinfo);
		
//		String s = this.getCompanyAndProject();		
//		request.setAttribute("companyList", s);
//		request.setAttribute("roleList", CommUtils.getPrintSelect(rlist,"groupCode", "roleName", "id", groupCode, 1));
		List<DataMap> rlist = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(roleinfo);
		request.setAttribute("roleList", CommUtils.getPrintSelect(rlist,
				"groupCode", "roleName", "id", groupCode, 0));
		String s = this.getCompanyAndProject();		
		request.setAttribute("companyList", s);
		return mapping.findForward("updateUserInfo");
	}

	public void validateUpdate(UserInfoForm form) throws SystemException {
		
	}

	public ActionForward updateUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		Result result = new Result();
		String roleDesc ="";
		int tag = 0;
		try {
			UserInfoForm form = (UserInfoForm) actionForm;
			validateUpdate(form);
			UserInfo vo = new UserInfo();
			RoleInfo roleInfo = new RoleInfo();
			vo.setCondition("id ='"+form.getId()+"'");
			BeanUtils.copyProperties(vo, form); 
			
			String[] companyIds = request.getParameterValues("companyId");			
			
			if(companyIds != null){
				String companyId = "";
				int l_com = companyIds.length;
				for(int i=0; i<l_com; i++){
					companyId += companyIds[i];
					if(i != l_com-1){
						companyId += ",";
					}
				}
				vo.setCompanyId(companyId);
			}else{
				vo.setCompanyId("0");
			}
			String[] projectIds = request.getParameterValues("projectId");
			
			if(projectIds != null){
				String projectId = "";
				int l_pro = projectIds.length;
				for(int i=0; i<l_pro; i++){
					projectId += projectIds[i];
					if(i != l_pro-1){
						projectId += ",";
					}
				}
				vo.setProjectId(projectId);
			}else{
				vo.setProjectId("0");
			}
			roleInfo.setCondition("id ="+form.getGroupCode());
			List<DataMap> list = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(roleInfo);
			if(!list.isEmpty()){
				roleDesc = list.get(0).getAt("roleDesc").toString();
			}
			
			if (request.getParameter("tag") != null
					&& !"".equals(request.getParameter("tag"))) {
				tag = Integer.valueOf(request.getParameter("tag"));
				vo.setTag(tag);
				if (tag == 0) {
					vo.setTag(0);
				} else if (tag == 1) {
					vo.setTag(1);
				}
			}
			vo.setUpdateDate(new Date());
			vo.setPassWrd1(vo.getPassWrd());
			vo.setPassWrd(MD5.MD5(vo.getPassWrd()));
			vo.setCode(roleDesc);
			ServiceBean.getInstance().getUserInfoFacade()
					.updateUserInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			if (e instanceof SystemException) {
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else {
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}

	public ActionForward initUpdatePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] row = HttpTools.requestArray(request, "crow");
		UserInfo vo = new UserInfo();
		vo.setCondition("id ='"+row[0]+"'");
		request.setAttribute("id", row[0]);
		return mapping.findForward("updateUserPwdInfo");
	}

	public ActionForward updateUserPwdInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		Result result = new Result();
		try {
			UserInfoForm form = (UserInfoForm) actionForm;
			validateUpdate(form);
			UserInfo vo = new UserInfo();
			vo.setCondition("id ='"+form.getId()+"'");
			BeanUtils.copyProperties(vo, form);
			vo.setUpdateDate(new Date());
			vo.setPassWrd1(vo.getPassWrd());
			vo.setPassWrd(MD5.MD5(vo.getPassWrd()));
			ServiceBean.getInstance().getUserInfoFacade()
					.updateUserInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			if (e instanceof SystemException) {
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else {
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}

	public ActionForward deleteUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		try {
			String userCode = request.getParameter("userCode");
			UserInfo vo = new UserInfo();
				vo.setCondition("userCode ='"+userCode+"'");  
				ServiceBean.getInstance().getUserInfoFacade()
						.deleteUserInfo(vo); 
			
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("deletes");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			if (e instanceof SystemException) {
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { 
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	
	public ActionForward internalApply(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		RoleInfo ri = new RoleInfo();
		ri.setCondition("roleDesc != 'admin'");
		try {
			List<DataMap> riList = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(ri);
			request.setAttribute("pageList", riList);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward("internalApply");
	}
	
	public ActionForward addInsertUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		UserInfoForm form = (UserInfoForm)actionForm;
		try {
			UserInfo vo = new UserInfo();
			vo.setCondition("phoneNo='"+form.getPhoneNo()+"'");
			vo.setUserCode(form.getUserName());
			vo.setUserName(form.getUserName());
			vo.setGroupCode(form.getGroupCode());
			vo.setApplyStatus("2");
			vo.setIsInApply("1");
			ServiceBean.getInstance().getUserInfoFacade().updateUserInfo(vo);
			
			result.setBackPage(HttpTools.httpServletPath(request,
					"internalApply"));
			result.setResultCode("inserts"); 
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"internalApply"));
			if (e instanceof SystemException) {
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { 
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	
	private String getCompanyAndProject(){
		StringBuffer sb = new StringBuffer();
		List<DataMap> companyList;
		try {
			CompanyInfo company = new CompanyInfo();
			company.setCondition("status='1'");
			companyList = ServiceBean.getInstance().getCompanyInfoFacade().getCompanyInfo(company);
			
			if(companyList.size() > 0){
				int companyListSize = companyList.size();
				ProjectInfo project = new ProjectInfo();
				project.setCondition("status='1'");
				List<DataMap> projectList = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(project);
				for(int i=0; i<companyListSize; i++){
					int companyId = (Integer)companyList.get(i).getAt("id");
					String companyName = (String)companyList.get(i).getAt("company_name");
					sb.append("<tr class=\"tr_11\"><td align=\"left\" width=\"7%\">&nbsp;&nbsp;<input type=\"checkbox\" name=\"companyId\" value=\""+ companyId + "\" />" + companyName + "</td><td align=\"left\" width=\"20%\" colspan=\"3\">");
					if(projectList.size() > 0){					
						int projectListSize = projectList.size();
						for(int j=0; j<projectListSize; j++){
							int proCompanyId = (Integer)projectList.get(j).getAt("company_id");
							int projectId = (Integer)projectList.get(j).getAt("id");
							String projectName = (String)projectList.get(j).getAt("project_name");
							if(companyId == proCompanyId){
								sb.append("<input type=\"checkbox\" name=\"projectId\" value=\"" + projectId + "\" />" + projectName);
							}
						}
											
						sb.append("</td></tr>");
					}
				}
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	public ActionForward getPhoneNoInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String phoneNo = request.getParameter("phoneNo");
		
		List<DataMap> list = null;
		try {
			UserInfo vo = new UserInfo();
			vo.setPhoneNo(phoneNo);
			
			list = ServiceBean.getInstance().getUserInfoFacade().getUserInfo(vo);
			
			if(list.size()==0){
				response.getWriter().write("false");
			}else if(list.size()>0){
				
				String applyStatus = (String)list.get(0).getAt("apply_status");
				if("1".equals(applyStatus.toString())){
					response.getWriter().write("fail");
				}else if("2".equals(applyStatus.toString())){
					response.getWriter().write("fail2");
				}else{
					response.getWriter().write("success");
				}
		}
		}
		catch (Exception e) {
			e.printStackTrace();

		return null;
	}
	
		
	return null;
	}
	
	public ActionForward getUserNamefromUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		String userName = request.getParameter("userName");
		String phoneNo = request.getParameter("phoneNo");
		UserInfo vo = new UserInfo();
		List<DataMap> list = null;
			vo.setCondition("userName ='" + userName + "'");
			list = ServiceBean.getInstance().getUserInfoFacade()
					.getUserInfo(vo);
			if(list.size() != 0){
			try {
				if (!list.isEmpty() && !phoneNo.toString().equals(list.get(0).getAt("phoneNo"))) {
					response.getWriter().write("fail");
				} else {
					response.getWriter().write("success");
				}
			 
			}catch (IOException e) {
				e.printStackTrace();
			}
			}
		return null;
	}
	
	public ActionForward getUserCodeFromUserInfo(ActionMapping mapping,ActionForm actionForm,
												 HttpServletRequest request,HttpServletResponse response){
		List<DataMap> list = null;
		UserInfo vo = new UserInfo();
		String userCode = request.getParameter("userCode");
		UserInfoFacade facade = ServiceBean.getInstance().getUserInfoFacade();
		if(userCode != null && !"".equals(userCode)){
			vo.setCondition("userCode ='"+userCode+"'");
			try {
				list = facade.getUserInfo(vo);
				if(!list.isEmpty()){
					response.getWriter().write("fail");
				}else{
					response.getWriter().write("success");
				}
			} catch (SystemException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}				
		return null;
	}
	
	/*public ActionForward getAllCompany(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		CompanyInfo vo = new CompanyInfo();
		String roleId = request.getParameter("roleId");
		try {
			vo.setCondition("status='1'");
			List<DataMap> list = ServiceBean.getInstance().getCompanyInfoFacade().getCompanyInfo(vo);
			int num = list.size();
			if(num > 0){
				StringBuffer sb = new StringBuffer();
				if(roleId.equals("3")){
					sb.append("<tr class=\"tr_11\" id=\"tr4\"><td align=\"left\" width=\"7%\">&nbsp;&nbsp;客户</td><td align=\"left\" width=\"20%\" colspan=\"2\">");
					sb.append("<select name='companyId' id='companyId'>/n");
					sb.append("<option value='' >请选择");
					for(int i=0; i<num; i++){
						sb.append("<option value='" + list.get(i).getAt("id") + "'>" + list.get(i).getAt("company_name"));						
					}
					sb.append("</select></td><td></td></tr>");
				}else if(roleId.equals("4")){
					sb.append("<tr class=\"tr_11\" id=\"tr4\"><td align=\"left\" width=\"7%\">&nbsp;&nbsp;客户</td><td align=\"left\" width=\"20%\" colspan=\"2\">");
					for(int i=0; i<num; i++){
						sb.append("<input type='checkbox' name='companyId' value='" + list.get(i).getAt("id") + "' />" + list.get(i).getAt("company_name"));
						if(i != 0 && i%5 == 0){
							sb.append("<br/>");
						}
					}
					sb.append("</td><td></td></tr>");
				}
				response.getWriter().write(sb.toString());
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	
	/*public ActionForward getAllProject(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		try{
			ProjectInfo vo = new ProjectInfo();
			vo.setCondition("status=1");
			List<DataMap> list = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(vo);
			int num = list.size();
			if(num > 0){
				StringBuffer sb = new StringBuffer();
				sb.append("<tr class=\"tr_11\" id=\"tr4\"><td align=\"left\" width=\"7%\">&nbsp;&nbsp;项目</td><td align=\"left\" width=\"20%\" colspan=\"2\">");
				for(int i=0; i<num; i++){
					sb.append("<input type='checkbox' name='projectId' value='" + list.get(i).getAt("id") + "' />" + list.get(i).getAt("project_name"));
					if(i != 0 && i%5 == 0){
						sb.append("<br/>");
					}					
				}
				sb.append("</td><td></td></tr>");
				response.getWriter().write(sb.toString());
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}*/
	
}
