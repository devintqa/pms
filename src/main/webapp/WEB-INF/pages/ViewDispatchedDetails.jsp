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
        $(document).ready(function () {
            if ($('#projId').val() == 0) {
                $("#fieldUserRow").hide();
            }
            $("#projId").change(function () {
                var projId = $('#projId').val();
                var employeeId = $('#employeeId').val();
                $.ajax({
                    type: "GET",
                    url: "getFieldUsers.do",
                    cache: false,
                    data: "projId=" + projId + "&employeeId=" + employeeId,
                    success: function (response) {
                        if (response.success) {
                            var obj = jQuery.parseJSON(response.object);
                            var options = '';
                            for (var key in obj) {
                                var attrName = key;
                                var attrValue = obj[key];
                                options = options + '<option value="' + attrValue + '">'
                                        + attrValue + '</option>';
                            }
                            $('#fieldUser').html(options);
                            $("#fieldUserRow").show();
                        } else {
                            $("#projId").prop('selectedIndex', 0);
                            $("#dialog-confirm").html(response.data);
                            $("#dialog-confirm").dialog({
                                modal: true,
                                title: "Warning!",
                                height: 200,
                                width: 300,
                                buttons: {
                                    Ok: function () {
                                        $(this).dialog("close");
                                    }
                                }
                            });
                        }
                    }
                });
            });
        });

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
                        <tr id="fieldUserRow">
                            <td>Field User<span id="colon">:</span>
                            </td>
                            <td><form:select path="fieldUser" cssClass="inputText"
                                             id="fieldUser" items="${fieldUsers}">
                                <option value="${fieldUser}"
                                        selected="selected">${fieldUser}</option>

                            </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Recieved Date<span id="colon">:</span>
                            </td>
                            <td><form:input path="dispatchedDate" placeholder="DD-MM-YYYY"
                                            cssClass="inputText"/></td>
                            <td><form:errors path="dispatchedDate" cssClass="error"/></td>
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
            <table id="dispatchDetails" class="gridView">
                <thead>
                <tr>
                    <th>Item Name</th>
                    <th>Quantity</th>
                    <th>Type</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty dispatchDetails}">
                    <c:forEach var="item" items="${dispatchDetails}">
                        <tr>
                            <td>${item.itemName}</td>
                            <td>${item.requestedQuantity}</td>
                            <td>${item.description}</td>
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