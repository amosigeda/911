<%@ page language="java" contentType="text/html;charset=GB2312"%>
<%@ page autoFlush="true" %>
<%@ page import="com.care.app.LoginUser"%>
<%@ page import="com.care.common.config.Config"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
<title>无标题文档</title>
</head>
<script language="javascript">
function onAdd(){
	if(frmGo.name.value.trim() == ""){
		alert("名称不能为空，请重新输入");
		frmGo.name.focus();
		return false;
	}
	if(frmGo.mName.value.trim() == ""){
		alert("英文名称不能为空，请重新输入");
		frmGo.mName.focus();
		return false;
	}
	frmGo.submit();
}

</script>
<body>
<span class="title_1"></span>
<form name="frmGo" method="post" action="doDynamicInfo.do?method=insertDynamicInfo" onsubmit="return onAdd()">
<% LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
	String loginUserCode = loginUser.getUserCode();
%>
<!-- <input type="hidden" name="addUser" value="<%=loginUserCode %>"> -->
<table width="100%" border="0"cellpadding="0" cellspacing="1"  class="tbl_11">
  <tr>
        <th colspan="3" nowrap="nowrap" align="left">
                                   添加菜单
        </th>
       </tr>
  <tr class="tr_11">
    <td align="left" width="7%">&nbsp;&nbsp;名称</td>
    <td align="left" width="20%" colspan="2">
      <input name="name" id="name" type="text" class="txt_1"maxlength="20"/></font>
    </td>
    </tr>
    
  
   <tr class="tr_11">
    <td align="left" width="7%">&nbsp;&nbsp;英文名</td>
    <td align="left" width="20%" colspan="2">
      <input name="mName" type="mName" class="txt_1"maxlength="20"><font color="red">*</font>
    </td>
  </tr>
    
  <tr class="tr_11">
    <td align="left" width="7%">上级菜单名称</td>
    <td align="left" width="20%" colspan="2">
      <input name="superId" type="superId" class="txt_1"maxlength="20"><font color="red">*</font>
    </td>
  </tr>
  
    <tr class="tr_11">
    <td align="left" width="7%">&nbsp;&nbsp;菜单等级</td>
    <td align="left" width="20%" colspan="2">
      <input name="menuLeave" type="menuLeave" class="txt_1"maxlength="20"><font color="red">*</font>
    </td>
  </tr>
  
    
  <tr class="tr_11">
    <td align="left" width="7%">&nbsp;&nbsp;菜单排行</td>
    <td align="left" width="20%" colspan="2">
      <input name="menuRank" id="menuRank" rows="5" cols="50" class="txt_1"></input>
    </td>
  </tr>
  
<!--    <tr class="tr_11">
    <td align="left" width="7%">子菜单&nbsp;个数</td>
    <td align="left" width="20%" colspan="2">
      <input name="submenuNumber" id="submenuNumber" rows="5" cols="50" class="txt_1"></input>
    </td>
  </tr> -->
  
   <tr class="tr_11">
    <td align="left" width="7%">&nbsp;&nbsp;是否生效</td>
    <td align="left" width="20%" colspan="2">
      <input name="effect" id="effect" rows="5" cols="50" class="txt_1">0不生效1生效
    </td>
    
  </tr>
  
  <tr class="tr_11">
    <td align="left" width="7%">&nbsp;&nbsp;描述</td>
    <td align="left" width="20%" >
      <textarea name="describe" id="describe" rows="5" cols="50" class="txt_1"></textarea>
    </td>
    <td><font color="red"></font></td>
  </tr>
  
  <tr class="tr_11">
  	<td></td><td></td>
  </tr>
  <tr class="tr_11">
  <td width="7%"></td>
    <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;
    <input type="button" name="ok"accesskey="y" tabindex="y"  value="确 定" class="but_1" onclick="onAdd()" style="font-size:12;width:40px;height:21px;">
    <input type="reset" name="reset"accesskey="y" tabindex="y"  value="重置" class="but_1"  style="font-size:12;width:40px;height:21px;">
      <input type="button" name="back"accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doDynamicInfo.do?method=queryDynamicInfo'" style="font-size:12;width:40px;height:21px;">
  
    </td>
  </tr>
</table>
</form>
</body>
</html>