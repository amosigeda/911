package com.care.sys.projectimage.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.CommUtils;
import com.care.sys.checkinfo.domain.CheckInfo;
import com.care.sys.projectimage.domain.ProjectImage;
import com.care.sys.projectimage.domain.logic.ProjectImageFacade;
import com.care.sys.projectimage.form.ProjectImageForm;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.projectinfo.domain.logic.ProjectInfoFacade;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.sinaapp.msdxblog.apkUtil.utils.ApkUtil;

public class ProjectImageAction extends BaseAction {
	
	Log logger = LogFactory.getLog(ProjectImageAction.class);
	
	public ActionForward queryProjectImage(ActionMapping mapping,ActionForm actionForm,
										   HttpServletRequest request,HttpServletResponse response){
		String projectName = "";
		String downloadstatus = "";
		StringBuffer sb = new StringBuffer();
		ProjectImage vo = new ProjectImage();
		ProjectInfo projectInfo = new ProjectInfo();
		ProjectImageForm form =(ProjectImageForm)actionForm;
		List<DataMap> list = null;
		List<DataMap> projectInfos =null;
		Result result = new Result();
		PagePys pys = new PagePys();
		ProjectImageFacade projectImageFacade = ServiceBean.getInstance().getProjectImageFacade();
		ProjectInfoFacade projectInfoFacade = ServiceBean.getInstance().getProjectInfoFacade();
		
		projectName = request.getParameter("projectName");
		downloadstatus = request.getParameter("downloadstatus");
		if(projectName!=null && !"".equals(projectName)){
			sb.append("p.project_name='"+projectName+"'");
		}
		if(downloadstatus!=null && !"".equals(downloadstatus)){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append("pi.downloadstatus='"+downloadstatus+"'");
		}
		
		if(sb.length()>0){
			sb.append(" and ");
		}
		sb.append("pi.hideline = 1");
		
		vo.setCondition(sb.toString());
		form.setOrderBy("pi.id");
		form.setSort("0");
		
		try {
			projectInfos = projectInfoFacade.getProjectInfo(projectInfo);
			BeanUtils.copyProperties(vo,form);			
			list = projectImageFacade.getProjectImageListByVo(vo);
			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.size());
			request.setAttribute("projectInfos", projectInfos);
			request.setAttribute("projectName", projectName);
			request.setAttribute("downloadstatus", downloadstatus);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(request.getQueryString()+" "+e);
			result.setBackPage(Config.ABOUT_PAGE);
			
			if(e instanceof SystemException){
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally{
			request.setAttribute("result",result);
			request.setAttribute("pageList",list);
			request.setAttribute("pagePys", pys);
			request.setAttribute("projectName", projectName);
			request.setAttribute("downloadstatus", downloadstatus);
			request.setAttribute("projectInfos", projectInfos);
		}
		
		return mapping.findForward("queryProjectImage");
		
	}
	
	public ActionForward initUploadImage(ActionMapping mapping,ActionForm actionForm,
										 HttpServletRequest request,HttpServletResponse response){
			int id = 0;
			ProjectInfo projectInfo = new ProjectInfo();
			ProjectInfoFacade projectInfoFacade = ServiceBean.getInstance().getProjectInfoFacade();
			List<DataMap> list = null;
			
			
			try {
				list = projectInfoFacade.getProjectInfo(projectInfo);
				if(!list.isEmpty()){
					id = (Integer) list.get(0).getAt("id");
				}
				request.setAttribute("projectName", CommUtils.getPrintSelect(list, "projectName", "project_name", "id", "", 0));
			} catch (SystemException e) {
				e.printStackTrace();
				logger.error(request.getQueryString() + "  " + e);
			}finally{
				request.setAttribute("id",id);
				request.setAttribute("projectName", CommUtils.getPrintSelect(list, "projectName", "project_name", "id", "", 0));
		
			}
			
		return mapping.findForward("uploadImage");
	}
	
	public String getServerName() throws Exception {
		String serverName = "";
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
		serverName = pros.getProperty("servername");
		return serverName;
	}
	
	private String createDownLoadPath(String downloadpath,HttpServletRequest request) throws Exception{
		
		if("".equals(CommUtils.getSubStr(downloadpath,2))){
			downloadpath ="http://"+getServerName()+":"+request.getServerPort()
							+request.getContextPath()+"/upload/image/"+downloadpath;
		}else{
			downloadpath = "http://" + getServerName()+":"+request.getServerPort()
							+ request.getContextPath() + "/upload/iamge/"
					        + CommUtils.getSubStr(downloadpath,2);					
		}				
		return downloadpath;
	}
	
	public ActionForward uploadImage(ActionMapping mapping,ActionForm actionForm,
									  HttpServletRequest request,HttpServletResponse response){
		String projectId="";
		String name = "";
		String fileFormat = "";
		List<DataMap> list = null;
		Result result = new Result();
		ProjectImage vo = new ProjectImage();
		ProjectImageForm form = (ProjectImageForm)actionForm;
		ProjectImageFacade projectImageFacade = ServiceBean.getInstance().getProjectImageFacade();
		projectId = request.getParameter("projectId");
		
		String downloadpath1 = request.getParameter("downloadpath1");
		String downloadpath2 = request.getParameter("downloadpath2");
		String downloadpath3 = request.getParameter("downloadpath3");
		String downloadpath4 = request.getParameter("downloadpath4");
		String downloadpath5 = request.getParameter("downloadpath5");	

		try {
			BeanUtils.copyProperties(vo, form);
			vo.setCondition("projectId="+projectId);
			
			downloadpath1 = createDownLoadPath(downloadpath1,request);
			downloadpath2 = createDownLoadPath(downloadpath2,request);
			downloadpath3 = createDownLoadPath(downloadpath3,request);
			downloadpath4 = createDownLoadPath(downloadpath4,request);
			downloadpath5 = createDownLoadPath(downloadpath5,request);
			
			list = projectImageFacade.getProjectImage(vo);
			
			vo.setProjectId(projectId);
			
			String interPath = "";
			String path = "";	
			Hashtable<?, ?> files = form.getMultipartRequestHandler().getFileElements();//获取所有文件路径的枚举；
			if (files != null & files.size() > 0) {
				Enumeration<?> enums = files.keys();
				String fileKey = null;					
				String dir = request.getSession(true).getServletContext().getRealPath("/upload/image/");
				while (enums.hasMoreElements()) {
					fileKey = (String) (enums.nextElement());
					FormFile file = (FormFile) files.get(fileKey);
					if(!file.getFileName().isEmpty())
					{
						fileFormat = file.toString().substring(file.toString().lastIndexOf("."),file.toString().length());
						name=Long.toString(new Date().getTime())+fileFormat;
						CommUtils.createDateFile(dir); //创建当前文件夹，存在则返回文件名；
						InputStream in = file.getInputStream();
						path =  dir + "/" + name;   //输出文件路径
						File f = new File(path);
						if(f.exists()){
							f.delete();	
						}
						interPath = "http://" + getServerName()+":"+request.getServerPort()+ request.getContextPath() + "/upload/image/"+ name;
						if(fileKey.equals("downloadpath1")){
							vo.setDownLoadPath1(interPath);
						}else if(fileKey.equals("downloadpath2")){
							vo.setDownLoadPath2(interPath);
						}else if(fileKey.equals("downloadpath3")){
							vo.setDownLoadPath3(interPath);
						}else if(fileKey.equals("downloadpath4")){
							vo.setDownLoadPath4(interPath);
						}else if(fileKey.equals("downloadpath5")){
							vo.setDownLoadPath5(interPath);
						}
						OutputStream out = new FileOutputStream(path);
						out.write(file.getFileData(), 0, file.getFileSize());
							
						out.close();
						out = null;				
						in.close();		
					}
					
				}
				vo.setDownLoadStatus("1");
				if(list.isEmpty()){
					projectImageFacade.insertProjectImage(vo);
				}else
				{
					projectImageFacade.updateProjectImage(vo);
				}
			}

			result.setBackPage(HttpTools.httpServletPath(request,
					"queryProjectImage"));
			result.setResultCode("updates");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(request.getQueryString()+" "+e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryProjectImage"));
			if(e instanceof SystemException){
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result",result);
		}
		return mapping.findForward("result");
	}
	
	public ActionForward downloadImage(ActionMapping mapping,ActionForm actionform,
									   HttpServletRequest request,HttpServletResponse response){
		Result result = new Result();
		boolean isOnLine = true;
		try {
			String downloadUrl = request.getParameter("download");
					
			String dir = request.getSession(true).getServletContext().getRealPath("/upload/image/");
			String apkname = downloadUrl.substring(downloadUrl.lastIndexOf("/")+1);
			
			 File f = new File(dir+"/"+apkname);		 
		        if (!f.exists()) {
		            throw new SystemException("fail","noImagePath");
		        }
		        FileInputStream fin = new FileInputStream(f);
		        BufferedInputStream br = new BufferedInputStream(fin);
		        byte[] buf = new byte[1024*8];
		        int len = 0;

		        response.reset(); 
		        if (!isOnLine) { 
		            URL u = new URL("file:///" + dir);
		            response.setContentType(u.openConnection().getContentType());
		            response.setHeader("Content-Disposition", "inline; filename=" + apkname);
		            // 文件名应该编码成UTF-8
		        } else { // 纯下载方式
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
			result.setBackPage(HttpTools.httpServletPath(request,"queryProjectImage"));
			if (e instanceof SystemException) { /* 对已知异常进行解析 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 对未知异常进行解析，并全部定义成未知异常 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);				
		}
		return mapping.findForward("result");
	}
	
	public ActionForward updateProjectImage(ActionMapping mapping,ActionForm actionForm,
											HttpServletRequest request,HttpServletResponse response){
		int id = 0;
		int updNum = 0;
		String downLoadStatus = "";
		Result result = new Result();
		ProjectImage vo = new ProjectImage();
		ProjectImageFacade projectImageFacade = ServiceBean.getInstance().getProjectImageFacade();
		
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			id =Integer.valueOf(request.getParameter("id"));
		}	
		
		downLoadStatus = request.getParameter("downLoadStatus");
		vo.setDownLoadStatus(downLoadStatus);
		vo.setCondition("id="+id);

		try {
			 	updNum= projectImageFacade.updateProjectImage(vo);
			 	if(updNum==1){
			 		vo = new ProjectImage();
			 		vo.setCondition("pi.id ="+id);
			 		List<DataMap> list = projectImageFacade.getProjectImage(vo);
			 		if(!list.isEmpty()){
			 			JSONArray jsonArray = new JSONArray();
			 			JSONObject json = new JSONObject();
			 			json.put("id", Integer.valueOf(list.get(0).getAt("id").toString()));
			 			json.put("projecid", list.get(0).getAt("projectid").toString());
			 			json.put("downloadpath1",list.get(0).getAt("downloadpath1").toString());
			 			json.put("downloadpath2",list.get(0).getAt("downloadpath2").toString());
			 			json.put("downloadpath3",list.get(0).getAt("downloadpath3").toString());
			 			json.put("downloadpath4",list.get(0).getAt("downloadpath4").toString());
			 			json.put("downloadpath5",list.get(0).getAt("downloadpath5").toString());
			 			jsonArray.add(json);
			 			
			 			response.getWriter().write(jsonArray.toString());
			 		}
			 		
			 	}else{
			 		response.getWriter().write("fail");
			 	}
		} catch (SystemException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	public ActionForward deleteImagePath(ActionMapping mapping,ActionForm actionForm,
									 HttpServletRequest request,HttpServletResponse response){
		int id = 0;
		int sortPath = 0;
		String downLoadPath = "";
		int updNum = 0;
		PagePys pys = new PagePys();
		ProjectImage vo = new ProjectImage();
		ProjectImageForm form = (ProjectImageForm)actionForm;
		List<DataMap> list = null;
		ProjectImageFacade projectImageFacade = ServiceBean.getInstance().getProjectImageFacade();
	    if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
	    	id = Integer.valueOf(request.getParameter("id"));
	    }
	    
	    if(request.getParameter("sortPath")!=null && !"".equals(request.getParameter("sortPath"))){
	    	sortPath = Integer.valueOf(request.getParameter("sortPath"));
	    }
	    downLoadPath = request.getParameter("downloadpath");
	    vo.setCondition("pi.id="+id);
	    try {
			list = projectImageFacade.getProjectImage(vo);
			vo.setId(id);
			if(!list.isEmpty()){
				if(sortPath==1){
					vo.setDownLoadPath1(downLoadPath);
				}else if(sortPath==2){
					vo.setDownLoadPath2(downLoadPath);
				}else if(sortPath==3){
					vo.setDownLoadPath3(downLoadPath);
				}else if(sortPath==4){
					vo.setDownLoadPath4(downLoadPath);
				}else if(sortPath==5){
					vo.setDownLoadPath5(downLoadPath);
				}
				
				vo.setCondition("id="+id);
				updNum = projectImageFacade.updateProjectImagePath(vo);
				if(updNum==1){
					String dir = request.getSession(true).getServletContext().getRealPath("/upload/image/");
					String imageName = downLoadPath.substring(downLoadPath.lastIndexOf("/")+1);
					File file =new File(dir+"/"+imageName);	 
					if (!file.exists()) {
						throw new SystemException("fail","noImagePath");
					}else{
						if(file.isFile()){
							file.delete();
						}
					}
					response.getWriter().write("success");
				}else if(updNum<1){
					response.getWriter().write("fail");
				}			
			}
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ActionForward getProjectName(ActionMapping mapping,ActionForm form,
							HttpServletRequest request,HttpServletResponse response){
		int id = 0;
		int projectId = 0;
		List<DataMap> list = null;
		ProjectImage vo = new ProjectImage();
		ProjectImageFacade projectImageFacade = ServiceBean.getInstance().getProjectImageFacade();
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			id = Integer.valueOf(request.getParameter("id"));
		}
		
		if(request.getParameter("projectId")!=null && "".equals(request.getParameter("projectId"))){
			projectId = Integer.valueOf(request.getParameter("projectId"));
		}
		vo.setCondition("pi.id="+id+"");
		return null;
	}
	
	public ActionForward hideRecord(ActionMapping mapping,ActionForm actionForm,
									HttpServletRequest request,HttpServletResponse response){
		int id = 0;
		int updNum = 0;
		List<DataMap> list=null;
		ProjectImage vo = new ProjectImage();
		ProjectImageFacade projectImageFacade = ServiceBean.getInstance().getProjectImageFacade();
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			id = Integer.valueOf(request.getParameter("id"));
			vo.setCondition("id ="+id);
			vo.setHideLine("0");
			vo.setDownLoadStatus("0");
			try {
				updNum = projectImageFacade.updateProjectImage(vo);
				if(updNum>0){
					response.getWriter().write("success");
				}else{
					response.getWriter().write("fail");
				}
			} catch (SystemException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
			
		return null;
	}
}
