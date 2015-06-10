<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
    <head>
        <title>PMS :: Update EMD Detail</title>
        <%@include file="Script.jsp" %>
        <%@include file="Utility.jsp" %>
    </head>
    <body>
	    <header>
		    <jsp:include page="Header.jsp" />
	    </header>
	    <div>
	    	<c:if test="${emdDetailsSize gt 0}">
        		<h1 style="text-align: center; color: #007399; font-size: 24px;"> Project Description Details</h1>
	        	<table id="emdList" class="gridView">
            		<thead>
            			<tr>
            				<th>Project Name</th>
            				<th>Sub project Name</th>
            				<th>EMD Type</th>
            		    	<th>EMD Amount</th>
            				<th>EMD Start Date</th>
            				<th>EMD End Date</th>
            			</tr>
            		</thead>
					<tbody>
						<c:if test="${not empty emdDetails}">
							<c:forEach var="emd" items="${emdDetails}">
								<tr>
								<td><a href="/pms/emp/myview/buildEmd/${employeeObj.employeeId}?emdId=${emd.emdId}&action=updateEmd&aliasProjectName=${emd.aliasProjectName}&aliasSubProjectName=${emd.aliasSubProjectName}"
                                		class="userAction">${emd.aliasProjectName}</a></td>
								<td><a href="/pms/emp/myview/buildEmd/${employeeObj.employeeId}?emdId=${emd.emdId}&action=updateEmd&aliasProjectName=${emd.aliasProjectName}&aliasSubProjectName=${emd.aliasSubProjectName}"
                                        class="userAction">${emd.aliasSubProjectName}</a></td>
									<td>${emd.emdType}</td>
									<td>${emd.emdAmount}</td>
									<td>${emd.sqlEmdStartDate}</td>
									<td>${emd.sqlEmdEndDate}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
	        	</table>
	        </c:if>
	    </div>
        <footer>
		    <jsp:include page="Footer.jsp" />
        </footer>
</body>
</html>