<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ page import="com.care.common.config.Config"%>
<%@ page import="com.care.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%int i=0; %>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- 调用此方法 -->
		<script language="JavaScript" src="../../js/jquery-1.8.2.js"></script>
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
<title>无标题文档</title>
</head>
<script language="javascript">
var value = "";
var userIds;
$(document).ready(function(){
	userIds = document.getElementsByName("userId");
	for(var i=0;i<userIds.length;i++){
		if(i == userIds.length-1){
			value += userIds[i].value;
		}else{
			value += userIds[i].value+",";
		}		
	}
});


var j=0;
function onUpdate(){
	var flag = false;
	var values = value.trim().split(",");
	var user = document.getElementsByName("userId");
	if(values.length == user.length){
		for(var i=0;i<values.length;i++){
			if(values[i] != user[i].value){
				flag = true;
			}
		}
	}else{
		if(values.length == 1 && values[0] == "" && user.length == 0){
			falg = false;
		}else{
			flag = true;
		}		
	}
	if(flag){
		frm.submit();
	}else{
		if(confirm("配置未更改，是否保存？")){
			frm.action = "doCheckInfo.do?method=queryCheckInfo";
			frm.submit();
		}
	}
}
function findUser(){
	var name = document.getElementById("userName").value;
	$.ajax({
		type:"get",
		url:"doCheckInfo.do?method=getAppUserName",
		data:"userName="+name,
		success:function(msg){			
			if(msg.trim() == ""){
				alert("手机号未注册，请重新输入");
				return false;
			}else{
				var user = msg.split(",");
				if(user.length == 2){
					var userId = user[0];
					var userName = user[1];
					var oldUserId = document.getElementsByName("userId");
					if(oldUserId.length > 0){
						for(var i=0;i<oldUserId.length;i++){
							if(userId == oldUserId[i].value){
								alert("已添加过该手机号，请重新输入");
								return false;
							}
						}						
					}
					var tr = $("<tr class='tr_11' id='tr_"+userId+"'><td ><input type='hidden' name='userId' value='"+ userId+"' /></td><td><a style='color:#0000FF' href='../appUserInfo/doAppUserInfo.do?method=queryAppUserInfo&user_id="+userId+"'>"+ userName +"</a> <input type='button' value='删除' onclick='removeTr(this)' id='"+userId+"' /></td><td></td></tr>")
   					$("#table1").append(tr);
   					document.getElementById("userName").value="";
				}
			}
			
		}
	});
}

function addUser(){
	var userId = document.getElementsByName("appUserId");
	var userName = document.getElementsByName("p_name");
	for(var i=0;i<userId.length; i++){
		if(userId[i].checked){
			var tr = $("<tr class='tr_11' id='tr_"+j+"'><td ><input type='hidden' name='userId' value='"+ userId[i].value+"' /></td><td>"+ userName[i].innerHTML +" <input type='button' value='删除' onclick='removeTr(this)' id='"+userId+"' /></td><td></td></tr>")
   			$("#table1").append(tr);
		}
	}
}
function removeTr(obj){
	if(confirm("是否确定删除？")){
		$("#tr_"+obj.id).remove();
	}
}
function c(){
	document.getElementById("userName").value="";
}

</script>
<body>
<span class="title_1"></span>
<form name="frm" method="post" action="doCheckInfo.do?method=updateUpUser" onsubmit="return onUpdate()">
<input name="id" type="hidden" value="<%=request.getAttribute("id")%>" >
<table width="100%" border="0" cellpadding="0" cellspacing="1"  class="tbl_11"> 
  <tr>
     <th colspan="13" nowrap="nowrap" align="left">
                           配置
     </th>
   </tr>
   
  <tr class="tr_11">
  	<td width="10%"></td>
    <td width="40%" align="center">请选择用户</td>
    <td width="50%"></td>
  </tr>
  <tr><td></td>
  <td>
  <table name="table1" id="table1">
  <logic:iterate id="element" name="userNameList">
  	<tr id="tr_<bean:write name="element" property="id"/>" >
  		<td><input type="hidden" name="userId" value=<bean:write name="element" property="id"/> /></td>
  		<td>
  			<a style="color:#0000FF" href="../appUserInfo/doAppUserInfo.do?method=queryAppUserInfo&user_id=<bean:write name="element" property="id" />">
				<bean:write name="element" property="user_name"/>
			</a>
  			<input type="button" value="删除" onclick="removeTr(this)" id="<bean:write name="element" property="id"/>"/>
  		</td>
  		<td></td>
  	</tr>
  </logic:iterate>
  </table>
  </td>
  
  <td></td>
  </tr>
  <tr>
  	<td></td>
  	<td>
  		<input type="text" name="userName" id="userName"/>
  		<input name="find1" type="button" class="but_1" value="添加" onclick="findUser()">
  		<font color="red">*（请填写手机号）</font>
  		<input name="clearNum" type="button" class="but_1" value="清空" onclick="c()">
  	</td>
  	<td></td>
  </tr>
 
 
  <tr  class="tr_11">
    <td></td>
    <td  align="left">
    <input type="button" name="back" accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doCheckInfo.do?method=queryCheckInfo'"> 
    <input type="button" name="ok" accesskey="y" tabindex="y"  value="确 定" class="but_1" onclick="onUpdate()">	     
    </td>
    <td><br></td>
  </tr>
</table>
</form>
</body>
</html>