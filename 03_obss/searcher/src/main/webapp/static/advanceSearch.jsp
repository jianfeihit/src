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
	高级查询
</h1>

<form action="../search/advanceSearch" method="get">
	基本查询词：<input name="basequery"/><br/>
	扩展查询词：<input name="extentquery"/><br/>
	类型：<input name="type"/><br/>
	站点：<input name="site"/><br/>
	<input type="submit"/> 
</form>
</body>
</html>
