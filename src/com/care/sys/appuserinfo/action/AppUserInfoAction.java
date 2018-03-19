package com.care.sys.appuserinfo.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
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
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.care.sys.appuserinfo.form.AppUserInfoForm;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.sysregisterinfo.domain.UserInfo;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class AppUserInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(AppUserInfoAction.class);
	
	public ActionForward queryAppUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		AppUserInfoFacade info = ServiceBean.getInstance().getAppUserInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
			if (loginUser == null) {
				return null;
			}
			String companyInfoId = loginUser.getCompanyId();
			String projectInfoId = loginUser.getProjectId();
			AppUserInfoForm form = (AppUserInfoForm) actionForm;
			AppUserInfo vo = new AppUserInfo(); 
			String userName = request.getParameter("user");
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String user_id = request.getParameter("user_id");
			String belongProject = request.getParameter("belongProject");

			/*���û������ֶ�*/
            form.setOrderBy("create_time"); 
            form.setSort("1"); 

            if(!projectInfoId.equals("0")){
				sb.append("a.belong_project in(" + projectInfoId + ")");
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
						sb.append("a.belong_project in(" + str + ")");
					}					
				}
			}
			if(startTime != null && !"".equals(startTime)){		
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(create_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(create_time,1,10) <= '"+endTime+"'");
			}
			if(userName != null && !"".equals(userName)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.user_name like '%" + userName + "%'");
			}
			if(user_id != null && !"".equals(user_id)){				
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.id = '" + user_id + "'");
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.belong_project='"+belongProject+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("user", userName);
		    request.setAttribute("user_id", user_id);
		    request.setAttribute("belongProject", belongProject);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getAppUserInfoListByVo(vo);  
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
		return mapping.findForward("queryAppUserInfo");
	}
	
	public ActionForward queryAppUserCount(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		AppUserInfoFacade info = ServiceBean.getInstance().getAppUserInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			AppUserInfoForm form = (AppUserInfoForm) actionForm;
			AppUserInfo vo = new AppUserInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	

			/*���û������ֶ�*/
            form.setOrderBy("create_time"); 
            form.setSort("1"); 
           
			if(startTime != null && !"".equals(startTime)){				
				sb.append("substring(create_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(create_time,1,10) <= '"+endTime+"'");
			}			
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);		    
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getAppUserInfoGroupByTime(vo); 
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
		return mapping.findForward("queryAppUserCount");
	}
	
	public ActionForward downloadApk(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result = new Result();
		boolean isOnLine = true;
		try {
			String downloadUrl = request.getParameter("download");
			String dir = request.getSession(true).getServletContext()
					.getRealPath("/upload");
			String apkname = CommUtils.getSubStr(downloadUrl,3);
			File f = new File(dir + "/" + apkname);
			if (!f.exists()) {
				throw new SystemException("fail", "noIconPath");
			}
           
			FileInputStream fin = new FileInputStream(f);

			BufferedInputStream br = new BufferedInputStream(fin);
			byte[] buf = new byte[1024 * 8];
			int len = 0;

		        response.reset(); 
		        if (!isOnLine) { 
		            URL u = new URL("file:///" + dir);
		            response.setContentType(u.openConnection().getContentType());
		            response.setHeader("Content-Disposition", "inline; filename=" + apkname);
		            // �ļ���Ӧ�ñ����UTF-8
		        } else { // �����ط�ʽ
		            response.setContentType("application/x-msdownload");
		            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
		        }
		        OutputStream out = response.getOutputStream();
		        while ((len = br.read(buf)) > 0)
		            out.write(buf, 0, len);
		        br.close();
		        out.close();
		        return null;
		}catch (Exception e) {
			e.printStackTrace();
			result.setBackPage(HttpTools.httpServletPath(request,"queryAppUserInfo"));
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);				
			}
		return mapping.findForward("result");
	}

}
