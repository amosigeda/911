<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ page import="com.care.common.config.Config"%>
<%@ page import="com.care.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
	LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
	String role = loginUser.getGroupCode();
	String loginUserCode = loginUser.getUserCode();
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- 调用此方法 -->
	</head>
	<script language="javascript">
function finds(){
	frmGo.submit();
}

function del(userCode){
		if(confirm("确定删除吗?"))
		{
			frmGo.action = "doUserInfo.do?method=deleteUserInfo&userCode="+userCode+" ";
			frmGo.submit();
		}
}
function insert(){
	frmGo.action = "doUserInfo.do?method=initInsert";
	frmGo.submit();
}

//修改账户				
function update(userCode)
{
		frmGo.action="doUserInfo.do?method=initUpdate&userCode="+userCode+" ";
		frmGo.submit();
	

}
function updateP()
{
	if(countBox()==1){
		frmGo.action="doUserInfo.do?method=initUpdatePwd";
		frmGo.submit();
	}else{alert("请选择一行记录");}

}
function c(){
	document.getElementById("roleId").options[0].selected=true;
}


</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post"
			action="doUserInfo.do?method=queryUserInfo">
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="13" nowrap="nowrap" align="left">
                                                                系统用户
                 <%if(!"操作员".equals(role)){ %>
                 <input name="inset" type="button" class="but_1" accesskey="a"
							tabindex="a" value="添 加" onclick="insert()">	
				 <%} %>						
                </th>
                </tr>
                </tr>  								
               <tr class="title_3">
               	<td colspan="15">
               	      角色
        		  <%=request.getAttribute("roleList")%>
        		   <input type="hidden" />
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	     
				  <input name="find1" type="button" class="but_1" accesskey="f"
					     tabindex="f" value="搜 索" onclick="javascript:finds()">	     	     				     	     
				  <input name="clear" type="button" class="but_1" accesskey="c"
						 tabindex="c"  value="清除搜索" onclick="c()">
				 </td>		 
				</tr> 
                  <tr class="title_2">                  
					<td width="10%">登录账号</td>                   
					<td width="10%">角色名称</td>					
					<td width="10%">登录密码</td>
					<td width="10%">账户是否生效</td>
					<td width="15%">创建时间</td>
					<td width="15%">最新修改时间</td>
					<td width="15%">备注</td>
					<td width="15%">操作</td>                 
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<bean:define id="userCode" name="element" property="userCode"
							type="java.lang.String" />
						<bean:define id="groupCode" name="element" property="groupCode"
							type="java.lang.String" />							
						<td>
							<bean:write name="element" property="userCode" />
						</td>
						<td>
						<logic:empty name="element" property="roleName">无</logic:empty>
						<logic:notEmpty name="element" property="roleName">						  
						     <bean:write name="element" property="roleName"/>
						     </logic:notEmpty>
						</td>
						<td>
							<bean:write name="element" property="passWrd1" />
						</td>												
						<td>
							<logic:equal name="element" property="tag" value="0"><font style="color:red;font-size: 20px;">×</font></logic:equal>
							<logic:equal name="element" property="tag" value="1"><font style="color:green;font-size: 20px;">√</font></logic:equal>
						</td>
						<td>
							<bean:write name="element" property="createDate"
								format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
						<logic:empty name="element" property="updateDate">无</logic:empty>
						<logic:notEmpty name="element" property="updateDate">
							<bean:write name="element" property="updateDate" format="yyyy-MM-dd HH:mm:ss"/>
							</logic:notEmpty>
						</td>
						<td align="left">
							<logic:empty name="element" property="remark">无</logic:empty>
							<logic:notEmpty name="element" property="remark">
								<bean:write name="element" property="remark"/>
							</logic:notEmpty>							
						</td>
						<td align="left">
							<a href=# onclick="update('<bean:write name="element" property="userCode" />')" style="color:#0000FF" > 【配置】</a>						
							<logic:notEqual name="element" property="userCode" value="<%=loginUserCode %>">
								<a href=# onclick="del('<bean:write name="element" property="userCode" />')" style="color:#0000FF" > 【删除】</a>
							</logic:notEqual>
						</td>
					</tr>
				</logic:iterate>

			  	<tr class="title_3">
					<td colspan="10" align="left" >
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>  
			</table>
		</form>
	</body>
</html>
