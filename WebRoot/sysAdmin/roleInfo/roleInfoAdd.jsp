<%@ page language="java" contentType="text/html;charset=GB2312"%>
<%@ page autoFlush="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
<title>无标题文档</title>
</head>
<script language="javascript">
var postContent = "";
function onAdd(){
	if(frm.roleName.value.trim() == ''){
		alert("角色名称不能为空！");
		frm.roleName.focus();
		return false;			 
	}

	frm.submit();
}
</script>
<body>
<span class="title_1"></span>
<form name="frm" method="post" action="doRoleInfo.do?method=insertRoleInfo" onsubmit="return onAdd()">
<input name="roleType" type="hidden" value="角色">
<table width="100%"   class="tbl_11">
<tr>
<th colspan="13" nowrap="nowrap" align="left">
添加角色
</th>
</tr>
  <tr class="tr_11">
    <td align="right" width="7%">角色名称</td>
    <td><div align="left">
      <input name="roleName" type="text" class="txt_1"maxlength="20" />
    </div></td>
  </tr>
  <tr class="tr_11">
    <td align="right" width="7%">描述</td>
    <td><div align="left">
      <textarea name="roleDesc" rows="3" cols="50" class="txt_1"></textarea>
    </div></td>
  </tr>
  <tr class="tr_11">
    <td></td>
    <td  colspan="2"><input type="button" name="ok"accesskey="y" tabindex="y"  value="确 定" class="but_1" onclick="onAdd()" >
	
      <input type="button" name="back"accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doRoleInfo.do?method=queryRoleInfo'">
    </td>
  </tr>
</table>
</form>
</body>
</html>
