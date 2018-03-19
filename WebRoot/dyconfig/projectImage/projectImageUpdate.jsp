<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean" %>

<!DOCTYPE HTML>
<html>
  <head>
    
    <title>My JSP 'projectImageUpdate.jsp' starting page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    	<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
		<script type="text/javaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
			
		<script type="text/javascript">
			function updateProjectImageStatus(){
				frmGo.action="doProjectImage.do?method=updateProjectImage";
				frmGo.submit();
			}
		</script>	
		

  </head>
  
  <body>
  	<span class="title_1"></span>
    <form name="frmGo" method="post" action="doProjectImage.do?method=updateProjectImage()">
    	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="tbl_11">
        	<tr>
            	<th colspan="3" nowrap="nowrap" align="left" width="100%">
                                                      编辑教程动态
               	</th>
        	</tr>
           	<logic:iterate id="element" name="pageList">
           		<tr class="tbl_11">
           	    	<td>
           	   			<input type="hidden" id="id" name="id" value="<bean:write name='element' property='id'/>"/>
           	   		</td>
           	   	</tr>
                <tr class="tr_11">
                	<td width="10%">项目名称</td>
               	    <td widht="90%">&nbsp;<bean:write name="element" property="project_name"/></td>	
               	</tr>
               	<tr class="tr_11">
               	 	<td width="10%">下载状态</td>
               	 	<td width="90%">
               	 	  <%=request.getAttribute("downLoadStatus") %>
               	 	</td>	
               	</tr>
               	<tr class="tr_11">
      				<td colspan="12">&nbsp;&nbsp;&nbsp;&nbsp;
      					<input type="button" name="ok" accesskey="y" tabindex="y"  value="确认" onclick="updateProjectImageStatus()" class="but_1">
      					<input type="button" name="back"accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doProjectImage.do?method=queryProjectImage'"  style="font-size:12;width:50px;height:21px;">
      				</td>
    			</tr>
               </logic:iterate>
        </table>
    </form>
  </body>
</html>
