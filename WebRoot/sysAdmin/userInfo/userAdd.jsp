<%@ page language="java" contentType="text/html;charset=GB2312"%>
<%@ page autoFlush="true" %>
<%@ page import="com.care.app.LoginUser"%>
<%@ page import="com.care.common.config.Config"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
<title>无标题文档</title>
</head>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script>
<script type="text/javascript">

    function changeRole(value){
	var role = document.getElementById("groupCode").value;
	var trCompany = document.getElementById("tr4");
	if(trCompany != null){
			$("#tr4").remove();
		}
	if(role == 3){
		$.ajax({
			type:"get",
			url:"doUserInfo.do?method=getAllCompany",
			data:"roleId=3",
			success:function(msg){
				if(msg.trim() != ""){					
					$("#tb1 #tr_role").after(msg);
				}
			}
		
		});		
	}else if(role == 4){
		$.ajax({
			type:"get",
			url:"doUserInfo.do?method=getAllCompany",
			data:"roleId=4",
			success:function(msg){
				if(msg.trim() != ""){					
					$("#tb1 #tr_role").after(msg);
				}
			}
		
		});	
	}else if(role == 5 || role == 6){
		$.ajax({
			type:"get",
			url:"doUserInfo.do?method=getAllProject",
			data:"",
			success:function(msg){
				alert(msg);
				if(msg.trim() != ""){		
					$("#tb1 #tr_role").after(msg);
				}
			}
		
		});
	}
}

 $(document).ready(function(){
	 $("#userCode").blur(function(){
		 var userCodeValue = $("#userCode").val().trim();
		 $.ajax({
			 type:"post",
			 url:"doUserInfo.do?method=getUserCodeFromUserInfo",
		 	 data:"userCode="+userCodeValue,
		 	 success:function(msg){
		 		 if(msg=="fail"){
		 			 alert("该登录账号已经被占用！");
		 			 $("#userCode").focus();
		 			 return false;
		 		 }
		 	 }
		 });
	 });
 });
 function onAdd(){
		if(frm.userCode.value.trim() == ''){
			alert("登录账号不能为空");
			frm.userCode.focus();
			return false;
		}else{
			var userCode = frm.userCode.value;
			var reg = /^[a-zA-Z]\w{4,14}$/;		
			if(!reg.test(userCode)){
				alert("账号格式不正确，请重新输入！");
				frm.userCode.focus();
				return false;
			}
		}
		if(frm.passWrd.value.trim() == ''){
			alert("密码不能为空");
			frm.passWrd.focus();
			return false;
		}else{
			var passWrd = frm.passWrd.value;
			var reg = /^\S{5,15}$/;	
			//var reg1 = /[0-9]{1,}/;
			//var reg2 = /[a-zA-Z]{1,}/;
			if(!reg.test(passWrd)){
				alert("密码应为5-16位字母和数字组合！");
				frm.passWrd.focus();
				return false;
			}else if(passWrd.length<5 || passWrd.length>16){
				alert("密码应为5-16位字母和数字组合！");
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

</script>

<body>
<span class="title_1"></span>
<form name="frm" method="post" action="doUserInfo.do?method=insertUserInfo" onsubmit="return onAdd()">
<% LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
	String loginUserCode = loginUser.getUserCode();
%>
<input type="hidden" name="addUser" value="<%=loginUserCode %>">
<table width="100%" border="0"cellpadding="0" cellspacing="1"  class="tbl_11" id="tb1">
  <tr>
        <th colspan="4" nowrap="nowrap" align="left">
                                    添加系统用户
        </th>
       </tr>
  <tr class="tr_11">
    <td align="left" width="12%">&nbsp;&nbsp;登录账号</td>
    <td align="left" width="20%" colspan="2">
      <input name="userCode" id="userCode" type="text" class="txt_1"maxlength="20"/><font color="red">*（字母开头，5~15位字母、数字或下滑线组合）</font>
    </td>
    <td></td>
    </tr>
   <tr class="tr_11">
    <td align="left" width="12%">&nbsp;&nbsp;密码</td>
    <td align="left" width="20%" colspan="2">
      <input name="passWrd" type="text" class="txt_1"maxlength="20"><font color="red">*（5~15位字母、数字或下滑线组合）</font>
    </td>
    <td></td>
  </tr>
    
  <tr class="tr_11">
    <td align="left" width="12%">&nbsp;&nbsp;是否生效
 	</td>
    <td align="left" width="20%" colspan="2">
      <div style="margin-left: 10px;">是 <input name="tag" type="radio" class="radio_1" value="1" checked="checked" >
	    否<input type="radio" name="tag" class="radio_1" value="0" >
      </div>
    </td>
    <td></td>
  </tr>
  <tr class="tr_11" id="tr_role">
    <td align="left" width="12%">&nbsp;&nbsp;角色</td>   
    <td align="left" width="20%" colspan="2" id="td_role">
		<%=request.getAttribute("roleList") %><font color="red">*</font>
    </td>
    <td></td>
  </tr>
  <tr class="tr_11" id="tr_role">
    <td align="left" width="12%">&nbsp;&nbsp;客户</td>   
    <td align="left" width="20%" colspan="2">项目</td>
    <td></td>
  </tr>
  <%=request.getAttribute("companyList") %>
  <tr class="tr_11" >
    <td align="left" width="12%">&nbsp;&nbsp;备注</td>
    <td align="left" width="20%">
      <textarea name="remark" id="remark" rows="5" cols="50" class="txt_1"></textarea>
    </td>
    <td><font color="red">（字数不能超过30字）</font></td>
    <td></td>
  </tr>
  <tr class="tr_11" id="a1">
  	<td></td><td></td><td></td><td></td>
  </tr>
  <tr class="tr_11">
  <td width="12%"></td>
    <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;<input type="button" name="ok"accesskey="y" tabindex="y"  value="确 定" class="but_1" onclick="onAdd()" style="font-size:12;width:40px;height:21px;">
      <input type="button" name="back"accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doUserInfo.do?method=queryUserInfo'" style="font-size:12;width:40px;height:21px;">
  
    </td>
    <td></td>
    <td></td>
  </tr>
</table>
</form>
</body>
</html>
