package com.care.sys.appinterfaces;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class getProjectStatusAction extends BaseAction {
	Log logger = LogFactory.getLog(getProjectStatusAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		JSONObject json = new JSONObject();
		String projectName = request.getParameter("project_name");
		if (projectName != null && !"".equals(projectName)) {
			try {
				ProjectInfo vo = new ProjectInfo();
				vo.setCondition("project_name=" + "'" + projectName + "'");
				List<DataMap> list = ServiceBean.getInstance()
						.getProjectInfoFacade().getProjectStatus(vo);
				if (list.size() > 0) {
					json.put("s_w", list.get(0)
							.getAt("project_status"));
					result = Constant.SUCCESS_CODE;// 成功

				} else {
					result = Constant.FAIL_CODE;// 0没有这个项目
				}
			} catch (Exception e) {

				e.printStackTrace();
				StringBuffer sb = new StringBuffer();
				Writer writer = new StringWriter();
				PrintWriter printWriter = new PrintWriter(writer);
				Throwable cause = e.getCause();
				while (cause != null) {
					cause.printStackTrace(printWriter);
					cause = cause.getCause();
				}
				printWriter.close();
				String resultSb = writer.toString();
				sb.append(resultSb);

				logger.error(e);
				result = Constant.EXCEPTION_CODE;
				json.put(Constant.EXCEPTION, sb.toString());

			}

		}
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

}
