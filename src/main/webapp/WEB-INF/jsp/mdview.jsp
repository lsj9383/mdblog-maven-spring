<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ page import="java.io.*" %>
<%@ page import="com.lsj.util.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<%
	response.setCharacterEncoding("utf-8");
	request.setCharacterEncoding("utf-8");
	String blogName = request.getParameter("md");
	String path = String.format("blogRoot/%s.md/%s.html", blogName, blogName);
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	Configuration blogConfig = (Configuration)wac.getBean("blogConfiguration");
	String viewName = blogName;
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
	
	
	<script type="text/javascript">
		$(document).ready(function(){	
			$("#mdframe").load(function(){
				var mainheight = $(this).contents().find("body").height()+100;
				$(this).height(mainheight);
				$(this).css("visibility", "none");
				$(this).css('margin-top',$("nav").height());
			});
		});
		
	</script>
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
					<a class="navbar-brand" href="http://www.yinwang.org/#"><%=blogName%></a>
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
		
    	<iframe id="mdframe" style="width:100%;visibility:hidden" src=<%= path %> scrolling="no" frameborder="0"></iframe>
    
    </body>
</html>