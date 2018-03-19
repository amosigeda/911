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
    document.all.user.value="";   
    document.getElementById("belongProject").options[0].selected=true;
} 
function onView(download){
	if(download.length == 0){
		alert("没有头像文件，无法下载！");
		return false;
	}
	frmGo.action="doAppUserInfo.do?method=downloadApk&download="+download;
   	frmGo.submit();
}
</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doAppUserInfo.do?method=queryAppUserInfo">						
		
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="15" nowrap="nowrap" align="left">
                    APP用户信息
                </th>
                </tr>
                <% if(request.getAttribute("user_id") == null){%>
                 <tr class="title_3">
                       <td colspan="15">
					  注册时间
                     <input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly> -
							<input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="9" readonly>						
						手机号				
						    <input id="user" name="user" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"user");%>" size="9">
						项目
							<%String belongProject = (String)request.getAttribute("belongProject"); %>
							<select id="belongProject" name="belongProject">
								<option value="">全部</option>
								<logic:iterate id="pro" name="project">
									<bean:define id="projectId" name="pro" property="id" type="java.lang.Integer" />																	
									<option value='<%=projectId %>' <%=String.valueOf(projectId).equals(belongProject)? "selected" : "" %>><bean:write name="pro" property="project_name"/></option>
								</logic:iterate>
							</select>
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
					     <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
				</tr> 
				<%} %>
                  <tr class="title_2"> 
                  	<td width="9%" >注册时间</td>
                  <!-- 	<td width="5%">用户ID</td>  --> 
                  	<td width="8%" >手机号</td> 
                  	<td width="6%" >项目</td>  
                  	<td width="6%" >昵称</td>
                  	<td width="5%" >绑定设备数</td>
                  	<td width="5%" >绑定次数</td>
                  	<td width="5%" >分享设备数</td> 
                  	<td width="8%" >密码</td>
                  	<td width="5%" >头像</td>                  	
                  	<td width="8%" >手机型号</td>
                  	<td width="8%" >系统版本</td>
                    <td width="8%" >APP版本</td>
                    <td width="8%" >IP地址</td>
                    <td width="8%" >归属地</td>
				</tr>
 
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<td>
						<logic:empty name="element" property="create_time">无</logic:empty>
						<logic:notEmpty name="element" property="create_time">
						<bean:write name="element" property="create_time" format="yyyy-MM-dd HH:mm:ss"/>
						</logic:notEmpty>
						</td>
						<%-- <td><bean:write name="element" property="id"/></td> --%>	
						<td><bean:write name="element" property="user_name"/></td>
						<td>
						<logic:empty name="element" property="project">无</logic:empty>
						<logic:notEmpty name="element" property="project">
						<a style="color:#00f" href="../projectInfo/doProjectInfo.do?method=queryProjectInfo&projectId=<bean:write name="element" property="belong_project"/>">
									<bean:write name="element" property="project"/>
							</a>
					</logic:notEmpty>
						</td>
						<td>
						<logic:empty name="element" property="nick_name">无</logic:empty>
						<logic:notEmpty name="element" property="nick_name">
						<bean:write name="element" property="nick_name" />
						</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="count_device">0</logic:empty>
							<a style="color: #0000FF"
								href="../deviceActiveInfo/doDeviceActiveInfo.do?method=queryDeviceActiveInfo&user_id=<bean:write name="element" property="id" />">								
								<bean:write name="element" property="count_device" />
							</a>							
						</td>
						<td><bean:write name="element" property="bind_count" /></td>
						<td>
							<logic:empty name="element" property="count_share">0</logic:empty>
							<logic:notEmpty name="element" property="count_share">
								<bean:write name="element" property="count_share" />
							</logic:notEmpty>
						</td>
						<td>
						<logic:empty name="element" property="password" >无</logic:empty>
						<logic:notEmpty name="element" property="password">
						<bean:write name="element" property="password" />
						</logic:notEmpty></td>
						
						<td>
							<logic:empty name="element" property="head">无</logic:empty>
							<logic:notEmpty name="element" property="head">
								<a href=# onclick="onView('<bean:write name="element" property="head"/>')" style="color:#0000FF">下载</a>
							</logic:notEmpty>							
						</td>						
						<td>
						<logic:empty name="element" property="phone_model">无</logic:empty>
						<logic:notEmpty name="element" property="phone_model">
						<bean:write name="element" property="phone_model"/>
						</logic:notEmpty></td>	
						<td>
						<logic:empty name="element" property="sys_version">无</logic:empty>
						<logic:notEmpty name="element" property="sys_version">
						<bean:write name="element" property="sys_version" />
						</logic:notEmpty></td>
						<td>
						<logic:empty name="element" property="app_version">无</logic:empty>
						<logic:notEmpty name="element" property="app_version">
						<bean:write name="element" property="app_version" />
						</logic:notEmpty></td>																				
						<td>
							<logic:empty name="element" property="ip" >旧数据</logic:empty>
							<logic:notEmpty name="element" property="ip">
								<bean:write name="element" property="ip" />
							</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="province" >旧数据</logic:empty>
							<logic:notEmpty name="element" property="province">
								<bean:write name="element" property="province" />
							</logic:notEmpty>
						</td>
					</tr>
				</logic:iterate> 

			  	<tr class="title_3">
					<td colspan="15" align="left" >  
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>  
			</table>
		</form>
	</body>
</html>