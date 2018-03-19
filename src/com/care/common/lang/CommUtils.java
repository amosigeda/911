package com.care.common.lang;

import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.care.app.LoginUser;
import com.care.common.config.ServiceBean;
import com.care.common.config.Config.SystemConfig;
import com.care.sys.funcinfo.domain.FuncInfo;
import com.care.sys.monitorinfo.domain.MonitorInfo;
import com.care.sys.roleinfo.domain.RoleInfo;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.lang.DataMap;

public class CommUtils {
//	
//
	/* 字典表 */
	static List<DataMap> dicList = new ArrayList<DataMap>();
	static List<DataMap> provinceList = new ArrayList<DataMap>();
	
	static {
		try {
			/* 加载字典表 */
//			DicInfo vo = new DicInfo();
//			vo.setOrderBy("dicSort");
//			dicList = ServiceBean.getInstance().getDicInfoFacade().getDicInfo(vo);
//			vo.setCondition("dicGroup = 'province'");
//			provinceList = ServiceBean.getInstance().getDicInfoFacade().getDicInfo(vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public CommUtils() {
	}

	/* 返回字典表数据 */
	public static List<DataMap> getDict(String dicGroup) {
		List<DataMap> rs = new ArrayList<DataMap>();
		try {
			for (int i = 0; i < dicList.size(); i++) {
				DataMap map = dicList.get(i);
				String gic = (String) map.get("dicGroup");
				if (gic.equals(dicGroup)) {
					rs.add(map);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}
	//根据省的值得到省名称
	public static String getProvinceName(String value) {
		String name = "";
		try {
			for (int i = 0; i < provinceList.size(); i++) {
				DataMap map = provinceList.get(i);
				String gic = (String) map.get("dicValue");
				String provinceName = (String)map.get("dicCode");
				if (gic.equals(value)) {
					name = provinceName;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return name;
	}
//
	/* 返回字典表数据 */
	public static String getDictValueByGroup(String dicGroup, String code) {
		String rs = null;
		try {
			for (int i = 0; i < dicList.size(); i++) {
				DataMap map = dicList.get(i);
				String gic = (String) map.get("dicGroup");
				String codes = (String) map.get("dicCode");
				if (gic.equals(dicGroup)) {
					if (code.equals(codes)) {
						return (String) map.get("dicValue");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}
//
//	/* 返回用户一级菜单 */
	public static void getMenuTop(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PrintWriter p = response.getWriter();
			// 取用户信息
			LoginUser user = (LoginUser) request.getSession().getAttribute(
					SystemConfig.LOGINUSER);
			FuncInfo vo = new FuncInfo();
			vo.setCondition(" levels='1' and  funccode in "
					+ "(select funccode from rolefuncinfo where rolecode in "
					+ "(select groupcode from userinfo where usercode='"
					+ user.getUserCode() + "') and userCode ='"+user.getCode()+"') order by funcSort");
			
			List<DataMap> list = ServiceBean.getInstance().getFuncInfoFacade()
					.getFuncInfo(vo);
			for (int i = 0; list != null && i < list.size(); i++) {
				DataMap map = list.get(i);
				String name = (String) map.get("funcName");
				String code = (String) map.get("funcCode");
				//<li class=\"view_cur\"></li> onmouseout=\"javascript:mouseOut("+i+")\"
				p.print("<li class=\"view_no_cur\" id=\"ch_mouse"+i+"\"><a id=\"cd"+i+"\" href=\"javascript:clmenu('" + code + "','" + name
						+ "')\" onclick=\"javascript:mouseOver("+i+","+list.size()+")\" >" + name + "</a></li>\n");
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void printUserMenu(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(
					SystemConfig.LOGINUSER);
			PrintWriter p = response.getWriter();
			if (user == null) {
				p.print("alert('系统等待超时,请先退出!!!!!!')");
				return;
			}
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			if (code == null)
				return;
//			p.print("m = new dTree('m');\r\t");
//			p.print("m.add(0,-1,'"+name+"');\r\t"); // 创建根 " + name + "
		    StringBuffer dt = new StringBuffer("m = new dTree('m');\r\t");
		    dt.append("m.add(0,-1,'');\r\t"); //创建根
			FuncInfo vo = new FuncInfo();
			// levels='2' and 
			vo.setCondition("levels='2' and superCode='"+code+"'");
			List<DataMap> voList = ServiceBean.getInstance().getFuncInfoFacade()
			                     .getFuncInfo(vo);
			String querySql = "";
			for(int i=0;i<voList.size();i++){
				String funcCode = ""+voList.get(i).getAt("funcCode");
				if(querySql != null&&!"".equals(querySql)){
	        		   querySql += " OR ";
	        	    }
				querySql += "superCode ='"+funcCode+"'";
			}
			if(querySql != null&&!"".equals(querySql)){
     		   querySql += " OR ";
     	    }
			querySql +="funcCode ='"+code+"' OR ";
			vo.setCondition("("+querySql+"(levels='2' and superCode ='" + code
					+ "')) and  funccode in "
					+ "(select funccode from rolefuncinfo where rolecode in "
					+ "(select groupcode from userinfo where usercode='"
					+ user.getUserCode() + "') and userCode ='"+user.getCode()+"') order by funcSort");;

			List<DataMap> list = ServiceBean.getInstance().getFuncInfoFacade()
					.getFuncInfo(vo);
			if (list.size() > 0 ) {
	            dtRoleFunc(list, "super", dt, 0);
	        }
			dt.append("document.write(m);");
			p.print(dt.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	int dtRoleFunc(List<DataMap> list, String sCode, StringBuffer sb, int treeid) {
        int s = treeid; //保留父结点treeid
        for (int i = 0; i < list.size(); i++) {
            DataMap map = list.get(i);
            if (sCode.equals ((String) map.get("superCode"))) {
                treeid++; //父结点下结点 +1
                String fDo = (String)map.get("funcDo");
                sb.append("m.add("+treeid+","+s+",'"+
                          map.get("funcName") +
                    "','"+fDo+"','','mainFrame','');\r\t");
                treeid = dtRoleFunc(list, (String) map.get("funcCode"), sb, treeid);
            }
        }
        return treeid;
    }
//	/**
//	 * 输出下拉框
//	 * 
//	 * @param list
//	 *            ArrayList 数据
//	 * @param names
//	 *            String 控件名称
//	 * @param tname
//	 *            String 数据对应的表字段名称 text
//	 * @param tvalue
//	 *            String 数据对应的表字段名称 vlaue
//	 * @param isSelect
//	 *            int 是否选择
//	 * @return String
//	 */
	public static String getPrintSelect(List<DataMap> list, String names, String tname,
			String tvalue, String svalue, int isSelect) {
		if (list == null || list.size() == 0) {
			return "";
		}
		StringBuffer sf = new StringBuffer();
		sf.append("<select name='" + names + "' id='"+ names +"'>\n");
		Iterator<DataMap> it = list.iterator();
		while (it.hasNext()) {
			DataMap map = it.next();
			String value = "" + map.getAt(tvalue);
			String name = "" + map.getAt(tname);
			
			String selected = "";
			if (value.equals(svalue)) {
				selected = "selected";
			}
			sf.append("\t\t\t<option value='" + value + "' " + selected + ">"
					+ name + "</option>\n");
		}
		sf.append("\t\t\t</select>");
		return sf.toString();
	}
	
	public static String getPrintRegisterSelect(List<DataMap> list, String names, String tname,
			String tvalue, int isSelect) {
		if (list == null || list.size() == 0) {
			return "";
		}
		StringBuffer sf = new StringBuffer();
		sf.append("<select name='" + names + "' id='"+ names +"'>\n");
		sf.append("\t\t\t<option value='" + 0 + "' " + "selected" + ">"
				+ "全部" + "</option>\n");
		Iterator<DataMap> it = list.iterator();
		while (it.hasNext()) {
			DataMap map = it.next();
			String value = "" + map.getAt(tvalue);
			String name = "" + map.getAt(tname);
			
			String selected = "";
			if (value.equals(Integer.toString(isSelect))) {
				selected = "selected";
			}
			sf.append("\t\t\t<option value='" + value + "' " + selected + ">"
					+ name + "</option>\n");
		}
		sf.append("\t\t\t</select>");
		return sf.toString();
	}
	
	
	public static String getPrintChannelSelect(List<DataMap> list, String names, String tname,
			String tvalue, String svalue, int isSelect, String onchange) {
		if (list == null || list.size() == 0) {
			return "";
		}
		StringBuffer sf = new StringBuffer();
		sf.append("<select name='" + names + "' id='type' onchange='"+onchange+"'>\n");
		if (1 == isSelect) {
			sf.append("\t\t\t<option value=''>请选择</option>\n");
		}else if(2 == isSelect){
			sf.append("\t\t\t<option value='未选择广告主'>未选广告主</option>\n");
		}
		Iterator<DataMap> it = list.iterator();
		while (it.hasNext()) {
			DataMap map = it.next();
			String value = "" + map.getAt(tvalue);
			String name = "" + map.getAt(tname);
			String selected = "";
			if (value.equals(svalue)) {
				selected = "selected";
			}
			sf.append("\t\t\t<option value='" + value + "' " + selected + ">"
					+ name + "</option>\n");
		}
		sf.append("\t\t\t</select>");
		return sf.toString();
	}
	public static String getPrintTable(List<String> list,int column,String svalue) {
		if(list == null || list.size() == 0) {
			return "";
		}		
		StringBuffer sf = new StringBuffer();
		int row = list.size() / column;
		if(list.size() % column != 0) {//每行有几列
			row = row + 1;//分成几行
		}
       sf.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tr_5\">");
        for(int i = 0; i < row; i++) {
        	sf.append("<tr class=\"tr_5\">");
        	int endIndex = (i+1)*column;
        	if(endIndex > list.size()) {
        		endIndex = list.size();
        	}
        	for(int j = i * column; j < endIndex; j++) {
        		String ip = list.get(j);        		
        		String s1 = svalue;
        		String[] array = s1.split(",");
        		String checked = "";
        		for(int n=0; n<array.length ;n++){
        		if(ip.equals(array[n])) {
        			checked = "checked";
        		}
        		}
//        		if(ip.equals("全国")){
//        		sf.append("<td align=\"center\" width=\"2%\"><input name=\"crow\" type=\"checkbox\" id=\"crow\" checked=\"true\" value='"+value+"' ></td><td width=\"18%\">"+ip+"</td>");
//        		}else{
        		sf.append("<td align=\"center\" width=\"2%\"><input name=\"crow\" type=\"checkbox\" id=\"crow\" value='"+ip+"' "+checked+"></td><td width=\"18%\">"+ip+"</td>");
//        	}
        		
        	}
        	sf.append("</tr>");
        }
        sf.append("</table>");
		return sf.toString();
	}
	public static String getPrintTable(List<String> list,List<String> listId, int column,String svalue) {
		if(list == null || list.size() == 0) {
			return "";
		}		
		StringBuffer sf = new StringBuffer();
		int row = list.size() / column;
		if(list.size() % column != 0) {//每行有几列
			row = row + 1;//分成几行
		}
       sf.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tr_5\">");
        for(int i = 0; i < row; i++) {
        	sf.append("<tr class=\"tr_5\">");
        	int endIndex = (i+1)*column;
        	if(endIndex > list.size()) {
        		endIndex = list.size();
        	}
        	for(int j = i * column; j < endIndex; j++) {
        		String ip = list.get(j);
        		String value = listId.get(j);
        		String s1 = svalue;
        		String[] array = s1.split(",");
        		String checked = "";
        		for(int n=0; n<array.length ;n++){
        		if(ip.equals(array[n])) {
        			checked = "checked";
        		}
        		}
        		sf.append("<td align=\"center\" width=\"2%\"><input name=\"crow\" type=\"checkbox\" id=\"crow\" value='"+ip+"' "+checked+"></td><td width=\"18%\">"+value+"</td>");
//        	}
        		
        	}
        	sf.append("</tr>");
        }
        sf.append("</table>");
		return sf.toString();
	}
	public static String getPrintTable2(List<String> list,int column,String svalue) {
		if(list == null || list.size() == 0) {
			return "";
		}		
		StringBuffer sf = new StringBuffer();
		int row = list.size() / column;
		if(list.size() % column != 0) {//每行有几列
			row = row + 1;//分成几行
		}
       sf.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"tbl_11\">");
        for(int i = 0; i < row; i++) {
        	sf.append("<tr class=\"tr_11\">");
        	int endIndex = (i+1)*column;
        	if(endIndex > list.size()) {
        		endIndex = list.size();
        	}
        	for(int j = i * column; j < endIndex; j++) {
        		String s = list.get(j);
        		String ip = s.split("#")[0];
        		String value = s.split("#")[1];        		
        		String s1 = svalue;
        		String[] array = s1.split(",");
        		String checked = "";
        		for(int n=0; n<array.length ;n++){
        		if(ip.equals(array[n])) {
        			checked = "checked";
        		}
        		}
//        		if(ip.equals("全国")){
//        		sf.append("<td align=\"center\" width=\"2%\"><input name=\"crow\" type=\"checkbox\" id=\"crow\" checked=\"true\" value='"+value+"' ></td><td width=\"18%\">"+ip+"</td>");
//        		}else{
        		sf.append("<td align=\"center\" width=\"2%\"><input name=\"crow\" type=\"checkbox\" id=\"crow\" value='"+value+"' "+checked+"></td><td width=\"18%\">"+ip+"</td>");
//        	}
        		
        	}
        	sf.append("</tr>");
        }
        sf.append("</table>");
		return sf.toString();
	}
	
	/**
	 * 打印attri
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param req
	 *            String
	 */
	public static void printReqByAtt(HttpServletRequest request,
			HttpServletResponse response, String req) {
		Object obj = request.getAttribute(req);// 取得置
		try {
			if (obj == null)
				response.getWriter().print("");
			response.getWriter().print(obj.toString());
		} catch (Exception e) {

		}
	}


	/**
	 * 打印checked
	 * 
	 * @param request
	 * @param response
	 * @param req
	 * @param at
	 */
	public static void printReqByChecked(HttpServletRequest request,
			HttpServletResponse response, String req, String at) {
		Object obj = request.getAttribute(req);// 取得值
		if (obj == null)
			obj = "";
		try {
			if (at.equals(obj))
				response.getWriter().print("checked");
			response.getWriter().print("");
		} catch (Exception e) {
		}
	}


	public static String getRoleCodeByType(String type) {
		RoleInfo vo = new RoleInfo();
		vo.setRoleType(type);
		String rs = "";
		try {
			List<DataMap> list = ServiceBean.getInstance().getRoleInfoFacade()
					.getRoleInfo(vo);
			for (int i = 0; i < list.size(); i++) {
				String rCode = "" + (list.get(i)).getAt("roleCode");
				if ("".equals(rs)) {
					rs = " '" + rCode + "' ";
				} else {
					rs = rs + " , '" + rCode + "' ";
				}
			}
		} catch (Exception e) {

		}
		if (!"".equals(rs)) {
			rs = " (" + rs + ") ";
		}
		return rs;
	}

	/**
	 * 取得一个月最大天
	 * 
	 * @param str
	 * @return
	 */
	public static int getMaxDayByMonth(String str) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(format.parse(str));
			return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 28;
	}
	
	public static String getCurDateBefore(int l) {
		SimpleDateFormat DF_NORMAL_01 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setGregorianChange(new Date());
		calendar.add(Calendar.HOUR, l);
		return DF_NORMAL_01.format(calendar.getTime());
	}

	public static String getCurDateBeforeByDay(int l) {
		SimpleDateFormat DF_NORMAL_01 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setGregorianChange(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, l);
		return DF_NORMAL_01.format(calendar.getTime());
	}

	/**
	 * 获取周几
	 */
	public static int getDay(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		if(w < 0){
			w = 0;
		}
		return w;
	}
	
	/**
	 * 获得string的当前时间
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurDateStr() {
		SimpleDateFormat DF_NORMAL_01 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return DF_NORMAL_01.format(new Date());
	}
	/**
	 * 获得当前年月字符串 2010-11
	 * @return
	 */
	public static String getCurYearMonthStr(Date date){
		SimpleDateFormat YM=new SimpleDateFormat("yyyy-MM");
		return YM.format(date);
	}

	public static String getCurTimeStr() {
		SimpleDateFormat DF_NORMAL_01 = new SimpleDateFormat("yyyy-MM-dd");
		return DF_NORMAL_01.format(new Date());
	}

	public static String getYesterdayTimeStr() {
		SimpleDateFormat DF_NORMAL_01 = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setGregorianChange(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return DF_NORMAL_01.format(calendar.getTime());
	}

	/**
	 * 获得当前小时
	 * @return
	 */
	public static int getCurDateByHour() {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			return calendar.get(Calendar.HOUR_OF_DAY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 12;
	}
	public static String getPrintDynamicTable(String[] s,String[] missionCodes, String username) {
		if(s == null || s.length == 0) {
			return "";
		}
		if(s.length == 1 && (s[0] == null || s[0] == "" || "".equals(s[0]))) {
			return "";
		}
		StringBuffer sf = new StringBuffer();
        sf.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"tbl_1\" id=\"iptable\">");
        sf.append("<tr class=\"tr_5\">");
        sf.append("<td width=\"10%\"><div align=\"center\">序号</div></td><td width=\"40%\"><div align=\"center\">任务名称</div></td><td width=\"28%\">删除任务</td></tr>");
        for(int i = 0; i < s.length; i++) {
        	sf.append("<tr class=\"tr_1\">");
        		sf.append("<td width=\"10%\">"+(i+1)+"</td><td width=\"38%\"><div align=\"center\"><input name=\"task\" type=\"text\"  readonly=\"readonly\" value="+s[i]+" class=\"txt_noborder\" maxlength=\"10\" onMouseOver=\"messageBox(event,'"+missionCodes[i]+"');\" size=40></div></td><td width=\"28%\"><a onclick=\"delRow(this)\" href=\"#\" style=\"color:#0000FF\">删除该行</a></td></tr>");
        	}
//        	sf.append("<tr class=\"tr_0\">");
//        	sf.append("<td  colspan=\"4\"><input type=\"button\" name=\"add\" accesskey=\"A\" tabindex=\"A\" value=\"增加IP(A)\" class=\"but_1\" onclick=\"addRow()\"><input type=\"submit\" name=\"ok\" accesskey=\"y\" tabindex=\"y\"  value=\"确 定(Y)\" class=\"but_1\">");
//            sf.append("<input type=\"button\" name=\"back\" accesskey=\"b\" tabindex=\"b\" value=\"返 回(B)\" class=\"but_1\" onclick=\"location='doUserInfo.do?method=queryFixmanInfo'\">");
//            sf.append("</td></tf>");
        sf.append("</table>");
		return sf.toString();
	}
	public static List<DataMap> getPagePys(PagePys pys,List<DataMap> list){
		int size=pys.getPageSize();
		int index=pys.getIndex();
        pys.setCounts(list.size());
        int counts=pys.getCounts();
		List<DataMap> list2=new ArrayList<DataMap>();
		for (int i=(index-1)*size;i<size*index;i++)
		{
			if(i>=counts)
			{
				break;
			}
			else
			{
				list2.add(list.get(i));	
			}
		}
		return list2;
	}
	public static String getCurrTime(String startTime,String endTime){
		String queryValue = "";
		String querySql = "";
		Calendar calendar = new GregorianCalendar();
		int mouth = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		int min = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		String minDate = "";
		String todayDate = "";
		if(mouth >= 10){
			minDate = String.valueOf(year)+"-"+String.valueOf(mouth)+"-0"+String.valueOf(min);
			if(today >= 10){
				todayDate = String.valueOf(year)+"-0"+String.valueOf(mouth)+"-"+String.valueOf(today);
			}else{
				todayDate = String.valueOf(year)+"-0"+String.valueOf(mouth)+"-0"+String.valueOf(today);
			}
		}else{
			minDate = String.valueOf(year)+"-0"+String.valueOf(mouth)+"-0"+String.valueOf(min);
			if(today >= 10){
				todayDate = String.valueOf(year)+"-0"+String.valueOf(mouth)+"-"+String.valueOf(today);
			}else{
				todayDate = String.valueOf(year)+"-0"+String.valueOf(mouth)+"-0"+String.valueOf(today);
			}
		}			 
		if(startTime != null && !"".equals(startTime)){
			queryValue = startTime;
			querySql += "date_time between '"+queryValue+
	        " 00:00:00' and '"+endTime+" 23:59:59'";
			return querySql+","+queryValue+","+endTime;
		}else{
		    querySql += "date_time between '"+minDate+
            " 00:00:00' and '"+todayDate+" 23:59:59'";
		    return querySql+","+minDate+","+todayDate;
		}
	}
	/*
	 * 获取查询间隔时间
	 */
	public static void getIntervalTime(Date start,Date end,String href) throws Exception{
		String tag = "1";	
		
		if("1".equals(tag)){
			FuncInfo funcInfo = new FuncInfo();
			funcInfo.setCondition("funcDo like'%"+href.substring(1)+"%'");
			List<DataMap> funcList = ServiceBean.getInstance().getFuncInfoFacade().getFuncInfo(funcInfo);
			String funcName = "";
			funcName = ""+funcList.get(0).getAt("funcName");	
			Long startTime = start.getTime();
			Long endTime = end.getTime();
			Long between = endTime - startTime;
			int costTime = between.intValue();
			MonitorInfo moniInfo = new MonitorInfo();
			moniInfo.setStartTime(start);
			moniInfo.setEndTime(end);
			moniInfo.setCostTime(costTime);
			moniInfo.setFunction(funcName);
			moniInfo.setFunctionHref(href);
			ServiceBean.getInstance().getMonitorInfoFacade().insertMonitorInfo(moniInfo);
		}
	}
	public static String getSelectMess(String code,int isSelect){
		StringBuffer tag = new StringBuffer();
		
		tag.append("<select id='"+code+"' name='"+code+"'>\n");
		for(int i=2;i>=0;i--){
			String select = "";
			if(isSelect == i){
				select += "selected";
			}			
			if(i==0){
				tag.append("<option value='"+i+"' "+select+">×");
			}else if(i==1){
				tag.append("<option value='"+i+"' "+select+">√");
			}else{
				tag.append("<option value=''"+select+">全部");
			}
			tag.append("</option>\n");
		}
		tag.append("</select>");
		return tag.toString();
	}
	
	public static String getComStatus(String code,int isSelect){
		StringBuffer tag = new StringBuffer();
		
		tag.append("<select id='"+code+"' name='"+code+"'>\n");
		for(int i=2;i>=0;i--){
			String select ="";
			if(isSelect ==i){
				select +="selected";
			}
			if(i==0){
				tag.append("<option value='"+i+"' "+select+">暂停");
			}else if(i==1){
				tag.append("<option value='"+i+"'"+select+">正常");
			}else if(i==2){
				tag.append("<option value=''"+select+">全部");
			}
			tag.append("</option>\n");
		}
		tag.append("</select>");
		return tag.toString();
	}
	public static String getStatusSelect(String status, int isSelect){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='"+status+"' id='"+status+"'>/n");
		for(int i=0;i<5; i++){
			String select = "";
			if(isSelect+1 == i){
				select += "selected";
			}
			if(i == 0){
				sb.append("<option value=''"+select+">全部");								
			}else if(i == 1){
				sb.append("<option value='0'"+select+">录入");
			}else if(i == 2){
				sb.append("<option value='1'"+select+">出厂");				
			}else if(i == 3){
				sb.append("<option value='2'"+select+">绑定");				
			}else if(i == 4){
				sb.append("<option value='3'"+select+">解绑");				
			}			
		}
		sb.append("</select>");
		
		return sb.toString();
	}
	public static String getTypeSelect(String type, int isSelect){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='"+type+"' id='"+type+"'>/n");
		for(int i=0;i<3; i++){
			String select = "";
			if(isSelect == i){
				select += "selected";
			}
			if(i == 0){
				sb.append("<option value=''"+select+">全部");								
			}else if(i == 1){
				sb.append("<option value='1'"+select+">测试");
			}else if(i == 2){
				sb.append("<option value='2'"+select+">量产");				
			}	
		}
		sb.append("</select>");
		
		return sb.toString();
	}
	
	public static String getSelect(String status, int isSelect){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='"+status+"' id='"+status+"'>/n");
		for(int i=0;i<4; i++){
			String select = "";
			if(isSelect+1 == i){
				select += "selected";
			}
			if(i == 0){
				sb.append("<option value=''"+select+">全部");								
			}else if(i == 1){
				sb.append("<option value='0'"+select+">LBS");
			}else if(i == 2){
				sb.append("<option value='1'"+select+">GPS");				
			}else if(i == 3){
				sb.append("<option value='2'"+select+">WIFI");				
			}			
		}
		sb.append("</select>");
		
		return sb.toString();
	}
	
	public static String getBindStatus(String status, int isSelect){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='"+status+"' id='"+status+"'>/n");
		for(int i=0;i<4; i++){
			String select = "";
			if(isSelect+1 == i){
				select += "selected";
			}
			if(i == 0){
				sb.append("<option value=''"+select+">全部");								
			}else if(i == 1){
				sb.append("<option value='0'"+select+">解绑");
			}else if(i == 2){
				sb.append("<option value='1'"+select+">绑定");				
			}			
		}
		sb.append("</select>");
		
		return sb.toString();
	}
	public static String getMenuLeave(String status, int isSelect){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='"+status+"' id='"+status+"'>/n");
		for(int i=0;i<4; i++){
			String select = "";
			if(isSelect == i){
				select += "selected";
			}
			if(i == 0){
				sb.append("<option value=''"+select+">全部");								
			}else if(i == 1){
				sb.append("<option value='1'"+select+">一级菜单");
			}else if(i == 2){
				sb.append("<option value='2'"+select+">二级菜单");				
			}			
		}
		sb.append("</select>");
		
		return sb.toString();
	}
	
	public static String getPrintApplyStatus(int selected){		
		StringBuffer sb = new StringBuffer();
		sb.append("<select name=apply_status id=apply_status>/n");
		for(int i=0;i<=3;i++){
			String strSelected="";
			if(i==0){
				if(selected==3){
					strSelected="selected";
				}
				sb.append("<option value='3' "+strSelected+">全部</option>/n");
			}else if(i==1){
				if(selected==0){
					strSelected="selected";
				}
				sb.append("<option value='0' "+strSelected+">新申请</option>/n");
			}else if(i==2){
				if(selected==1){
					strSelected="selected";
				}
				sb.append("<option value='1' "+strSelected+">批准</option>/n");
			}else if(i==3){
				if(selected==2){
					strSelected="selected";
				}
				sb.append("<option value='2' "+strSelected+">不批准</option>/n");
			}
		}
		sb.append("</select");
		
		return sb.toString();
	}
	
	public static String getPrintTag(int checked){
		StringBuffer sb = new StringBuffer();

		if(checked==0 || checked==1){
			sb.append("<input type='radio' name='tag' id='status' value='1' checked>是</input>");
			sb.append("<input type='radio' name='tag' id='status' value='0'>否</input>");
		}else if(checked==2){
			sb.append("<input type='radio' name='tag' id='status' value='1'>是</input>");
			sb.append("<input type='radio' name='tag' id='status' value='0' checked>否</input>");
		}
		
		return sb.toString();
	}

	/*
	 * list是要查的链表,name触发方法
	 * tvalue是上传的值,tname是界面上显示的值
	 * param是是否选上,
	 * column表示每行显示几个数据，isSelect表示(1)隐藏,0显示
	 */
	public static String getPrintList(List<DataMap> list, String name,String tvalue,String tname,
			                          String frameid, String crowName, String code, List<DataMap> moList,int column,int isSelect) {
		// TODO Auto-generated method stub
		StringBuffer sf = new StringBuffer();
		int rows = list.size() / column;
		if(list.size() % column != 0) {//每行有几列
			rows = rows + 1;//多的部分显示行
		}
		String attribute = "display:block;";
		if(isSelect == 1){
			attribute = "display:none;";
		}
		sf.append("<table width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tr_8\" style=\"display:none\" id='"+frameid+"'>" );
		
		ArrayList<String> parameter1 = new ArrayList<String>();
		ArrayList<String> parameter2 = new ArrayList<String>();
		ArrayList<String> parameter3 = new ArrayList<String>();
			
		if(moList == null || (moList.size()==0)){
			
		}else{
			for(int i=0;i<moList.size();i++){
				String sCode = ""+moList.get(i).getAt(code);
				StringTokenizer st = new StringTokenizer(sCode, ",");
				while (st.hasMoreTokens()) {
					String nodeName = st.nextToken();
					parameter3.add(nodeName);
				}
			}
		}
		Iterator<DataMap> it = list.iterator();
		while (it.hasNext()) {
			DataMap listMap = it.next();
			parameter1.add(""+listMap.getAt(tvalue));
			parameter2.add(""+listMap.getAt(tname));
		}
		for(int i=0; i<rows; i++){
			sf.append("\n<tr class=\"tr_8\" >\n");
			int endIndex = (i+1)*column;
        	if(endIndex > parameter1.size()) {  //表示行的个数若多于数据总数,则就按endIndex算
        		endIndex = parameter1.size();
        	}
        	for(int j=i*column; j < endIndex; j++){
        		String value = parameter1.get(j);       //这个是商务里有的Code
        		String names = parameter2.get(j);       //这个是商务里有的name
        		String checked = "";
        		for(int k=0;k<parameter3.size();k++){
        			String paramValue = parameter3.get(k);  //这个是项目选中的
        			if(value.equals(paramValue)){
        				checked = "checked";
        				break;
        			}
        		}
        		sf.append("\t\t<td align=\"left\" width=\"20\" style='"+attribute+"'><input name='"+crowName+"' type=\"checkbox\" id='"+crowName+j+"' onclick=\""+name+"('"+crowName+j+"');\" value='"+value+"' "+checked+">"+names+"</td>\n");
        	}
        		sf.append("</tr>\n");
		}
//		String[] ins = {} ;
//		String[] ins_value = {};
//		if(listCount != null){
//			ins = CommTools.compareStrArray(parameter1, parameter4);
//			ins_value = CommTools.compareStrArray(parameter2, parameter5);
//		}
//		if(ins.length > 0 && (parameter1.size()%5)==0){
//			
//		}else{
//			
//		}
//		System.out.println(ins.toString());
		sf.append("</table>");
		return sf.toString();
	}

	public static String getHideParamValue(List<DataMap> list, String name,String tname) {
		// TODO Auto-generated method stub
		StringBuffer sf = new StringBuffer();
		
		Iterator<DataMap> it = list.iterator();
		while (it.hasNext()) {
			DataMap listMap = it.next();
			String names = ""+listMap.getAt(tname);
			sf.append("<input name='"+name+"' type=\"hidden\" value='"+names+"'>");
		}
		return sf.toString();
	}

	public static Object getPrintDyList(List<DataMap> list, List<DataMap> customListCount,String name,String tvalue,String tname,
            String frameid, String crowName, String code, List<DataMap> moList,int column,int isSelect) {
		// TODO Auto-generated method stub
		StringBuffer sf = new StringBuffer();
		int rows = customListCount.size() / column;
		if(customListCount.size() % column != 0) {//每行有几列
			rows = rows + 1;//多的部分显示行
		}
		String attribute = "display:block;";
		if(isSelect == 1){
			attribute = "display:none;";
		}
		sf.append("<table width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tr_8\" style=\"display:none\" id='"+frameid+"'>" );
		
		ArrayList<String> parameter1 = new ArrayList<String>();
		ArrayList<String> parameter2 = new ArrayList<String>();
		ArrayList<String> parameter3 = new ArrayList<String>();
		ArrayList<String> parameter4 = new ArrayList<String>();
		ArrayList<String> parameter5 = new ArrayList<String>();
		
		Iterator<DataMap> its = customListCount.iterator();
		while (its.hasNext()) {
			DataMap listMap = its.next();
			parameter4.add(""+listMap.getAt(tvalue));
			parameter5.add(""+listMap.getAt(tname));
		}
		
		if(moList == null || (moList.size()==0)){
			
		}else{
			for(int i=0;i<moList.size();i++){
				String sCode = ""+moList.get(i).getAt(code);
				StringTokenizer st = new StringTokenizer(sCode, ",");
				while (st.hasMoreTokens()) {
					String nodeName = st.nextToken();
					parameter3.add(nodeName);
				}
			}
		}
		Iterator<DataMap> it = list.iterator();
		while (it.hasNext()) {
			DataMap listMap = it.next();
			parameter1.add(""+listMap.getAt(tvalue));
			parameter2.add(""+listMap.getAt(tname));
		}
		for(int i=0; i<rows; i++){
			sf.append("\n<tr class=\"tr_8\" >\n");
			int endIndex = (i+1)*column;
        	if(endIndex > parameter4.size()) {  //表示行的个数若多于数据总数,则就按endIndex算
        		endIndex = parameter4.size();
        	}
        	for(int j=i*column; j < endIndex; j++){
        		String value = parameter4.get(j);       
        		String names = parameter5.get(j);       
        		String checked = "";
        		String pValue = "";
        		String flag = "0";
        		attribute = "display:none;";
        		for(int n=0;n<parameter1.size();n++){
        			pValue = parameter1.get(n);         //这个是商务里有的Code
        			if(value.equals(pValue)){           
        				attribute = "display:block;";
        				flag = "1";
        				break;
        			}
        		}
        		for(int k=0;k<parameter3.size();k++){
        			String paramValue = parameter3.get(k);  //这个是项目选中的
        			if(pValue.equals(paramValue) && ("1".equals(flag))){
        				checked = "checked";
        				attribute = "display:block;";
        				break;
        			}
        		}
        		sf.append("\t\t<td align=\"left\" width=\"20\" style='"+attribute+"'><input name='"+crowName+"' type=\"checkbox\" id='"+crowName+j+"' onclick=\""+name+"('"+crowName+j+"');\" value='"+value+"' "+checked+">"+names+"</td>\n");
        	}
        		sf.append("</tr>\n");
		}
		sf.append("</table>");
		return sf.toString();
	}
	
	public static String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	public static boolean isSameDay(String date){
		boolean isSome = false;
		if(!date.equals("0")){
			date = date.substring(0,10);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			isSome = date.equals(df.format(new Date()));
		}
		return isSome;
	}
	public static boolean isTimeJianGe(String date1,String date2,long jiange){
		boolean timeJianGe = false;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date date_time1 = df.parse(date1);
			Date date_time2 = df.parse(date2);
			
			if(date_time2.getTime() - date_time1.getTime() >= jiange){   //比较两个时间
				timeJianGe = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return timeJianGe;
	}
//	public static void main(String[] args){
//		boolean s = isSameDay("2015-10-08 18:16:53");
//		System.out.print(s);
//	}
	/** 创建当前日期文件夹,存在则返回文件名 */
	public static void createDateFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	public static String getSubStr(String str, int num) {
		String result = "";
		  int i = 0;
		  while(i < num) {
		   int lastFirst = str.lastIndexOf('/');
		   if(lastFirst == -1){
			   return result;
		   }
		   result = str.substring(lastFirst) + result;
		   str = str.substring(0, lastFirst);
		   i++;
		  }
		  return result.substring(1);
	}
	public static String formatCode(int code) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		String codes = String.valueOf(code);
		if(codes.length() > 5){
			result.append("100000");
		}else{
			for(int i=5;0<i;i--){
				if(i == codes.length()){
					result.append(codes);
					break;
				}else{
					result.append("0");
				}		
			}
		}	
		return result.toString();
	}
	public static Object[] getJsonToArray(String str) {
        JSONArray jsonArray = JSONArray.fromObject(str);
        return jsonArray.toArray();
   }
}
