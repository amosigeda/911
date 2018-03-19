package com.care.sys.appinterfaces;

/**
 * �ֻ��ϴ���Ϣ�ӿ�
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class setSportInfoAction extends BaseAction {
	Log logger = LogFactory.getLog(setSportInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		JSONObject json = new JSONObject();
		// JSONObject locationData = new JSONObject();
		// JSONArray trackData = new JSONArray();
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";

		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		JSONObject object = JSONObject.fromObject(sb.toString());
		String serial_number = object.getString("serial_number");
		String belong_project = object.getString("belong_project");
		try {

			DeviceActiveInfoFacade facade = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade();
			DeviceActiveInfo vo = new DeviceActiveInfo();
			vo.setCondition("imei = '" + serial_number + "' and b_g = '"
					+ belong_project + "' AND DATE(upload_time)=CURDATE()");
			List<DataMap> list = facade.getSportTypeInfo(vo);
			/*
			 * String sport_type = list.get(0).getAt("sport_type") + "";
			 * vo.setCondition("imei = '" + serial_number +
			 * "' AND DATE(upload_time)=CURDATE() LIMIT 0,1;"); List<DataMap> a
			 * = facade.getmaxtime(vo); vo.setCondition("imei = '" +
			 * serial_number +
			 * "' AND DATE(upload_time)=CURDATE() ORDER BY id DESC LIMIT 0,1;");
			 * List<DataMap> b = facade.getminitime(vo);
			 */
			//int size = list.size();
			double energy1 = 0.00;
			int useTime1 = 0;
			double distance1 = 0.00;

			double energy2 = 0.00;
			int useTime2 = 0;
			double distance2 = 0.00;

			double energy3 = 0.00;
			int useTime3 = 0;
			double distance3 = 0.00;

			double energy4 = 0.00;
			int useTime4 = 0;
			double distance4 = 0.00;

			double energy5 = 0.00;
			int useTime5 = 0;
			double distance5 = 0.00;
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String sport_type1 = list.get(i).getAt("sport_type") + "";

					if (sport_type1.equals("1")) {
						energy1 = energy1
								+ Double.parseDouble((list.get(i).getAt("energy") + ""));
						useTime1 = useTime1
								+ Integer
										.parseInt((list.get(i).getAt("use_time") + ""));
						distance1 = distance1
								+ Double.parseDouble((list.get(i).getAt("distance") + ""));
					} else if (sport_type1.equals("2")) {
						energy2 = energy2
								+ Double.parseDouble((list.get(i).getAt("energy") + ""));
						useTime2 = useTime2
								+ Integer
										.parseInt((list.get(i).getAt("use_time") + ""));
						distance2 = distance2
								+ Double.parseDouble((list.get(i).getAt("distance") + ""));
					} else if (sport_type1.equals("3")) {
						energy3 = energy3
								+ Double.parseDouble((list.get(i).getAt("energy") + ""));
						useTime3 = useTime3
								+ Integer
										.parseInt((list.get(i).getAt("use_time") + ""));
						distance3 = distance3
								+ Double.parseDouble((list.get(i).getAt("distance") + ""));
					} else if (sport_type1.equals("4")) {
						energy4 = energy4
								+ Double.parseDouble((list.get(i).getAt("energy") + ""));
						useTime4 = useTime4
								+ Integer
										.parseInt((list.get(i).getAt("use_time") + ""));
						distance4 = distance4
								+ Double.parseDouble((list.get(i).getAt("distance") + ""));
					} else if (sport_type1.equals("5")) {
						energy5 = energy5
								+ Double.parseDouble((list.get(i).getAt("energy") + ""));
						useTime5 = useTime5
								+ Integer
										.parseInt((list.get(i).getAt("use_time") + ""));
						distance5 = distance5
								+ Double.parseDouble((list.get(i).getAt("distance") + ""));
					}
				}
				json.put("energy1", energy1);
				json.put("energy2", energy2);
				json.put("energy3", energy3);
				json.put("energy4", energy4);
				json.put("energy5", energy5);
				json.put("useTime1", useTime1);
				json.put("useTime2", useTime2);
				json.put("useTime3", useTime3);
				json.put("useTime4", useTime4);
				json.put("useTime5", useTime5);
				json.put("distance1", distance1);
				json.put("distance2", distance2);
				json.put("distance3", distance3);
				json.put("distance4", distance4);
				json.put("distance5", distance5);
				result = Constant.SUCCESS_CODE;
				json.put("resultCode", result);
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
			json.put("resultCode", result);
		}
		// json.put("mode", trackData);
		// System.out.println(trackData);
		// insertVisit(href, belongProject, serieNo, 1,start,new
		// Date(),json.toString().getBytes("utf-8").length);
		json.put("request", href);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

}
