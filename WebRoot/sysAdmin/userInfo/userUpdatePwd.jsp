<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page autoFlush="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
<title>无标题文档</title>
</head>
<script language="javascript">
function onAdd(){

	if(frm.passWrd.value.trim() == ''){
		alert("密码不能为空！");
		frm.passWrd.focus();
		return false;			 
	}
	if(frm.passWrd2.value.trim() == ''){
		alert("密码确认不能为空！");
		frm.passWrd2.focus();
		return false;			 
	}
	if(frm.passWrd.value.trim() != frm.passWrd2.value.trim()){
		alert("两次密码不同！");
		return false;		
	}
	//frm.submit();
}

</script>
<body>
<span class="title_1"></span>
<form name="frm" method="post" action="doUserInfo.do?method=updateUserPwdInfo" onsubmit="return onAdd()">
<input name="id" type="hidden" value="<%=request.getAttribute("id")%>" />
<table width="100%" border="0"cellpadding="0" cellspacing="1"  class="tbl_1">
  <tr class="tr_1">
    <td>密码</td>
    <td><div align="left">
      <input name="passWrd" type="password" class="txt_1"maxlength="22">
    </div></td>
  </tr>
  <tr class="tr_1">
    <td>密码确认</td>
    <td><div align="left">
      <input name="passWrd2" type="password" class="txt_1"maxlength="22">
    </div></td>
  </tr>

  <tr class="tr_0">
    <td  colspan="2"><input type="submit" name="ok"accesskey="y" tabindex="y"  value="确 定(Y)" class="but_1" >
	
      <input type="button" name="back"accesskey="b" tabindex="b" value="返 回(B)" class="but_1" onclick="location='doUserInfo.do?method=queryUserInfo'">
    </td>
  </tr>
</table>
</form>
</body>
</html>
