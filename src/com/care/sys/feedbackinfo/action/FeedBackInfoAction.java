package com.care.sys.feedbackinfo.action;

import java.util.Date;
import java.util.List;
import javax.servlet.http.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.action.*;

import com.care.app.LoginUser;
import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.CommUtils;
import com.care.sys.feedbackinfo.domain.FeedBackInfo;
import com.care.sys.feedbackinfo.domain.logic.FeedBackInfoFacade;
import com.care.sys.feedbackinfo.form.FeedBackInfoForm;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.roleinfo.domain.RoleInfo;
import com.care.sys.userinfo.domain.*;
import com.care.sys.userinfo.domain.logic.*;
import com.care.sys.userinfo.form.*;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.*;

import org.apache.commons.logging.Log;

import com.godoing.rose.log.LogFactory;
import com.godoing.rose.http.common.*;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class FeedBackInfoAction extends BaseAction {
	
	Log logger = LogFactory.getLog(FeedBackInfoAction.class);

	public ActionForward queryFeedBackInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();		
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();//锟斤拷锟斤拷锟街凤拷锟斤拷锟斤拷
		FeedBackInfoFacade info = ServiceBean.getInstance().getFeedBackInfoFacade();
		try {			
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			
			FeedBackInfoForm form = (FeedBackInfoForm) actionForm;
			/* 锟斤拷锟矫筹拷始锟斤拷锟斤拷锟斤拷锟斤拷锟?*/			
			form.setOrderBy("date_time");
			form.setSort("1");

			FeedBackInfo vo = new FeedBackInfo();
			String userName = request.getParameter("userName");
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");		
			String belongProject = request.getParameter("belongProject");
			String feedbackStatus = request.getParameter("feedbackStatus");
		    String userfeedbackcontent=request.getParameter("userfeedbackcontent");
		    String protime=request.getParameter("protime");
		    
		    
			/*锟斤拷锟矫伙拷锟斤拷锟斤拷锟街讹拷*/
            form.setOrderBy("date_time"); 
            form.setSort("1"); 
          
            if(!projectInfoId.equals("0")){
				sb.append("f.belong_project in(" + projectInfoId + ")");
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
						sb.append("f.belong_project in(" + str + ")");
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
			if(userName != null && !"".equals(userName)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.user_name like '%" + userName + "%'");
			}
			if(userfeedbackcontent !=null && !"".equals(userfeedbackcontent)){
				if(sb.length()>0){
					sb.append(" and ");
				}
				sb.append("f.user_feedback_content like '%" + userfeedbackcontent +"%'");
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("f.belong_project = '" + belongProject + "'");
			}
			
			if(feedbackStatus!=null && !"".equals(feedbackStatus)){
				if(sb.length()>0){
					sb.append(" and ");
				}
				sb.append("f.feedback_status ='"+ feedbackStatus +"'");
				request.setAttribute("feedbackStatus", CommUtils.getSelectMess("feedbackStatus", Integer.parseInt(feedbackStatus)));
			}
		
			vo.setCondition(sb.toString());
			
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			request.setAttribute("userfeedbackcontent", userfeedbackcontent);
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("userName", userName);
		    request.setAttribute("belongProject", belongProject);
			BeanUtils.copyProperties(vo, form);
			list = info.getDataFeedBackInfoListByVo(vo);
			BeanUtils.copyProperties(pys, form);
			pys.setCounts(list.getTotalSize());
			/* 锟斤拷锟矫伙拷锟斤拷锟斤拷锟街讹拷 */

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* 锟斤拷锟斤拷为锟斤拷锟斤拷页锟芥，锟斤拷锟皆筹拷锟斤拷锟斤拷锟阶拷锟较低衬拷锟揭筹拷锟?*/
			if (e instanceof SystemException) { /* 锟斤拷锟斤拷知锟届常锟斤拷锟叫斤拷锟斤拷 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 锟斤拷未知锟届常锟斤拷锟叫斤拷锟斤拷锟斤拷锟斤拷全锟斤拷锟斤拷锟斤拷锟轿粗拷斐?*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward("queryFeedBackInfo");
	}

	public ActionForward deleteFeedBackInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		try {
			String id = request.getParameter("id");
			FeedBackInfo vo = new FeedBackInfo();
				vo.setCondition("id ='"+id+"'");    
				//锟斤拷锟斤拷锟矫伙拷锟剿伙拷
				ServiceBean.getInstance().getFeedBackInfoFacade()
						.deleteFeedBackInfo(vo);   //锟斤拷bean锟斤拷删锟斤拷锟矫伙拷
			
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryFeedBackInfo"));
			result.setResultCode("deletes");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryFeedBackInfo"));
			if (e instanceof SystemException) { /* 锟斤拷锟斤拷知锟届常锟斤拷锟叫斤拷锟斤拷 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 锟斤拷未知锟届常锟斤拷锟叫斤拷锟斤拷锟斤拷锟斤拷全锟斤拷锟斤拷锟斤拷锟轿粗拷斐?*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	public ActionForward initFeedBackInfoUpdate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		FeedBackInfo vo = new FeedBackInfo();
		vo.setCondition("id='"+id+"'");
		List<DataMap> list = ServiceBean.getInstance().getFeedBackInfoFacade().getFeedBackInfo(vo);
		if (list == null || list.size() == 0) {
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryFeedBackInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("feedBackInfo", list.get(0));
		return mapping.findForward("updateFeedBackInfo");
	}
	public ActionForward updateFeedBackInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		Result result = new Result();
		String feedbackstatus="1";
		Date protime = new Date();
		try {
			FeedBackInfoForm form = (FeedBackInfoForm) actionForm;			
			FeedBackInfo vo = new FeedBackInfo();
			vo.setCondition("id='" + form.getId() + "'");
			BeanUtils.copyProperties(vo, form);
			vo.setFeedbackstatus(feedbackstatus);
			vo.setProtime(protime);
			ServiceBean.getInstance().getFeedBackInfoFacade().updateFeedBackInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryFeedBackInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryFeedBackInfo"));
			if (e instanceof SystemException) { /* 锟斤拷锟斤拷知锟届常锟斤拷锟叫斤拷锟斤拷 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 锟斤拷未知锟届常锟斤拷锟叫斤拷锟斤拷锟斤拷锟斤拷全锟斤拷锟斤拷锟斤拷锟轿粗拷斐?*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	
}
