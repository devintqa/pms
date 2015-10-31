<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>

    <title>PMS :: Create Supplier Detail</title>

    <%@include file="Script.jsp" %>

    <script type="text/javascript">
        $(document).on("keydown", "input[name = 'tinNumber']", function () {
            document.getElementById("reason").disabled = $('#tinNumber').val() != '';
        });
        $(document).on("keydown", "input[name = 'reason']", function () {
            document.getElementById("tinNumber").disabled = $('#reason').val() != '';
        });
        $(document).ready(function () {
            document.getElementById("reason").disabled = $('#tinNumber').val() != '';
            document.getElementById("tinNumber").disabled = $('#reason').val() != '';
        })
    </script>

</head>

<body>
<header>
    <jsp:include page="Header.jsp"/>
</header>
<div id="wrapper">
    <div>
        <h2
                style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${actionMessage}</h2>
    </div>
    <div>
        <form:form id="supplierForm" method="POST" commandName="supplierForm"
                   action="createOrUpdateSupplier.do">
            <center>
                <fieldset style="margin: 1em; text-align: left;">
                    <legend>Supplier Details</legend>
                    <table>
                        <tr>
                            <td>Supplier Name <span id="colon">:</span>
                            </td>
                            <td><form:textarea id="name" path="name"
                                               placeholder="Enter supplier name" cssClass="inputText"/></td>
                            <td><form:errors path="name" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Alias Supplier Name <span id="colon">:</span>
                            </td>
                            <td><form:input id="aliasName" path="aliasName"
                                            placeholder="Enter supplier alias name" cssClass="inputText"/></td>
                            <td><form:errors path="aliasName" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>TIN Number <span id="colon">:</span>
                            </td>
                            <td><form:input path="tinNumber"
                                            placeholder="Enter supplier TIN Number" cssClass="inputText"/></td>
                            <td><form:errors path="tinNumber" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Reason<span id="colon">:</span>
                            </td>
                            <td><form:textarea path="reason"
                                               placeholder="Enter Reason" cssClass="inputText"/></td>
                            <td><form:errors path="reason" cssClass="error"/></td>
                        </tr>

                        <tr>
                            <td>Mobile Number <span id="colon">:</span>
                            </td>
                            <td><form:input id="phoneNumber" path="phoneNumber"
                                            placeholder="Enter supplier Phone Number" cssClass="inputText"/></td>
                            <td><form:errors path="phoneNumber" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Email Address <span id="colon">:</span>
                            </td>
                            <td><form:input id="emailAddress" path="emailAddress"
                                            placeholder="Enter supplier Email Address" cssClass="inputText"/></td>
                            <td><form:errors path="emailAddress" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Description <span id="colon">:</span>
                            </td>
                            <td><form:textarea path="description"
                                               placeholder="Enter description" cssClass="inputText"/></td>
                            <td><form:errors path="description" cssClass="error"/></td>
                        </tr>
                        <tr></tr>
                    </table>
                </fieldset>
                <form:hidden path="isUpdate"/>
                <form:hidden path="supplierId"/>
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
    </div>
</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>