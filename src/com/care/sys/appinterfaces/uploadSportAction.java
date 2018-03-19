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
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.care.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class uploadSportAction extends BaseAction {
	Log logger = LogFactory.getLog(doDisturbAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();

		String belongProject = "";

		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		JSONObject object = JSONObject.fromObject(sb.toString());
		String serieNo = object.getString("imei");
		String sportType = object.getString("sport_type");
		String distance = object.getString("distance");
		String energy = object.getString("energy");
		String heartRate = object.getString("heart_rate");
		String useTime = object.getString("use_time");
		String step=object.getString("step");
		belongProject = object.getString("belong_project");
		try {
			if (!serieNo.equals("")) {
				DeviceActiveInfo vo = new DeviceActiveInfo();
			/*	vo.setCondition("device_imei ='" + serieNo
						+ "' and belong_project='" + belongProject + "'"); // 鍒ゅ畾鏉′欢
				List<DataMap> deviceList = ServiceBean.getInstance()
						.getDeviceActiveInfoFacade().getDeviceActiveInfo(vo);
				int size = deviceList.size();
				if (size > 0) {}*/
					vo.setBelongProject(belongProject);
                  vo.setuDate(new Date());
					vo.setUseTime(useTime);
					vo.setHeartRate(heartRate);
					vo.setEnergy(energy);
					vo.setDistance(distance);
					vo.setSportType(sportType);
					vo.setDeviceImei(serieNo);
					vo.setStep(step);
					
					int a = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().insertSportInfo(vo);
					if (a > 0) {
						result = 0;
					} else {
						result = -1;
					}
				
			} else {
				result = -3;
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
			result = -2;
			json.put(-2, sb1.toString());
		}
		json.put(Constant.RESULTCODE, result);
		json.put("cmd", "rUploadSport");
		json.put("imei", serieNo);
		insertVisit(href, belongProject, "0", 0, start, new Date(), json
				.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

}
