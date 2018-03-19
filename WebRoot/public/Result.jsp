<%@ page language="java" contentType="text/html;charset=gb2312" %>
<%@ page import="com.godoing.rose.lang.*" %>

<jsp:useBean id="result" class="com.godoing.rose.http.common.Result" scope="request"></jsp:useBean>
<%
	String resultType = result.getResultType();
	String resultCode = result.getResultCode();
	
	String backPape = result.getBackPage();
	String myself = "window";
	/*定义为超时*/
	if(resultType == null || resultCode == null || result.getBackPage() == null){
		resultType = "fail";
		resultCode = "timeout";
		backPape= "/GXCareDevice/index.jsp";
		myself = "top";
	}
	/*取得显示说明*/
	SystemException customEx = new SystemException(resultType,resultCode);
	String desc = customEx.getDesc();
	/*定义大类*/
	String pop = "操作失败";
	String tblClass = "result_err";
	String butClass = "result_err_btn";
	String trClass = "result_err_tr";
	String fontClass = "result_err_font";
	if(resultType.equals("success")){
		pop = "操作成功";
		tblClass = "result_cess";
		butClass = "result_cess_btn";
		trClass = "result_cess_tr";
		fontClass = "result_cess_font";
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title> ::DESN-MIS:: </title>
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript">
function myOnload()
{
  backForm.Submits.focus();
}
</script>
<body onload="javascript:myOnload()">
<br>
<p></p><br><p></p><br><p></p>

<table width="72%" height="199"  align="center"  border="0"cellpadding="0" cellspacing="1" class="<%=tblClass%>">
	<tr class="<%=trClass%>"><td colspan="2"></td></tr>
	<tr class="<%=trClass%>"><td colspan="2"></td></tr>
  <tr>
    <td width="50%" height="44"><%=pop%></td>
  </tr>
  <tr class="<%=trClass%>"><td colspan="2"></td></tr>
  <tr class="<%=trClass%>"><td colspan="2"></td></tr>
  <tr class="<%=trClass%>"><td colspan="2"></td></tr>
  <tr class="<%=trClass%>"><td colspan="2"></td></tr>
  <tr class="<%=trClass%>"><td colspan="2"></td></tr>
  <tr>
    <td width="50%" height="128" align="center" class="<%=fontClass%>"><%=desc!=""?desc:resultCode%></td>
  </tr>
  <tr>
    <td colspan="2" align="center">
	<form name="backForm">
      <input name="Submits" type="button" class="<%=butClass%>" onclick="<%=myself%>.location.href='<%=backPape%>'" value="返 回">
	  </form>
    </td>
  </tr>
  <tr class="<%=trClass%>"><td colspan="2"></td></tr>
  <tr class="<%=trClass%>"><td colspan="2"></td></tr>
</table>
<p>&nbsp;</p>
</body>
</html>
<%
	request.removeAttribute("result");
	request.getSession().removeAttribute("result");
%>
