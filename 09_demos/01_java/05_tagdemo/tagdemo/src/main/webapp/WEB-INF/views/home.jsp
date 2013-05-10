<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/mytaglibs.tld" prefix="out"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>
	<P>The time on the server is ${serverTime}.</P>
	<out:sd/>
	<table>
	<out:reporter var="item" items="${users }">
		<tr>
			<td>${item.userName }</td>
			<td>${item.age }</td>
			<td>${item.email }</td>
		</tr>
	</out:reporter>
	</table>
	
</body>
</html>
