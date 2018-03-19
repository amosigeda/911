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
   frmGo.submit();
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
    document.all.endTime.value="";

}
</script>
	<body>
		<form name="frmGo" method="post" action="doSysLogInfo.do?method=deleteSysLogInfo">
			<table width="100%" class="table" >
			     <tr>
                   <th colspan="13" nowrap="nowrap" align="left">
                                                                      清理日志
                   </th>
                 </tr>
				<tr class="title_3">
					<td colspan="13">
					清理时间
                     <input name="endTime" type="text" class="txt_1"  id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly>
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="清除日志" onclick="javascript:finds()">
						 <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除输入条件" onclick="c()">
					</td>
				</tr>
				<tr class="title_2">
					<td width="15%"> 
						详细说明&nbsp;
					</td>
					
					<td width="15%">  
						清理时间&nbsp;
					</td>
					<td width="10%"> 
						ip&nbsp;
					</td>
					<td width="15%">  
						操作时间&nbsp;
					</td>               
					<td>&nbsp;</td>
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<td>
							备份到sysloginfo_beifen表中
						</td>
						<td>
							<bean:write name="element" property="logTime" format="yyyy-MM-dd" />之前
						</td>
						<td>
							<bean:write name="element" property="ip"/>
						</td>
						<td>
							<bean:write name="element" property="updateTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
						</td>
					</tr>
				</logic:iterate>
                <tr class="title_3">
					<td colspan="13" align="left" >
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
