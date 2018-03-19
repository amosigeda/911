<%@ page language="java" contentType="text/html;charset=gb2312" %>
<%@ page autoFlush="true" %>
<HTML>
<HEAD>
<TITLE>È¨ÏÞÑ¡Ôñ¿ò</TITLE>
</HEAD>
<frameset cols="100%" FRAMEBORDER=0 FRAMESPACING=0 BORDER=0>
<frame name="hello" src="<%=request.getContextPath()%>/sysAdmin/roleInfo/doRoleInfo.do?method=roleFuncInfo&roleCode=<%=request.getParameter("roleCode")%>&factoryCode=<%=request.getParameter("factoryCode")%>" SCROLLING=no FRAMEBORDER=0 NORESIZE MARGINWIDTH=0 MARGINHEIGHT=0>
<frame name="boot"  target="_self" src="" scrolling="auto">
</frameset>

</HTML>