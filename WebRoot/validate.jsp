<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %> 
<script language="javascript">
function login(){
	location.href="login.do?code=<%=request.getParameter("code")%>&paw=<%=request.getParameter("paw")%>";
}
function login2(){
	location.href="login.do?code=admin&paw=123456";
}
</script>
<% 
String rand = (String)session.getAttribute("rand"); 
String input = request.getParameter("rand"); 
String code = request.getParameter("code");
String paw = request.getParameter("paw");
if(rand.equals(input)){ 
out.print("<script>login();</script>"); 
} else{ 
out.print("<script>alert('请输入正确的验证码！');location.href='index.jsp';</script>"); 
}
%> 
