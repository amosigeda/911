package com.care.sys.appinterfaces;

/**
 * �ֻ��ϴ���Ϣ�ӿ�
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.deviceLogin.domain.DeviceLogin;
import com.care.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class getSetAimStepAction extends BaseAction {
	Log logger = LogFactory.getLog(getSetAimStepAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		Date start = new Date();
		String href = request.getServletPath();
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		JSONObject object = JSONObject.fromObject(sb.toString());
		String imei = object.getString("serie_no");
		// String type = object.getString("type");// type1 获取type2设置
		String step = object.getString("step");
		// String type = object.getString("type");
		String bg = object.getString("belong_project");
		try {

			DeviceActiveInfoFacade facade = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade();

			DeviceActiveInfo vo = new DeviceActiveInfo();
			if (imei != null && !imei.equals("") && step != null
					&& !step.equals("") && bg != null && !bg.equals("")) {
				vo.setCondition("device_imei='" + imei
						+ "'and belong_project='" + bg + "'");
				List<DataMap> list = facade.getDeviceActiveInfo(vo);

				if (list.size() > 0) {
					vo.setStep(step);
					vo.setCondition("device_imei='" + imei
							+ "'and belong_project='" + bg + "'");
					int a = facade.updateDeviceActiveInfo(vo);
					if (a > 0) {

						result = Constant.SUCCESS_CODE;
						json.put("step", step);
					} else {
						result = Constant.FAIL_CODE;
					}
				}
			} else {

				result = Constant.FAIL_CODE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb1 = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb1.append(resultSb);

			logger.error(e);
			result = Constant.EXCEPTION_CODE;
		}
		json.put(Constant.RESULTCODE, result);
		json.put("request", href);
		insertVisit(href, bg, imei, 1, start, new Date(), json.toString()
				.getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	// double 相加
	public double jia(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.add(bd2).doubleValue();
	}

}
