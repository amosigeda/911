<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ page import="java.text.*" %>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ page import="com.care.common.config.Config"%>
<%@ page import="com.care.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	PagePys pys = (PagePys)request.getAttribute("PagePys");
%>

<html>
	<head>
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- 调用此方法 -->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
	</head>
	<script type="text/javascript">

	</script>
	
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doDevicePhoneInfo.do?method=queryDevicePhoneInfo">						
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="14" nowrap="nowrap" align="left">
                	设备新增数据
                </th>
                </tr>            
                <tr class="title_2">     
                	<td width="10%">手机imei号</td>
					<td width="10%">手机imsi号</td>
					<td width="10%">设备名称 </td> 
          			<td width="10%">项目</td> 
          		    <td width="10%">版本</td> 
          		    <td width="10%">国家</td>               	                           						 
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"'
							onmouseout='this.className="tr_5"'>
						<td>
							<bean:write name="element" property="device_phone"/>
						</td>	
						<td>
							<bean:write name="element" property="device_imsi"/>
						</td>	
						<td>
							<bean:write name="element" property="device_name"/>
						</td>	
						<td>
							<bean:write name="element" property="belong_project"/>
						</td>
						
						<td>
							<bean:write name="element" property="firm"/>
						</td>	
						<td>
							<bean:write name="element" property="country"/>
						</td>		
					</tr>		
				</logic:iterate>

			  	<tr class="title_3">
					<td colspan="12" align="left" >  
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr> 
			</table>
		</form>
	</body>
</html>

