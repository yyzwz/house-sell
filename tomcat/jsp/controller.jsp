<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	response.setHeader("Access-Control-Allow-Origin", "*");  
	response.setHeader("Access-Control-Allow-Methods", "*");  
	response.setHeader("Access-Control-Max-Age", "3600");  
	response.setHeader("Access-Control-Allow-Headers", "*");
	response.setHeader("Access-Control-Allow-Credentials", "true");
	
	String rootPath = application.getRealPath( "/" );
	System.err.println(rootPath);
	out.write( new ActionEnter( request, rootPath ).exec() );
	
%>