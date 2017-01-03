<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.lsj.util.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<%
	response.setCharacterEncoding("utf-8");
	request.setCharacterEncoding("utf-8");
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	Configuration blogConfig = (Configuration)wac.getBean("blogConfiguration");
	
	String  inform	= (String)  request.getSession(true).getAttribute("inform");
	String  to		= (String)  request.getSession(true).getAttribute("to");
	Integer seconds	= (Integer) request.getSession(true).getAttribute("seconds");
	request.getSession(true).setAttribute("inform", null);
	request.getSession(true).setAttribute("to",	 null);
	request.getSession(true).setAttribute("seconds", null);	
	if(inform == null){
		inform = "无信息";
	}
	if(to == null){
		to = "/springmdblog/pad.do";
	}
	if(seconds == null){
		seconds = 3;
	}
	
	String viewName = "等待";
 %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/mdblog/resource/bootstrap.min.css">
<link href="/mdblog/resource/home.css" rel="stylesheet" type="text/css">
<script src="/mdblog/resource/jquery.min.js"></script>
<script src="/mdblog/resource/bootstrap.min.js"></script>
<title>Shakeel</title>
	<script type="text/javascript">
		var t = setTimeout("timeout()", 1000);
		
		function timeout(){
	        var time=parseInt(document.getElementById("sec").innerHTML);
	        time = time - 1;
	        if(time < 0){
	        	window.location.href="<%= to %>";
	        }else{
	        	document.getElementById("sec").innerHTML = time;
	        	t = setTimeout("timeout()", 1000);
	        }
		}
	</script>
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
					<a class="navbar-brand" href="#"><%= viewName %></a>
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
		if(inform != null || to != null || seconds != null){ 
	%>
		<div style="margin: 5% 10% 2% 10%">
			<table style="width: 100%">
				<tbody><tr>
					<td width="70%"><ul class="list-group">
			          	 <li class="list-group-item title">
			          	 	<p><%= inform %>, <span id="sec"><%= seconds %></span>后跳转</p>
							<a href="<%= to %>">立即跳转</a>
			          	 </li>
					</ul></td>
				</tr></tbody>
			</table>
		</div>
		
		
	<%
		}
	%>
</body>
</html>