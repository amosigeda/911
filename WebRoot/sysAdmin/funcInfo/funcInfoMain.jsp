<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	//PagePys pys = (PagePys) request.getAttribute("PagePys");
	String trees = (String) request.getAttribute("trees");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dtree.js"></script>
	</head>
	<script language="javascript">
function finds(){
	frmGo.submit();
}

function del(){
	if(countBox()>0){
		if(confirm("确认删除吗，此操作不可恢复"))
		{
			frmGo.action = "doIomsUserInfo.do?method=deleteIomsUserInfo";
			frmGo.submit();
		}
	}else{alert("没有选择记录");}
}
function insert(){
	frmGo.action = "doIomsUserInfo.do?method=initInsert";
	frmGo.submit();
}
function update()
{
	if(countBox()==1){
		frmGo.action="doIomsUserInfo.do?method=initUpdate";
		frmGo.submit();
	}else{alert("请选择一行记录");}

}
function updateP()
{
	if(countBox()==1){
		frmGo.action="userUpdatePws.jsp";
		frmGo.submit();
	}else{alert("请选择一行记录");}

}
</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doFuncInfo.do?method=queryFuncInfo">
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				class="left_tbl_1">
				<tr class="left_tr_1">
					<td height="500" align="left" valign="top">
						<div class="dtree">
							<script type="text/javascript">
	                           <%=trees%>
	                        </script>
						</div>
					</td>
				</tr>
			</table>

			<P></P>

		</form>
	</body>
</html>
