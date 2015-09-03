<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>

    <title>PMS :: Create Role</title>
    <%@include file="Script.jsp" %>

    <script>
        $(document).ready(function () {
            $("#roleName").autocomplete({
                source: function (request, response) {
                    $.getJSON("/pms/emp/myview/createRole/getRole.do", {
                        teamName: $('#teamName').val()
                    }, response);
                }
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
                style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${roleCreationMessage}</h2>

        <h2
                style="text-align: left; font-family: arial; color: red; font-size: 14px;">${roleCreationErrorMessage}</h2>

    </div>
    <div>
        <form:form method="POST" commandName="teamForm" modelAttribute="teamForm" enctype="multipart/form-data"
                   action="saveRole.do">
            <center>
                <fieldset style="margin: 1em; text-align: left;">
                    <legend>Role Details</legend>
                    <table>
                        <tr>
                            <td>Team<span id="colon">:</span>
                            </td>
                            <td><form:select path="teamName" cssClass="inputText"
                                             items="${teamNames}"/></td>
                            <td><form:errors path="teamName" cssClass="error"/></td>
                        </tr>
                        <tr></tr>
                        <tr>
                            <td>Team Role<span id="colon">:</span>
                            </td>
                            <td><form:input path="roleName"
                                            placeholder="Enter Role" cssClass="inputText"/></td>
                            <td><form:errors path="roleName" cssClass="error"/></td>
                        </tr>


                        <tr></tr>
                    </table>
                </fieldset>

                <form:hidden path="employeeId"/>
                <table>
                    <tr>
                        <td></td>
                        <td><input class="button" type="submit"/></td>
                        <td></td>
                    </tr>
                </table>
            </center>
            <br>
        </form:form>

    </div>

</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>


