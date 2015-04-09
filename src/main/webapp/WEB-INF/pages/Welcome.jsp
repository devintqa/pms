<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Project Home</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/script.js" />"></script>
</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<h3 id="welcomeMessage">Welcome, ${employee.empId}&nbsp!</h3>
		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>



</body>
</html>

