<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: A Licensed Product</title>
	<%@include file="Script.jsp" %>
</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2 style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${sessionTimeOutMessage}</h2>
		</div>
		<div>
			<article id="one">
				<section class="one">
				   <div style="text-align: center;">
					<br><br>
					<br><br>		
<%-- 						<img src="<c:url value="/resources/images/PSK.jpg" />" --%>
<!-- 							alt="PSK Logo">		 -->
							<br><br>
							<br><br>
							<br><br>
							<br><br>
							<br><br>
					</div>
				</section>

			</article>
			<aside>
				<br> <br>
				<c:if test="${'fail' eq param.auth}">
					<div style="color: #007399">
						Login Failed!<br /> Reason :
						${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
					</div>
				</c:if>
				<br>
				<h2
					style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${loginMessage}</h2>
				<!-- <h2>Employee Login Here </h2> -->
				<br>
				<form:form method="POST" commandName="employeeForm"
					action="${pageContext.request.contextPath}/login.do">

					<form:errors path="*" cssClass="errorblock" element="div" />
					<center>
						<table>
							<tr>
								<td>UserName :</td>
								<td><form:input path="employeeId"
										placeholder="Enter User Name" cssClass="inputText" /></td>
								<td><form:errors path="employeeId" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Password :</td>
								<td><form:password path="employeePwd"
										placeholder="Enter Password" cssClass="inputText" /></td>
								<td><form:errors path="employeePwd" cssClass="error" /></td>
							</tr>

							<tr></tr>
							<tr>
								<td></td>
								<td><input class="button" type="submit" /></td>
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