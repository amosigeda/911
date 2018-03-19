package com.care.sys.projectinfo.action;

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
import com.care.sys.companyinfo.domain.CompanyInfo;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.projectinfo.domain.logic.ProjectInfoFacade;
import com.care.sys.projectinfo.form.ProjectInfoForm;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class ProjectInfoAction extends BaseAction {

	Log logger = LogFactory.getLog(ProjectInfoAction.class);

	public ActionForward queryProjectInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String href = request.getServletPath();
		Date start = new Date();
		Result result = new Result();// ���
		PagePys pys = new PagePys();// ҳ������
		DataList list = null; // ����ҳ��List ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();// �����ַ�����
		ProjectInfoFacade info = ServiceBean.getInstance()
				.getProjectInfoFacade();// ����userApp������ȡ��user�ֵ䣩
		ProjectInfo pro = new ProjectInfo();
		
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			ProjectInfoForm form = (ProjectInfoForm) actionForm;
			ProjectInfo vo = new ProjectInfo();
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String companyId = request.getParameter("companyId");
			String userId = request.getParameter("userId");
			String projectId = request.getParameter("projectId");
			
			/* ���û������ֶ� */
			form.setOrderBy("p.add_time");
			form.setSort("1");
			sb.append("1=1");
			
			if(!projectInfoId.equals("0")){
				sb.append(" and p.id in(" + projectInfoId + ")");
			}else{
				if(!"0".equals(companyId) && companyId != null){
					sb.append(" and p.company_id in(" + companyInfoId + ")");
				}
			}
			if (startTime != null && !"".equals(startTime)) {
				sb.append(" and substring(p.add_time,1,10) >= '" + startTime + "'");
			}
			if (endTime != null && !"".equals(endTime)) {				
				sb.append(" and substring(p.add_time,1,10) <= '" + endTime + "'");
			}
			if(companyId != null && !"".equals(companyId)){
				sb.append(" and p.company_id='"+companyId+"'");
			}			
			if(projectId != null && !"".equals(projectId)){
				sb.append(" and p.id ='"+projectId+ "'");
			}
			if(userId != null && !"".equals(userId)){
				sb.append(" and p.company_id='"+userId+"'");
				pro.setCondition("company_id = '"+userId+"'");
			}
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			CompanyInfo ci = new CompanyInfo();
			List<DataMap> coms = ServiceBean.getInstance().getCompanyInfoFacade().getCompanyInfo(ci);
			request.setAttribute("company", coms);
									
			request.setAttribute("fNow_date", startTime);
			request.setAttribute("now_date", endTime);
			request.setAttribute("companyId", companyId);
			request.setAttribute("userId", userId);
			request.setAttribute("projectId", projectId);

			vo.setCondition(sb.toString());

			BeanUtils.copyProperties(vo, form);
			list = info.getProjectInfoListByVo(vo);
			BeanUtils.copyProperties(pys, form);
			pys.setCounts(list.getTotalSize());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /*
													 * ����Ϊ����ҳ�棬���Գ������ת��ϵ
													 * ͳĬ��ҳ��
													 */
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
		return mapping.findForward("queryProjectInfo");
	}

	public ActionForward initInsert(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String company_name = request.getParameter("company_name");
		CompanyInfo vo = new CompanyInfo();
		List<DataMap> Clist = ServiceBean.getInstance().getCompanyInfoFacade()
				.getCompanyInfo(vo);
		request.setAttribute("companyList", CommUtils
				.getPrintSelect(Clist, "companyName", "company_name",
						"id", company_name, 1));


		return mapping.findForward("addProjectInfo");
	}

	public ActionForward insertProjectInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		String companyId = request.getParameter("companyName");
//		String channelIds = request.getParameter("channel_name");

		Result result = new Result();
		try {
			
			ProjectInfoForm form = (ProjectInfoForm) actionForm;
			ProjectInfoFacade facade = ServiceBean.getInstance()
					.getProjectInfoFacade();
			ProjectInfo vo = new ProjectInfo();
			int num = ServiceBean.getInstance().getProjectInfoFacade()
					.getProjectInfoCount(vo) + 1;
			BeanUtils.copyProperties(vo, form);
			vo.setId(num);
			vo.setProjectNo(form.getProjectNo() + Constant.SPLITE + num);
			vo.setCompanyId(Integer.parseInt(companyId));
			vo.setAddTime(new Date());
			vo.setStatus("1");
			facade.insertProjectInfo(vo);

			result.setBackPage(HttpTools.httpServletPath(request, // ����ɹ�����ת��ԭ��ҳ��
					"queryProjectInfo"));
			result.setResultCode("inserts"); // ���ò���Code
			result.setResultType("success"); // ���ò���ɹ�
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request, "initInsert"));
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
	
	public ActionForward getProjectByCompanyId(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		String companyId = request.getParameter("companyId");		
		
		try {
			ProjectInfo vo = new ProjectInfo();
			if(companyId != null && !"".equals(companyId)){
				vo.setCondition("company_id = '" + companyId + "'");
			}
			
			List<DataMap> list = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(vo);
			StringBuffer sb = new StringBuffer();
			if(list.size() > 0){
				for(int i=0; i<list.size(); i++){
					sb.append(list.get(i).getAt("id"));
					sb.append(",");
					sb.append(list.get(i).getAt("project_name"));
					sb.append("&");
				}
			}
		response.getWriter().write(sb.toString());
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return null;
	}
	public ActionForward queryProjectName(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		String projectName = request.getParameter("projectName");		
		
		try {
			ProjectInfo vo = new ProjectInfo();
			if(projectName != null && !"".equals(projectName)){
				vo.setCondition("project_name = '" + projectName + "'");
			}
			
			List<DataMap> list = ServiceBean.getInstance().getProjectInfoFacade().getProjectName(vo);
			StringBuffer sb = new StringBuffer();
			if(list.size() > 0){
				for(int i=0; i<list.size(); i++){
					sb.append(list.get(i).getAt("project_name"));
				}
			}
			 response.getWriter().write(sb.toString());
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return  null;
	}
	
	public ActionForward initUpdate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		ProjectInfo vo = new ProjectInfo();
		vo.setCondition("id='"+id+"'");
		List<DataMap> list = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(vo);
		if (list == null || list.size() == 0) {
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryProjectInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("projectInfo", list.get(0));

		return mapping.findForward("updateProjectInfo");
	}
	public ActionForward initUpdateSwitch(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		ProjectInfo vo = new ProjectInfo();
		vo.setCondition("id='"+id+"'");
		List<DataMap> list = ServiceBean.getInstance().getProjectInfoFacade().getProjectLocationSwitchInfo(vo);
		if (list == null || list.size() == 0) {
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,
					"querySwitchInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("projectInfo", list.get(0));

		return mapping.findForward("updateProjectSwitchInfo");
	}
	
	public ActionForward updateProjectInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		Result result = new Result();
		try {
			ProjectInfoForm form = (ProjectInfoForm) actionForm;
			
			ProjectInfo vo = new ProjectInfo();
			vo.setCondition("id='"+form.getId()+"'");
			BeanUtils.copyProperties(vo, form);
			ServiceBean.getInstance().getProjectInfoFacade().updatePorjectInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryProjectInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryProjectInfo"));
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
	
	public ActionForward updateProjectSwitchInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		Result result = new Result();
		try {
			ProjectInfoForm form = (ProjectInfoForm) actionForm;
			
			ProjectInfo vo = new ProjectInfo();
			String pN=	request.getParameter("projectName");
		String s=	request.getParameter("status");
		String r=	request.getParameter("remark");
		vo.setProjectName(pN);
		vo.setLocationSwitch(s);
			vo.setBeifen(r);
			vo.setCondition("id='"+form.getId()+"'");
			//BeanUtils.copyProperties(vo, form);
			ServiceBean.getInstance().getProjectInfoFacade().updatePorjectSwitchInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,
					"querySwitchInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"querySwitchInfo"));
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
	 public ActionForward projectRoleFuncInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
    	String projectId=request.getParameter("projectId");
    	String factoryCode = request.getParameter("factoryCode");
        request.setAttribute("trees",ServiceBean.getInstance().getProjectInfoFacade().getProjectRoleInfo(projectId));
      // String foleCodeP=request.getParameter("projectId");
        request.setAttribute("roleCodeP",request.getParameter("projectId"));    //��Ӧ���������
        request.setAttribute("factoryCode", request.getParameter("factoryCode"));
        return mapping.findForward("projectRoleFuncInfo");
    }
	 public ActionForward insertProjectRoleFuncInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	        Result result = new Result();
	        try{
	        	
	            String roleCode = request.getParameter("roleCodeP");
	            String funcs = request.getParameter("funcs");
	            String factoryCode = request.getParameter("factoryCode");
	           // ServiceBean.getInstance().getRoleInfoFacade().insertRoleFuncInfo(roleCode,funcs);
	            ServiceBean.getInstance().getProjectInfoFacade().insertRoleProjectFuncInfo(roleCode,funcs);
	            
	            response.getWriter().println("1");
	            return null;
	            /*RoleInfoForm form = (RoleInfoForm)actionForm;
	            validateInsert(form);
	            RoleInfo vo = new RoleInfo();
	            BeanUtils.copyProperties(vo,form);
	            getRoleInfoFacade().insertRoleInfo(vo);
	            result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
	            result.setResultCode("inserts");
	            result.setResultType("success");*/
	        }catch(Exception e){
	            e.printStackTrace();
	            logger.debug(request.getQueryString() + "  " + e);
	            result.setBackPage(HttpTools.httpServletPath(request,"queryProjectInfo"));
	            if(e instanceof SystemException){/*����֪�쳣���н���*/
	                result.setResultCode(((SystemException)e).getErrCode());
	                result.setResultType(((SystemException)e).getErrType());
	            }else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
	                result.setResultCode("noKnownException");
	                result.setResultType("sysRunException");
	            }
	        }finally {
	            request.setAttribute("result", result);
	        }
	        return mapping.findForward("result");
	    }
	 
	 public ActionForward querySwitchInfo(ActionMapping mapping,
				ActionForm actionForm, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			String href = request.getServletPath();
			Date start = new Date();
			Result result = new Result();// ���
			PagePys pys = new PagePys();// ҳ������
			DataList list = null; // ����ҳ��List ��logic itrate��ȡ��
			StringBuffer sb = new StringBuffer();// �����ַ�����
			ProjectInfoFacade info = ServiceBean.getInstance()
					.getProjectInfoFacade();// ����userApp������ȡ��user�ֵ䣩
			ProjectInfo pro = new ProjectInfo();
			
			try {
				
				ProjectInfoForm form = (ProjectInfoForm) actionForm;
				ProjectInfo vo = new ProjectInfo();
				String startTime = request.getParameter("startTime");
				String endTime = request.getParameter("endTime");
				String companyId = request.getParameter("companyId");
				String userId = request.getParameter("userId");
				String projectId = request.getParameter("projectName");
				
				// ���û������ֶ� 
				form.setOrderBy("id");
				form.setSort("1");
				//sb.append("1=1");
				
				/*if(!projectInfoId.equals("0")){
					sb.append(" and p.id in(" + projectInfoId + ")");
				}else{
					if(!"0".equals(companyId) && companyId != null){
						sb.append(" and p.company_id in(" + companyInfoId + ")");
					}
				}*/
				if (startTime != null && !"".equals(startTime)) {
					sb.append(" and substring(p.add_time,1,10) >= '" + startTime + "'");
				}
				if (endTime != null && !"".equals(endTime)) {				
					sb.append(" and substring(p.add_time,1,10) <= '" + endTime + "'");
				}
				if(companyId != null && !"".equals(companyId)){
					sb.append(" and p.company_id='"+companyId+"'");
				}			
				if(projectId != null && !"".equals(projectId)){
					sb.append(" and p.id ='"+projectId+ "'");
				}
				if(userId != null && !"".equals(userId)){
					sb.append(" and p.company_id='"+userId+"'");
					pro.setCondition("company_id = '"+userId+"'");
				}
				List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectLocationSwitchInfo(vo);
				request.setAttribute("project", pros);
				
				
										
				request.setAttribute("fNow_date", startTime);
				request.setAttribute("now_date", endTime);
				request.setAttribute("companyId", companyId);
				request.setAttribute("userId", userId);
				request.setAttribute("projectId", projectId);

				vo.setCondition(sb.toString());

				BeanUtils.copyProperties(vo, form);
				list =  info.getProjectLocationSwitchInfoBvo(vo);
				BeanUtils.copyProperties(pys, form);
				pys.setCounts(list.getTotalSize());

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(request.getQueryString() + "  " + e);
				result.setBackPage(Config.ABOUT_PAGE); /*
														 * ����Ϊ����ҳ�棬���Գ������ת��ϵ
														 * ͳĬ��ҳ��
														 */
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
			return mapping.findForward("querySwitchInfo");
		}


}
