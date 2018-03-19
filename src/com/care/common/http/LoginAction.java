package com.care.common.http;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.app.LoginUser;
import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.sys.appinterfaces.AddDeviceFamilyAction;
import com.care.sys.userinfo.domain.UserInfo;
import com.care.sys.userinfo.domain.logic.UserInfoFacade;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.MD5;
import com.godoing.rose.log.LogFactory;


/**
 * ��¼action
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class LoginAction extends BaseAction {
	String logs = "登录系统";

	public String getLogs() {
		return logs;
	}
	
	Log logger = LogFactory.getLog(LoginAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Result result = new Result();
		try {
			String userCode = request.getParameter("code");
			String pwd = request.getParameter("paw");
			UserInfo user = new UserInfo();
			user.setUserCode(userCode);       
			user.setPassWrd(MD5.MD5(pwd));     //��������м���
			user.setTag(1);        //��֤״̬
			UserInfoFacade uf = ServiceBean.getInstance().getUserInfoFacade();
			if(userCode != null && pwd != null) {
			if (!uf.checkUser(user)) {
				result.setResultType("fail");
				result.setResultCode("loginCodeError");
			} else {
				/*   */
				List<DataMap> us = uf.getUserInfo(user);
				LoginUser u = new LoginUser(us.get(0));
				request.getSession().setAttribute(
						Config.SystemConfig.LOGINUSER, u);   

				String log = "";
				if("admin".equals(u.getGroupCode())){
					log = "登录系统";
				}else{
					log = "登录系统";
				}
//				SwitchInfo switchVo = new SwitchInfo();
//				List<DataMap> switchList = ServiceBean.getInstance().getSwitchInfoFacade().
//				                           getSwitchInfo(switchVo);
//				String tag = ""+switchList.get(0).getAt("tag");
				String ip = request.getRemoteAddr();
//				if("1".equals(tag)){
				  
			  	   ServiceBean.getInstance().getSysLogInfoFacade().insertSysLogInfo(u.getUserCode(), log,ip);
				//}
				response.sendRedirect("frame.jsp");
				return null;
			}
			}else {
				result.setResultType("fail");
				result.setResultCode("loginCodeError");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof SQLException) {
				// ���ڲ�û�в������ݿ��쳣�������׳�SQL�쳣��ͳһΪ��ݿ������쳣��
				result.setResultType("database");
				result.setResultCode("connectDBError");
			} else if (ex instanceof Exception) { // ����δ�����쳣
				result.setResultType("sysRunException");
				result.setResultCode("noKnownException");
			}
			Throwable cause = ex.getCause();
			logger.error("error", cause);
		} finally {
			result.setBackPage("index.jsp");
			result.setContext("TOP");
			request.setAttribute("result", result);
		}
		/* ��ת�ļ� */
		return mapping.findForward("result");
	}
}
