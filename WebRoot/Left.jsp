<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import = "com.care.common.lang.*" %>
<%@ page autoFlush="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dtree.js"></script>
<title>无标题文档</title>
<script type="text/javascript">
   function CheckShowLeft(){
     if(document.all){
        document.getElementById('pm3').click();
     }else{
        //alert("hello");
        var evt = document.createEvent("MouseEvent");
        evt.initEvent("click",false,true);
        document.getElementById('pm3').click().dispatchEvent(evt);
     }   
   }
</script>
</head>
<!-- onload="CheckShowLeft();" -->
<body onload="CheckShowLeft();">
<table cellpadding="0" cellspacing="0" class="table2" id="leftmenu">
  <tr>
     <td width="120" valign="top" > 
      <script type="text/javascript">
		 <%new CommUtils().printUserMenu(request,response);%>
	  </script>
    </td>
      <td width="15" valign="top">&nbsp;</td>   
  </tr>
</table>
</body>
</html>