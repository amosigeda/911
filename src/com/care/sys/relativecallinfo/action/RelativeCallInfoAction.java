package com.care.sys.relativecallinfo.action;

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
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.relativecallinfo.domain.logic.RelativeCallInfoFacade;
import com.care.sys.relativecallinfo.form.RelativeCallInfoForm;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class RelativeCallInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(RelativeCallInfoAction.class);
	
	public ActionForward queryRelativeCallInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		RelativeCallInfoFacade info = ServiceBean.getInstance().getRelativeCallInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			RelativeCallInfoForm form = (RelativeCallInfoForm) actionForm;
			RelativeCallInfo vo = new RelativeCallInfo(); 
			
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");
			String serieNo = request.getParameter("serieNo");
			String phoneNumber   = request.getParameter("phoneNumber");	
			String userName = request.getParameter("userName");
			String projectId = request.getParameter("projectId");

            form.setOrderBy("id"); 
            form.setSort("1"); 

            if(!projectInfoId.equals("0")){
				sb.append("r.belong_project in(" + projectInfoId + ")");
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
						sb.append("r.belong_project in(" + str + ")");
					}					
				}
			}
			if(serieNo != null && !"".equals(serieNo)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("serie_no like '%"+serieNo+"%'");
			}
			if(phoneNumber != null && !"".equals(phoneNumber)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("phone_number like '%"+phoneNumber+"%'");
			}
			if(userName != null && !"".equals(userName)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.user_name like '%" +userName+"%'");
			}

			if(startTime != null && !"".equals(startTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(r.add_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(r.add_time,1,10) <= '"+endTime+"'");
			}
			if(projectId != null && !"".equals(projectId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("p.id ='"+projectId+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
			request.setAttribute("serieNo", serieNo);
		    request.setAttribute("phoneNumber", phoneNumber);
		    request.setAttribute("userName", userName);
		    request.setAttribute("projectId", projectId);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getRelativeCallInfoListByVo(vo);  
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			/* ���û������ֶ� */ 
			 
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
		return mapping.findForward("queryRelativeCallInfo");
	}

}
