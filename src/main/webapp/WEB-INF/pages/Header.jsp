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
						href="/pms/emp/myview/${employeeObj.employeeId}?action=editProjectDesc" class="active">Home</a>
					</li>
					<li><a href="#">Create</a>
						<ul>
							<li><a id="createProject"
								href="/pms/emp/myview/buildProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Project</a></li>
							<li><a id="createSubProject"
								href="/pms/emp/myview/buildSubProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Sub-Project</a></li>
							<li><a id="createDescription"
								href="/pms/emp/myview/buildProjectDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Project Description</a></li>
						</ul></li>					
					<li><a href="#">EMD</a>
						<ul>
							<li><a id="createEmd" href="/pms/emp/myview/buildEmd/${employeeObj.employeeId}">Create</a></li>
							<li><a id="updateEmd" href="/pms/emp/myview/updateEmd/${employeeObj.employeeId}">Update</a></li>
						</ul></li>
					<li><a href="#">File</a>
						<ul>
							<li><a id="uploadFile" href="/pms/emp/myview/uploadFile/${employeeObj.employeeId}">Upload</a></li>
							<li><a id="downloadFile" href="/pms/emp/myview/downloadFile/${employeeObj.employeeId}">Download</a></li>
						</ul></li>
					<li><a href="#">Search </a>
						<ul>
							<li><a id="searchProject"
									href="/pms/emp/myview/searchProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&search=project"> Project</a> </li>
							<li><a id="searchSubProject"
									href="/pms/emp/myview/searchProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&search=subProject"> Sub Project</a> </li>
							<li><a id="searchProjectDesc"
									href="/pms/emp/myview/searchProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&search=projectDesc"> Project Description </a></li>
						</ul>
					</li>
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