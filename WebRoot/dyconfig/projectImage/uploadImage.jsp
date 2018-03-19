<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.godoing.rose.lang.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ page import="com.care.common.config.Config"%>
<%@ page import="com.care.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<jsp:useBean id = "checkInfo" scope = "request" class = "com.godoing.rose.lang.DataMap"/>
<%@ page autoFlush="true"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>My JSP 'uploadImage.jsp' starting page</title>
	<link href="<%=request.getContextPath()%>/css/tbls.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- 调用此方法 -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script>
	<script type="text/javascript">
		$("document").ready(function(){
			
			$("#downloadpath2").hide();
			$("#downloadpath3").hide();
			$("#downloadpath4").hide();
			$("#downloadpath5").hide();
						
			$("#projectName").change(function(){
				var projectId = $("#projectName").val();
				var id =$("#projectImageId").val();
				$.ajax({
					type:"post",
					url:"doProjectImage?method=",
					data:"id="+id+"&projectId="+projectId,
					success:function(msg){
						
					}
				});
			});
			
			$("#downloadpath1").change(function(){		
				var url = null;
				if(window.createObjectURL!=undefined){
					url = window.createObjectURL(this.files[0]);
				}else if(window.URL!=undefined){
					url = window.URL.createObjectURL(this.files[0]);
				}else if(window.webkitURL!=undefined){
					url = window.webkitURL.createObjectURL(this.files[0]);
				}
				
				if(url){
					$("#image1").attr("src",url);
					$("#image1").css("width",50);
					$("#downloadpath2").toggle();
					
				}					
			});
			
			
			$("#downloadpath2").change(function(){
				var url = null;
				if(window.createObjectURL!=undefined){
					url = window.createObjectURL(this.files[0]);
				}else if(window.URL!=undefined){
					url = window.URL.createObjectURL(this.files[0]);
				}else if(window.webkitURL!=undefined){
					url = window.webkitURL.createObjectURL(this.files[0]);
				}
				
				if(url){
					$("#image2").attr("src",url);
					$("#image2").css("width",50);
					$("#downloadpath3").toggle();
				}					
			});
			
			$("#downloadpath3").change(function(){
				var url = null;
				if(window.createObjectURL!=undefined){
					url = window.createObjectURL(this.files[0]);
				}else if(window.URL!=undefined){
					url = window.URL.createObjectURL(this.files[0]);
				}else if(window.webkitURL!=undefined){
					url = window.webkitURL.createObjectURL(this.files[0]);
				}
				
				if(url){
					$("#image3").attr("src",url);
					$("#image3").css("width",50);
					$("#downloadpath4").toggle();
				}					
			});
			
			$("#downloadpath4").change(function(){
				var url = null;
				if(window.createObjectURL!=undefined){
					url = window.createObjectURL(this.files[0]);
				}else if(window.URL!=undefined){
					url = window.URL.createObjectURL(this.files[0]);
				}else if(window.webkitURL!=undefined){
					url = window.webkitURL.createObjectURL(this.files[0]);
				}
				
				if(url){
					$("#image4").attr("src",url);
					$("#image4").css("width",50);
					$("#downloadpath5").toggle();
				}					
			});
			
			$("#downloadpath5").change(function(){
				if(window.createObjectURL!=undefined){
					url = window.createObjectURL(this.files[0]);
				}else if(window.URL!=undefined){
					url = window.URL.createObjectURL(this.files[0]);
				}else if(window.webkitURL!=undefined){
					url = window.webkitURL.createObjectURL(this.files[0]);
				}
				
				if(url){
					$("#image5").attr("src",url);
					$("#image5").css("width",50);
				}					
			});			
		});
		
		function addProjectNameOption(){
			var projectName = document.getElementById("projectName");
			projectName.appendChild("<Option value='0' selected>===请选择===</Option>");
		}
		
		function changeProjectOption(){
			var projectId = document.getElementById("projectName").value;
		}
		
	
		
		function uploadImage(){
			var projectId = document.getElementById("projectName").value;
			var downloadpath1 = frmGo.downloadpath1.value;
			var downloadpath2 = frmGo.downloadpath2.value;
			var downloadpath3 = frmGo.downloadpath3.value;
			var downloadpath4 = frmGo.downloadpath4.value;
			var downloadpath5 = frmGo.downloadpath5.value;
			
			frmGo.action="doProjectImage.do?method=uploadImage"
					                        +"&projectId="+projectId
					                        +"&downloadpath1="+downloadpath1
					                        +"&downloadpath2="+downloadpath2
					                        +"&downloadpath3="+downloadpath3
					                        +"&downloadpath4="+downloadpath4
					                        +"&downloadpath5="+downloadpath5;
			frmGo.submit();
		}
	</script>
  </head>
  
  <body>
 <body onload="">
	<span class="title_1"></span>
		<form name="frmGo" method="post" action="doProjectImage.do?method=uploadImage" encType="multipart/form-data">
			<input name="downloadPath" type="hidden" />
			<table width="100%" class="tbl_11" border="0" cellpadding="1" cellspacing="1" >
               <tr>
                  <th colspan="12" nowrap="nowrap" align="left">
                                                              上传图片   <input type="hidden" id="projectImageId" value="<%=request.getAttribute("id")%>"/>                               
               	  </th>
                </tr>   
    			<tr class="tb1_11">
    				<td width="10%" align="right">项目名称&nbsp;&nbsp;&nbsp;</td>
    				<td width="20%" align="left" id="projectId"><%=request.getAttribute("projectName") %></td>    			
    			</tr>
    			<tr>
    				<td width="10%" align="right">图片一&nbsp;&nbsp;&nbsp;</td>
    				<td width="20%">
    					<input type="file" name="downloadpath1" id="downloadpath1" multiple="multiple" class="imagePath"/>
    					<img alt="" src="" id="image1" class="showImage">
    				</td>
    			</tr>
    			<tr>
    				<td width="10%" align="right">图片二&nbsp;&nbsp;&nbsp;</td>
    				<td width="20%">
    					<input type="file" name="downloadpath2" id="downloadpath2" multiple="multiple" class="imagePath"/>
    					<img alt="" src="" id="image2" class="showImage">
    				</td>
    			</tr>
    			<tr>
    				<td width="10%" align="right">图片三&nbsp;&nbsp;&nbsp;</td>
    				<td width="20%">
    					<input type="file" name="downloadpath3" id="downloadpath3" class="imagePath"/>
    					<img alt="" src="" id="image3" class="showImage">
    				</td>
    			</tr>
    			<tr>
    				<td width="10%" align="right">图片四&nbsp;&nbsp;&nbsp;</td>
    				<td width="20%">
    					<input type="file" name="downloadpath4" id="downloadpath4" class="imagePath"/>
    					<img alt="" src="" id="image4" class="showImage">
    				</td>
    			</tr>
    			<tr>
    				<td width="10%" align="right">图片五&nbsp;&nbsp;&nbsp;</td>
    				<td width="20%">
    					<input type="file" name="downloadpath5" id="downloadpath5" class="imagePath"/>
    					<img alt="" src="" id="image5" class="showImage">
    				</td>
    			</tr>
    			<tr class="tr_11">
      				<td>&nbsp;</td>
      				<td align="left">
      				    <input type="button" name="ok"accesskey="y" tabindex="y"  value="确 定" class="but_1" onclick="uploadImage()" style="font-size:12;width:40px;height:21px;">
      					<input type="button" name="back"accesskey="b" tabindex="b" value="返 回" class="but_1" onclick="location='doProjectImage.do?method=queryProjectImage'" style="font-size:12;width:40px;height:21px;">
      				</td>
     	    		<td><br></td>    
    			</tr>
			</table>
		</form>
	</body>
</html>
