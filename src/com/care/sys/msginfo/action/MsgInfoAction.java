package com.care.sys.msginfo.action;

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
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.msginfo.domain.logic.MsgInfoFacade;
import com.care.sys.msginfo.form.MsgInfoForm;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class MsgInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(MsgInfoAction.class);
	
	public ActionForward queryMsgInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		MsgInfoFacade info = ServiceBean.getInstance().getMsgInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			
			MsgInfoForm form = (MsgInfoForm) actionForm;
			MsgInfo vo = new MsgInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String fromUserName = request.getParameter("fromUserName");
			String toUserName = request.getParameter("toUserName");
			String status = request.getParameter("statusSelect");
			String content = request.getParameter("content");
			String serieNo = request.getParameter("serieNo");
			String startTime1 = request.getParameter("startTime1");
			String endTime1   = request.getParameter("endTime1");
			String belongProject = request.getParameter("belongProject");
			

			/*���û������ֶ�*/
            form.setOrderBy("msg_handler_date"); 
            form.setSort("1"); 
            
            sb.append("1=1");
            if(!projectInfoId.equals("0")){
				sb.append(" and m.belong_project in(" + projectInfoId + ")");
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
						sb.append(" and m.belong_project in(" + str + ")");
					}					
				}
			}
            if(fromUserName != null && !"".equals(fromUserName)){
				sb.append(" and a1.user_name = '"+fromUserName+"'");
			}
            if(toUserName != null && !"".equals(toUserName)){
				sb.append(" and a2.user_name = '"+toUserName+"'");
			}
			if(startTime != null && !"".equals(startTime)){				
				sb.append(" and substring(msg_handler_date,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){				
				sb.append(" and substring(msg_handler_date,1,10) <= '"+endTime+"'");
			}			
			if(startTime1 != null && !"".equals(startTime1)){				
				sb.append(" and substring(msg_occur_date,1,10) >= '"+startTime1+"'");
			}
			if(endTime1 != null && !"".equals(endTime1)){				
				sb.append(" and substring(msg_occur_date,1,10) <= '"+endTime1+"'");
			}			
			if(status != null && !"".equals(status)){
				sb.append(" and is_handler ='"+status+"'");
				request.setAttribute("statusSelect", CommUtils.getSelectMess("statusSelect", Integer.parseInt(status)));
			}
			if(content != null && !"".equals(content)){
				sb.append(" and msg_content like '%" + content + "%'");
							}
			if(serieNo != null && !"".equals(serieNo)){
				sb.append(" and msg_content like '%"+serieNo+"%'");
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("m.belong_project = '" + belongProject + "'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project_name", pros);
			
			
			

			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("fromUserName", fromUserName);
		    request.setAttribute("toUserName", toUserName);
		    request.setAttribute("content", content);
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("fNow_date1", startTime1);
		    request.setAttribute("now_date1", endTime1);
		    request.setAttribute("belongProject", belongProject);
		    
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getMsgInfoListByVo(vo); 
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
		return mapping.findForward("queryMsgInfo");
	}

}
