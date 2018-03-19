<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.godoing.rose.lang.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ page import="com.care.common.config.Config"%>
<%@ page import="com.care.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
	int count = 1;	
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
    document.getElementById("belongProject").options[0].selected=true;
    document.all.feedbackStatus.value="";
    document.all.userfeedbackcontent.value="";
    document.all.protime.value="";
}
function update(id){
	frmGo.action="doFeedBackInfo.do?method=initFeedBackInfoUpdate&id="+id;
	frmGo.submit();
}
function del(id){
		if(confirm("确定删除吗?"))
		{
			frmGo.action = "doFeedBackInfo.do?method=deleteFeedBackInfo&id="+id+" ";
			frmGo.submit();
		}
}

</script>
	<body>
		<form name="frmGo" method="post" action="doFeedBackInfo.do?method=queryFeedBackInfo">
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="13" nowrap="nowrap" align="left">
                                                            意见反馈
                </th>
                </tr>
                   <tr class="title_3">			
				    <td colspan="13">
				    
				    	反馈时间		
    					<input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="6" readonly> -
				    	<input name="endTime" type="text" class="txt_1"  id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="6" readonly>
						用户名
						    <input id="userName" name="userName" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"userName");%>" size="6">
						    反馈内容
						    <input id="userfeedbackcontent" name="userfeedbackcontent" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"userfeedbackcontent");%>" size="6">
						项目
						
							<%String belongProject = (String)request.getAttribute("belongProject"); %>
							<select id="belongProject" name="belongProject">
								<option value="">全部</option>
								<logic:iterate id="pro" name="project">
									<bean:define id="projectId" name="pro" property="id" type="java.lang.Integer" />																	
									<option value='<%=projectId %>' <%=String.valueOf(projectId).equals(belongProject)? "selected" : "" %>><bean:write name="pro" property="project_name"/></option>
								</logic:iterate>
							</select>
						是否处理
							<%if(request.getAttribute("feedbackStatus")!=null && !"".equals(request.getAttribute("feedbackStatus"))){ %>
							<%=request.getAttribute("feedbackStatus") %>	
							<%}else{ %>				
							
							<select id="status" name="feedbackStatus">
								<option value="">全部</option>
								<option value="1">√</option>
								<option value="0">×</option>
							</select>
							<%} %>
								
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
							 <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
						</td>					
				</tr>
                 <tr class="title_2">                 	
                    <td width="4%">用户名</td>
                    <td width="4%">项目</td>                  
					<td width="10%">反馈内容</td>	
					<td width="8%">反馈时间</td>	
					<td width="6%">是否处理</td>	
					<td width="8%">处理内容</td>	
					<td width="8%">处理时间</td>	
					<td width="8%">操作</td>														
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >					 							
						<td>
							<a style="color:#00f" href="../appUserInfo/doAppUserInfo.do?method=queryAppUserInfo&user_id=<bean:write name="element" property="user_id"/>">		
							<bean:write name="element" property="user_name" />
							</a>
						</td>
						<td>
							<a style="color:#00f" href="../projectInfo/doProjectInfo.do?method=queryProjectInfo&projectId=<bean:write name="element" property="id"/>">
								<bean:write name="element" property="project_name" />
							</a>
						</td>
						<td align="left">
							<bean:write name="element" property="user_feedback_content" />
						</td>
						<td>
							<bean:write name="element" property="date_time" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						
						<td>
							<logic:equal name="element" property="feedback_status" value="0">
								<font style="color:red;font-size:20px">×</font>
							</logic:equal>
							<logic:equal name="element" property="feedback_status" value="1">
								<font style="color:green;font-size:20px">√</font>
							</logic:equal>
						</td>
							<td>
							<bean:write name="element" property="processing_content"/>
						</td>
							<td>
							<bean:write name="element" property="processing_time" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
						<a href=# onclick="update('<bean:write name="element" property="id" />')" style="color:#0000FF">【配置】</a>
						<a href=# onclick="del('<bean:write name="element" property="id" />')" style="color:#0000FF" > 【删除】</a>
						
					</td>
					</tr>
				</logic:iterate>
				
				<tr class="title_3">			
					<td colspan="8" align="left">
					  <% 
					  pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>
				
			</table>
		</form>
	</body>
</html>
