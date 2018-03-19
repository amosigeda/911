<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page autoFlush="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>欢迎使用数据管理系统</title>
<SCRIPT language="javascript">
top.moveTo(0,0);
top.resizeTo(screen.availWidth ,screen.availHeight);
</script>
</head>
<!-- noresize="noresize" -->
<frameset rows="7%,90%,3%" cols="*" name="main" frameborder="NO" border="0" framespacing="0">
  <frame src="Top.jsp" name="topFrame" scrolling="NO"  noresize="noresize">
  <frameset cols="10%,90%" frameborder="NO" border="0" framespacing="0" id=setyou>
    <frame src="Left.jsp" name="leftFrame" scrolling="NO" noresize="noresize">
	<!--  <frame src="L.htm" name="lFrame" scrolling="NO" noresize>-->
    <frame src="About.htm" name="mainFrame" noresize>
  </frameset>
  <frame src="down.html" name="bottomFrame" scrolling="No"  id="bottomFrame" noresize="noresize"/>
</frameset>

</html>