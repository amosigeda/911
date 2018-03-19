package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
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
import com.care.sys.checkinfo.domain.CheckInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class updateAppNewAction extends BaseAction {

	Log logger = LogFactory.getLog(updateAppNewAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			// String user_id =
			// object.has("user_id")?object.getString("user_id"):"-1";
			String version_code = object.getString("version_code");// 版本号
			String belongProject = object.getString("belong_project");// 项目编号
			String packages = object.has("pa") ? object.getString("pa") : "-1";// 包名

			// get com.example.tabdemo
			// String user_id = request.getParameter("user_id");
			// String version_code = request.getParameter("version_code");
			// String belongProject = request.getParameter("belong_project");
			// String packages = request.getParameter("pa");

			CheckInfo vo = new CheckInfo();
			if (!version_code.equals("") && !belongProject.equals("")
					&& !packages.equals("")) {
				vo.setCondition("belong_project ='" + belongProject
						+ "' and status = '1' and package_name ='" + packages
						+ "' order by id desc limit 0,1");
				List<DataMap> List = ServiceBean.getInstance()
						.getCheckInfoFacade().getCheckInfo(vo);
				if (List.size() > 0) {
					String versionCode = List.get(0).get("version_code") + "";
					String name = List.get(0).get("version_name") + "";
					String path = List.get(0).get("download_path") + "";
					String cap = List.get(0).get("function_cap") + "";
					String type = List.get(0).get("up_type") + "";
					int banben = Integer.valueOf(versionCode);

					if (banben > Integer.valueOf(version_code)) {
						json.put("apk_version_code", versionCode);
						json.put("apk_version_name", name);
						json.put("download_url", path);
						json.put("apk_function_cap", cap);
						json.put("up_type", type);
						result = Constant.SUCCESS_CODE;
					} else {
						result = Constant.FAIL_CODE;
					}

				} else {
					result = Constant.FAIL_CODE;
				}
			} else {
				result = Constant.FAIL_CODE;
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
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());

		return null;
	}
}
