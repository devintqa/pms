<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>

    <title>PMS :: Supplier Quote Details</title>

    <%@include file="Script.jsp" %>


    <style>
        .quoteDetailStyle {
            width: 200px;
        }

        .tdStyle {
            font-weight: bold;
            padding-left: 10px;
        }
    </style>
</head>

<body>
<header>
    <jsp:include page="Header.jsp"/>
</header>
<div>
    <h2
            style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${actionMessage}</h2>
</div>
<div id="wrapper">
    <div>
        <form:form method="POST" commandName="quoteDetailsForm"
                   id="quoteDetailsForm" action="savePurchaseDetails.do">
            <center>
                <fieldset style="margin: 1em; text-align: left;">
                    <legend>Supplier Details</legend>
                    <table>
                        <tr>
                            <td>Project Name <span id="colon">:</span>
                            </td>
                            <td id="projName" class="tdStyle">${quoteDetailsForm.projName}</td>
                        </tr>
                        <tr>
                            <td>Item Name<span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${quoteDetailsForm.itemName}</td>
                        </tr>
                        <tr>
                            <td>Alias Supplier Name <span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${supplierDetails.supplierAliasName}</td>


                        </tr>
                        <tr>
                            <td>Brand Name<span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${supplierDetails.brandName}</td>

                        </tr>
                        <tr>
                            <td>Email Address <span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${supplierDetails.emailAddress}</td>

                        </tr>
                        <tr>
                            <td>Phone Number <span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${supplierDetails.phoneNumber}</td>
                        </tr>
                        <tr>
                            <td>Quoted Price<span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${supplierDetails.quotedPrice}</td>
                        </tr>
                        <tr>
                            <td>Quantity <span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${supplierDetails.itemQty}</td>
                        </tr>
                        <tr></tr>
                        <tr></tr>
                        <tr>
                            <td>Tentative Delivery Date<span id="colon">:</span>
                            </td>
                            <td><form:input path="tentativeDeliveryDate"
                                            placeholder="DD-MM-YYYY" cssClass="inputText"/></td>
                            <td><form:errors path="tentativeDeliveryDate"
                                             cssClass="error"/></td>

                        </tr>

                        <tr>
                            <td>Comments<span id="colon">:</span>
                            </td>
                            <td><form:textarea path="comments"
                                               placeholder="Enter comments" cssClass="inputText"/></td>
                            <td><form:errors path="comments" cssClass="error"/></td>

                        </tr>
                    </table>

                </fieldset>
            </center>
            <br>
            <form:hidden path="quoteDetailsValue" id="quoteDetailsValue"/>
            <form:hidden path="projName" id="projName"/>
            <form:hidden path="itemName" id="itemName"/>
            <form:hidden path="itemType" id="itemType"/>
            <form:hidden path="employeeId" id="employeeId"/>
            <form:hidden path="submittedForApproval" id="submittedForApproval"/>

            <center>
                <table>
                    <tr>
                        <td></td>
                        <td><input class="button" type="submit"/></td>
                        <td></td>
                    </tr>
                </table>
            </center>
        </form:form>
    </div>
    <div id="dialog-confirm"></div>
</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>

