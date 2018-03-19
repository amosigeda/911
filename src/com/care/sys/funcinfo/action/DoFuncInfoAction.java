package com.care.sys.funcinfo.action;

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
import com.care.sys.funcinfo.domain.FuncInfo;
import com.care.sys.funcinfo.domain.logic.FuncInfoFacade;
import com.care.sys.funcinfo.form.FuncInfoForm;
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
public class DoFuncInfoAction extends BaseAction{
	Log logger = LogFactory.getLog(DoFuncInfoAction.class);

	public ActionForward queryFuncInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Date start = new Date();
		String href = request.getServletPath();
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		FuncInfoFacade info = ServiceBean.getInstance().getFuncInfoFacade();
		try{
			FuncInfoForm form = (FuncInfoForm)actionForm;
			/*���ó�ʼ���������*/
            /*���ó�ʼ���������*/
            if (form.getOrderBy() == null) {
                form.setOrderBy("funcCode");
            }

			FuncInfo vo = new FuncInfo();
			BeanUtils.copyProperties(vo,form);
            vo.setOrderSort("funcCode");
			list = info.getDataFuncInfoListByVo(vo);
			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.getTotalSize());
			/*���û������ֶ�*/
			request.setAttribute("trees",info.getAllFuncTree());
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
		try {
			CommUtils.getIntervalTime(start, new Date(), href);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward("queryFuncInfo");
	}

	public ActionForward initInsert(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		return mapping.findForward("insertFuncInfo");
	}

	public void validateInsert(FuncInfoForm form) throws SystemException {
		//FuncInfo testvo = new FuncInfo();
		/*��ݺϷ�����֤*/
	}

	public ActionForward insertFuncInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			FuncInfoForm form = (FuncInfoForm)actionForm;
			validateInsert(form);
			FuncInfo vo = new FuncInfo();
			BeanUtils.copyProperties(vo,form);
			ServiceBean.getInstance().getFuncInfoFacade().insertFuncInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,"queryFuncInfo"));
			result.setResultCode("inserts");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryFuncInfo"));
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
		FuncInfo vo = new FuncInfo();
		/*������Ҫ����vo��ѯ���� һ��Ϊ�� vo.setId(new Integer(row[0]));*/
		List<DataMap> list = ServiceBean.getInstance().getFuncInfoFacade().getFuncInfo(vo);
		if (list == null || list.size() == 0){/*������ݱ�ɾ��ʱ����,��ͨ����²��ᷢ��*/
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,"queryFuncInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("funcInfo",list.get(0));
		return mapping.findForward("updateFuncInfo");
	}

	public void validateUpdate(FuncInfoForm form) throws SystemException {
		//FuncInfo testvo = new FuncInfo();
		/*��ݺϷ�����֤*/
	}

	public ActionForward updateFuncInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			FuncInfoForm form = (FuncInfoForm)actionForm;
			validateUpdate(form);
			FuncInfo vo = new FuncInfo();
			BeanUtils.copyProperties(vo,form);
			ServiceBean.getInstance().getFuncInfoFacade().updateFuncInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,"queryFuncInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryFuncInfo"));
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

	public ActionForward deleteFuncInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			String[] row = HttpTools.requestArray(request, "crow");
			FuncInfo vo = new FuncInfo();
			for (int i = 0; i < row.length; i++) {
				ServiceBean.getInstance().getFuncInfoFacade().deleteFuncInfo(vo);
			}
			result.setBackPage(HttpTools.httpServletPath(request,"queryFuncInfo"));
			result.setResultCode("deletes");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryFuncInfo"));
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

}
