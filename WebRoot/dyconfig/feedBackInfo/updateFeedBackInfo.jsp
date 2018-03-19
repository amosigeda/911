<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import = "com.godoing.rose.http.common.*" %>
<%@ page import = "com.godoing.rose.lang.*" %>

<jsp:useBean id = "feedBackInfo" scope = "request"  class = "com.godoing.rose.lang.DataMap"/>
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
	if(frmGo.procontent.value.trim() == ''){
		alert("处理内容不能为空");
		frmGo.procontent.focus();
		return false;
	}

	frmGo.submit();
}


</script>
<body>
<span class="title_1"></span>
<form name="frmGo" method="post" action="doFeedBackInfo.do?method=updateFeedBackInfo" onsubmit="return onUpdate()">
<input name="id" type="hidden" value="<%=feedBackInfo.getAt("id")%>" >
<table width="100%" border="0"cellpadding="0" cellspacing="1"  class="tbl_11"> 
  <tr>
     <th colspan="13" nowrap="nowrap" align="left">
                        意见反馈配置
     </th>
   </tr>
  <tr class="tr_11">
    <td width="7%" align="left">&nbsp;&nbsp;处理内容</td>
    <td width="80%" align="left">
    	 <textarea name="procontent" id="procontent" rows="5" cols="50" class="txt_1" maxlength="200"><%=feedBackInfo.getAt("processing_content")%></textarea><font color="red">(最多输入200个字符)</font>
    </td>
  </tr>
  <tr  class="tr_11"> 
    <td></td>
    <td  align="left"><input type="button" name="ok" accesskey="y" tabindex="y"  value="确 定" class="but_1" onclick="onUpdate()">
      <input type="button" name="back" accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doFeedBackInfo.do?method=queryFeedBackInfo'">
       <input type="reset" name="back" accesskey="b" tabindex="b" value="重置" class="but_1" >
    </td>	     
  </tr>
</table>
</form>
</body>
</html>