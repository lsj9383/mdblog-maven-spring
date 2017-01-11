<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ page import="java.io.*" %>
<%@ page import="com.lsj.util.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<%
	response.setCharacterEncoding("utf-8");
	request.setCharacterEncoding("utf-8");
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	Configuration blogConfig = (Configuration)wac.getBean("blogConfiguration");
	String viewName = "目录";
 %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/bootstrap.min.css">
		<link href="${pageContext.request.contextPath}/resource/home.css" rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath}/resource/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/resource/bootstrap.min.js"></script>
		<title>Shakeel</title>
	</head>
	
	<body>　
		<nav class="navbar navbar-default navbar-fixed-top" style="opacity: .9" role="navigation">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">目录</a>
				</div>
				
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						<%
							if(blogConfig != null){
								for(UrlButton btn : blogConfig.getUrlButtons()){
									String name = btn.getName();
									String url = btn.getUrl();
									String classType = viewName.equals(name) ? "class=\"active\"" : "";
						%>
									<li <%= classType %>><a href="<%= url %>"><%= name %></a></li>		
						<%
								}
							}
						 %>
					</ul>
				</div>
			</div>
		</nav>
		
		<%	
			String appRoot = request.getServletContext().getRealPath("/");
			String blogRoot = appRoot+"blogRoot/";
			String dirName = request.getParameter("dir");
			File[] dirList = (File[])  request.getAttribute("dirList");
			File[] fileList = (File[]) request.getAttribute("fileList");
		%>
		<div style="margin: 5% 10% 2% 10%">
			<table style="width: 100%">
				<tbody><tr>
					<td width="70%"><ul class="list-group">
						
						<% 
							String parentUrl = String.format("/springmdblog/dir.do?dir=%s", FileUtil.ParentName(dirName)); 
						%>
						<li class="list-group-item title">
							<a href="<%= parentUrl %>">..</a>
						</li>
			          	 
			          	 
						<%
			          		if(dirList != null){
			            		for(File subDir : dirList){
									String subDirName = subDir.getName()+"/";
									String dirUrl = String.format("/springmdblog/dir.do?dir=%s", dirName+subDirName);
									String deleteUrl = String.format("/springmdblog/delete.do?file=%s", dirName+subDirName);
			          	 %>
			          	 <li class="list-group-item title">
			          	 	<a href="<%= dirUrl %>">[DIR] <%= subDirName %>  </a> <a href="javascript:alert('<%= deleteUrl %>')">删除</a>
			          	 </li>
			          	 <%
			          	 		}
			          	 	}
			          	  %>
			          	  
			          	  
			          	  <%
			          		if(fileList != null){
			            		for(File file : fileList){
									String fileName = file.getName();
									String fileUrl = String.format("/springmdblog/download.do?file=%s", dirName+fileName);
									String deleteUrl = String.format("/springmdblog/delete.do?file=%s", dirName+fileName);
			          	 %>
			          	 <li class="list-group-item title">
			          	 	<a href="<%= fileUrl %>">[FIL] <%= fileName %></a> <a href="<%= deleteUrl %>">删除</a>
			          	 </li>
			          	 <%
			          	 		}
			          	 	}
			          	  %>
					</ul></td>
				</tr></tbody>
			</table>
		</div>
	</body>
</html>