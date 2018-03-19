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
%>

<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
	<script type="text/javaScript" src="<%=request.getContextPath()%>/public/public.js"></script>
	<title>无标题文档</title>	
	<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
	<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- 调用此方法 -->		
	<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript">		
	function checkPhoneNo(){
		var phoneNo = document.getElementById("phoneNo").value;
		if(isNaN(phoneNo)){
			alert("手机号必须全部为数字！");
			document.getElementById("phoneNo").focus();
			return false;
		}
	}
	
	function finds(){
		var st = new Date(frmGo.startTime.value.replace(/-/g,'/'));
		var et = new Date(frmGo.endTime.value.replace(/-/g,'/'));
		if(Date.parse(st) - Date.parse(et)>0){
			alert("开始时间不能大于结束时间!");
			return false;
		}
		
		var phoneNo = document.getElementById("phoneNo").value;
		if(isNaN(phoneNo)){
			alert("手机号必须全部为数字！");
			return false;
		}
		frmGo.submit();
	}
	
	function setRole(objId){
		frmGo.action ="doRegisterUser.do?method=initChangeRoleInfo&id="+objId;
		frmGo.submit();
	}
	
	function setTag(objId){
		frmGo.action="doRegisterUser.do?method=initChangeTagInfo&id="+objId;
		frmGo.submit();
	}
	
	function c(){
		document.getElementById("startTime").value="";
		document.getElementById("endTime").value="";
		document.getElementById("phoneNo").value="";
		document.getElementById("roleId").options[0].selected=true;
		document.getElementById("apply_status").options[0].selected=true;
	}
	</script>
  </head>
  
  <body>
    <body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doRegisterUser.do?method=queryRegisterInfo">
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <a href="/dyconfig/userRegisterInfo/addRegisterInfo.jsp"><th colspan="12" nowrap="nowrap" align="left">
                	所有注册
                </th>
               </tr>  								
               <tr class="title_3">
               	<td colspan="15">
					申请时间
                  <input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
						 value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
						 size="9" readonly>-
				  <input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
						 value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
						 size="9" readonly>				       
				         手机账号
				  <input name="phoneNo" type="text" class="txt_1" id="phoneNo" size=13 style="cursor:text" value="<%CommUtils.printReqByAtt(request,response,"phoneNo");%>" onBlur="checkPhoneNo()">
        		        角色
        		  <%=request.getAttribute("roleList")%>
        		       状态
        		  <%=request.getAttribute("apply_status")%>     
				  <input type="hidden" />
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	     
				  <input name="find1" type="button" class="but_1" accesskey="f"
					     tabindex="f" value="搜 索" onclick="javascript:finds()">	     	     				     	     
				  <input name="clear" type="button" class="but_1" accesskey="c"
						 tabindex="c"  value="清除搜索" onclick="c()">
				 </td>		 
				</tr>               
                <tr class="title_2"> 
                  	 <td width="15%">姓名</std>
                  	 <td width="8%">密码</td>
                  	 <td width="10%">手机账号</td>
                  	 <td width="8%">角色</td>
                     <td width="8%">状态</td>
                     <td width="10%">申请日期</td> 
                     <td width="10%">申请理由</td>           	                 	
                  	 <td width="15%">操作</td>                              	                 	  						 
			    </tr>
			    <logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"'>						
						<td>							
							<bean:write name="element" property="userName" />
						</td>
						<td>
							<bean:write name="element" property="passWrd1"/>
						</td>
						<td>
							<bean:write name="element" property="phoneNo"/>
							<logic:empty name="element" property="phoneNo">无</logic:empty>
						</td>	
						<td>
							<bean:write name="element" property="roleName"/>
							<logic:empty name="element" property="roleName">无</logic:empty>
						</td>
						<logic:equal name="element" property="apply_status" value="0">
							<td style="color:black;">新申请</td>
						</logic:equal>
						<logic:equal name="element" property="apply_status" value="1">
							<td style="color:green;">批准</td>
						</logic:equal>
						<logic:equal name="element" property="apply_status" value="2">
							<td style="color:red;">不批准</td>
						</logic:equal>
						<!--  
						<td style="color:green;">
							<logic:equal name="element" property="apply_status" value="0">新申请</logic:equal>
							<logic:equal name="element" property="apply_status" value="1">批准</logic:equal>
							<logic:equal name="element" property="apply_status" value="2" >不批准</logic:equal>
						</td>
						-->
						<td>
							<bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<bean:write name="element" property="applyReason"/>
							<logic:empty name="element" property="applyReason">无</logic:empty>
						</td>
						<td align="left">
							<logic:equal  name="element" property="apply_status" value="0">【角色分配】</logic:equal>
							<logic:equal  name="element" property="apply_status" value="1">
								<a href=# onclick="setRole('<bean:write name="element" property="id"/>')" style="color:#0000FF">【角色分配】</a>
							</logic:equal>
							<logic:equal  name="element" property="apply_status" value="2">【角色分配】</logic:equal>							
													
							<a href=# onclick="setTag('<bean:write name="element" property="id"/>')" style="color:#0000FF">【是否审批】 </a>
						</td>
					</tr>		
				</logic:iterate>		   
			  <tr class="title_3">
				<td colspan="12" align="left" >  
					<%
						pys.printGoPage(response,"frmGo");
					%>
				</td>
			 </tr>  
			</table>
		</form>
  </body>
</html>
