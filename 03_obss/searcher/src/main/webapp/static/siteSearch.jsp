<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
	<title>详细内容查询</title>
</head>
<body>
<h1>
	站点统计查询 
</h1>

<form action="../search/siteSearch" method="get">
	<input name="query"/>
	<input type="submit"/> 
</form>
</body>
</html>
