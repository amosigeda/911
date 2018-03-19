<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.care.common.lang.*"%>
<jsp:useBean id="LOGINUSER" class="com.care.app.LoginUser"
	scope="session"></jsp:useBean>
<%@ page autoFlush="true"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<link href="css/style.css" rel="stylesheet" type="text/css">
		<link href="css/tbls.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="../../js/jquery-1.8.2.js"></script>
		<title>无标题文档</title>
		<style type="text/css">
#a_test {
	color: #fff;
}
</style>
	
	<script language="javascript">
	window.setTimeout(CheckShowLeft(), 1000);
	
	function CheckShowLeft(){
	if(document.all) {
        document.getElementById("cd0").click(); 
    } else { 
        var evt = document.createEvent("MouseEvents"); 
        evt.initEvent("click", true, true);
        document.getElementById("cd0").dispatchEvent(evt); 
       }
	}
arrow1=new Image;
arrow2=new Image;
arrow1.src="images/arrowup.gif";
arrow2.src="images/arrowdown.gif";
function switchSysBar_top(){
		if(top.main.rows == "91,*,24"){
			top.main.rows="24,*,24"
			document.all("topmnuList").style.display="none"
			document.img1.src=arrow2.src;
			document.img1.alt="显示菜单";
		}else{
			top.main.rows="91,*,24"
			document.all("topmnuList").style.display=""
			document.img1.src=arrow1.src;
			document.img1.alt="隐藏菜单";
		}
}
function clmenu(code,name){
	//alert(window.parent.leftFrame);
	//window.parent.leftFrame.location.reload(); 
	window.parent.leftFrame.location.href="Left.jsp?code=" + code + "&name=" + name; 
	window.parent.mainFrame.location.href="About.htm"
}
function exit(){
	/* if(!confirm("确定要退出系统？")){
		return ;
	} */
	top.location.href="index.jsp";
}


	function startTime() {
		var today = new Date();
		var y = today.getFullYear();
		var M = today.getMonth()+1;
		var d = today.getDate();
		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();
		// add a zero in front of numbers<10//#8db2e3
		m = checkTime(m);
		s = checkTime(s);
		document.getElementById('txt').innerHTML = y + "年" + M + "月" + d + "日   " + h + ":" + m + ":" + s ;
		t = setTimeout('startTime()', 500);
	}

	function checkTime(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
	function mouseOver(i,j){
	 for(var n=0;n<j;n++){
	    if(n == i){
	       document.getElementById("ch_mouse"+i+"").className="view_cur";
	    }else{
	       document.getElementById("ch_mouse"+n+"").className="view_no_cur";
	    }
	 }
	}
</script>
</head>
  <!-- ;CheckShowLeft() -->
	<body leftMargin=0 topMargin=0 marginwidth="0" marginheight="0"
		onload="startTime();CheckShowLeft()">
	 <div id="top">
  <div class="top_user">
        <span class="smalltext"> 当前用户-<strong><%=LOGINUSER.getUserName()%></strong>-版本(V0.31)-   
          <a href="sysAdmin/sysLogInfo/doSysLogInfo.do?method=getOutTime" id="a_test" onclick="exit()">退出</a></span>
         <span id="txt" class="smalltext">   
          </span>     
  </div>
	<div class="manage_title">
	      数据管理系统
    </div>
<ul class="view_menu">
		   <%
		      CommUtils.getMenuTop(request,response);
		   %>
		   
		</ul>
		</div>
	</body>
</html>
