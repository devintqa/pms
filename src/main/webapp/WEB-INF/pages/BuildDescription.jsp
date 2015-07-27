<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Create Detailed Description</title>

<%@include file="Script.jsp" %>

<script type="text/javascript">
$(document).ready(function () {
	  $("#showSubProject").hide();

	  $('#subProjectDesc').change(function() {
	        if($(this).is(":checked")) {
			var aliasProjectName  = $('#projId').val();
			$.ajax({
				type : "GET",
				url : "getSubAliasProject.do",
				cache : false,
				data: "aliasProjectName="+aliasProjectName,
				success : function(response) {
					var options = '';
					if (response != null) {
						var obj = jQuery.parseJSON(response);
						var options = '';
						//options = '<option value=0>--Please Select--</option>';
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
				$("#showSubProject").show();
	        }else {
	        	$("#aliasSubProjectName").prop('selectedIndex',0);
	        	$("#showSubProject").hide();
	        }
		}); 
	  $('#projId').change(function() {
		  $("#showSubProject").hide();
		  $('#subProjectDesc').attr('checked', false);
	  });

	  if($('#subProjectDesc').is(':checked')) {
	  		$("#showSubProject").show();
	  };

	  if($('#isUpdate').val()=='Y') {
      	  	$("#serialNumber").attr("readonly", "readonly");
      }

});
</script>

</head>


<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${projDescCreationMessage}</h2>
		</div>
		<div>
			<form:form id="projDescForm" method="POST" commandName="projDescForm"
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
								<td><form:select path="aliasProjectName"
										cssClass="inputText" id="projId" items="${aliasProjectList}" >
									</form:select></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Description For Sub Project? :</td>
								<td><form:checkbox path="subProjectDesc" id="subProjectDesc"/></td>
								<td><form:errors path="subProjectDesc" cssClass="error" /></td>
							</tr>
							<tr id="showSubProject">
								<td>Sub Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="aliasSubProjectName"
										id="aliasSubProjectName" cssClass="inputText"  items="${subAliasProjectList}">
										<c:if test="${projDescForm.subProjId gt '0'}">
											<option value="${projDescForm.subProjId}" selected="selected">${projDescForm.aliasSubProjectName}</option>
										</c:if>
									</form:select></td>
								<td><form:errors path="aliasSubProjectName"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Serial Number<span id="colon">:</span>
								</td>
								<td><form:input id="serialNumber" path="serialNumber"
										placeholder="Enter serial number" cssClass="inputText" /></td>
								<td><form:errors path="serialNumber" cssClass="error" /></td>
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
								<td><form:input id="quantityInFig" path="quantityInFig"
										placeholder="Enter quantity for the selected work" cssClass="inputText" /></td>
								<td><form:errors path="quantityInFig" cssClass="error" /></td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td>Quantity in Unit<span id="colon">:</span> -->
<!-- 								</td> -->
<%-- 								<td><form:input id="quantityInUnit" path="quantityInUnit" --%>
<%-- 										placeholder="Enter quantity for the selected work" cssClass="inputText" /> --%>
<!-- 								</td> -->
<%-- 								<td><form:errors path="quantityInUnit" cssClass="error" /></td> --%>
<!-- 							</tr> -->
							<tr>
								<td>Description<span id="colon">:</span>
								</td>
								<td><form:textarea path="description"
										placeholder="Enter description" cssClass="inputText" /></td>
								<td><form:errors path="description" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Alias Description<span id="colon">:</span>
								</td>
								<td><form:input path="aliasDescription"
										placeholder="Enter alias description" cssClass="inputText" /></td>
								<td><form:errors path="aliasDescription" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Cost per Quantity<span id="colon">:</span>
								</td>
								<td><form:input path="rateInFig"
										placeholder="Enter rate in figures" cssClass="inputText" /></td>
								<td><form:errors path="rateInFig" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Total Cost<span id="colon">:</span>
								</td>
								<td><form:input path="projDescAmount"
										placeholder="Enter Amount" cssClass="inputText" /></td>
								<td><form:errors path="projDescAmount" cssClass="error" /></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>

					<form:hidden path="employeeId" />
					<form:hidden path="projId" />
					<form:hidden path="subProjId" />
					<form:hidden path="isUpdate" />
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


