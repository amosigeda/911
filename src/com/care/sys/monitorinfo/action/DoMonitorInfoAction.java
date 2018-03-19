package com.care.sys.monitorinfo.action;

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

import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.CommUtils;
import com.care.sys.monitorinfo.domain.MonitorInfo;
import com.care.sys.monitorinfo.domain.logic.MonitorInfoFacade;
import com.care.sys.monitorinfo.form.MonitorInfoForm;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class DoMonitorInfoAction extends BaseAction{
	Log logger = LogFactory.getLog(DoMonitorInfoAction.class);

	public ActionForward queryMonitorInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = new DataList();
		StringBuffer sb = new StringBuffer();
		MonitorInfoFacade info = ServiceBean.getInstance().getMonitorInfoFacade();
		try{
			MonitorInfoForm form = (MonitorInfoForm)actionForm;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String costTime1 = request.getParameter("costTime1");
			String costTime2 = request.getParameter("costTime2");
			String function = request.getParameter("func");
//			request.setAttribute("costTime1", costTime1);
//			request.setAttribute("costTime2", costTime2);
			/*设置初始化排序参数*/
			MonitorInfo vo = new MonitorInfo();
			/*设置化排序字段*/
			if (form.getOrderBy() == null) {
                form.setOrderBy("id");
                form.setSort("1");        //1代表降序,0代表升序
			}
			if(startTime == null && endTime == null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String newDate = format.format(new Date());
				sb.append("start_time between '"+ newDate +" 00:00:00' and '"+ newDate +" 23:59:59'");
				
				request.setAttribute("fNow_date", newDate);
				request.setAttribute("now_date", newDate);
			}
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(start_time,1,10) >='"+startTime+"'");
				
				request.setAttribute("fNow_date", startTime);
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(start_time,1,10) <='"+endTime+"'");
				
				request.setAttribute("now_date", endTime);
			}
			if(function != null && !"".equals(function)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("function like '%"+ function +"%'");
			}
//			BeanUtils.copyProperties(vo,form);
			
			/* 时间限制 */
//			if(sb.toString() != null && !"".equals(sb.toString())){
//				sb.append(" and ");
//			}
//			sb.append(" start_time between '" + startTime
//			+ " 00:00:00' and '" + endTime + " 23:59:59' ");	
//			
			if(costTime1 != null && !"".equals(costTime1)){
				if(sb.toString() != null && !"".equals(sb.toString())){
					sb.append(" and ");
				}
				sb.append(costTime1+" < cost_time");
			}
			if(costTime2 != null && !"".equals(costTime2)){
				if(sb.toString() != null && !"".equals(sb.toString())){
					sb.append(" and ");
				}
				sb.append("cost_time< "+costTime2);
			}
//			if(function != null && !"".equals(function)){
//				if(sb.length() > 0){
//					sb.append(" and ");
//				}
//				sb.append("function like '%" + function + "%'");
//			}
		
			
//			request.setAttribute("fNow_date", startTime);
//			request.setAttribute("now_date", endTime);
			request.setAttribute("func", function);
//			request.setAttribute("functions", form.getFunctions());
			
			vo.setCondition(sb.toString());
			BeanUtils.copyProperties(vo,form);

			list = info.getDataMonitorInfoListByVo(vo);
			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.getTotalSize());
			
			List<DataMap> switchList = info.getSwitchInfo(vo);
			if(switchList.size() > 0){
				request.setAttribute("moniSwitch", switchList.get(0).getAt("moni_s"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*这里为管理页面，所以出错后跳转到系统默认页面*/
			if(e instanceof SystemException){/*对已知异常进行解析*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*对未知异常进行解析，并全部定义成未知异常*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward("queryMonitorInfo");
	}
	
	public ActionForward queryVisit(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = new DataList();
		StringBuffer sb = new StringBuffer();
		MonitorInfoFacade info = ServiceBean.getInstance().getMonitorInfoFacade();
		String forword = "";
		try{
			MonitorInfoForm form = (MonitorInfoForm)actionForm;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String costTime1 = request.getParameter("costTime1");
			String costTime2 = request.getParameter("costTime2");
			request.setAttribute("costTime1", costTime1);
			request.setAttribute("costTime2", costTime2);
			String type = request.getParameter("type");
			String phone = request.getParameter("phone");
			String func = request.getParameter("func");
			String funcHref = request.getParameter("href");
			
			if(type.equals("0")){
				forword="queryAppVisit";
			}else{
				forword="queryDeviceVisit";
			}
			/*设置初始化排序参数*/
			MonitorInfo vo = new MonitorInfo();
			/*设置化排序字段*/			
            form.setOrderBy("id");
            form.setSort("1");        //1代表降序,0代表升序			
			
            vo.setType(type);
            if(form.getPhone() != null){
            	if(type.equals("1")){
            		sb.append("v.phone like '%"+form.getPhone()+"%'");
            	}else{
            		sb.append("a.user_name like '%"+form.getPhone()+"%'");
            	}				
			}
            if (startTime == null || "".equals(startTime)) {
				list = new DataList();
			} else {
				/* 时间限制 */
				if(sb.toString() != null && !"".equals(sb.toString())){
					sb.append(" and ");
				}
				sb.append(" start_time between '" + startTime
				+ " 00:00:00' and '" + endTime + " 23:59:59' ");	
			}
			if(costTime1 != null && !"".equals(costTime1)){
				if(sb.toString() != null && !"".equals(sb.toString())){
					sb.append(" and ");
				}
				sb.append(costTime1+" < v.cost_time");
			}
			if(costTime2 != null && !"".equals(costTime2)){
				if(sb.toString() != null && !"".equals(sb.toString())){
					sb.append(" and ");
				}
				sb.append("v.cost_time< "+costTime2);
			}
			request.setAttribute("fNow_date", startTime);
			request.setAttribute("now_date", endTime);
			request.setAttribute("costTime1", costTime1);
			request.setAttribute("costTime2", costTime2);
			request.setAttribute("phone", phone);
			request.setAttribute("func", func);
			request.setAttribute("href", funcHref);
			if("".equals(sb.toString())){
				sb.append(" start_time between '" + dateFormat.format(new Date())
						+ " 00:00:00' and '" + dateFormat.format(new Date()) + " 23:59:59' ");								
				request.setAttribute("fNow_date", dateFormat.format(new Date()));
				request.setAttribute("now_date", dateFormat.format(new Date()));
			}
//			if(phone != null && !"".equals(phone)){
//				if(sb.length() > 0){
//					sb.append(" and ");
//				}
//				sb.append("phone like '%"+phone+"'%");
//			}
			if(func != null && !"".equals(func)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("function like '%" + func + "%'");
			}
			if(funcHref != null && !"".equals(funcHref)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("function_href like '%" + funcHref + "%'");
			}
			
			vo.setCondition(sb.toString());
			BeanUtils.copyProperties(vo,form);

			list = info.getVisitInfoListByVo(vo);
			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.getTotalSize());
			
			List<DataMap> switchList = info.getSwitchInfo(vo);
			if(switchList.size() > 0){
				request.setAttribute("appSwitch", switchList.get(0).getAt("visit_s"));
				request.setAttribute("deviceSwitch", switchList.get(0).getAt("device_s"));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*这里为管理页面，所以出错后跳转到系统默认页面*/
			if(e instanceof SystemException){/*对已知异常进行解析*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*对未知异常进行解析，并全部定义成未知异常*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward(forword);
	}
	
	public ActionForward changeSwitch(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		String forword = "";
		try{
			String type = request.getParameter("type");
			String value = request.getParameter("value");			
			MonitorInfo vo = new MonitorInfo();
			
			if(type.equals("monitor")){
				forword="queryMonitorInfo";
				vo.setMoni_s(value);
			}else if(type.equals("app")){
				forword="queryVisit&type=0";
				vo.setVisit_s(value);
			}else{
				forword="queryVisit&type=1";
				vo.setDevice_s(value);
			}
			ServiceBean.getInstance().getMonitorInfoFacade().updateSwitchInfo(vo);
													
			result.setBackPage(HttpTools.httpServletPath(request,forword));							
			result.setResultCode("updates");							
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,forword));
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
	
}
