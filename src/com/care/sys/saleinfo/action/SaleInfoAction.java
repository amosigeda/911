package com.care.sys.saleinfo.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.care.sys.saleinfo.domain.SaleInfo;
import com.care.sys.saleinfo.domain.logic.SaleInfoFacade;
import com.care.sys.saleinfo.form.SaleInfoForm;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class SaleInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(SaleInfoAction.class);
	
	public ActionForward querySaleInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		SaleInfoFacade info = ServiceBean.getInstance().getSaleInfoFacade();
		try {
			SaleInfoForm form = (SaleInfoForm) actionForm;
			SaleInfo vo = new SaleInfo(); 
			String userName = request.getParameter("userName");
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String projectId = request.getParameter("projectId");
		
            form.setOrderBy("s.id"); 
            form.setSort("1"); 

            if(userName != null && !"".equals(userName)){
				sb.append("s.user_name like '%"+userName+"%'");
			}
            System.out.println("username--------"+userName);
			if(startTime != null && !"".equals(startTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(s.date_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(s.date_time,1,10)<= '"+endTime+"'");
			}
			if(projectId != null && !"".equals(projectId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("s.belong_project='"+projectId+ "'");
			}
			System.out.println("p.id--------"+projectId);
			
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("userName", userName);
		    request.setAttribute("projectId", projectId);
		    
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getSaleInfoListByVo(vo);  
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
		return mapping.findForward("querySaleInfo");
	}
	
	public ActionForward querySaleAreaInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null; 
		StringBuffer sb = new StringBuffer();
		SaleInfoFacade info = ServiceBean.getInstance().getSaleInfoFacade();
		try {
			SaleInfoForm form = (SaleInfoForm) actionForm;
			SaleInfo vo = new SaleInfo(); 			
			
			
//            form.setOrderBy("id"); 
//            form.setSort("1"); 

           
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getSaleInfoListGroupByProvince(vo);  
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
		return mapping.findForward("querySaleAreaInfo");
	}
	
	public ActionForward queryDayAddInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null; 
		StringBuffer sb = new StringBuffer();
		SaleInfoFacade info = ServiceBean.getInstance().getSaleInfoFacade();
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			SaleInfoForm form = (SaleInfoForm) actionForm;
			SaleInfo vo = new SaleInfo(); 			
			
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String belongProject = request.getParameter("belongProject");
			
            form.setOrderBy("d.add_time"); 
            form.setSort("1"); 
            StringBuffer buffer = new StringBuffer();
            if(!projectInfoId.equals("0")){
				sb.append("d.belong_project in(" + projectInfoId + ")");
				buffer.append("belong_project in(" + projectInfoId + ")");
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
						sb.append("d.belong_project in(" + str + ")");
						buffer.append("belong_project in(" + str + ")");
					}					
				}
			}
			if(startTime == null && endTime == null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String time1 = format.format(new Date());
				String time2 = time1.substring(0,8) + "01";
				
				request.setAttribute("fNow_date", time2);
			    request.setAttribute("now_date", time1);
			    if(sb.length() > 0){
					sb.append(" and ");
					buffer.append(" and ");
				}
				sb.append("d.add_time >= '"+time2+"' and d.add_time <= '"+time1+"'");
				
				buffer.append("add_time >= '"+time2+"' and add_time <= '"+time1+"'");
			}
			
			if(startTime != null && !"".equals(startTime)){	
				if(sb.length() > 0){
					sb.append(" and ");
					buffer.append(" and ");
				}
				sb.append("d.add_time >= '"+startTime+"'");
				buffer.append("add_time >='"+startTime+"'");
				
				request.setAttribute("fNow_date", startTime);
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
					buffer.append(" and ");
				}
				sb.append("d.add_time <= '"+endTime+"'");
				buffer.append("add_time <='"+endTime+"'");
				
				request.setAttribute("now_date", endTime);
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("d.belong_project='"+belongProject+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			vo.setAddTime(buffer.toString());
			vo.setCondition(sb.toString());					    
			request.setAttribute("belongProject", belongProject);
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getAddDayGroupByTime(vo); 
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
		return mapping.findForward("queryDayAddInfo");
	}

}
