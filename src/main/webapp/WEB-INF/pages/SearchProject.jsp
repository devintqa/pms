<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery UI Autocomplete - Default functionality</title>
<%@include file="Script.jsp" %>
  <script>
  
  $(document).ready(function() {
	  $("#aliasProjectName").autocomplete({
	  source: function(request, response) {
	  $.ajax({
	  url: "/searchProject/searchProject.do",
	  data : {
			input : request.term
		},  
	  success: function(data) {
		  response(data);
	  }
	  });
	  }
	  });
	  });
  </script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
 <div class="ui-widget">
			<form:form id="searchForm" method="POST" commandName="searchForm"
				action="searchDetails.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Search Details</legend>
						<table>
							<tr>
								<td>Alias Project Name<span id="colon">:</span>
								</td>
								<td><form:input path="aliasProjectName" id="aliasProjectName"
										placeholder="Enter Alias Project Name" cssClass="inputText" /></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							
							<tr></tr>
						</table>
					</fieldset>
					<table>
						<tr>
							<td></td>
							<td><input type="submit"/></td>
							<td></td>
						</tr>
					</table>
				</center>
				<br>
				<br>

			</form:form>
		</div>
 <footer>
		<jsp:include page="Footer.jsp" />
	</footer>
 
</body>
</html>