<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ page autoFlush="true" %>
<%@ page import="com.care.app.LoginUser"%>
<%@ page import="com.care.common.config.Config"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
	<script type="text/javaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
	<title>注册用户</title>
	<style type="text/css">
		#pwdLever td{
			background-color:Gray;
			width:45px;
			text-algin:center;
			font-size:10px;
		}
		#pwdLever tr{height:2px;}
	</style>	
	<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){	
		var isReturn =true;
		
		$("#uName").click(function(){
			$("#uNameSpan").html("字母开头，5-15位，可由字母、数字和&quot;_&quot;组成，注册成功后用户名不可修改");
			$("#uNameSpan").css("color","#30c");
		});
		
		var isUserName = true;
		$("#uName").blur(function(){
			isUserName=true;
			var userName = $("#uName").val().trim();
			if(userName==""){
				$("#uNameSpan").html("您还没有输入用户名");
				$("#uNameSpan").css("color","#f00");
				isUserName = false;
			}else{
				if($.isNumeric(userName)){
					$("#uNameSpan").html("用户名不能全为数字");
					$("#uNameSpan").css("color","#f00");
					isUserName = false;
				}
					
				var reg = /^[a-zA-Z]\w{4,14}$/;
				if(!reg.test(userName)){
					$("#uNameSpan").html("用户名需为字母开头，5-15个字符(包括字母、数字和&quot;_&quot;)");
					$("#uNameSpan").css("color","#f00");
					isUserName = false;
				}
				
				$.ajax({
					type:"get",
					url:"dyconfig/userRegisterInfo/doRegisterUser.do?method=getUserNamefromUserInfo",
					data:"userName="+userName,
					success:function(msg){
						if(msg=="fail"){
							$("#uNameSpan").html("该用户名已经被注册");
							$("#uNameSpan").css("color","#f00");
							isUserName = false;
						}
					}
				});
			}
			if(isUserName){
				$("#uNameSpan").html("");
			}
		});
		
		var isPhoneNo = true;
		$("#phoneNo").blur(function(){
			isPhoneNo = true;
			var phoneNo=$("#phoneNo").val().trim();
			if(phoneNo==""){
				$("#phoneNoSpan").html("您还没有输入手机账号");
				$("#phoneNoSpan").css("color","#f00");
				isPhoneNo = false;
			}else{
				var phoneReg= /^1[3|4|5|7|8][0-9]\d{4,8}$/;
				if(!((phoneNo.length==11)&&(phoneReg.test(phoneNo)))){
					$("#phoneNoSpan").html("不是完整的11位手机账号或者正确的手机账号前7位");
					$("#phoneNoSpan").css("color","#f00");
					isPhoneNo = false;
				}else{					
					$.ajax({
						type:"get",
						url:"dyconfig/userRegisterInfo/doRegisterUser.do?method=getPhoneNofromUserInfo",
						data:"phoneNo="+phoneNo,
						success:function(msg){	
							if(msg=="fail"){
								$("#phoneNoSpan").html("该手机号已经被注册");
								$("#phoneNoSpan").css("color","#f00");
								isPhoneNo = false;						
							}
						}	
					});
				}				
			}
			
			if(isPhoneNo){
				$("#phoneNoSpan").html("");
			}
		});
		
		$("#pwd").click(function(){
			$("#pwdSpan").html("密码由5~15位字母、数字或下滑线组合");
			$("#pwdSpan").css("color","#30c");
						
		});
		
		$("#pwd").keyup(function(){
			$("#pwdSpan").html("<table id='pwdLever' cellspacing='0' cellpadding='0' height='2'><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td style='background-color:#fff'></td></tr></table>");
			var pwd = $("#pwd").val().trim();
			var num= pwdChange(pwd);
			if(num<=1){			
				$("#pwdLever td:eq(0)").css("background-color","#ffd5d5");
				$("#pwdLever td:eq(1)").css("background-color","gray");
				$("#pwdLever td:eq(2)").css("background-color","gray");
				$("#pwdLever td:eq(3)").text("太短");
				$("#pwdLever td:eq(3)").css("color","#ac2402");
			}else if(num==2){
				$("#pwdLever td:eq(0)").css("background-color","#ffd5d5");
				$("#pwdLever td:eq(1)").css("background-color","#ff9b9b");
				$("#pwdLever td:eq(2)").css("background-color","gray");
				$("#pwdLever td:eq(3)").text("较好");
				$("#pwdLever td:eq(3)").css("color","#ff9b9b");
			}else if(num>=3){
				$("#pwdLever td:eq(0)").css("background-color","#ffd5d5");
				$("#pwdLever td:eq(1)").css("background-color","#ff9b9b");
				$("#pwdLever td:eq(2)").css("background-color","#ac2402");
				$("#pwdLever td:eq(3)").text("很强");
				$("#pwdLever td:eq(3)").css("color","#ac2402");
			}else{
				$("#pwdLever td:eq(0)").css("background-color","gray");
				$("#pwdLever td:eq(1)").css("background-color","gray");
				$("#pwdLever td:eq(2)").css("background-color","gray");
			}
		});
		
		var isPassword=true;
		$("#pwd").blur(function(){
			isPassword = true;
			var pwd=$("#pwd").val().trim();
			if(pwd==""){
				$("#pwdSpan").html("您还没有输入密码");
				$("#pwdSpan").css("color","#f00");
				isPassword = false;
			}else{
				if(pwd.length<5){
					$("#pwdSpan").html("密码太短，至少5位");
					$("#pwdSpan").css("color","#f00");
					isPassword = false;
				}
			}
			
			if(isPassword){
				$("#pwdSpan").html("");
			}
		});
		
		var isPasswordcfg = true;
		$("#pwdconfirm").blur(function(){
			isPasswordcfg = true;
			var pwd=$("#pwd").val().trim();
			var pwdcfm=$("#pwdconfirm").val().trim();
			if(pwd!=pwdcfm){
				$("#cfmSpan").html("两次密码输入不一致");
				$("#cfmSpan").css("color","#f00");
				isPasswordcfg = false;
			}
			
			if(isPasswordcfg){
				$("#cfmSpan").html("");
			}
		});
		
		function pwdChange(v){
			var numlgh = 0;
			var reg = /\d/;//如果有数字		
			if(reg.test(v)){			
				numlgh++;
			}
			reg=/[a-zA-Z]/;//如果有字母
			if(reg.test(v)){
				numlgh++;
			}
			
			reg =/[^0-9a-zA-Z]/;//如果有特殊字符
			if(reg.test(v)){
				numlgh++;
			}
			if(v.length<=5){
				numlgh=1;
			}
			return numlgh;
		}
		
		$("#applyReason").click(function(){
			$("#reasonSpan").html("字数不能超过30个");
			$("#reasonSpan").css("color","#30c");
		});
		
		$("#remark").click(function(){
			$("#remarkSpan").html("字数不嫩超过30个");
			$("#remarkSpan").css("color","#30c");
		});
				
		
		$("#smit").click(function(){
			isReturn = isUserName && isPhoneNo &&isPassword && isPasswordcfg;
			var userName = $("#uName").val().trim();
			if(userName==""){
				$("#uNameSpan").html("您还没有输入用户名");
				$("#uNameSpan").css("color","#f00");
				isReturn = isReturn && false;
			}
			
			var phoneNo =$("#phoneNo").val().trim();
			if(phoneNo==""){
				$("#phoneNoSpan").html("您还没有输入手机账号");
				$("#phoneNoSpan").css("color","#f00");
				isReturn = isReturn && false;
			}
			
			var password = $("#pwd").val().trim();
			if(password==""){
				$("#pwdSpan").html("您还没有输入密码");
				$("#pwdSpan").css("color","#f00");
				isReturn = isReturn && false;
			}
			
			var pwdcfg = $("#pwdconfirm").val().trim();
			if(pwdcfg==""){
				$("#cfmSpan").html("您还没有输入确认密码");
				$("#cfmSpan").css("color","#f00");
				isReturn = isReturn && false;
			}
			
			if(isReturn)
			{
				frm.submit();
			}else{
				return;
			}						
		});	
		
		$("#reset").click(function(){
			$("#uNameSpan").html("");
			$("#phoneNoSpan").html("");
			$("#pwdSpan").html("");
			$("#cfmSpan").html("");
			$("#reasonSpan").html("");
			$("#remarkSpan").html("");
		});
	});
	
	
	
	</script>
	
</head>
  
  <body>
    <body>
    <span class="title_1"></span>
	<form name="frm" method="post" action="dyconfig/userRegisterInfo/doRegisterUser.do?method=insertRegisterInfo">
    <table border="0"cellpadding="1" cellspacing="1"  class="tbl_11">
    	<tr>
        	<th colspan="3" nowrap="nowrap" align="left" width="100%">
                                         注册用户
            </th>
        </tr>
  	    <tr class="tr_11">
          <td align="left" width="2%" id="num">&nbsp;&nbsp;注册账号</td>
          <td align="left" width="30%" colspan="2">
             <input name="userName" id="uName" type="text" class="txt_1"maxlength="15"/>&nbsp;<span id="uNameSpan"></span>
          </td>
        </tr>
        <tr class="tr_11">
        	<td align="left" width="2%">&nbsp;&nbsp;手机账号</td>
        	<td align="left" width="30%" colspan="2">
        	<input name="phoneNo" id="phoneNo" type="text" class="txt_1"maxlength="20">&nbsp;<span id="phoneNoSpan"></span>
    	</td>
    	<tr class="tr_11" hidden="true">
        	<td align="left" width="2%">&nbsp;&nbsp;&nbsp;验证码</td>
        	<td align="left" width="30%" colspan="2">
        		<input name="checkPhoneNo" type="text" class="txt_1"maxlength="20"><font color="#595959">*</font>
        		<input type="button" value="免费获取验证码" id="checkPhoneBtn"/>
    		</td>
    	</tr>	
        <tr class="tr_11">
        	<td align="left" width="2%">&nbsp;&nbsp;&nbsp;密&nbsp;码</td>
        	<td align="left" width="30%" colspan="2">
        		<input name="passWrd" type="password" class="txt_1"maxlength="15" id="pwd">&nbsp;<span id="pwdSpan"></span>
    		</td>    		
  		</tr>
  		<tr class="tr_11">
        	<td align="left" width="2%">&nbsp;&nbsp;确认密码</td>
        	<td align="left" width="30%" colspan="2">
        		<input name="passWrd" type="password" class="txt_1"maxlength="15" id="pwdconfirm">&nbsp;<span id="cfmSpan"></span>
    		</td>
  		</tr>
  		
  		<tr class="tr_11">
  			<td align="left" width="2%">&nbsp;&nbsp;申请理由</td>
    		<td align="left" width="30%" colspan="2">
      			<textarea name="applyReason" id="applyReason" rows="5" cols="50" maxLength="30" class="txt_1"></textarea>
      			<span id="reasonSpan"></span>
    		</td>
  		</tr>
  		
  		<tr class="tr_11">
  			<td align="left" width="2%">&nbsp;&nbsp;备注</td>
    		<td align="left" width="30%" colspan="2">
      			<textarea name="remark" id="remark" rows="5" cols="50" class="txt_1" maxLength="30"></textarea>
      			<span id="remarkSpan"></span>
    		</td>
    		
  		</tr>
  		<tr class="tr_11">
  			<td width="2%"></td>
    		<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;
    			<input type="button" name="ok" id="smit"
    			       accesskey="y" tabindex="y" 
    			       value="提交" class="but_1"
    			       style="font-size:12;width:40px;height:21px;"/>
      			<input type="reset" name="back" 
      			       accesskey="b" tabindex="b" 
      			       value="重置" class="but_1" id="reset" style="font-size:12;width:40px;height:21px;"/>  
    		</td>
  		</tr>
	</table>
	</form>
  </body>
</html>
