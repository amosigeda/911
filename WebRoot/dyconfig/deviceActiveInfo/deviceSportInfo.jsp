<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/js/jquery.js"></script>
	</head>
<script language="javascript">
function finds(){
	frmGo.submit();
}

</script>
	<body>

		<form name="frmGo" method="post"
			action="doDeviceActiveInfo.do?method=querySportInfo">
			<table width="100%" class="table" >
               <tr>
                   <th colspan="13" nowrap="nowrap" align="left">角色 
                  <!--  <input name="inset" type="button" class="but_1" accesskey="a"
							tabindex="a" value="添加角色" onclick="insert()"> --></th>
                </tr>         
                  <tr class="title_2">
								
					<td width="10%">
						用户id
					</td>
					<td width="10%">
					    IMEI
					</td>
					<td width="10%" >
					   运动类型
					   </td>
					   <td width="10%" >
					   消耗能量
					   </td>
					   <td width="10%" >
					   心率
					   </td>
					   <td width="10%" >
					   上传时间
					   </td>
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<td>
							<bean:write name="element" property="id" />
						</td>
							<td>
							<bean:write name="element" property="imei" />
						</td>
							<td>
							<logic:equal name="element" property="sport_type" value="1">走路</logic:equal>
							<logic:equal name="element" property="sport_type" value="2">慢跑</logic:equal>
							<logic:equal name="element" property="sport_type" value="3">篮球</logic:equal>
							<logic:equal name="element" property="sport_type" value="4">足球</logic:equal>
							<logic:equal name="element" property="sport_type" value="5">羽毛球</logic:equal>
						</td>
						<td>
							<bean:write name="element" property="energy" />
						</td>
						<td>
							<bean:write name="element" property="heart_rate" />
						</td>
						<td>
							<bean:write name="element" property="upload_time" />
						</td>
					</tr>
				</logic:iterate>
			 	<tr class="title_3">
					<td colspan="8" align="left" >
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>  
			</table>
		</form>
	</body>
</html>
