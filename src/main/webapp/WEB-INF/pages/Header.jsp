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
	$(document).ready(function() {
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
					href="/pms/emp/myview/${employeeObj.employeeId}?action=editProjectDesc"
					class="active">Home</a></li>
				<li><a href="#">Create</a>
					<ul>
						<li><a id="createProject"
							href="/pms/emp/myview/buildProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Project</a>
						</li>
						<li><a id="BaseProjectDescription"
							href="/pms/emp/myview/buildBaseDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Base
								Description</a></li>
						<li><a id="createDepositDetail"
							href="/pms/emp/myview/buildDepositDetail/${employeeObj.employeeId}">Deposit
								Details</a></li>
						<li><a id="createItem"
							href="/pms/emp/myview/buildItem/${employeeObj.employeeId}">Item</a></li>
						<li><a id="uploadExcel"
							href="/pms/emp/myview/uploadExcel/${employeeObj.employeeId}">Bulk
								Description</a></li>

					</ul></li>
				<li><a href="#">Search</a>
					<ul>
						<li><a id="searchProject"
							href="/pms/emp/myview/searchProject/${employeeObj.employeeId}">
								Project</a></li>
						<li><a id="searchProjectDesc"
							href="/pms/emp/myview/searchProjectDescription/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Project Description </a></li>
						<li><a id="searchDepositDetail"
							href="/pms/emp/myview/searchDepositDetail/${employeeObj.employeeId}">
								Deposit Details </a></li>
						<li><a id="searchPwdProjectDescription"
							href="/pms/emp/myview/searchBaseDescription/${employeeObj.employeeId}">
								Base Description </a></li>
					</ul></li>
				<li><a href="#">View</a>
					<ul>
						<li><a id="viewDetails"
							href="/pms/emp/myview/viewDetails/${employeeObj.employeeId}">Project
								Details</a></li>
					</ul></li>
				<li><a href="#">File</a>
					<ul>
						<li><a id="uploadFile"
							href="/pms/emp/myview/uploadFile/${employeeObj.employeeId}">Upload</a></li>
						<li><a id="downloadFile"
							href="/pms/emp/myview/downloadFile/${employeeObj.employeeId}">Download</a></li>
					</ul></li>
				<li><a href="#">User</a>
					<ul>
						<li><a id="profile"
							href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update
								Profile</a></li>
						<li><a id="logout"
							href="${pageContext.request.contextPath}/logout">Logout</a></li>
					</ul></li>
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
				<li><a href="#">Create</a>
					<ul>
						<li><a id="createProject"
							href="/pms/emp/myview/buildProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Project</a></li>
						<li><a id="BaseProjectDescription"
							href="/pms/emp/myview/buildBaseDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Base Description</a></li>
						<li><a id="createDepositDetail"
							href="/pms/emp/myview/buildDepositDetail/${employeeObj.employeeId}">
								Deposit Details</a></li>
						<li><a id="createItem"
							href="/pms/emp/myview/buildItem/${employeeObj.employeeId}">
								Item</a></li>
						<li><a id="uploadExcel"
							href="/pms/emp/myview/uploadExcel/${employeeObj.employeeId}">
								Bulk Description</a></li>
						<li><a id="createField"
							href="/pms/emp/myview/buildFieldDescription/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Field Description</a></li>
						<li><a id="createRole"
							href="/pms/emp/myview/createRole/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Role</a></li>
						<li><a id="createStore"
							href="/pms/emp/myview/buildStoreDetail/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Store
								Detail</a></li>
					</ul></li>
				<li><a href="#">Search</a>
					<ul>
						<li><a id="searchProject"
							href="/pms/emp/myview/searchProject/${employeeObj.employeeId}">
								Project</a></li>
						<li><a id="searchProjectDesc"
							href="/pms/emp/myview/searchProjectDescription/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Project Description </a></li>
						<li><a id="searchDepositDetail"
							href="/pms/emp/myview/searchDepositDetail/${employeeObj.employeeId}">
								Deposit Details </a></li>
						<li><a id="searchPwdProjectDescription"
							href="/pms/emp/myview/searchBaseDescription/${employeeObj.employeeId}">
								Base Description </a></li>
						<li><a id="searchEmployee"
							href="/pms/emp/myview/searchEmployee/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Employee </a></li>

					</ul></li>
				<li><a href="#">View</a>
					<ul>
						<li><a id="viewDetails"
							href="/pms/emp/myview/viewDetails/${employeeObj.employeeId}">
								Project Details</a></li>
						<li><a id="viewStoreDetails"
							href="/pms/emp/myview/viewStoreDetails/${employeeObj.employeeId}">
								Store Details</a></li>
					</ul></li>
				<li><a href="#">File</a>
					<ul>
						<li><a id="uploadFile"
							href="/pms/emp/myview/uploadFile/${employeeObj.employeeId}">
								Upload</a></li>
						<li><a id="downloadFile"
							href="/pms/emp/myview/downloadFile/${employeeObj.employeeId}">
								Download</a></li>
					</ul></li>
				<li><a href="#">User</a>
					<ul>
						<li><a id="grant"
							href="/pms/emp/myview/manageAccess/${employeeObj.employeeId}">Authorize</a></li>
						<li><a id="profile"
							href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">
								Update Profile</a></li>
						<li><a id="logout"
							href="${pageContext.request.contextPath}/logout"> Logout</a></li>
					</ul></li>
			</c:when>

			<c:when test="${employeeObj.employeeTeam eq 'Management'}">

				<li><a id="emphome"
					href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
				</li>

				<li><a href="#">Create</a>
					<ul>
						<li><a id="searchDescriptionForIndent"
							href="/pms/emp/myview/indent/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Indent </a></li>
					</ul></li>
				<li><a href="#">Search</a>
					<ul>
						<li><a id="searchIndent"
							href="/pms/emp/myview/indent/search_${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">
								Indent </a></li>
					</ul></li>
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

			<c:when test="${employeeObj.employeeTeam eq 'Store'}">

				<li><a id="emphome"
					href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
				</li>
				<li><a href="#">Create</a>
					<ul>
						<li><a id="createStore"
							href="/pms/emp/myview/buildStoreDetail/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Store
								Detail</a></li>
					</ul></li>
				<li><a href="#">Transact</a>
					<ul>
						<li><a id="dispatchTransaction"
							href="/pms/emp/myview/dispatchTransaction/${employeeObj.employeeId}">Dispatch
						</a></li>
						<li><a id="returnTransaction"
							href="/pms/emp/myview/returnTransaction/${employeeObj.employeeId}">Return
						</a></li>
					</ul></li>

				<li><a href="#">View</a>
					<ul>
						<li><a id="viewStoreDetails"
							href="/pms/emp/myview/viewStoreDetails/${employeeObj.employeeId}">
								Store Details</a></li>
                        <li><a id="viewDispatchDetails"
							href="/pms/emp/myview/viewDispatchDetails/${employeeObj.employeeId}">
								Dispatched Details</a></li>
					</ul></li>

				<li><a id="profile"
					href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update
						Profile</a></li>
				<li><a id="logout"
					href="${pageContext.request.contextPath}/logout">Logout</a></li>

			</c:when>

			<c:when test="${employeeObj.employeeTeam eq 'Field'}">
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