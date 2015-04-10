<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: A Licensed Product</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/script.js" />"></script>

</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<article id="one">
				<section class="one">
					<p>
						<img src="<c:url value="/resources/images/PSK.jpg" />"
							alt="PSK Logo">
					</p>
				</section>

			</article>
			<aside>

				<c:if test="${'fail' eq param.auth}">
					<div style="color: #E08B14">
						Login Failed!<br /> Reason :
						${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
					</div>
				</c:if>
				<br>
				<h2
					style="text-align: left; font-family: arial; color: #E08B14; font-size: 14px;">${loginMessage}</h2>
				<!-- <h2>Employee Login Here </h2> -->
				<br>
				<form:form method="POST" commandName="employeeForm"
					action="${pageContext.request.contextPath}/login.do">

					<form:errors path="*" cssClass="errorblock" element="div" />
					<center>
						<table>
							<tr>
								<td>UserName :</td>
								<td><form:input path="empId"
										placeholder="Enter Employee ID" cssClass="inputText" /></td>
								<td><form:errors path="empId" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Password :</td>
								<td><form:password path="empPassword"
										placeholder="Enter Password" cssClass="inputText" /></td>
								<td><form:errors path="empPassword" cssClass="error" /></td>
							</tr>
							
							<tr>
								<td>Team :</td>
								<td><form:select path="empRole" cssClass="inputText" items="${teamList}"/></td>
								<td><form:errors path="empRole" cssClass="error" /></td>
							</tr>
							
							<tr></tr>
							<tr>
								<td></td>
								<td><input type="submit" /></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="3" style="text-align: center">Forgot Password?
									<a href="/pms/emp/forgotpassword">Reset Here</a>
								</td>
							</tr>
							<tr>
								<td colspan="3" style="text-align: center">Create an
									Account! <a href="/pms/emp/signup">SignUp Here</a>
								</td>
							</tr>
						</table>
						<br>

					</center>
				</form:form>

				<br> <br>
			</aside>

		</div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>