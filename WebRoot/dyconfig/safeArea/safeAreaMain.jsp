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
	var safeRange1 = document.getElementById("safeRange1").value;
	var safeRange2 = document.getElementById("safeRange2").value;
	if(safeRange1 > safeRange2){
		alert("开始范围不能大于结束范围");
		return false;
	}
	   frmGo.submit();
}
function c(){
    document.all.startTime.value="";
    document.all.endTime.value="";
    document.all.serieNo.value="";
    document.all.safeRange1.value="";
    document.all.safeRange2.value="";
    document.all.userName.value="";
    document.all.belongProject.value="";
} 

</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doSafeArea.do?method=querySafeArea">			
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="12" nowrap="nowrap" align="left">
                                           安全区域信息
                </th>
                </tr>            		
                 <tr class="title_3">
                       <td colspan="13">
					  创建时间
                     <input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly> -
							<input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="9" readonly>
						IMEI				
						    <input id="serieNo" name="serieNo" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"serieNo");%>" size="15">
						范围				
						    <input id="safeRange1" name="safeRange1" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"safeRange1");%>" size="7">
						-				
						    <input id="safeRange2" name="safeRange2" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"safeRange2");%>" size="7">m
						 项目
						 <%String belongProject = (String)request.getAttribute("belongProject"); %>			
							<select id="belongProject" name="belongProject" >
								<option value="">全部</option>
								<logic:iterate id="pro" name="project_name">
									<bean:define id="projectId" name="pro" property="id" type="java.lang.Integer" />																	
									<option value='<%=projectId %>' <%=String.valueOf(projectId).equals(belongProject)? "selected" : "" %>><bean:write name="pro" property="project_name"/></option>
								</logic:iterate>
							</select>
						 用户手机号
						  <input id="userName" name="userName" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"userName");%>" size="9">
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
					     <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
				</tr> 
                  <tr class="title_2">     
                  	<td width="10%">设备IMEI</td>  
                  	<td width="10%">项目名称</td>         	
                  	<td width="6%" >用户手机号</td>                  	                  	
                  	<td width="7%" >中心经度</td>
                  	<td width="7%" >中心纬度</td>
                  	<td width="6%" >范围(m)</td>
                  	<td width="10%" >中心区域地址</td> 
                  	<td width="10%" align="left">安全区域名称</td>                 	 
                  	<td width="10%" >创建时间</td>
                  	<td width="10%" >修改时间</td>                 	                	               	                  	          						 
				</tr>

				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<td>
							<a style="color:#00f" href="../deviceActiveInfo/doDeviceActiveInfo.do?method=queryDeviceActiveInfo&deviceImei=<bean:write name="element" property="seri_no"/>">
								<bean:write name="element" property="seri_no"/>
							</a>			
						</td>						
						<td>
							<a style="color:#00f" href="../projectInfo/doProjectInfo.do?method=queryProjectInfo&projectId=<bean:write name="element" property="belong_project"/>">
								<bean:write name="element" property="project"/>
							</a>	
						</td>
						<td align="left" style="text-align:center"><a style="color: #0000FF " 
								href="../appUserInfo/doAppUserInfo.do?method=queryAppUserInfo&user_id=<bean:write name="element" property="user_id" />">
								<bean:write name="element" property="user_name"/>
							</a></td>					
						<td><bean:write name="element" property="longitude" /></td>	
						<td><bean:write name="element" property="latitude" /></td>
						<td><bean:write name="element" property="safe_range" /></td>	
						<td align="left"><bean:write name="element" property="safe_address" /></td>	
						<td><bean:write name="element" property="area_name"/></td>													
						<td><bean:write name="element" property="create_time" format="yyyy-MM-dd HH:mm:ss"/></td>	
						<td>
							<logic:empty name="element" property="update_time">无</logic:empty>
							<bean:write name="element" property="update_time" format="yyyy-MM-dd HH:mm:ss"/>
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