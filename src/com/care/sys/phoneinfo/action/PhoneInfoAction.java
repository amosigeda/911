package com.care.sys.phoneinfo.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.care.app.LoginUser;
import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.CommUtils;
import com.care.common.lang.ReadExcelTest;
import com.care.sys.companyinfo.domain.CompanyInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.care.sys.phoneinfo.form.PhoneInfoForm;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class PhoneInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(PhoneInfoAction.class);
	
	public ActionForward queryPhoneInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		PhoneInfoFacade info = ServiceBean.getInstance().getPhoneInfoFacade();
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			PhoneInfoForm form = (PhoneInfoForm) actionForm;
			PhoneInfo vo = new PhoneInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String serieNo = request.getParameter("serieNo");
			String status = request.getParameter("status");
			String type=request.getParameter("type");
			String phoneManageId = request.getParameter("phoneManageId");

            form.setOrderBy("p.input_time"); 
            form.setSort("1"); 
            
            if(!projectInfoId.equals("0")){
				sb.append("p.belong_project in(" + projectInfoId + ")");
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
						sb.append("p.belong_project in(" + str + ")");
					}					
				}
			}
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(p.input_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(p.input_time,1,10) <= '"+endTime+"'");
			}
			if(serieNo != null && !"".equals(serieNo)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("p.serie_no like '%"+serieNo+"%'");
			}
			if(phoneManageId != null && !"".equals(phoneManageId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("p.phone_manage_id='" + phoneManageId + "'");
			}
			if(status != null && !"".equals(status)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("p.status ='"+status+"'");
				request.setAttribute("status", CommUtils.getStatusSelect(
						"status", Integer.parseInt(status)));
			}
			if(type != null && !"".equals(type)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("m.type ='"+type+"'");
				request.setAttribute("type", CommUtils.getTypeSelect(
						"type", Integer.parseInt(type)));
			}
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("serieNo", serieNo);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getPhoneInfoListByVo(vo);  
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
		return mapping.findForward("queryPhoneInfo");
	}
	
	public ActionForward queryPhoneIMEIInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		PhoneInfoFacade info = ServiceBean.getInstance().getPhoneInfoFacade();
		ProjectInfo pro = new ProjectInfo();
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			PhoneInfoForm form = (PhoneInfoForm) actionForm;
			PhoneInfo vo = new PhoneInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String projectId = request.getParameter("projectId");//��Ŀ��
			String companyId = request.getParameter("companyId");
			String type=request.getParameter("type");

            form.setOrderBy("input_time"); 
            form.setSort("1"); 
            if(!projectInfoId.equals("0")){
				sb.append("m.project_id in(" + projectInfoId + ")");
			}else{
				if(!companyInfoId.equals("0")){
					
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
						sb.append("m.project_id in(" + str + ")");
					}					
				}
			}
			if(startTime != null && !"".equals(startTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(input_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(input_time,1,10) <= '"+endTime+"'");
			}
			if(projectId != null && !"".equals(projectId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("m.project_id = '"+projectId+"'");
			}
			if(companyId != null && !"".equals(companyId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("m.company_id = '"+companyId+"'");
				pro.setCondition("company_id = '"+companyId+"'");
			}
			
			if(type!=null && !"".equals(type)){
				if(sb.length()>0){
					sb.append(" and ");
				}
				sb.append("m.type ='"+type+"'");
				request.setAttribute("type", CommUtils.getTypeSelect(
						"type", Integer.parseInt(type)));
			}
			
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			CompanyInfo ci = new CompanyInfo();
			List<DataMap> coms = ServiceBean.getInstance().getCompanyInfoFacade().getCompanyInfo(ci);
			request.setAttribute("company", coms);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("companyId", companyId);
		    request.setAttribute("projectId", projectId);
		    
			vo.setCondition(sb.toString());
			
			BeanUtils.copyProperties(vo,form);	
			List<DataMap> listTemp = info.getPhoneManagerInfo(vo);
			int length = listTemp.size();
			for(int i = 0;i<length;i++){
         		DataMap temp = listTemp.get(i);
         		String managerId = ""+temp.getAt("id");
         		vo.setCondition("phone_manage_id = '"+managerId+"' and status != 0 and status !=1 ");
         		int active = info.getPhoneInfoCount(vo);
         		temp.put("active", active);
         		listTemp.set(i, temp);    		
         	}
         	
         	list = new DataList(listTemp);
    		list.setTotalSize(listTemp.size());
//         	list = info.getPhoneManageListByVo(vo);  
         	
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
		return mapping.findForward("queryPhoneIMEIInfo");
	}
	
	public ActionForward initInsert(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("addimei");
	}
	
	public ActionForward addImei(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "";
		String type = request.getParameter("addType");
		if(type.equals("1")){
			forward="excelInput";
		}else if(type.equals("2")){
			forward="textInput";
		}else{			
			forward="manualInput";
		}
		ProjectInfo vo = new ProjectInfo();
		List<DataMap> list = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(vo);
		request.setAttribute("list", list);
		return mapping.findForward(forward);
	}
	
	public ActionForward manualInput(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PhoneInfoForm form = (PhoneInfoForm)actionForm;
		Result result = new Result();
		try{
			String belongProject = form.getBelongProject();
			int countNum = Integer.parseInt(form.getCountNum());
			Long miniNum = Long.parseLong(form.getSerieNo());
			Long maxNum = miniNum + (countNum - 1);
			Date inputTime = new Date();
			PhoneInfoFacade df = ServiceBean.getInstance().getPhoneInfoFacade();
			PhoneInfo vo = new PhoneInfo();
			Integer maxPhoneManage = df.getPhoneManageMaxId(vo);
			if(maxPhoneManage == null){
				maxPhoneManage = 0;
			}
			vo.setPhoneManageId(String.valueOf(maxPhoneManage + 1));
			vo.setInputTime(inputTime);
			vo.setBelongProject(belongProject);
			for(int i=0;i<countNum; i++){
				vo.setSerieNo(String.valueOf(miniNum));
				df.insertPhoneInfo(vo);
				miniNum ++;
			}
			
			String companyId = this.getCompanyId(belongProject);
			
			this.insertPhoneManage(maxPhoneManage + 1, inputTime, companyId, belongProject, String.valueOf(countNum), form.getSerieNo(), String.valueOf(maxNum), "1");
			
			result.setBackPage(HttpTools.httpServletPath(request,
			"queryPhoneIMEIInfo"));
			result.setResultCode("inserts"); 
			result.setResultType("success");
		}catch (Exception e) { 
			e.printStackTrace();
			logger.error(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); 
			if (e instanceof SystemException) { 
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { 
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		}finally{
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	
	public ActionForward excelInput(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PhoneInfoForm form = (PhoneInfoForm)actionForm;
		FormFile file = form.getFile1();
		Result result = new Result();
		String dir = request.getSession(true).getServletContext().getRealPath("/upload");
		try{
			String path = dir + "/" + file.getFileName();
			
			OutputStream os = new FileOutputStream(path);
			os.write(file.getFileData(), 0, file.getFileSize());
			os.flush();
			os.close();
			ReadExcelTest read = new ReadExcelTest();
			ArrayList<String> list = read.readExcel(path);
			PhoneInfoFacade df = ServiceBean.getInstance().getPhoneInfoFacade();
			PhoneInfo vo = new PhoneInfo();
			String belongProject = form.getBelongProject();
			Integer maxPhoneManage = df.getPhoneManageMaxId(vo);
			if(maxPhoneManage == null){
				maxPhoneManage = 0;
			}
			Date inputTime = new Date();
			vo.setPhoneManageId(String.valueOf(maxPhoneManage + 1));
			vo.setInputTime(inputTime);
			vo.setBelongProject(belongProject);
			
			int countNum = 0;
			Long miniNum = 0L;
			Long maxNum = 0L;
			for(String str: list){
				countNum ++;
				Long s = Long.parseLong(str);
				if(miniNum > s){
					miniNum = s;
				}
				if(maxNum < s){
					maxNum = s;
				}
				vo.setSerieNo(str);
				df.insertPhoneInfo(vo);
			}
			
			String companyId = this.getCompanyId(belongProject);		
			
			this.insertPhoneManage(maxPhoneManage + 1, inputTime, companyId, belongProject, String.valueOf(countNum), String.valueOf(miniNum), String.valueOf(maxNum), "2");
			
			result.setBackPage(HttpTools.httpServletPath(request,
			"queryPhoneIMEIInfo"));
			result.setResultCode("inserts");
			result.setResultType("success");
		}catch (Exception e) { 
			e.printStackTrace();
			logger.error(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); 
			if (e instanceof SystemException) { 
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { 
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		}finally{
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	
	public ActionForward textInput(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PhoneInfoForm form = (PhoneInfoForm)actionForm;
		FormFile file = form.getFile1();
		Result result = new Result();
		String dir = request.getSession(true).getServletContext().getRealPath("/upload");
		try{
			String path = dir + file.getFileName();
			
			OutputStream os = new FileOutputStream(path);
			os.write(file.getFileData(), 0, file.getFileSize());
			os.flush();
			os.close();
			File f = new File(path);
			Scanner in = new Scanner(f);
			String str = "";
			PhoneInfoFacade df = ServiceBean.getInstance().getPhoneInfoFacade();
			PhoneInfo vo = new PhoneInfo();
			String belongProject = form.getBelongProject();
			Integer maxPhoneManage = df.getPhoneManageMaxId(vo);
			if(maxPhoneManage == null){
				maxPhoneManage = 0;
			}
			Date inputTime = new Date();
			vo.setPhoneManageId(String.valueOf(maxPhoneManage + 1));
			vo.setInputTime(inputTime);
			vo.setBelongProject(belongProject);
			int countNum = 0;
			Long miniNum = 0L;
			Long maxNum = 0L;
			while(in.hasNext()){
				str = in.nextLine();
				if(str != null && !"".equals(str)){
					countNum ++;
					Long s = Long.parseLong(str);
					if(miniNum > s){
						miniNum = s;
					}
					if(maxNum < s){
						maxNum = s;
					}
					vo.setSerieNo(str);
					df.insertPhoneInfo(vo);
				}
			}							
				
			f.delete();
			
			String companyId = this.getCompanyId(belongProject);						
			
			this.insertPhoneManage(maxPhoneManage + 1, inputTime, companyId, belongProject, String.valueOf(countNum), String.valueOf(miniNum), String.valueOf(maxNum), "2");

			result.setBackPage(HttpTools.httpServletPath(request, 
			"queryPhoneIMEIInfo"));
			result.setResultCode("inserts");
			result.setResultType("success");
		}catch (Exception e) { 
			e.printStackTrace();
			logger.error(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);
			if (e instanceof SystemException) {
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else {
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		}finally{
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}	


	private void insertPhoneManage(
			int id, Date inputTime, String companyId, String projectId, String countNum, String miniNum, String maxNum, String status){
				
		try {
			PhoneInfo vo = new PhoneInfo();
			vo.setId(id);
			vo.setInputTime(inputTime);
			vo.setCompanyId(companyId);
			vo.setBelongProject(projectId);
			vo.setCountNum(countNum);
			vo.setMiniNum(miniNum);
			vo.setMaxNum(maxNum);
			vo.setStatus(status);
			
			ServiceBean.getInstance().getPhoneInfoFacade().insertPhoneManage(vo);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String getCompanyId(String projectId){
		
		Integer companyId = 0;
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setCondition("id = '" + projectId + "'");
		List<DataMap> projectList;
		try {
			projectList = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(projectInfo);
		
			if(projectList.size() > 0){
				companyId = (Integer)projectList.get(0).getAt("company_id");
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return String.valueOf(companyId);
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

	
}