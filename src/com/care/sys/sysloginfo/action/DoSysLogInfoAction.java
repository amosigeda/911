package com.care.sys.sysloginfo.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.care.sys.sysloginfo.domain.SysLogInfo;
import com.care.sys.sysloginfo.domain.logic.SysLogInfoFacade;
import com.care.sys.sysloginfo.form.SysLogInfoForm;
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
public class DoSysLogInfoAction extends BaseAction{
	Log logger = LogFactory.getLog(DoSysLogInfoAction.class);

	public ActionForward querySysLogInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		SysLogInfoFacade info = ServiceBean.getInstance().getSysLogInfoFacade();
		try{
			SysLogInfoForm form = (SysLogInfoForm)actionForm;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			request.setAttribute("store",form.getUserName());
			if(form.getTag() == null){
		
			request.setAttribute("store2",form.getLogs());
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			request.setAttribute("store4", endTime);
			request.setAttribute("store5", startTime);
			SysLogInfo vo = new SysLogInfo();
			/*���û������ֶ�*/
			if (form.getOrderBy() == null) {
                form.setOrderBy("logDate");
                form.setSort("1");
			}
            
			BeanUtils.copyProperties(vo,form);
			StringBuffer sb = new StringBuffer();
			if(vo.getLogs() != null) {
				sb.append(" logs like '%");
				sb.append(vo.getLogs());
				sb.append("%' and "); 
				vo.setLogs(null);
			}
			if (vo.getUserName()!=null){
				sb.append("userName like '%"+vo.getUserName()+"%'");
				form.setUserName(null);
			}
			if (startTime != null && !"".equals(startTime)) {				
			sb.append("and logDate between '" + startTime
			+ " 00:00:00' and '" + endTime + " 23:59:59' ");					
			}
			request.setAttribute("fNow_date", startTime);
			request.setAttribute("now_date", endTime);
			if("".equals(sb.toString())){
				sb.append(" logDate between '" + dateFormat.format(new Date())
						+ " 00:00:00' and '" + dateFormat.format(new Date()) + " 23:59:59' ");								
				request.setAttribute("fNow_date", dateFormat.format(new Date()));
				request.setAttribute("now_date", dateFormat.format(new Date()));
			}
			
			vo.setCondition(sb.toString());
			BeanUtils.copyProperties(vo,form);

			list = info.getDataSysLogInfoListByVo(vo);
			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.getTotalSize());
			/*���û������ֶ�*/
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ��*/
			if(e instanceof SystemException){/*����֪�쳣���н���*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward("querySysLogInfo");
	}
	public ActionForward querySysRecord(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		SysLogInfoFacade info = ServiceBean.getInstance().getSysLogInfoFacade();
		try{
			SysLogInfoForm form = (SysLogInfoForm)actionForm;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//获取三天前的时间天数
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(start);
			calendar.add(Calendar.DAY_OF_MONTH,-3);
			start=calendar.getTime();
			
			request.setAttribute("store",form.getUserName());
			if(form.getTag() == null){
		
			request.setAttribute("store2",form.getLogs());
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			request.setAttribute("store4", endTime);
			request.setAttribute("store5", startTime);
			SysLogInfo vo = new SysLogInfo();
			/*���û������ֶ�*/
			if (form.getOrderBy() == null) {
                form.setOrderBy("logDate");
                form.setSort("1");
			}
            
			BeanUtils.copyProperties(vo,form);
			StringBuffer sb = new StringBuffer();
			if(vo.getLogs() != null) {
				sb.append(" logs like '%");
				sb.append(vo.getLogs());
				sb.append("%' and "); 
				vo.setLogs(null);
			}
			if (vo.getUserName()!=null){
				sb.append("userName like '%"+vo.getUserName()+"%'");
				form.setUserName(null);
			}
			if (startTime != null && !"".equals(startTime)) {				
			sb.append("and logDate between '" + startTime
			+ " 00:00:00' and '" + endTime + " 23:59:59' ");					
			}
			request.setAttribute("fNow_date", startTime);
			request.setAttribute("now_date", endTime);
			if("".equals(sb.toString())){
			 sb.append(" logDate between '" + dateFormat.format(start)
						+ " 00:00:00' and '" + dateFormat.format(new Date()) + " 23:59:59' ");								
				request.setAttribute("fNow_date", dateFormat.format(start));
				request.setAttribute("now_date", dateFormat.format(new Date()));
			}
			
			vo.setCondition(sb.toString());
			BeanUtils.copyProperties(vo,form);

			list = info.getDataSysLogInfoListByVo(vo);
			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.getTotalSize());
			/*���û������ֶ�*/
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ��*/
			if(e instanceof SystemException){/*����֪�쳣���н���*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward("querySysRecord");
	}
	public ActionForward queryBeifenRecord(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String href= request.getServletPath()+"?method=queryBeifenRecord";
		Date start = new Date();
		Result result = new Result();	
		PagePys pys = new PagePys();
		DataList list = null;
		try {
			SysLogInfoForm form=(SysLogInfoForm)actionForm;
			SysLogInfo sysLog=new SysLogInfo();
			if (form.getOrderBy() == null) {
                form.setOrderBy("updateTime");
                form.setSort("1");
            }
			BeanUtils.copyProperties(sysLog,form);
			list=ServiceBean.getInstance().getSysLogInfoFacade().getBeifenRecord(sysLog);
			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.getTotalSize());
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ��*/
			if(e instanceof SystemException){/*����֪�쳣���н���*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*��δ֪�쳣���н�������ȫ�������δ֪�쳣*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally{
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward("queryBeifenRecord");
	}
	public ActionForward initInsert(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		return mapping.findForward("insertSysLogInfo");
	}
	
	public void validateInsert(SysLogInfoForm form) throws SystemException {
		//SysLogInfo testvo = new SysLogInfo();
		/*��ݺϷ�����֤*/
	}
	
	public ActionForward insertSysLogInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			SysLogInfoForm form = (SysLogInfoForm)actionForm;
			validateInsert(form);
			SysLogInfo vo = new SysLogInfo();
			BeanUtils.copyProperties(vo,form);
			
			ServiceBean.getInstance().getSysLogInfoFacade().insertSysLogInfo(vo);
			
			result.setBackPage(HttpTools.httpServletPath(request,"querySysLogInfo"));
			result.setResultCode("inserts");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"querySysLogInfo"));
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
	
	public ActionForward initUpdate(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//String[] row = HttpTools.requestArray(request, "crow");
		SysLogInfo vo = new SysLogInfo();
		/*������Ҫ����vo��ѯ���� һ��Ϊ�� vo.setId(new Integer(row[0]));*/
		List<DataMap> list = ServiceBean.getInstance().getSysLogInfoFacade().getSysLogInfo(vo);
		if (list == null || list.size() == 0){/*������ݱ�ɾ��ʱ����,��ͨ����²��ᷢ��*/
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,"querySysLogInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("sysLogInfo",list.get(0));
		return mapping.findForward("updateSysLogInfo");
	}
	
	public void validateUpdate(SysLogInfoForm form) throws SystemException {
		//SysLogInfo testvo = new SysLogInfo();
		/*��ݺϷ�����֤*/
	}
	
	public ActionForward updateSysLogInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			SysLogInfoForm form = (SysLogInfoForm)actionForm;
			validateUpdate(form);
			SysLogInfo vo = new SysLogInfo();
			BeanUtils.copyProperties(vo,form);
			ServiceBean.getInstance().getSysLogInfoFacade().updateSysLogInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,"querySysLogInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"querySysLogInfo"));
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
	
	public ActionForward deleteSysLogInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			String tag = request.getParameter("tag");
		    SysLogInfo vo = new SysLogInfo();	

			String row[] = HttpTools.requestArray(request, "crow");
			if("1".equals(tag)){
				for(int i=0;i<row.length;i++){
					vo.setCondition("id ='"+row[i]+"'");
					ServiceBean.getInstance().getSysLogInfoFacade().deleteSysLogInfos(vo);
				}
			}

			result.setBackPage(HttpTools.httpServletPath(request,"querySysLogInfo"));
			result.setResultCode("deletes");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"querySysLogInfo"));
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
	
	public ActionForward getOutTime(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		Result result = new Result();
		Date outDate = new Date();
		try {
			SysLogInfoForm form = (SysLogInfoForm) actionForm;			
			SysLogInfo vo = new SysLogInfo();
			Integer MaxNumB= ServiceBean.getInstance().getSysLogInfoFacade().selectSysLogInfoCount(vo);
			if(MaxNumB==null){
		    }else{vo.setCondition("id='"+MaxNumB+"'");
			vo.setOutDate(outDate);
			BeanUtils.copyProperties(vo, form);
		    ServiceBean.getInstance().getSysLogInfoFacade().updateOutDate(vo);
		    }
		   /* result.setBackPage(HttpTools.httpServletPath(request,
					"updateOutTime"));*/
			/*result.setResultCode("updates");
			result.setResultType("success");*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			
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
		return mapping.findForward("/index.jsp");
	}


}
