<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
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
			src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script> 
        <script language="JavaScript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
	</head>
	<script language="javascript">
function finds(){
	if((frmGo.startTime.value.trim() != '')||(frmGo.endTime.value.trim() != '')){
	    if(frmGo.startTime.value.trim() == ''){
			alert("开始时间不能为空！");
			frmGo.tmBt0.focus();
			return false;			 
		}
		if(frmGo.endTime.value.trim() == ''){
			alert("结束时间不能为空！");
			frmGo.tmBt1.focus();
			return false;			 
		}
		var st = new Date(frmGo.startTime.value.replace(/-/g,'/'));
		var et = new Date(frmGo.endTime.value.replace(/-/g,'/'));
		if(Date.parse(st) - Date.parse(et)>0){
			alert("开始时间不能大于结束时间!!请重新输入");
			return false;
		}
	
	    frmGo.submit();
	}else{
		  frmGo.submit();
	}
}

function querySwitch(){
     frmGo.submit();
}
function del(){
	if(countBox()>0){
		if(confirm("确认删除吗，此操作不可恢复"))
		{
			frmGo.action = "doSysLogInfo.do?method=deleteSysLogInfo";
			frmGo.submit();
		}
	}else{alert("没有选择记录");}
}
function c(){
	document.all.userName.value="";
	/* document.all.logs.value=""; */
	document.all.endTime.value="";
    document.all.startTime.value="";

}
function selectAll(){
   if(document.getElementById('allrow').checked){
   
   }else{
      if(document.all) {
        document.getElementById('allrow').click(); 
      } else { 
        var evt = document.createEvent("MouseEvents"); 
        evt.initEvent("click", true, true);
        document.getElementById('allrow').dispatchEvent(evt); 
      }
      document.getElementById('allrow').checked = true;          //放在后面
   }
    
}

function selectCanAll(){
    if(document.getElementById('allrow').checked){
      if(document.all) {
        document.getElementById('allrow').click(); 
       } else { 
        var evt = document.createEvent("MouseEvents"); 
        evt.initEvent("click", true, true);
        document.getElementById('allrow').dispatchEvent(evt); 
       }
         document.getElementById('allrow').checked = false;          //放在后面
   }else{
   
   } 
}
function ondel(uni,tag){
   if(uni != ''){
      if(confirm("确认删除吗，此操作不可恢复")){
         frmGo.action="doSysLogInfo.do?method=deleteSysLogInfo&uni="+uni+"&tag="+tag;
         frmGo.submit();
      }
   }else{
      if(countBox()>0){
		if(confirm("确认删除吗，此操作不可恢复"))
		{
            frmGo.action="doSysLogInfo.do?method=deleteSysLogInfo&uni="+uni+"&tag="+tag;
            frmGo.submit();
        }
	  }else{alert("没有选择记录");}
   }  
}
</script>
	<body>
		<form name="frmGo" method="post" action="doSysLogInfo.do?method=querySysRecord">
		<%if(request.getAttribute("fNow_date") != null && !"".equals(request.getAttribute("fNow_date"))){ %>
			<table class="table_1" style="font-size:14px;margin-bottom:5px;">
			   <tr>  
			     <td>
			                      时间范围:
			              <font color="#FFA500">
			               <strong >
			               <%=request.getAttribute("fNow_date") %>		            
			                                                       至
			               <%=request.getAttribute("now_date") %></strong>
			            </font>
			     </td>
			   </tr>
			</table>
		<%} %>
			<table width="100%" class="table" >
			     <tr>
                   <th colspan="13" nowrap="nowrap" align="left">
                                                                     登陆记录
                        <!-- <input name="" class="but_1" type="button" accesskey="" value="清除并备份日志" onclick="location.href='doSysLogInfo.do?method=queryBeifenRecord'";>  -->                                             
                   </th>
                 </tr>
				<tr class="title_3">
					<td colspan="5">
					  时间范围
					  <input name="startTime" type="text"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								size="15" class="Wdate" readonly> -
							<input name="endTime" type="text" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								size="15" class="Wdate" readonly>
                     <%-- <input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly> -
							<input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="9" readonly> --%>
						登录账号
					    <%if(request.getAttribute("store")!=null){ %>
							<input name="userName" type="text" class="txt_1" id="userName"
								value="<%=request.getAttribute("store")%>" size="10">
						<%}else{%>
							<input id="userName" name="userName" type="text" class="txt_1"
								value="" size="10">
							<%}%>
					<%-- 	描述
					    <%if(request.getAttribute("store2")!=null){ %>
							<input name="logs" type="text" class="txt_1" id="logs"
								value="<%=request.getAttribute("store2")%>" size="10">
						<%}else{%>
									<input id="logs" name="logs" type="text" class="txt_1" id="logs"
								value="" size="10">
							<%}%> --%>
					  
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
						 <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
					</td>
				</tr>
				<tr class="title_2">
					<!-- <td align="center" width="10%">&nbsp; 
					 <input name="allrow" type="checkbox" id="allrow" value="checkbox"
							onClick="selectAllRow();" style="display:none">
					</td> -->
					<td width="15%"> 
						账号
					</td>
					<td width="15%">  
						登陆时间
					</td>
					<td width="15%">
					    退出时间
					</td>
					<!-- <td width="40%" align="left">
						描述
					</td>   -->                
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<%-- <td align="center" > 
						 <input name="crow" type="checkbox" id="crow" onclick="selectRow();"
								value='<bean:write name="element" property="id" />'>
					    </td> --%>
						<td >
						    <bean:write name="element" property="userName" />
						</td>
						<td >						  
							<bean:write name="element" property="logDate"
								format="yyyy-MM-dd HH:mm:ss" />
						</td>
                        <td>
                          	<bean:write name="element" property="outDate"
								format="yyyy-MM-dd HH:mm:ss" />
							<logic:empty name="element" property="outDate">无</logic:empty>	
                        </td>
						<%-- <td align="left" id="wrap" >
							<bean:write name="element" property="logs" />
						</td> --%>					
					</tr>
				</logic:iterate>
                <tr class="title_3">
                	<!-- <td align="left" colspan="2">
						<a href=# onClick="selectAll();" style="color:#0000FF;text-align:left">全选</a>/
						<a href=# onClick="selectCanAll();" style="color:#0000FF;text-align:left">取消全选</a>-
					    <a href="#" onclick="ondel('',1)" style="color:#0000FF;text-align:left;">批量删除</a>
					</td> -->
					<td colspan="5" align="left" >
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
