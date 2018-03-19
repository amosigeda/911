package com.care.sys.shareinfo.action;

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
import com.care.sys.shareinfo.domain.ShareInfo;
import com.care.sys.shareinfo.domain.logic.ShareInfoFacade;
import com.care.sys.shareinfo.form.ShareInfoForm;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class ShareInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(ShareInfoAction.class);
	
	public ActionForward queryShareInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		ShareInfoFacade info = ServiceBean.getInstance().getShareInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			ShareInfoForm form = (ShareInfoForm) actionForm;
			ShareInfo vo = new ShareInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String userName = request.getParameter("userName");
			String serieNo = request.getParameter("serieNo");
			String toUserName = request.getParameter("toUserName");
			String projectId = request.getParameter("projectId");
			
			/*���û������ֶ�*/
            form.setOrderBy("share_date"); 
            form.setSort("1"); 
            
            sb.append("is_priority = '0'");
            if(!projectInfoId.equals("0")){
				sb.append(" and s.belong_project in(" + projectInfoId + ")");
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
						sb.append(" and s.belong_project in(" + str + ")");
					}					
				}
			}
            if(serieNo != null && !"".equals(serieNo)){
				sb.append(" and device_imei like '%"+serieNo+"%'");
			}            
			if(startTime != null && !"".equals(startTime)){				
				sb.append(" and substring(share_date,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){				
				sb.append(" and substring(share_date,1,10) <= '"+endTime+"'");
			}
			if(userName != null && !"".equals(userName)){				
				sb.append(" and a1.user_name like '%"+userName+"%'");
			}
			if(toUserName != null && !"".equals(toUserName)){				
				sb.append(" and a2.user_name like '%"+toUserName+"%'");
			}
			if(projectId != null && !"".equals(projectId)){
				sb.append(" and p.id ='"+projectId+ "'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("userName", userName);
		    request.setAttribute("toUserName", toUserName);
			request.setAttribute("projectId", projectId);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getShareInfoListByVo(vo);
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
		return mapping.findForward("queryShareInfo");
	}

}
