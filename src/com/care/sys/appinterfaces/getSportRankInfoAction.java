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

public class getSportRankInfoAction extends BaseAction {
	Log logger = LogFactory.getLog(getSportRankInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		Date start=new Date();
		String href = request.getServletPath();
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		JSONObject object = JSONObject.fromObject(sb.toString());
		String imei=object.getString("imei");
		String time1 = object.getString("time1");
		String time2 = object.getString("time2");
	//	String type = object.getString("type");
		String bg = object.getString("belong_project");
		try {

			DeviceActiveInfoFacade facade = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade();

			if (time1 != null && !time1.equals("") && time2 != null
					&& !time2.equals("") && imei != null && !imei.equals("")
					&& bg != null && !bg.equals("")) {
				DeviceActiveInfo vo = new DeviceActiveInfo();
				DeviceActiveInfo co = new DeviceActiveInfo();
				co.setCondition("device_imei='" + imei
						+ "'and belong_project='" + bg + "'");
				List<DataMap> listA = facade.getDeviceActiveInfo(co);
				vo.setCondition("upload_time>='" + time1
						+ "'and upload_time<='" + time2 + "' and imei='" + imei
						+ "'and b_g='" + bg + "'");
				//vo.setOrderBy("distance");
				List<DataMap> list = facade.getSportTypeInfo(vo);
				if (list.size() <= 0) { // 说明后台没有记录
				result=Constant.SUCCESS_CODE;
					//	json.put("resultCode", 1); // 记录-1
				} else {
					/*JSONObject json1 = new JSONObject();
					JSONArray jsonArray1 = new JSONArray();*/
					int a = 0;
					double b=0.00;
					double c=0.00;
					int d=0;
					for (int i = 0; i < list.size(); i++) {
						a=a+Integer.valueOf(list.get(i).get("use_time")+"");
						b=jia(b,Double.parseDouble(list.get(i).get("distance")+""));
						c=jia(c,Double.parseDouble(list.get(i).get("energy")+""));
						d=d+Integer.valueOf(list.get(i).get("step")+"");
					}
					if(listA.size()>0){
						json.put("mubiao",listA.get(0).get("step")+"");
					}else{
						json.put("mubiao","10000");
					}
					json.put("shijian",a);
					json.put("juli",b);
					json.put("nengliang",c);
					json.put("bushu",d);
					result=Constant.SUCCESS_CODE;
				}
			} else {
				result=Constant.FAIL_CODE;
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
		json.put("request",href );
		insertVisit(href, bg, imei, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	//double 相加
		public double jia(double d1,double d2){
			BigDecimal bd1=new BigDecimal(Double.toString(d1));
			BigDecimal bd2=new BigDecimal(Double.toString(d2));
			return bd1.add(bd2).doubleValue();
		}

}
