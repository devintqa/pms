<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PMS</title>

<script type="text/javascript">
	$(document).ready(
	function() {
		$('.nav li').hover(function() { 
			$('ul', this).fadeIn();
		}, function() { 
			$('ul', this).fadeOut();
		});
	});
</script>

</head>
	<div class="navigation">
		<ul class="nav">
			<c:choose>
				<c:when test="${employeeObj.employeeTeam eq 'Technical'}">

					<li><a id="emphome"
						href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
					</li>
					<li><a href="#">Project</a>
						<ul>
							<li><a id="createProject"
								href="/pms/emp/myview/buildProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Create</a></li>
							<li><a id="updateProject"
								href="/pms/emp/myview/${employeeObj.employeeId}?action=editProject">Update</a></li>
						</ul></li>
					<li><a href="#">Sub-Project</a>
						<ul>
							<li><a id="createSubProject"
								href="/pms/emp/myview/buildSubProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Create</a></li>
							<li><a id="updateSubProject"
								href="/pms/emp/myview/${employeeObj.employeeId}?action=editSubProject">Update</a></li>
						</ul></li>
					<li><a href="#">Project Description</a>
						<ul>
							<li><a id="createDescription"
								href="/pms/emp/myview/buildProjectDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Create</a></li>
							<li><a id="updateDescription"
								href="/pms/emp/myview/${employeeObj.employeeId}?action=editProjectDesc">Update</a></li>
						</ul>
					<li><a href="#">User</a>
						<ul>
							<li><a id="profile"
								href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update
									Profile</a></li>
							<li><a id="logout"
								href="${pageContext.request.contextPath}/logout">Logout</a></li>
						</ul>
						
				</c:when>

				<c:when test="${employeeObj.employeeTeam eq 'Account'}">

					<li><a id="emphome"
						href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
					</li>
					<li><a id="profile"
						href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update
							Profile</a></li>
					<li><a id="logout"
						href="${pageContext.request.contextPath}/logout">Logout</a></li>

				</c:when>

				<c:when test="${employeeObj.employeeTeam eq 'Admin'}">

					<li><a id="emphome"
						href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
					</li>
					<li><a id="profile"
						href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update
							Profile</a></li>
					<li><a id="logout"
						href="${pageContext.request.contextPath}/logout">Logout</a></li>

				</c:when>

				<c:when test="${employeeObj.employeeTeam eq 'Management'}">

					<li><a id="emphome"
						href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
					</li>
					<li><a id="profile"
						href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update
							Profile</a></li>
					<li><a id="logout"
						href="${pageContext.request.contextPath}/logout">Logout</a></li>

				</c:when>

				<c:when test="${employeeObj.employeeTeam eq 'Purchase'}">

					<li><a id="emphome"
						href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
					</li>
					<li><a id="profile"
						href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update
							Profile</a></li>
					<li><a id="logout"
						href="${pageContext.request.contextPath}/logout">Logout</a></li>

				</c:when>

				<c:otherwise>


				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</html>