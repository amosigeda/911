<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import = "com.godoing.rose.http.common.*" %>
<%@ page import = "com.godoing.rose.lang.*" %>
<jsp:useBean id = "roleInfo" scope = "request" class = "com.godoing.rose.lang.DataMap"/>
<%@ page autoFlush="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
<title>无标题文档</title>
</head>
<script language="javascript">
function onUpdate(){
	if(frm.roleDesc.value.trim() == ''){
		alert("描述不能为空");
		frm.roleDesc.focus();
		return false;
	}else if(frm.roleDesc.value.trim().length > 30){
		alert("字数不能超过30字");
		frm.roleDesc.focus();
		return false;
	}
}
</script>
<body>
<span class="title_1"> </span>
<form name="frm" method="post" action="doRoleInfo.do?method=updateRoleInfo" onsubmit="return onUpdate()">
<input name="roleCode" type="hidden" value="<%=roleInfo.getAt("roleCode")%>" >
<input name="roleName" type="hidden" value="<%=roleInfo.getAt("roleName")%>" >
 <input name="roleType" type="hidden" value="<%=roleInfo.getAt("roleType") %>" />
<table width="100%" border="0"cellpadding="0" cellspacing="1"  class="tbl_11">
  <tr>
     <th colspan="3" nowrap="nowrap" align="left">
                           编辑描述
     </th>
   </tr>
<!--   <tr class="tr_1">
    <td width="24%">角色编码</td>
    <td width="76%"><div align="left"><input name="roleCode" type="text" class="txt_1"maxlength="20"
		value="<%=roleInfo.getAt("roleCode")%>" readonly></div></td>
  </tr>
  <tr class="tr_1">
    <td>角色名称</td>
    <td><div align="left">
      <input name="roleName" type="text" class="txt_1"maxlength="20"
		value="<%=roleInfo.getAt("roleName")%>" readonly>
    </div></td>
  </tr>  -->
  
  <tr class="tr_11">
    <td align="left" width="5%">&nbsp;&nbsp;&nbsp;描述</td>
    <td align="left" width="20%">
    <textarea name="roleDesc" id="roleDesc" rows="5" cols="50" class="txt_1"><%=roleInfo.getAt("roleDesc")%></textarea>
    </td>
    <td><font color="red">*（字数不能超过30字）</font></td>
  </tr>
  
  <tr><td>&nbsp;</td></tr>
  <tr class="tr_11" align="center">
  	<td></td>
    <td align="left">
    	&nbsp;&nbsp;<input type="submit" name="ok" accesskey="y" tabindex="y"  value="确定" class="but_1" style="font-size:11;width:40px;height:21px;" onclick="onUpdate()">
      	<input type="button" name="back" accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doRoleInfo.do?method=queryRoleInfo'" style="font-size:11;width:40px;height:21px;">  
    </td>
  </tr>
</table>
</form>
</body>
</html>
