<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<title>PMS :: SessionTimeout Page</title>
<head>
<title>PMS :: A Licensed Product</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
</head>
<body>
    <h2>Your session got timed out. Please login again.</h2>
    <a href="${pageContext.request.contextPath}/emp/login">Login to My Secured Page</a>
</body>
</html>