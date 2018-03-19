package com.care.sys.roleinfo.action;

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
import com.care.sys.roleinfo.domain.RoleInfo;
import com.care.sys.roleinfo.domain.logic.RoleInfoFacade;
import com.care.sys.roleinfo.form.RoleInfoForm;
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
public class DoRoleInfoAction extends BaseAction{
	Log logger = LogFactory.getLog(DoRoleInfoAction.class);

	public ActionForward queryRoleInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		RoleInfoFacade info = ServiceBean.getInstance().getRoleInfoFacade();
		try{
			RoleInfoForm form = (RoleInfoForm)actionForm;
            /*���ó�ʼ���������*/
            if (form.getOrderBy() == null) {
                form.setOrderBy("id");
            }

			RoleInfo vo = new RoleInfo();
			BeanUtils.copyProperties(vo,form);
            vo.setOrderSort("roleCode");
            list = info.getDataRoleInfoListByVo(vo);

			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.getTotalSize());
			/*���û������ֶ�*/

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
		return mapping.findForward("queryRoleInfo");
	}

    public ActionForward roleFuncInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
    	String roleCode = request.getParameter("roleCode");
    	String factoryCode = request.getParameter("factoryCode");
        request.setAttribute("trees",ServiceBean.getInstance().getRoleInfoFacade().getRoleFuncTree(roleCode)); //��ӦȨ�޵�����  Ȩ����
        request.setAttribute("roleCode",request.getParameter("roleCode"));    //��Ӧ���������
        request.setAttribute("factoryCode", request.getParameter("factoryCode"));
        return mapping.findForward("roleFuncInfo");
    }


	public ActionForward initInsert(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		/* ȡ���ֵ� */
		
		return mapping.findForward("insertRoleInfo");
	}

	public void validateInsert(RoleInfoForm form) throws SystemException {
		RoleInfo testvo = new RoleInfo();		
		/*��ݺϷ�����֤*/
        testvo.setRoleCode(form.getRoleCode());
        if (ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(testvo).size() > 0) {
            throw new SystemException("fail", "roleCodeError");
        }
        else{
        	testvo.setRoleName(form.getRoleName());
        	if (ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(testvo).size()>1)
        	{
        		throw new SystemException("fail", "roleNameError");
        	}
        }
	}

	public ActionForward insertRoleInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			RoleInfoForm form = (RoleInfoForm)actionForm;
			form.setRoleCode(form.getRoleName());
			validateInsert(form);
			RoleInfo vo = new RoleInfo();
			BeanUtils.copyProperties(vo,form);
			vo.setRoleCode(form.getRoleName());
			ServiceBean.getInstance().getRoleInfoFacade().insertRoleInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
			result.setResultCode("inserts");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
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

    public ActionForward insertRoleFuncInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
        Result result = new Result();
        try{
            String roleCode = request.getParameter("roleCode");
            String funcs = request.getParameter("funcs");
            String factoryCode = request.getParameter("factoryCode");
            ServiceBean.getInstance().getRoleInfoFacade().insertRoleFuncInfo(roleCode,funcs);
            response.getWriter().println("1");
            return null;
            /*RoleInfoForm form = (RoleInfoForm)actionForm;
            validateInsert(form);
            RoleInfo vo = new RoleInfo();
            BeanUtils.copyProperties(vo,form);
            getRoleInfoFacade().insertRoleInfo(vo);
            result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
            result.setResultCode("inserts");
            result.setResultType("success");*/
        }catch(Exception e){
            e.printStackTrace();
            logger.debug(request.getQueryString() + "  " + e);
            result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
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
		String roleCode = request.getParameter("roleCode");
		RoleInfo vo = new RoleInfo();
		/*������Ҫ����vo��ѯ���� һ��Ϊ�� vo.setId(new Integer(row[0]));*/
		vo.setCondition("roleCode ='"+roleCode+"'");
		List<DataMap> list = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(vo);
		if (list == null || list.size() == 0){/*������ݱ�ɾ��ʱ����,��ͨ����²��ᷢ��*/
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		/* ȡ���ֵ� */
//		DicInfo dicvo = new DicInfo();
//		dicvo.setDicGroup("roleType");
//		String roleType=(String)list.get(0).getAt("roleCode");
//		System.out.println(roleType);
//		dicvo.setCondition("dicValue='"+roleType+"'");
//		List<DataMap> dlist = ServiceBean.getInstance().getDicInfoFacade()
//				.getDicInfo(dicvo);
//		request.setAttribute("roleType", CommUtils.getPrintSelect(dlist,
//				"roleType", "dicCode", "dicValue", "" + (list.get(0)).getAt("roleType"), 0));
		
		request.setAttribute("roleInfo",list.get(0));
		return mapping.findForward("updateRoleInfo");
	}

	public void validateUpdate(RoleInfoForm form) throws SystemException {
		//RoleInfo testvo = new RoleInfo();
		/*��ݺϷ�����֤*/
	}

	public ActionForward updateRoleInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			RoleInfoForm form = (RoleInfoForm)actionForm;
			validateUpdate(form);
			RoleInfo vo = new RoleInfo();
			BeanUtils.copyProperties(vo,form);
			ServiceBean.getInstance().getRoleInfoFacade().updateRoleInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
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

	public ActionForward deleteRoleInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			//String[] row = HttpTools.requestArray(request, "crow");
			String roleCode = request.getParameter("roleCode");
			RoleInfo vo = new RoleInfo();
//			//for (int i = 0; i < row.length; i++) {
//				vo.setRoleCode(row[i]);
//				System.out.println(row[i]);
//				ServiceBean.getInstance().getRoleInfoFacade().deleteRoleInfo(vo);
//			//}
			vo.setRoleCode(roleCode);
			ServiceBean.getInstance().getRoleInfoFacade().deleteRoleInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
			result.setResultCode("deletes");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryRoleInfo"));
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
