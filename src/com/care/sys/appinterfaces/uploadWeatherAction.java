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

public class uploadWeatherAction extends BaseAction {
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
		String serieNo = object.getString("serie_no");
		String city = object.getString("city");
		String country = object.getString("country");
		String tempe = object.getString("temperature");
		String status = object.getString("status");
		String weather = object.getString("weather");
		belongProject = object.getString("belong_project");
		try {
			if (!serieNo.equals("") && serieNo != null && !country.equals("")
					&& country != null && !tempe.equals("")
					&& tempe != null && !status.equals("")
					&& status != null && !weather.equals("")
					&& weather != null) {
				DeviceActiveInfo vo = new DeviceActiveInfo();
				vo.setCondition("city ='" + city
						+ "' and country='" + country + "'"); 
				List<DataMap> deviceList = ServiceBean.getInstance()
						.getDeviceActiveInfoFacade().getCityWeatherInfo(vo);
				int size = deviceList.size();
				if (size > 0) {
					//vo.setBelongProject(belongProject);
					vo.setUploadTime(new Date()+"");
					vo.setTemperature(tempe);
					vo.setwStatus(status);
					vo.setWeather(weather);
					vo.setCondition("city ='" + city
							+ "' and country='" + country + "'"); 
					 ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().updateWeatherInfo(vo);
				
				}else{
					vo.setCountry(country);
					vo.setCity(city);
					vo.setTemperature(tempe);
					vo.setwStatus(status);
					vo.setWeather(weather);
					vo.setUploadTime(new Date()+"");
					vo.setBelongProject(belongProject);
					ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().insertWeatherInfo(vo);
				}
			} else {
				result = -1;
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
		/*
		 * json.put(Constant.RESULTCODE, result); json.put("cmd",
		 * "rUploadSport"); json.put("imei", serieNo);
		 */
		insertVisit(href, belongProject, "0", 0, start, new Date(), json
				.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

}
