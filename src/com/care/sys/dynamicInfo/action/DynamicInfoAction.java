package com.care.sys.dynamicInfo.action;

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
import com.care.sys.dynamicInfo.domain.DynamicInfo;
import com.care.sys.dynamicInfo.domain.logic.DynamicInfoFacade;
import com.care.sys.dynamicInfo.form.DynamicInfoForm;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class DynamicInfoAction extends BaseAction {
	Log logger = LogFactory.getLog(DynamicInfoAction.class);

	public ActionForward queryDynamicInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String href = request.getServletPath();
		Date start = new Date();
		Result result = new Result();// ���
		PagePys pys = new PagePys();// ҳ������
		DataList list = null; // ����ҳ��List ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();// �����ַ�����
		DynamicInfoFacade info = ServiceBean.getInstance()
				.getDynamicInfoFacade();
		try {
			DynamicInfoForm form = (DynamicInfoForm) actionForm;
			DynamicInfo vo = new DynamicInfo();
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
            String menuLeave=request.getParameter("menuLeave");
			/* ���û������ֶ� */
			form.setOrderBy("id");
			form.setSort("0");
			if (startTime != null && !"".equals(startTime)) {
				sb.append("substring(add_time,1,10) >= '" + startTime + "'");
			}
			if (endTime != null && !"".equals(endTime)) {
				if (sb.length() > 0) {
					sb.append(" and ");
				}
				sb.append("substring(add_time,1,10) <= '" + endTime + "'");
			}
			if(menuLeave != null && !"".equals(menuLeave)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("menuLeave ='"+menuLeave+"'");
				request.setAttribute("menuLeave", CommUtils.getMenuLeave(
						"menuLeave", Integer.parseInt(menuLeave)));
			}

			request.setAttribute("fNow_date", startTime);
			request.setAttribute("now_date", endTime);

			vo.setCondition(sb.toString());

			BeanUtils.copyProperties(vo, form);
			list = info.getDynamicInfoListByVo(vo);
			BeanUtils.copyProperties(pys, form);
			pys.setCounts(list.getTotalSize());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /*
													 * ����Ϊ����ҳ�棬���Գ������ת��ϵ
													 * ͳĬ��ҳ��
													 */
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
		return mapping.findForward("dynamicInfo");
	}
	
	public ActionForward initInsert(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("addDynamicInfo");
	}
	public ActionForward insertDynamicInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		try {
			DynamicInfoForm form=(DynamicInfoForm)actionForm; 
            DynamicInfoFacade facade=ServiceBean.getInstance().getDynamicInfoFacade();
            DynamicInfo vo=new DynamicInfo();
			
			BeanUtils.copyProperties(vo, form);    //把表单信息复制到用户信息中
			
           vo.setAddTime(new Date());
           vo.setDescribe(form.getDescribe());
           vo.setEffect(form.getEffect());
           vo.setMenuLeave(form.getMenuLeave());
           vo.setMenuRank(form.getMenuRank());
           vo.setmName(form.getmName());
           vo.setName(form.getName());
           vo.setSuperId(form.getSuperId());
           vo.setSubmenuNumber(form.getSubmenuNumber());
           vo.setDescribe(form.getDescribe());
			facade.insertDynamicInfo(vo);        //设置修改后，重新把用户信息重新映射到数据库中
			result.setBackPage(HttpTools.httpServletPath(request,  //插入成功后，跳转到原先页面
					"queryDynamicInfo"));
			result.setResultCode("inserts");    //设置插入Code
			result.setResultType("success");    //设置插入成功
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"initInsert"));
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
	
	public ActionForward initDynamicInfoUpdate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		DynamicInfo vo=new DynamicInfo();
		vo.setCondition("id=" + id);

		List<DataMap> list = ServiceBean.getInstance().getDynamicInfoFacade().getDynamicInfo(vo);

		if (list == null || list.size() == 0) {
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryDynamicInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("dynamicInfo", list.get(0)); // 把获取的信息用userInfo字符串相关联,在跳转getSession中获取userInfo中的属性值
																// ID
		return mapping.findForward("updateDynamicInfo");
	}
   
	public ActionForward updateDynamicInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		Result result = new Result();
		try {
			DynamicInfoForm form=(DynamicInfoForm)actionForm;
            DynamicInfo vo=new DynamicInfo(); 
			vo.setCondition("id='" + form.getId() + "'");
			BeanUtils.copyProperties(vo, form);
			ServiceBean.getInstance().getDynamicInfoFacade().updateDynamicInfo(vo);
			
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryDynamicInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryDynamicInfo"));
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
		return mapping.findForward("result");
	}
   
	public ActionForward deleteDynamicInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		try {
			String id = request.getParameter("id");
			DynamicInfo vo=new DynamicInfo();
			vo.setCondition("id ='" + id + "'"); // 设置用户账户
			ServiceBean.getInstance().getDynamicInfoFacade().deleteDynamicInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryDynamicInfo"));
			result.setResultCode("deletes");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryDynamicInfo"));
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
