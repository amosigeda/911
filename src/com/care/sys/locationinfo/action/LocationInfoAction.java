package com.care.sys.locationinfo.action;

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

import com.care.app.LoginUser;
import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.CommUtils;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.care.sys.locationinfo.form.LocationInfoForm;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class LocationInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(LocationInfoAction.class);
	
	public ActionForward queryLocationInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		LocationInfoFacade info = ServiceBean.getInstance().getLocationInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			
			LocationInfoForm form = (LocationInfoForm) actionForm;
			LocationInfo vo = new LocationInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String serieNo = request.getParameter("serieNo");
			String locationType = request.getParameter("locationType");
			String status = request.getParameter("statusSelect");
			String projectId = request.getParameter("projectId");

            form.setOrderBy("id"); 
            form.setSort("1"); 
            if(!projectInfoId.equals("0")){
				sb.append("belong_project in(" + projectInfoId + ")");
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
						sb.append("l.belong_project in(" + str + ")");
					}					
				}
			}
            if(startTime == null && endTime == null){
            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            	String dTime = format.format(new Date());
            	if(sb.length() > 0){
					sb.append(" and ");
				}
            	sb.append("substring(l.upload_time,1,10) = '" + dTime + "'");
            	request.setAttribute("fNow_date", dTime+" 00:00:00");
    		    request.setAttribute("now_date", dTime+" 23:59:59");
            }
			if(startTime != null && !"".equals(startTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("l.upload_time >= '"+startTime+"'");
				request.setAttribute("fNow_date", startTime);
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("l.upload_time <= '"+endTime+"'");
				request.setAttribute("now_date", endTime);
			}
			if(serieNo != null && !"".equals(serieNo)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("l.serie_no like '%"+serieNo+"%'");
			}
			if(locationType != null && !"".equals(locationType)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("l.location_type ='"+locationType+"'");
				request.setAttribute("locationType", CommUtils.getSelect(
						"locationType", Integer.parseInt(locationType)));
			}
			if(status != null && !"".equals(status)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("l.s_t ='"+status+"'");
				request.setAttribute("statusSelect", CommUtils.getSelectMess("statusSelect", Integer.parseInt(status)));
			}
			if(status == null){
				if(sb.length()>0){
					sb.append("and");
				}
				String statu = "1";
				sb.append(" l.s_t ='"+ statu +"'");
				request.setAttribute("statusSelect", CommUtils.getSelectMess("statusSelect", 1));
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
			
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("projectId", projectId);
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getLocationInfoListByVo(vo);  
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
		return mapping.findForward("queryLocationInfo");
	}
	public ActionForward queryNineOneOneLocationInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		LocationInfoFacade info = ServiceBean.getInstance().getLocationInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			
			LocationInfoForm form = (LocationInfoForm) actionForm;
			LocationInfo vo = new LocationInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String serieNo = request.getParameter("serieNo");
			String locationType = request.getParameter("locationType");
			String status = request.getParameter("statusSelect");
			String projectId = request.getParameter("projectId");

            form.setOrderBy("OBJ_ID"); 
            form.setSort("1"); 
            if(!projectInfoId.equals("0")){
				sb.append("belong_project in(" + projectInfoId + ")");
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
						sb.append("l.belong_project in(" + str + ")");
					}					
				}
			}
            if(startTime == null && endTime == null){
            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            	String dTime = format.format(new Date());
            	if(sb.length() > 0){
					sb.append(" and ");
				}
            	sb.append("substring(INSERT_TIME,1,10) = '" + dTime + "'");
            	request.setAttribute("fNow_date", dTime+" 00:00:00");
    		    request.setAttribute("now_date", dTime+" 23:59:59");
            }
			if(startTime != null && !"".equals(startTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("INSERT_TIME >= '"+startTime+"'");
				request.setAttribute("fNow_date", startTime);
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("INSERT_TIME <= '"+endTime+"'");
				request.setAttribute("now_date", endTime);
			}
			if(serieNo != null && !"".equals(serieNo)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("DEVICE_SERIAL_NUMBER like '%"+serieNo+"%'");
			}
			if(locationType != null && !"".equals(locationType)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("l.location_type ='"+locationType+"'");
				request.setAttribute("locationType", CommUtils.getSelect(
						"locationType", Integer.parseInt(locationType)));
			}
			if(status != null && !"".equals(status)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("l.s_t ='"+status+"'");
				request.setAttribute("statusSelect", CommUtils.getSelectMess("statusSelect", Integer.parseInt(status)));
			}
			/*if(status == null){
				if(sb.length()>0){
					sb.append("and");
				}
				String statu = "1";
				sb.append(" l.s_t ='"+ statu +"'");
				request.setAttribute("statusSelect", CommUtils.getSelectMess("statusSelect", 1));
			}*/
			if(projectId != null && !"".equals(projectId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("p.id ='"+projectId+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("projectId", projectId);
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getNineOneOneLocationInfoListByVo(vo);  
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
		return mapping.findForward("queryNineOneOneLocationInfo");
	}
}
