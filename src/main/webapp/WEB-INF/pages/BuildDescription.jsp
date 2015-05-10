<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Create Detailed Description</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-ui-1.10.3.css" />">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui-1.10.3.js" />"></script>
<script src="<c:url value="/resources/js/script.js" />"></script>
<script
	src="<c:url value="/resources/js/jquery.dataTables.1.10.6.min.js" />"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery.dataTables.1.10.6.css" />">
<script type="text/javascript">
	function getAliasSubProjects() {
		$.ajax({
			type : "GET",
			url : "getSubAliasProject.do",
			cache : false,
			data : 'aliasProjectName=' + $('#projId').val(),
			success : function(response) {
				var options = '';
				if (response != null) {
					var obj = jQuery.parseJSON(response);
					var options = '';
					for ( var key in obj) {
						var attrName = key;
						var attrValue = obj[key];
						options = options + '<option value='+attrName+'>'
								+ attrValue + '</option>';
					}
					$('#aliasSubProjectName').html(options);
				}
			}
		});
	}
</script>
</head>


<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
	<div>
		<h2 style="text-align: left; font-family: arial; color: #C6311D; font-size: 14px;">${projDescCreationMessage}</h2>
	</div>
		<div>
			<form:form method="POST" commandName="projDescForm"
				action="createProjDesc.do">
				<!--<form:errors path="*" cssClass="errorblock" element="div" />
				-->
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Project Description</legend>
						<table>
							<tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="aliasProjectName" cssClass="inputText"
										id="projId" items="${aliasProjectList}"
										onchange="getAliasSubProjects()" >
										<c:if test="${projDescForm.projId gt 0}">
										<option value="${projDescForm.projId}">${projDescForm.aliasProjectName}</option>
										</c:if>
										</form:select>
										</td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Sub Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="aliasSubProjectName"
										id="aliasSubProjectName" cssClass="inputText" >
										<c:if test="${projDescForm.subProjId gt 0}">
										<option value="${projDescForm.subProjId}">${projDescForm.aliasSubProjectName}</option>
										</c:if>
										</form:select>
										</td>
								<td><form:errors path="aliasSubProjectName"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Work Type <span id="colon">:</span>
								</td>
								<td><form:select path="workType" cssClass="inputText"
										id="workType" items="${workTypeList}" /></td>
								<td><form:errors path="workType" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Quantity in Figures<span id="colon">:</span>
								</td>
								<td><form:input path="quantityInFig"
										placeholder="Enter Quantity in Figures" cssClass="inputText" /></td>
								<td><form:errors path="quantityInFig" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Quantity in Words<span id="colon">:</span>
								</td>
								<td><form:input path="quantityInWords"
										placeholder="Enter Quantity in Words" cssClass="inputText" /></td>
								<td><form:errors path="quantityInWords" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Description<span id="colon">:</span>
								</td>
								<td><form:input path="description"
										placeholder="Enter Description" cssClass="inputText" /></td>
								<td><form:errors path="description" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Alias Description<span id="colon">:</span>
								</td>
								<td><form:input path="aliasDescription"
										placeholder="Enter Alias Description" cssClass="inputText" /></td>
								<td><form:errors path="aliasDescription" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Rate in Figures<span id="colon">:</span>
								</td>
								<td><form:input path="rateInFig"
										placeholder="Enter Rate in Figures" cssClass="inputText" /></td>
								<td><form:errors path="rateInFig" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Rate in Words<span id="colon">:</span>
								</td>
								<td><form:input path="rateInWords"
										placeholder="Enter Rate in Words" cssClass="inputText" /></td>
								<td><form:errors path="rateInWords" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Amount in Rupees<span id="colon">:</span>
								</td>
								<td><form:input path="projDescAmount"
										placeholder="Enter Amount" cssClass="inputText" /></td>
								<td><form:errors path="projDescAmount" cssClass="error" /></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>

					<form:hidden path="employeeId" />
					<form:hidden path="projDescId" />
					<table>
						<tr>
							<td></td>
							<td><input type="submit" /></td>
							<td></td>
						</tr>
					</table>
				</center>
				<br>
				<br>

			</form:form>
		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


