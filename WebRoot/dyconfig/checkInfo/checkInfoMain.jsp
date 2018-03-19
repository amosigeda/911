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
	var versionCode = document.getElementById('versionCode').value;
	if(Date.parse(st) - Date.parse(et)>0){
		alert("开始时间不能大于结束时间!");
		return false;
	}
	if(!isNumber(versionCode.trim()) && versionCode.trim() !=""){
		alert("版本号请填写数字");
		frmGo.versionCode.focus();
		return false;
		   }
	frmGo.action="doCheckInfo.do?method=queryCheckInfo";
	frmGo.submit();
}
function c(){
    document.all.startTime.value="";
    document.all.endTime.value="";
    document.all.versionCode.value="";
    document.getElementById("belongProject").options[0].selected=true;
    document.getElementById("upType").options[0].selected=true;
    document.getElementById("versionType").options[0].selected=true;
    document.getElementById("status").options[0].selected=true;
} 
function addIDCode(){
	frmGo.action="doCheckInfo.do?method=initInsert";
	frmGo.submit();
}
function updateStatusEnable(id){
	if(confirm("确定禁用该APK吗？")){
		frmGo.action="doCheckInfo.do?method=updateStatus&status=0&id="+id;
		frmGo.submit();
	}
}
function updateStatusDisable(id){
	if(confirm("确定启用该APK吗？")){
		frmGo.action="doCheckInfo.do?method=updateStatus&status=1&id="+id;
		frmGo.submit();
	}
}
function uploadApk(){
	frmGo.action="doCheckInfo.do?method=uploadCheckInfo";
	frmGo.submit();
}
function updateRemarks(id){
	frmGo.action="doCheckInfo.do?method=initUpdateRemarks&id="+id;
	frmGo.submit();
}
function updateUpUser(id){
	frmGo.action="doCheckInfo.do?method=initUpdateUpUser&id="+id;
	frmGo.submit();
}
function downloadApk(path,type,code){
	var obj = path.split(",");
	
	frmGo.action="doCheckInfo.do?method=downloadApk&download="+obj[0]+"&versionType="+obj[1]+"&IDCode="+obj[2];
   	frmGo.submit();
}
function del(id){
		if(confirm("确定删除吗?"))
		{
			frmGo.action = "doCheckInfo.do?method=deleteCheckInfo&id="+id+" ";
			frmGo.submit();
		}
}
</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post"
			action="doCheckInfo.do?method=queryCheckInfo">
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="13" nowrap="nowrap" align="left">
                APK升级信息
                <input name="addDevice" type="button" class="but_1" accesskey="a"
							tabindex="a" value="添加识别码" onclick="addIDCode()">
				<input name="addDevice" type="button" class="but_1" accesskey="a"
							tabindex="a" value="上传APK" onclick="uploadApk()">
                </th>
                </tr>
                 <tr class="title_3">
                       <td colspan="13">
					  上传时间
                     <input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly> -
							<input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="9" readonly>						
						版本号
						    <input id="versionCode" name="versionCode" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"versionCode");%>" size="9">
						项目
							<%String belongProject = (String)request.getAttribute("belongProject"); %>
							<select id="belongProject" name="belongProject">
								<option value="">全部</option>
								<logic:iterate id="pro" name="project">
									<bean:define id="projectId" name="pro" property="id" type="java.lang.Integer" />																	
									<option value='<%=projectId %>' <%=String.valueOf(projectId).equals(belongProject)? "selected" : "" %>><bean:write name="pro" property="project_name"/></option>
								</logic:iterate>
							</select>
						升级类型
							<%String upType = (String)request.getAttribute("upType"); %>
							<select id="upType" name="upType">
								<option value="">全部</option>
								<option value="0" <%if(upType != null && upType.equals("0")){out.print("selected");} %> >正常</option>
								<option value="1" <%if(upType != null && upType.equals("1")){out.print("selected");} %> >强制</option>
								<option value="2" <%if(upType != null && upType.equals("2")){out.print("selected");} %> >建议</option>
							</select>
						版本类型
							<%String versionType = (String)request.getAttribute("versionType"); %>
							<select id="versionType" name="versionType">
								<option value="">全部</option>
								<option value="0" <%if(versionType != null && versionType.equals("0")){out.print("selected");} %> >内部</option>
								<option value="1" <%if(versionType != null && versionType.equals("1")){out.print("selected");} %>>外部</option>
							</select>
						状态
							<%String status = (String)request.getAttribute("status"); %>
							<select id="status" name="status">
								<option value="">全部</option>
								<option value="0" <%if(status != null && status.equals("0")){out.print("selected");} %> >禁用</option>
								<option value="1" <%if(status != null && status.equals("1")){out.print("selected");} %> >启用</option>
							</select>
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
					     <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
				</tr> 
				<%int i=1; %>
                  <tr class="title_2"> 
                  	<td width="8%" >识别码</td> 
                  	<td width="6%" >项目</td> 
                  	<td width="6%" >版本名称</td>
                  	<td width="5%" >版本号</td>                  	                  	                                   	                 	
                  	<!-- <td width="10%" >包名</td> -->
                  	<td width="7%" >版本说明</td>
                  	<td width="6%" >升级类型</td>
                  	<td width="6%" >版本类型</td>
                  	<td width="5%" >二维码</td>
                  	<td width="6%" >上传平台</td>
                  	<td width="7%" >上传时间</td>
                  	<td width="6%" >状态</td>
                  	<td width="7%" >备注</td>
                  	<td width="9%" >操作</td>
				</tr>
 
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >						
						<td>
						<logic:empty name="element" property="ID_code" >无</logic:empty>
						<logic:notEmpty name="element" property="ID_code">
						<bean:write name="element" property="ID_code" />
						</logic:notEmpty>
						</td>
						<td>
						<logic:empty name="element" property="project_name" >无</logic:empty>
						<bean:write name="element" property="project_name" />
						</td>
						<td><bean:write name="element" property="version_name" /></td>
						<td><bean:write name="element" property="version_code" /></td>
												
						<!-- <td><bean:write name="element" property="package_name" /></td> -->
						<td>
						<logic:empty name="element" property="function_cap">无</logic:empty>
							<logic:notEmpty name="element" property="function_cap">
								<bean:write name="element" property="function_cap" />
							</logic:notEmpty>
						</td>			
						
						<td>
							<logic:equal name="element" property="up_type" value="0">正常</logic:equal>
							<logic:equal name="element" property="up_type" value="1">强制</logic:equal>
							<logic:equal name="element" property="up_type" value="2">建议</logic:equal>
						</td>
						<td>
							<logic:equal name="element" property="version_type" value="0">内部</logic:equal>
							<logic:equal name="element" property="version_type" value="1">外部</logic:equal>
							<logic:equal name="element" property="version_type" value="2">最新</logic:equal>
						</td>
						<td>
							<logic:equal name="element" property="er_weima" value="0">无</logic:equal>
							<logic:equal name="element" property="er_weima" value="1">有</logic:equal>
							<logic:empty name="element" property="er_weima">无</logic:empty>
						</td>
						<td>
							<logic:equal name="element" property="up_platform" value="0">安卓</logic:equal>
						</td>
						
						<td><bean:write name="element" property="upload_time" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<logic:equal name="element" property="status" value="0"><font style="color:blue;">禁用</font></logic:equal>
							<logic:equal name="element" property="status" value="1"><font style="color:green;">启用</font></logic:equal>
						</td>
						<td>
						<logic:empty name="element" property="remarks" >无</logic:empty>
						<logic:notEmpty name="element" property="remarks">
						<bean:write name="element" property="remarks" />
						</logic:notEmpty>
						</td>											
						<td>
							<logic:equal name="element" property="status" value="1"><a href=# onclick="updateStatusEnable('<bean:write name="element" property="id" />')" style="color:#0000FF" > 【禁用】</a></logic:equal>					
							<logic:equal name="element" property="status" value="0"><a href=# onclick="updateStatusDisable('<bean:write name="element" property="id" />')" style="color:green" > 【启用】</a></logic:equal>
							<a href=# onclick="updateRemarks('<bean:write name="element" property="id" />')" style="color:#0000FF" > 【备注】</a>
							<logic:equal name="element" property="version_type" value="0">
							<a href=# onclick="updateUpUser('<bean:write name="element" property="id" />')" style="color:#0000FF" > 【配置】</a>
							</logic:equal>
							<a href="#" onclick="downloadApk('<bean:write name="element" property="download_path" />,<bean:write name="element" property="version_type" />,<bean:write name="element" property="ID_code_id" />')" style="color:#0000FF" >【下载】</a>
							<%-- <a href=# onclick="del('<bean:write name="element" property="id" />')" style="color:#0000FF" > 【删除】</a> --%>
						</td>
					</tr>
				</logic:iterate> 

			  	<tr class="title_3">
					<td colspan="13" align="left" >  
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>  
			</table>
		</form>
	</body>
</html>
