<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/js/jquery.js"></script>
	</head>
<script language="javascript">
function finds(){
	frmGo.submit();
}

function del(roleCode){
	if(confirm("确认删除吗，此操作不可恢复"))
		{
			frmGo.action = "doRoleInfo.do?method=deleteRoleInfo&roleCode="+roleCode;
			frmGo.submit();
		}
}
function insert(){
	frmGo.action = "doRoleInfo.do?method=initInsert";
	frmGo.submit();
}
function update(roleCode)
{
	 frmGo.action="doRoleInfo.do?method=initUpdate&roleCode="+roleCode;
	 frmGo.submit();
}
function ofuncs(rcode){
	window.open("roleFuncFrame.jsp?roleCode=" + rcode);
	//window.showModalDialog("roleFuncFrame.jsp?roleCode=" + rcode,		"","help:0;resizable:0;status=0;scrollbars=0;dialogWidth=25;dialogHeight=35;center=true");;
}
</script>
	<body>

		<form name="frmGo" method="post"
			action="doRoleInfo.do?method=queryRoleInfo">
			<table width="100%" class="table" >
               <tr>
                   <th colspan="13" nowrap="nowrap" align="left">角色 
                  <!--  <input name="inset" type="button" class="but_1" accesskey="a"
							tabindex="a" value="添加角色" onclick="insert()"> --></th>
                </tr>         
                  <tr class="title_2">
								
					<td width="10%">
						角色名称
						<%
						//pys.printOrderBy(response, "roleName", "frmGo");
					%>
					</td>
					<td width="10%">
						角色类型
					</td>
					<td width="30%" >
						角色描述
					</td>
					<td width="25%">
						操作
					</td>
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
					<bean:define id="roleCode" name="element" property="roleCode"
							type="java.lang.String" />
					
						<td>
							<bean:write name="element" property="roleName" />
						</td>

						<td>
							<bean:write name="element" property="roleType" />
						</td>
						<td align="left">
							<bean:write name="element" property="roleDesc" />
						</td>
						<td align="left">
							<a href="#"
								onclick="ofuncs('<bean:write name="element" property="id" />')"  class="tbl_A" >【权限设置】</a>
							<a href="#"
							    onclick="update('<bean:write name="element" property="roleCode" />')"  class="tbl_A" >【编辑描述】</a>
						<% if(!(("admin".equals(roleCode)) || ("saler".equals(roleCode)) || ("custom".equals(roleCode)))){%>	
							<!-- <a href="#"
							    onclick="del('<bean:write name="element" property="roleCode" />')"  class="tbl_A" >【删除】</a> -->
						<%} %>
						</td>
					</tr>
				</logic:iterate>
			 	<tr class="title_3">
					<td colspan="8" align="left" >
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>  
			</table>
		</form>
	</body>
</html>
