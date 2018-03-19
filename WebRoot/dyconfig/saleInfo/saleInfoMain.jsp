<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ page import="com.care.common.config.Config"%>
<%@ page import="com.care.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
	LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER); 
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- 调用此方法 -->
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
	</head>
	<script language="javascript">
	function finds(){
    var st = new Date(frmGo.startTime.value.replace(/-/g,'/'));
	var et = new Date(frmGo.endTime.value.replace(/-/g,'/'));
	if(Date.parse(st) - Date.parse(et)>0){
		alert("开始时间不能大于结束时间!");
		return false;
	}
	   frmGo.submit();
}
	function c(){
		document.all.startTime.value="";
    	document.all.endTime.value="";
   		document.all.userName.value="";   
   		document.all.projectId.value="";   
	} 

	</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doSaleInfo.do?method=querySaleInfo">						
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="14" nowrap="nowrap" align="left">
                                            新增用户信息
                </th>
                </tr>            
                 <tr class="title_3">
                       <td colspan="14">	
                                                           新增时间
                     <input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly> -
							<input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="9" readonly>				
						用户名				
						    <input id="userName" name="userName" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"userName");%>" size="9">
						
					项目名		
						<%String projectId = (String)request.getAttribute("projectId"); %>			
							<select id="projectId" name="projectId" >
								<option value="">全部</option>
								<logic:iterate id="pro" name="project">
									<bean:define id="project" name="pro" property="id" type="java.lang.Integer" />																	
									<option value='<%=project %>' <%=String.valueOf(project).equals(projectId)? "selected" : "" %>><bean:write name="pro" property="project_name"/></option>
								</logic:iterate>
							</select>		
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
					     <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
				</tr> 
                  <tr class="title_2">     
                  	<td width="8%">用户名</td> 
                  	<td width="10%" >IMEI</td>
                  	<td width="10%" >IMSI</td>
                  	<td width="8%" >手机号</td>
                  	<td width="12%" >手机型号</td>
                  	<td width="8%" >系统版本</td>
                  	<td width="8%" >APP版本</td>
                  	<td width="8%" >设备类型</td>
                  	<td width="11%" >新增时间</td>                           						 
                  	<td width="6%" >项目名</td>           	
				</tr>
 
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<td>
						<logic:empty name="element" property="user_name">无</logic:empty>
						<logic:notEmpty name="element" property="user_name">						  
						     <bean:write name="element" property="user_name"/>
						     </logic:notEmpty>
						</td>						
						<td>
							<logic:empty name="element" property="imei">无</logic:empty>
						<logic:notEmpty name="element" property="imei">						  
						     <bean:write name="element" property="imei"/>
						     </logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="imsi">无</logic:empty>
						<logic:notEmpty name="element" property="imsi">						  
						     <bean:write name="element" property="imsi"/>
						     </logic:notEmpty>
						</td>
							<td>
							<logic:empty name="element" property="phone">无</logic:empty>
						<logic:notEmpty name="element" property="phone">						  
						     <bean:write name="element" property="phone"/>
						     </logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="phone_model">无</logic:empty>
						<logic:notEmpty name="element" property="phone_model">						  
						     <bean:write name="element" property="phone_model"/>
						     </logic:notEmpty>
						</td>
							<td>
							<logic:empty name="element" property="sys_version">无</logic:empty>
						<logic:notEmpty name="element" property="sys_version">						  
						     <bean:write name="element" property="sys_version"/>
						     </logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="app_version">无</logic:empty>
						<logic:notEmpty name="element" property="app_version">						  
						     <bean:write name="element" property="app_version"/>
						     </logic:notEmpty>
						</td>
						<td>
							<logic:equal name="element" property="device_type" value="0">无</logic:equal>
							<logic:equal name="element" property="device_type" value="1">蓝牙</logic:equal>
							<logic:equal name="element" property="device_type" value="2">连网</logic:equal>
						</td>
							<td>
							<logic:empty name="element" property="date_time">无</logic:empty>
						<logic:notEmpty name="element" property="date_time">						  
						     <bean:write name="element" property="date_time" format="yyyy-MM-dd HH:mm:ss"/>
						     </logic:notEmpty>
						</td>
							<td>
							<logic:empty name="element" property="project_name">无</logic:empty>
						<logic:notEmpty name="element" property="project_name">						  
						     <bean:write name="element" property="project_name"/>
						     </logic:notEmpty>
						</td>
					</tr>
				</logic:iterate> 


			  	<tr class="title_3">
					<td colspan="14" align="left" >  
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>  
			</table>
		</form>
	</body>
</html>