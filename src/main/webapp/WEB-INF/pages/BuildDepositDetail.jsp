<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>

    <title>PMS :: Deposit Details</title>

    <%@include file="Script.jsp" %>

    <script>
        $(document).ready(function () {

            if ($('#depositStatus').val() == 'Refunded') {
                $('#showDepositRecievedDate').show();
                $('#showDepositRecievedcomments').show();
            }else{
                $('#showDepositRecievedDate').hide();
                $('#showDepositRecievedcomments').hide();
            }
            $("#showSubProject").hide();
            $('#showDepositStatus').hide();
           ;


            $("#showCompetitorName").hide();

            if ($("#competitor").is(":checked")) {
                $("#showCompetitorName").show();
            }

            //called when key is pressed in textbox
            $("#depositPeriod").keypress(function (e) {
                //if the letter is not digit then display error and don't type anything
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    //display error message
                    $("#errmsg").html("Numbers Only").show().fadeOut("slow");
                    return false;
                }
            });

            $('#subProjectDepositDetail').change(function () {
                if ($(this).is(":checked")) {
                    var aliasProjectName = $('#projId').val();
                    $.ajax({
                        type: "GET",
                        url: "getSubAliasProject.do",
                        cache: false,
                        data: "aliasProjectName=" + aliasProjectName,
                        success: function (response) {
                            var options = '';
                            if (response != null) {
                                var obj = jQuery.parseJSON(response);
                                var options = '';
                                //options = '<option value=0>--Please Select--</option>';
                                for (var key in obj) {
                                    var attrName = key;
                                    var attrValue = obj[key];
                                    options = options + '<option value=' + attrName + '>'
                                            + attrValue + '</option>';
                                }
                                $('#aliasSubProjectName').html(options);
                            }
                        }
                    });
                    $("#showSubProject").show();
                } else {
                    $("#aliasSubProjectName").prop('selectedIndex', 0);
                    $("#showSubProject").hide();
                }
            });
            $('#projId').change(function () {
                $("#showSubProject").hide();
                $('#subProjectDepositDetail').attr('checked', false);
            });

            if ($('#subProjectDepositDetail').is(':checked')) {
                $("#showSubProject").show();
            }
            ;
            $('#competitor').change(function () {
                $("#depositDetailSubmitter").val('');
                $("#showCompetitorName").show();
            });
            $('#psk').change(function () {
                $("#showCompetitorName").hide();
                $("#depositDetailSubmitter").val('');
            });


            $('#depositStatus').change(function () {
                var status = $('#depositStatus').val();
                if (status == 'Refunded') {
                    $('#showDepositRecievedDate').show();
                    $('#showDepositRecievedcomments').show();
                }else{
                    $('#showDepositRecievedDate').hide();
                    $('#showDepositRecievedcomments').hide();
                }
            });

            var depositId = $('#depositId').val();
            if (depositId != "0") {
                $('#showAliasProject').hide();
                $('#showSubProjectCheckBox').hide();
                $('#showSubProject').hide();
                $('#showDepositStatus').show();
            }


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
                style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${emdUpdationMessage}</h2>

        <h2
                style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${depositCreationMessage}</h2>
    </div>
    <div>
        <form:form method="POST" commandName="depositDetailForm" action="createDepositDetail.do">
            <center>
                <fieldset style="margin: 1em; text-align: left;">
                    <legend>Deposit Details</legend>
                    <table>
                        <tr id="showAliasProject">
                            <td>Alias Project Name <span id="colon">:</span>
                            </td>
                            <td><form:select path="aliasProjectName"
                                             cssClass="inputText" id="projId" items="${aliasProjectList}">
                            </form:select></td>
                            <td><form:errors path="aliasProjectName" cssClass="error"/></td>
                        </tr>
                        <tr id="showSubProjectCheckBox">
                            <td>Deposit For Sub Project? :</td>
                            <td><form:checkbox path="subProjectDepositDetail" id="subProjectDepositDetail"/></td>
                            <td><form:errors path="subProjectDepositDetail" cssClass="error"/></td>
                        </tr>
                        <tr id="showSubProject">
                            <td>Sub Project Name <span id="colon">:</span>
                            </td>
                            <td><form:select path="aliasSubProjectName"
                                             id="aliasSubProjectName" cssClass="inputText"
                                             items="${subAliasProjectList}">
                                <c:if test="${projDescForm.subProjId gt '0'}">
                                    <option value="${projDescForm.subProjId}"
                                            selected="selected">${projDescForm.aliasSubProjectName}</option>
                                </c:if>
                            </form:select></td>
                            <td><form:errors path="aliasSubProjectName"
                                             cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Deposit Type <span id="colon">:</span>
                            </td>
                            <td><form:select path="depositType" cssClass="inputText"
                                             items="${depositTypeList}"/></td>
                            <td><form:errors path="depositType" cssClass="error"/></td>
                        </tr>

                        <tr>
                            <td>Deposit Detail <span id="colon">:</span>
                            </td>
                            <td><form:select path="depDetail" cssClass="inputText"
                                             items="${depositDetailList}"/></td>
                            <td><form:errors path="depDetail" cssClass="error"/></td>
                        </tr>

                        <tr>
                            <td>Deposit For <span id="colon">:</span></td>
                            <td><form:radiobutton path="depositFor" id="psk" value="PSK"
                                                  checked="true"/>PSK <form:radiobutton path="depositFor"
                                                                                        id="competitor"
                                                                                        value="competitor"/>Competitor
                            </td>
                            <td><form:errors path="depositFor" cssClass="error"/></td>
                        </tr>
                        <tr id="showCompetitorName">
                            <td>Competitor Name<span id="colon">:</span>
                            </td>
                            <td><form:input path="depositDetailSubmitter"
                                            placeholder="Enter Competitor Name" cssClass="inputText"/></td>
                            <td><form:errors path="depositDetailSubmitter" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Deposit Guarantee Number<span id="colon">:</span>
                            </td>
                            <td><form:input path="bgNumber"
                                            placeholder="Enter Bank Guarantee Number" cssClass="inputText"/></td>
                            <td><form:errors path="bgNumber" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Deposit Start Date<span id="colon">:</span>
                            </td>
                            <td><form:input path="depositStartDate"
                                            placeholder="DD-MM-YYYY" cssClass="inputText"/></td>
                            <td><form:errors path="depositStartDate" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Deposit End Date<span id="colon">:</span>
                            </td>
                            <td><form:input path="depositEndDate" placeholder="DD-MM-YYYY"
                                            cssClass="inputText"/></td>
                            <td><form:errors path="depositEndDate" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Deposit Period (in months)<span id="colon">:</span>
                            </td>
                            <td><form:input path="depositPeriod"
                                            placeholder="Enter Deposit Period (in months)" maxlength="4"
                                            cssClass="inputText" required="required"/></td>
                            <td>&nbsp;<span id="errmsg"></span></td>
                            <td><form:errors path="depositPeriod" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Deposit Extension Date<span id="colon">:</span>
                            </td>
                            <td><form:input path="depositExtensionDate"
                                            placeholder="DD-MM-YYYY" cssClass="inputText"/></td>
                            <td><form:errors path="depositExtensionDate" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Deposit Amount<span id="colon">:</span>
                            </td>
                            <td><form:input path="depositAmount"
                                            placeholder="Enter Deposit Amount" cssClass="inputText"/></td>
                            <td><form:errors path="depositAmount" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Ledger Number<span id="colon">:</span>
                            </td>
                            <td><form:input path="depositLedgerNumber"
                                            placeholder="Enter Ledger Number" cssClass="inputText"/></td>
                            <td><form:errors path="depositLedgerNumber" cssClass="error"/></td>
                        </tr>

                        <tr id="showDepositStatus">
                            <td>Deposit Status <span id="colon">:</span>
                            </td>
                            <td><form:select path="depositStatus" cssClass="inputText"
                                             items="${depositStatusList}"/></td>
                            <td><form:errors path="depositStatus" cssClass="error"/></td>
                        </tr>
                        <tr id="showDepositRecievedDate">
                            <td>Deposit Recieved Date<span id="colon">:</span>
                            </td>
                            <td><form:input path="depositRecievedDate" placeholder="DD-MM-YYYY"
                                            cssClass="inputText"/></td>
                            <td><form:errors path="depositRecievedDate" cssClass="error"/></td>
                        </tr>
                        <tr id="showDepositRecievedcomments">
                            <td>Deposit Recieved Comments<span id="colon">:</span>
                            </td>
                            <td><form:textarea path="depositRecievedComments"
                                               placeholder="Enter Deposit recieved comments" cssClass="inputText"
                                               rows="5" cols="40" maxlength="2000"/></td>
                            <td><form:errors path="depositRecievedComments" cssClass="error"/></td>
                        </tr>

                        <tr></tr>
                    </table>
                </fieldset>

                <form:hidden path="employeeId"/>
                <form:hidden path="projId"/>
                <form:hidden path="subProjId"/>
                <form:hidden path="isUpdate"/>
                <form:hidden path="depositId"/>

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


