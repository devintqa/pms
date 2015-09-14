<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: My Profile</title>

<%@include file="Script.jsp"%>
<script src="<c:url value="/resources/js/jquery-picklist.js" />"></script>
<script>
	$(function() {
		$("#user").pickList();
		$('#project').change(function() {

			$('.pickList_sourceList li').remove();
			$('.pickList_targetList li').remove();
			var data = document.getElementById("privilegeDetails").value;
			var project  = $('#project').val();
			var employee  = $('#employee').val();
			
			$.ajax({
				type : "GET",
				url : "getProjectUserPrivilege.do",
				cache : false,
				data: "employeeId="+employee+"&projectId="+project,
				success : function(response) {
					var data = $.parseJSON(response);
					$("#user").pickList();
					$.each(data, function(i, item) {
						$("#user").pickList("insert", {
							value : item.value,
							label : item.label,
							selected : item.selected
						});
					});
				}
			});
			
			

		});

	});

	function updateConsole() {
		$("#console").text($("#user").serialize());
		$("#user").serialize();
		$.ajax({
				type : "POST",
				url : "saveProjectUserPrivilege.do",
				cache : false,
				data: "employeeId="+employee,
				success : function(response) {
					
				}
			});
		}
</script>
<style>
.pickList_sourceListContainer, .pickList_controlsContainer, .pickList_targetListContainer { float: left; margin: 0.25em; }
.pickList_controlsContainer { text-align: center; }
.pickList_controlsContainer button { display: block; width: 100%; text-align: center; }
.pickList_list { list-style-type: none; margin: 0; padding: 0; float: left; width: 150px; height: 200px; border: 1px inset #eee; overflow-y: auto; cursor: default; }
.pickList_selectedListItem { background-color: #a3c8f5; }
.pickList_listLabel { font-size: 0.9em; font-weight: bold; text-align: center; }
.pickList_clear { clear: both; }


</style>
</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<br>
			<h1 style="text-align: center; color: #007399; font-size: 24px;">Control Authorization</h1>
			<br>
			<form:form id="authorize" method="POST" commandName="authorize">
				<table align="center">
					<tr>
						<td>Project<span id="colon">:</span></td>
						<td><select id="project" name="project">
								<option value="1">MMC</option>
								<option value="2">BHG</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2"><br></td>
					</tr>
					<tr>
						<td colspan="2">
							<div>
								<select id="user" name="user" multiple="multiple">
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><br></td>
					</tr>
					<tr>
						<td></td>
						<td><input class="button" value="Grant Access" onclick="updateConsole()" /></td>
					</tr>
				</table>
				 <label for="console">Form content:</label>
   						<div><textarea id="console"></textarea></div>
   							<form:hidden path="privilegeDetails" id="privilegeDetails" />
   							<form:hidden path="employeeId" id="employee" />
   							
			</form:form>
		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>



</body>
</html>


