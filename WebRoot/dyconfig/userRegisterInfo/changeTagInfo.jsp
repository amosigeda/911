<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ page import="com.care.common.config.Config"%>
<%@ page import="com.care.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
	<script type="text/javaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
	<title>无标题文档</title>
	<script type="text/javascript">
		function changeTag(){
			frm.actio="doRegisterUser.do?method=updateTag";
			frm.submit();
		}
	</script>	
</head>
  
  <body>
    <body>
    <span class="title_1"></span>
	<form name="frm" method="post" action="doRegisterUser.do?method=updateTag" onsubmit="return onAdd()">
    <table width="100%" border="0"cellpadding="0" cellspacing="1"  class="tbl_11">
    	<tr>
        	<th colspan="3" nowrap="nowrap" align="left">
            	审批
            </th>
        </tr>
        <tr class="tr_11">
        	<td>
        		<input type="hidden" id="id" name="id" value="<bean:write name="userInfo" property="id"/>"/>
        	</td>
        </tr>
  	    <tr class="tr_11">
          <td align="right" width="10%">&nbsp;&nbsp;用户名&nbsp;&nbsp;</td>
          <td align="left" width="90%" colspan="2">
          	<bean:write name="userInfo" property="userName"/>        
          </td>
        </tr>
        <tr class="tr_11">
    		<td align="right" width="10%">是否审批&nbsp;&nbsp;</td>
    		<td align="left" width="90%" colspan="2">
    		<%=request.getAttribute("status") %>      		
  		</tr>
  		<tr class="tr_11" align="left">
  			<td></td>
    		<td align="left" width="90%" colspan="2">
    			<input type="button" name="ok" 
    			       accesskey="y" tabindex="y" 
    			       value="确 定" class="but_1"
    			       onclick="changeTag()" 
    			       style="font-size:12;width:40px;height:21px;text-align:left"/>
    			       
      			<input type="button" name="back" 
      			       accesskey="b" tabindex="b" 
      			       value="返 回" class="but_1"
      			       onclick="location='doRegisterUser.do?method=queryRegisterInfo'" 
      			       style="font-size:12;width:40px;height:21px;text-align:left"/>  
    		</td>
  		</tr>
	</table>
	</form>
  </body>
</html>
