<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'fail.jsp' starting page</title>
    <style type="text/css">
    body{
    background-image:url(<%=request.getContextPath()%>/images/login/blue.jpg);
	}
	
	a{text-decoration:none;}
	
	#fail{
		width:800px;
		height:200px;
		margin:0 auto;
		text-align:center;
	}
    </style>
	


  </head>
  
  <body>
    <div id="fail"><a href="<%=request.getContextPath()%>/register.jsp">×¢²áÊ§°Ü£¬·µ»Ø×¢²áÒ³Ãæ</a></div>
  </body>
</html>
