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
		frmGo.submit();
	}
	function c(){
   		document.all.serieNo.value="";
   		document.getElementById("belongProject").options[0].selected=true;
	} 

	</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doSettingInfo.do?method=querySettingInfo">						
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="14" nowrap="nowrap" align="left">
                                            设备设置信息
                </th>
                </tr>            
                 <tr class="title_3">
                       <td colspan="14">					
						IMEI				
						    <input id="serieNo" name="serieNo" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"serieNo");%>" size="15">
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
                  <tr class="title_2">     
                  	<td width="10%">设备IMEI</td>
                  	<td width="8%" >项目</td>            	
                  	<td width="8%" >GPS开关</td>
                 <!--  	<td width="8%" >是否佩戴</td> -->
                  	<td width="8%" >定位频率</td>  
                  	<td width="8%" >驱蚊</td>  
                  	<td width="8%" >爱心奖励</td>  
				</tr>
 
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<td>
						<a style="color: #0000FF"
								href="../../dyconfig/deviceActiveInfo/doDeviceActiveInfo.do?method=queryDeviceActiveInfo&deviceImei=<bean:write name="element" property="serie_no" />">
							<bean:write name="element" property="serie_no" />
							</a>
						</td>						
						<td>
						<a style="color: #0000FF"  
								href="../../dyconfig/projectInfo/doProjectInfo.do?method=queryProjectInfo&projectId=<bean:write name="element" property="belong_project" />">
								<bean:write name="element" property="project_name"/> 
								</a>
					</td> 
						<td>
							<logic:equal name="element" property="gps_on" value="0">关闭</logic:equal>
							<logic:equal name="element" property="gps_on" value="1">打开</logic:equal>
							<logic:empty name="element" property="gps_on">关闭</logic:empty>
						</td>
						<%-- <td>
							<logic:equal name="element" property="fall" value="1">
								<font style="color:green;">佩戴</font>
							</logic:equal>
							<logic:equal name="element" property="fall" value="0">
								<font style="color:red;">未佩戴</font>
							</logic:equal>
							<logic:empty name="element" property="fall" >
							<font style="color:red;">未佩戴</font>
							</logic:empty>
						</td> --%>
						<td>
							<logic:empty name="element" property="light" >关闭</logic:empty>
							<logic:equal name="element" property="light" value="0">关闭</logic:equal>
							<logic:equal name="element" property="light" value="10">每10分钟一次</logic:equal>
							<logic:equal name="element" property="light" value="60">每60分钟一次</logic:equal>
							<logic:equal name="element" property="light" value="-1">手动</logic:equal>
							<logic:equal name="element" property="light" value="-2">手动</logic:equal>
						</td>	
						<td>
						  <logic:empty name="element" property="repellent">关</logic:empty>
					<logic:notEmpty name="element" property="repellent">
					         开
					</logic:notEmpty>
						</td>										
						<td><bean:write name="element" property="heart"/></td>
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