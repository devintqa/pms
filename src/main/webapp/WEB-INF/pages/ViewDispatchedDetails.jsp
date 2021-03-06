<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>PMS :: View Dispatched Details</title>
    <%@include file="Script.jsp" %>
    <%@include file="Utility.jsp" %>
    <script>

    </script>
</head>
<body>
<header>
    <jsp:include page="Header.jsp"/>
</header>
<div id="wrapper">
    <div>
        <h2
                style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${errorMessage}</h2>
    </div>
    <div class="ui-widget">
        <form:form id="dispatchDetailForm" method="POST"
                   commandName="dispatchDetailForm"
                   action="viewDispatchDetails.do">
            <center>
                <fieldset style="margin: 1em; text-align: left;">
                    <legend>View Dispatched Details</legend>
                    <table>
                        <tr>
                            <td>Project Name <span id="colon">:</span>
                            </td>
                            <td><form:select path="projId"
                                             cssClass="inputText" id="projId" items="${aliasProjectList}">
                            </form:select></td>
                            <td><form:errors path="projId" cssClass="error"/></td>
                        </tr>
                        <tr></tr>
                    </table>
                    <form:hidden path="employeeId"/>
                </fieldset>
                <table>
                    <tr>
                        <td></td>
                        <td><input class="button" type="submit"/></td>
                        <td></td>
                    </tr>
                </table>
            </center>
            <br>
            <br>

        </form:form>

        <c:if test="${dispatchDetailsSize gt 0}">
            <h1 style="text-align: center; color: #007399; font-size: 24px;">
                Dispatched Details</h1>
            <table id="aggregateItemList" class="gridView">
                <thead>
                <tr>
                    <th>Field User</th>
                    <th>Item Name</th>
                    <th>Dispatched Date</th>
                    <th>Dispatched Quantity</th>
                    <th>Returned Quantity</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty dispatchDetails}">
                    <c:forEach var="item" items="${dispatchDetails}">
                        <tr>
                            <td>${item.fieldUser}</td>
                            <td>${item.itemName}</td>
                            <td>${item.dispatchedDate}</td>
                            <td>${item.requestedQuantity}</td>
                            <td>${item.returnedQuantity}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <br>
            <br>
        </c:if>


    </div>
    <div id="dialog-confirm"></div>
</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>