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
	function onView1(download){
		if(download.length == 0){
			alert("没有头像文件，无法下载！");
			return false;
		}
		frmGo.action="doDeviceActiveInfo.do?method=downloadImg&download="+download;
	   	frmGo.submit();
	}
</script>
	<body>

		<form name="frmGo" method="post"
			action="doDeviceActiveInfo.do?method=queryMediaInfo">
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
					    发送用户id
					</td>
					<td width="10%" >
					   接收用户id
					   </td>
					   <td width="10%" >
					   发送类型
					   </td>
					   <td width="10%" >
					   发送时间
					   </td>
					   <td width="10%" >
					   状态
					   </td>
					      <td width="10%" >
					   持续时间
					   </td>
					   	<td width="10%">
						信息内容
					</td>
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<td>
							<bean:write name="element" property="id" />
						</td>
						
						<td>
							<bean:write name="element" property="from_id" />
						</td>
						<td>
							<bean:write name="element" property="to_id" />
						</td>
						<td>
						<logic:equal name="element" property="send_type" value="0">信息</logic:equal>
					<logic:equal name="element" property="send_type" value="1">文件</logic:equal>
						</td>
						<td>
								<logic:empty name="element" property="send_time">无</logic:empty>
							<logic:notEmpty name="element" property="send_time">			
							<bean:write name="element" property="send_time" />
							</logic:notEmpty>
						</td>
						<td>
								<logic:empty name="element" property="status">无</logic:empty>
							<logic:notEmpty name="element" property="status">			
								<logic:equal name="element" property="status" value="0">发送成功</logic:equal>
					<logic:equal name="element" property="status" value="1">发送失败</logic:equal>	
							</logic:notEmpty>
					</td>
							<td>
							<bean:write name="element" property="time_length" />
						</td>
						<td>
							<logic:equal name="element" property="msg_content" value="0">无</logic:equal>
							<logic:notEqual name="element" property="msg_content" value="0">
								<a href=# onclick="onView1('<bean:write name="element" property="msg_content"/>')" style="color:#0000FF">下载</a>
							</logic:notEqual>	
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
