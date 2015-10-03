<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <title>PMS :: Create Field Description</title>
    <%@include file="Script.jsp" %>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#showSubProject").hide();
            $('#subProjectDesc').change(function () {
                if ($(this).is(":checked")) {
                    var aliasProjectName = $('#projId').val();
                    var empId = $('#employeeId').val();
                    $.ajax({
                        type: "GET",
                        url: "getSubAliasProject.do",
                        cache: false,
                        data: "aliasProjectName=" + aliasProjectName + "&empId=" + empId,
                        success: function (response) {
                            var options = '';
                            if (response != null) {
                                var obj = jQuery.parseJSON(response);
                                var options = '';
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
                $('#subProjectDesc').attr('checked', false);
            });

            if ($('#subProjectDesc').is(':checked')) {
                $("#showSubProject").show();
            }
            ;
        });
        function confirmOverwriteOfFieldData(projectId, subProjectId) {
            if (0 == projectId || ($('#subProjectDesc').is(':checked') && 0 == subProjectId)) {
                $("#dialog-confirm").html("Please Select Alias Project/SubProjecct Name");
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
                return;
            }
            $.ajax({
                type: 'GET',
                url: 'FieldDescriptionPresent.do',
                data: "projectId=" + projectId + '&subProjectId=' + subProjectId,
                success: function (response) {
                    if (response == "NotExist") {
                        $("#dialog-confirm").html("Description Data doesn't Exist for Selected Alias Project/SubProjecct Name");
                        $("#dialog-confirm").dialog({
                            modal: true,
                            title: "Warning!",
                            height: 200,
                            width: 350,
                            buttons: {
                                Ok: function () {
                                    $(this).dialog("close");
                                }
                            }
                        });
                        return;
                    }
                    if (response == "AlreadyExist") {
                        $("#dialog-confirm")
                                .html("Description Already Exist for Selected Alias Project/SubProjecct Name, Do you want to Overwrite ??");
                        $("#dialog-confirm").dialog({
                            resizable: false,
                            modal: true,
                            title: "Warning!",
                            height: 200,
                            width: 350,
                            buttons: {
                                "Yes": function () {
                                    $.ajax({
                                        type: 'POST',
                                        url: 'createFieldDescription.do',
                                        data: "projectId=" + projectId + '&subProjectId=' + subProjectId,
                                        success: function (response) {
                                            $("#dialog-confirm").html("Success fully created field description Data");
                                            $("#dialog-confirm").dialog({
                                                modal: true,
                                                title: "Success!",
                                                height: 200,
                                                width: 300,
                                                buttons: {
                                                    Ok: function () {
                                                        $(this).dialog("close");
                                                    }
                                                }
                                            });
                                            return;
                                            console.log("Successfully created data");
                                        },
                                        error: function (err) {
                                            console.log("Error creating filed description");
                                        }
                                    });
                                },
                                "No": function () {
                                    $(this).dialog('close');
                                }
                            }
                        });
                    }
                    if (response == "Success") {
                        $("#dialog-confirm").html("Success fully created field description Data");
                        $("#dialog-confirm").dialog({
                            modal: true,
                            title: "Success!",
                            height: 200,
                            width: 300,
                            buttons: {
                                Ok: function () {
                                    $(this).dialog("close");
                                }
                            }
                        });
                        return;
                    }
                },
                error: function (err) {
                    console.log("Error creating field description ");
                }
            });
        }
    </script>

</head>

<body>
<header>
    <jsp:include page="Header.jsp"/>
</header>
<div id="wrapper">
    <div>
        <h2 style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${projDescCreationMessage}</h2>
    </div>
    <div>
        <form:form id="projDescForm" method="POST" commandName="projDescForm">
            <!--<form:errors path="*" cssClass="errorblock" element="div"/>
            -->
            <center>
                <fieldset style="margin: 1em; text-align: left;">
                    <legend>Project Description</legend>
                    <table>
                        <tr>
                            <td>Alias Project Name <span id="colon">:</span>
                            </td>
                            <td><form:select path="aliasProjectName"
                                             cssClass="inputText" id="projId" items="${aliasProjectList}">
                            </form:select></td>
                            <td><form:errors path="aliasProjectName" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Description For Sub Project? :</td>
                            <td><form:checkbox path="subProjectDesc" id="subProjectDesc"/></td>
                            <td><form:errors path="subProjectDesc" cssClass="error"/></td>
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
                    </table>
                </fieldset>

                <form:hidden path="employeeId"/>
                <form:hidden path="projId"/>
                <form:hidden path="subProjId"/>
                <form:hidden path="isUpdate"/>
                <form:hidden path="projDescId"/>
                <table>
                    <tr>
                        <td></td>
                        <td>
                            <input class="button" id="createFieldDescription" type="button" value="Create"
                                   onclick="confirmOverwriteOfFieldData($('#projId').val(),$('#aliasSubProjectName').val())"/>
                        </td>
                        <td></td>
                    </tr>
                </table>
            </center>
            <br>
            <br>

        </form:form>
    </div>
    <div id="dialog-confirm"></div>
</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>