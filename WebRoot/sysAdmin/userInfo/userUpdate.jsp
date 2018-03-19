<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import = "com.godoing.rose.http.common.*" %>
<%@ page import = "com.godoing.rose.lang.*" %>

<jsp:useBean id = "userInfo" scope = "request" class = "com.godoing.rose.lang.DataMap"/>
<%@ page autoFlush="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
<title>无标题文档</title>
</head>
<script language="javascript">
window.onload=function(){
	var checkCompanyId = document.getElementById("checkCompanyId").value;
	var checkProjectId = document.getElementById("checkProjectId").value;
	var companyCheckBox = document.getElementsByName("companyId");
	var projectCheckBox = document.getElementsByName("projectId");
	
	for(var i=0; i<checkCompanyId.length; i++){
		for(var j=0; j<companyCheckBox.length; j++){
			if(checkCompanyId[i] == companyCheckBox[j].value){
				companyCheckBox[j].checked = true;
			}
		}
	}
	for(var i=0; i<checkProjectId.length; i++){
		for(var j=0; j<projectCheckBox.length; j++){
			if(checkProjectId[i] == projectCheckBox[j].value){
				projectCheckBox[j].checked = true;
			}
		}
	}
	
	var roleValue = document.getElementById("roleValue").value;
	var roleSelect = document.getElementById("groupCode");	
	for(var i=0; i<roleSelect.length; i++){
		if(roleValue == roleSelect.options[i].value){
			roleSelect.options[i].selected = true;
		}
	}
}
function onUpdate(){
	if(frm.passWrd.value.trim() == ''){
		alert("密码不能为空");
		frm.passWrd.focus();
		return false;
	}else{
		var passWrd = frm.passWrd.value;
		var reg = /^\S{5,15}$/;	
		//var reg = /[^0-9a-zA-Z]{1,}/ ;
		//var reg1 = /[0-9a-zA-Z]{1,}/;
		if(!reg.test(passWrd)){
			alert("密码由5~15位字母、数字或符号组合");
			frm.passWrd.focus();
			return false;
		}else if(passWrd.length<5 || passWrd.length>15){
			alert("密码由5~15位字母、数字或符号组合");
			frm.passWrd.focus();
			return false;
		}
	}
	if(frm.remark.value.trim().length > 30){
		alert("字数不能超过30字");
		frm.remark.focus();
		return false;
	}
	frm.submit();
}
function onChannelPower() {
var id = frm.id.value.trim();
var userCode = frm.userCode.value.trim();
window.location="doUserInfo.do?method=initChannelPower&id="+id+"&userCode="+userCode+"";
}
function onAdverPower() {
var id = frm.id.value.trim();
var userCode = frm.userCode.value.trim();
window.location="doUserInfo.do?method=initAdverPower&id="+id+"&userCode="+userCode+"";
}

</script>
<body>
<span class="title_1"></span>
<form name="frm" method="post" action="doUserInfo.do?method=updateUserInfo" onsubmit="return onUpdate()">
<input name="id" type="hidden" value="<%=userInfo.getAt("id")%>" >
<input name="userCode" type="hidden" value="<%=userInfo.getAt("userCode")%>" />
<input name="userName" type="hidden" value="<%=userInfo.getAt("userName")%>" />
<table width="100%" border="0"cellpadding="0" cellspacing="1"  class="tbl_11">
  <tr>
     <th colspan="4" nowrap="nowrap" align="left">
                           系统用户配置-<font color="#FFFF00"><%=userInfo.getAt("userCode") %></font>
     </th>
   </tr>
   <!-- <tr class="tr_11">
   	<td>&nbsp;&nbsp;登录账号</td>
   	<td><%=userInfo.getAt("userCode") %></td>
   </tr> -->
   <tr class="tr_11">
    <td width="12%" align="left">&nbsp;&nbsp;密码</td>
    <td align="left" width="20%">
    	<input name="passWrd" type="text"  accesskey="p" tabindex="p" value="<%=userInfo.getAt("passWrd1")%>">
	  	<font color="red">*（5~16位字母、数字或下滑线组合）</font>
    </td>
  </tr>
  <%if(!userInfo.getAt("userCode").equals("admin")){ %>
  <tr class="tr_11">
    <td width="12%" align="left">&nbsp;&nbsp;是否生效 </td>
    <td align="left">
    	是<input name="tag" type="radio" class="radio_1" value="1" <%if("1".equals("" + userInfo.getAt("tag"))){%>checked<%}%>>
	          否<input type="radio" name="tag" class="radio_1" value="0" <%if("0".equals("" + userInfo.getAt("tag"))){%>checked<%}%>>
    </td>
  </tr>
  <%} %>
  <tr class="tr_11" id="tr_role">
    <td align="left" width="12%">&nbsp;&nbsp;角色</td> 
    <%if(!userInfo.getAt("userCode").equals("admin")){ %>  
    <td align="left" width="20%" colspan="2" id="td_role">
    	<input type="hidden" id="roleValue" value="<%=userInfo.getAt("groupCode")%>" />
		<%=request.getAttribute("roleList") %><font color="red">*</font>
    </td>
    <%} else{%>
    <td align="left" width="20%" colspan="2" id="td_role"><input style="color:blue" readonly value="超级管理员">
		 </td>
		  <%} %>
    <td></td>
  </tr>
  <tr class="tr_11" id="tr_role">
    <td align="left" width="12%">&nbsp;&nbsp;客户</td>   
    <td align="left" width="20%" colspan="2">项目</td>
    <td></td>
  </tr>
  <input type="hidden" id="checkCompanyId" value="<%=userInfo.getAt("company_id") %>" />
  <input type="hidden" id="checkProjectId" value="<%=userInfo.getAt("project_id") %>" />
  <%=request.getAttribute("companyList") %>
  <!-- 
  <tr class="tr_11">
    <td width="7%" align="right">渠道查看权限&nbsp;</td>
    <td ><div align="left">
      <input name="channelPower" type="button"  accesskey="p" tabindex="p" class="but_1" onclick="onChannelPower()" value="配置">
    </div></td>
    </tr>
     -->
  <tr class="tr_11">
    <td width="12%" align="left">&nbsp;&nbsp;备注</td>
    <td align="left" >
      <textarea name="remark" id="remark" rows="5" cols="50" class="txt_1"><%=userInfo.getAt("remark")%></textarea>
    </td>
    <td><font color="red">（字数不能超过30字）</font></td>
  </tr>
  <tr  class="tr_11">
    <td></td>
    <td  align="left">&nbsp;&nbsp;&nbsp;<input type="button" name="ok" accesskey="y" tabindex="y"  value="确 定" class="but_1" onclick="onUpdate()">
	
      <input type="button" name="back" accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doUserInfo.do?method=queryUserInfo'">
    </td>
  </tr>
</table>
</form>
</body>
</html>
