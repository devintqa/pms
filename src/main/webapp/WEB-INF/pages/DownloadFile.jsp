<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Download File</title>
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
<script>
$(function() {
	var table = $("#projectFileList").dataTable();
})
</script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
 
        <form:form method="post" action="getFiles.do"
            modelAttribute="downloadForm" enctype="multipart/form-data">
 
            <table id="fileTable">
                            <tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="aliasProjectName"
										cssClass="inputText" id="projId" items="${aliasProjectList}" >
									</form:select></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							</table>
            <br />
            <input type="submit" value="Download" />
        </form:form>
 
        <br />
    </div>
    
	<div>
		
		<c:if test="${projectFileSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectName} File Details</h1>
				<table id="projectFileList" class="gridView">
					<thead>
						<tr>
							<th>File Name</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty projectFileList}">
							<c:forEach var="fileDoc" items="${projectFileList}">
								<tr>
									<td><a href="/pms/emp/myview/downloadFile/downloadFiles.web?path=${fileDoc.filePath}">${fileDoc.fileName}</a></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
		
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>