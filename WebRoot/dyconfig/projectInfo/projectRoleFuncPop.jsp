<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ page import = "com.godoing.rose.http.common.*" %>
<%@ page import = "com.godoing.rose.lang.*" %>

<%@ page autoFlush="true" %>
<%
	String trees = (String)request.getAttribute("trees");
	//System.out.println("" + trees);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dtrees.js"></script>
</head>
<script language="javascript">
function sels(v0,v1){

	top.returnValue = v0 + "\3" + v1;
	top.close();
}
function clkBoxs(obj){
	if(obj.checked == true){
		ckChickBoxs(obj,true);
		ckSuperBoxs(obj,true);
	}else{
		ckChickBoxs(obj,false);
	}
}
function complete(){
     if (request.readyState == 4) { 
		 if(request.responseText == 1){
			//alert(request.responseText);
			alert("保存成功");
			top.close();
		 }
	 }
}
function save(){
	var url = "doProjectInfo.do?method=insertProjectRoleFuncInfo&roleCodeP=<%=request.getAttribute("roleCodeP")%>&factoryCode=<%=request.getAttribute("factoryCode")%>&funcs="+ckBoxsStr()+"&"+Math.random();
	request.open("GET", url, true);
	request.setRequestHeader("Cache-Control","no-cache");
	request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    request.onreadystatechange  = complete;
	//alert(postContent);
    request.send("");


}
</script>
<body>
<form name="frmGo" method="post" action="doProjectInfo.do?method=queryProjectInfo&pop=true&atTag=<%=request.getParameter("atTag")%>">
<table width="100%" border="0"cellpadding="0" cellspacing="1" class="left_tbl_1">
  <tr class="left_tr_1">
    <td height="400" align="left" valign="top">
		<div class="dtree">
	    <script type="text/javascript">
		
		<%=trees%>

	  </script>
    </div>
	</td>
  </tr>
</table>
<p></p>
<table width="100%" border="0"cellpadding="0" cellspacing="1" class="tbl_1">
	<tr class="left_tr_1">
    <td width="245" ><div align="center">
      <input name="b1" type="button" class="but_1"accesskey="S" tabindex="S" value="保存(S)" onclick="save()">
	  <input name="b2" type="button" class="but_1"accesskey="C" tabindex="C" value="关闭(C)" onclick="top.close()"></div>
    </td>
  </tr>
  </table>
</form>

</body>
</html>

