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
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="<c:url value="/resources/js/script.js" />"></script>
 <script type="text/javascript">
   function fillstates() {
     $.ajax({
    	  type: "get",
    	  url: "http://localhost:8082/pms/emp/myview/getSubAliasProject",
    	  cache: false,    
    	  data:'aliasProjectName=' + $('#aliasProjectName').val(),
       success : function(response) {
    	   var options = '';
           if (response != null) {
             $(response).each(function(index, value) {
               options = options + '<option>' + value + '</option>';
             });
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
			<form:form method="POST" commandName="createProjDescForm"
				action="createProjDesc.do">
				<!--<form:errors path="*" cssClass="errorblock" element="div" />
				--><center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Project Description</legend>
						<table>
						<tr>
								<td>Alias Project Name <span id="colon">:</span> </td>
								<td><form:select path="aliasProjectName" cssClass="inputText" id="aliasProjectName" items="${aliasProjectList}" onchange="fillstates()"/></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
						</tr>
						<tr>
								<td>Sub Project Name <span id="colon">:</span> </td>
								<td><form:select path="aliasSubProjectName" id="aliasSubProjectName" cssClass="inputText"/></td>
								<td><form:errors path="aliasSubProjectName" cssClass="error" /></td>
						</tr>	
						<tr>
							<td>Work Type<span id="colon">:</span>
							</td>
							<td><form:input path="workType"
									placeholder="Enter Work Type" cssClass="inputText" /></td>
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
							<td><form:input path="rateInWords" placeholder="Enter Rate in Words"
									 cssClass="inputText" /></td>
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

					<form:hidden path="employeeId"/>
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


