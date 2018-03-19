package com.care.sys.devicephoneinfo.action;

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
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.care.sys.devicephoneinfo.domain.logic.DevicePhoneInfoFacade;
import com.care.sys.devicephoneinfo.form.DevicePhoneInfoForm;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class DevicePhoneInfoAction extends BaseAction {
	
	Log logger = LogFactory.getLog(DevicePhoneInfoAction.class);

	
	public ActionForward queryDevicePhoneInfo(ActionMapping mapping,ActionForm actionForm,
											  HttpServletRequest request,
											  HttpServletResponse response){
		
		String href = request.getServletPath();
		List<DataMap> list = null;
		Result result = new Result();
		PagePys pys = new PagePys();
		DevicePhoneInfo vo = new DevicePhoneInfo();
		DevicePhoneInfoForm form = (DevicePhoneInfoForm)actionForm;
		DevicePhoneInfoFacade devicePhoneInfoFacade = ServiceBean.getInstance().getDevicePhoneInfoFacade();
		try {
			form.setOrderBy("id");
			form.setSort("0");
			
			BeanUtils.copyProperties(vo,form);
			
			list = devicePhoneInfoFacade.getDevicePhoneInfoListByVo(vo);
			BeanUtils.copyProperties(pys, form);
			pys.setCounts(list.size());;
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(request.getQueryString()+" "+e);
			result.setBackPage(Config.ABOUT_PAGE);
			if(e instanceof SystemException){
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			}else{
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally{
			request.setAttribute("result", result);
			request.setAttribute("pageList",list);
			request.setAttribute("PagePys", pys);
		}
		
		return mapping.findForward("queryDevicePhoneInfo");	
	}
}
