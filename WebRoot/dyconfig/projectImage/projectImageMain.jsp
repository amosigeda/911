<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.care.common.lang.*"%>
<%@ page import="com.care.common.config.Config"%>
<%@ page import="com.care.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%
	PagePys pys = (PagePys)request.getAttribute("pagePys");
%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>My JSP 'projectImageMain.jsp' starting page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="<%=request.getContextPath()%>/css/tbls.css"
									rel="stylesheet" type="text/css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- 调用此方法 -->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
			a{right:0px;bottom:0px;}
		</style>	
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script>
		<script type="text/javascript">
			function downLoadImage(downloadpath){
				frmGo.action = "doProjectImage.do?method=downloadImage&download="+downloadpath;
				frmGo.submit();
			}
			
			function initUpLoadImage(){
				frmGo.action = "doProjectImage.do?method=initUploadImage";
				frmGo.submit();
			}
			
							
			function updateProjectImage(objId,downloadstatus){
				var tdNode = event.target.parentNode;			
				var trNode = tdNode.parentNode;
				var nodeLists = trNode.childNodes;
				$.ajax({
					type:"post",
					url:"doProjectImage.do?method=updateProjectImage",
					data:"id="+objId+"&downLoadStatus="+downloadstatus,
					success:function(msg){
						if(msg=="fail"){
							alert("操作失败！");
						}else{
							var jsonData=eval(msg);
													
							if(downloadstatus=="0"){								
								tdNode.innerHTML="<a href='#' onclick='updateProjectImage("+objId+",1)' class='tbl_A'>启用</a>&nbsp;"+
												 "<a href='#' onclick='hideline("+objId+")' class='tbl_B'>删除</a>";		   
								nodeLists.item(13).innerHTML="<font color='red'>禁用</font>";
								nodeLists.item(3).innerHTML="";
								nodeLists.item(5).innerHTML="";
								nodeLists.item(7).innerHTML="";
								nodeLists.item(9).innerHTML="";
								nodeLists.item(11).innerHTML="";
							}else if(downloadstatus=="1"){	
								
								tdNode.innerHTML="<a href='#' onclick='updateProjectImage("+objId+",0)' class='tbl_A'>禁用</a>&nbsp;"+
								 				 "<a href='#' onclick='hideline("+objId+")' class='tbl_B'>删除</a>";
								nodeLists.item(13).innerHTML="已启用";
								
								if(jsonData[0].downloadpath1==""){
									nodeLists.item(3).innerHTML="无";
								}else{
									nodeLists.item(3).innerHTML="<a href='#' onclick=downLoadImage('"+jsonData[0].downloadpath1+"') class='tbl_A'>下载</a>&nbsp;"+
															    "<a href='#' onclick=deleteImage("+objId+",1,'"+jsonData[0].downloadpath1+"') class='tbl_A'>删除</a>";
								}
								
								if(jsonData[0].downloadpath2==""){
									nodeLists.item(5).innerHTML="无";
								}else{
									nodeLists.item(5).innerHTML="<a href='#' onclick=downLoadImage('"+jsonData[0].downloadpath2+"') class='tbl_A'>下载</a>&nbsp;"+
								    							"<a href='#' onclick=deleteImage("+objId+",2,'"+jsonData[0].downloadpath2+"') class='tbl_A'>删除</a>";
								}
								
								if(jsonData[0].downloadpath3==""){
									nodeLists.item(7).innerHTML="无";
								}else{
									nodeLists.item(7).innerHTML="<a href='#' onclick=downLoadImage('"+jsonData[0].downloadpath3+"') class='tbl_A'>下载</a>&nbsp;"+
								    							"<a href='#' onclick=deleteImage("+objId+",3,'"+jsonData[0].downloadpath3+"') class='tbl_A'>删除</a>";
								}
								
								if(jsonData[0].downloadpath4==""){
									nodeLists.item(9).innerHTML="无";
								}else{
									nodeLists.item(9).innerHTML="<a href='#' onclick=downLoadImage('"+jsonData[0].downloadpath4+"') class='tbl_A'>下载</a>&nbsp;"+
								    							"<a href='#' onclick=deleteImage("+objId+",4,'"+jsonData[0].downloadpath4+"') class='tbl_A'>删除</a>";
								}
								
								if(jsonData[0].downloadpath5==""){
									nodeLists.item(11).innerHTML="无";
								}else{
									nodeLists.item(11).innerHTML="<a href='#' onclick=downLoadImage('"+jsonData[0].downloadpath5+"') class='tbl_A'>下载</a>&nbsp;"+
								    							 "<a href='#' onclick=deleteImage("+objId+",5,'"+jsonData[0].downloadpath5+"') class='tbl_A'>删除</a>";
								}
							}			
						}
					}
				});
			}
			
			function deleteImage(objId,sortPath,downloadpath){
				var tdNode = event.target.parentNode;				
				var isOK = confirm("您确认删除此图片吗？");
				
				if(isOK){					
					$.ajax({
						type:"post",
						url:"doProjectImage.do?method=deleteImagePath",
					    data:"id="+objId+"&sortPath="+sortPath+"&downloadpath="+downloadpath,
					    success:function(msg){
					    	if(msg=="success"){
					    		tdNode.innerHTML="无";
					    	}else if(msg=="fail"){
					    		alert("删除失败");
					    	}					    		
					    }
					});							
				}
			}
			
			function hideline(objId){
				var tdNode = event.target.parentNode;
				var trNode = tdNode.parentNode;
				var tbodyNode=trNode.parentNode;
				var isOK = confirm("您确认要删除此列吗？");
				if(isOK){
					$.ajax({
						type:"post",
						url:"doProjectImage.do?method=hideRecord",
						data:"id="+objId,
						success:function(msg){
							if(msg=="success"){
					    		tbodyNode.removeChild(trNode);
					    	}else if(msg=="fail"){
					    		alert("删除失败");
					    	}					    		
						}
						
					});
				}
			}
			
			function find(){
				frmGo.action ="doProjectImage.do?method=queryProjectImage";
				frmGo.submit();
			}
			
			function c(){
				document.getElementById("projectName").options[0].selected=true;
				document.getElementById("downloadstatus").options[0].selected=true;
			}
		</script>	
  </head>
  
  <body> 
  	<span class="title_1"></span>
		<form name="frmGo" method="post" action="doProjectImage.do?method=queryProjectImage">
			<table width="100%" class="table" border="1" cellpadding="0" cellspacing="1">
               <tr>
               	   <th colspan="12" nowrap="nowrap" align="left">
                                                                  教程动态
			   	      &nbsp;&nbsp;
			   	      <input type="button" onclick="initUpLoadImage()" value="上传图片" class="but_1"/>                                   
                   </th>
               </tr>
               <tr class="title_3">
        	       <td colspan="8">
        		    	  项目名称
        		    	  <%String projectName=(String)request.getAttribute("projectName");
        		    	  	if(projectName==null || "".equals(projectName)){
        		    	  		projectName="全部";
        		    	  	}
        		    	  	
        		    	  %>
        		    	  <select id="projectName" name="projectName">
        		    	  	<option value="">全部</option>
        		    	  	<logic:iterate id="element" name="projectInfos">  		
        		    	  		<option value="<bean:write name='element' property='project_name'/>" <logic:equal name="element" property="project_name" value="<%=projectName %>">selected</logic:equal>>     		    	  			
        		    	  			<bean:write name="element" property="project_name"/>
        		    	  		</option>   	  		
        		    	  	</logic:iterate>
        		    	  </select>
        		    	  &nbsp;
        		    	 状态<%String downloadstatus =(String)request.getAttribute("downloadstatus");%>
        		    	 <select id="downloadstatus" name="downloadstatus">
        		    	 	<option value="">全部</option>
        		    	 	<option value="1" <%if("1".equals(downloadstatus)){out.print("selected");}%>>启用</option>
        		    	 	<option value="0" <%if("0".equals(downloadstatus)){out.print("selected");} %>>禁用</option>
        		    	 </select>
        		    	 &nbsp;
        		    	 <input name="find1" type="button" class="but_1" value="搜索"
        		    	 		accesskey="f" tabindex="f" onclick="find()"/>
        		    	 <input name="clear" type="button"  class="but_1" value="清除搜索" 
        		    	 		accesskey="f" tabindex="f" onclick="c()"/>
        		   </td>
        		</tr>
        		
               	<tr class="title_2">                 	
                  	<td width="10%">项目名称</td>
                  	<td width="14%">图片一</td>
                  	<td width="14%">图片二</td>                  	                                   	                 	
                  	<td width="14%">图片三</td>
                  	<td width="14%">图片四</td>
                  	<td widht="14%">图片五</td>                  	
                  	<td width="10%">下载状态</td> 
                  	<td width="10%">操作</td>                    						 
			   	</tr>
				<logic:iterate id="element" name="pageList">						
   				<tr class="tr_5">					
   					<td >				
   						<bean:write name="element" property="project_name"/>			
   					</td>				
   							
   					<logic:notEmpty name="element" property="downloadpath1">			   	
   						<td style="background:url(<bean:write name="element" property="downloadpath1"/>)">
   							<logic:equal name="element" property="downloadstatus" value="1">			
   								<a href="#" onclick="downLoadImage('<bean:write name="element" property="downloadpath1"/>')" class="tbl_A">下载</a>			
   								<a href="#" onclick="deleteImage(<bean:write name='element' property='id'/>,1,'<bean:write name="element" property="downloadpath1"/>')" class="tbl_A">删除</a>
   							</logic:equal>	
   						</td>			
   					</logic:notEmpty>				
   					<logic:empty name="element" property="downloadpath1">				
   						<td>无</td>			
   					</logic:empty>				
   							
   					<logic:notEmpty name="element" property="downloadpath2">				
   						<td style="background:url(<bean:write name="element" property="downloadpath2"/>)">	
   							<logic:equal name="element" property="downloadstatus" value="1">		
   								<a href="#" onclick="downLoadImage('<bean:write name="element" property="downloadpath2"/>')" class="tbl_A">下载</a>		
   								<a href="#" onclick="deleteImage(<bean:write name='element' property='id'/>,2,'<bean:write name="element" property="downloadpath2"/>')" class="tbl_A">删除</a>
   							</logic:equal>			
   						</td>			
   					</logic:notEmpty>				
   					<logic:empty name="element" property="downloadpath2">				
   						<td>无</td>			
   					</logic:empty>				
   							
   					<logic:notEmpty name="element" property="downloadpath3">				
   						<td style="background:url(<bean:write name="element" property="downloadpath3"/>)">
   							<logic:equal name="element" property="downloadstatus" value="1">			
   								<a href="#" onclick="downLoadImage('<bean:write name="element" property="downloadpath3"/>')" class="tbl_A">下载</a>		
   								<a href="#" onclick="deleteImage(<bean:write name='element' property='id'/>,3,'<bean:write name="element" property="downloadpath3"/>')" class="tbl_A">删除</a>
   							</logic:equal>			
   						</td>			
   					</logic:notEmpty>				
   					<logic:empty name="element" property="downloadpath3">				
   						<td>无</td>			
   					</logic:empty> 				
   							
   					<logic:notEmpty name="element" property="downloadpath4">				
   						<td style="background:url(<bean:write name="element" property="downloadpath4"/>)">	
   							<logic:equal name="element" property="downloadstatus" value="1">
   								<a href="#" onclick="downLoadImage('<bean:write name="element" property="downloadpath4"/>')" class="tbl_A">下载</a>		
   								<a href="#" onclick="deleteImage(<bean:write name='element' property='id'/>,4,'<bean:write name="element" property="downloadpath4"/>')" class="tbl_A">删除</a>
   							</logic:equal>			
   						</td>			
   					</logic:notEmpty>				
   					<logic:empty name="element" property="downloadpath4">				
   						<td>无</td>			
   					</logic:empty> 				
   							
   					<logic:notEmpty name="element" property="downloadpath5">				
   						<td style="background:url(<bean:write name="element" property="downloadpath5"/>)">
   							<logic:equal name="element" property="downloadstatus" value="1">			
   								<a href="#" onclick="downLoadImage('<bean:write name="element" property="downloadpath5"/>')" class="tbl_A">下载</a>		
   								<a href="#" onclick="deleteImage(<bean:write name='element' property='id'/>,5,'<bean:write name="element" property="downloadpath5"/>')" class="tbl_A">删除</a>
   							</logic:equal>			
   						</td>			
   					</logic:notEmpty>				
   					<logic:empty name="element" property="downloadpath5">				
   						<td>无</td>			
   					</logic:empty>	
   					<td>
   						<logic:equal name="element" property="downloadstatus" value="0"><font color="red">禁用</font></logic:equal>	
   						<logic:equal name="element" property="downloadstatus" value="1">已启用</logic:equal>
   					</td>					
   					<td>				
   						<logic:equal name="element" property="downloadstatus" value="0">			
   							<a href="#" onclick="updateProjectImage(<bean:write name='element' property='id'/>,1)" class="tbl_B">启用</a>		
   						</logic:equal>			
   							
   						<logic:equal name="element" property="downloadstatus" value="1">			
   							<a href="#" onclick="updateProjectImage(<bean:write name='element' property='id'/>,0)" class="tbl_B">禁用</a>		
   						</logic:equal>	
   						
   						<a href="#" onclick="hideline(<bean:write name='element' property='id'/>)" class="tbl_B">删除</a>		   
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
