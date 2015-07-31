<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Upload File</title>

<%@include file="Script.jsp" %>

<script>
    $(document)
            .ready(
                    function() {
                    	$("#showSubProject").hide();
                        //add more file components if Add is clicked
                        $('#addFile')
                                .click(
                                        function() {
                                            var fileIndex = $('#fileTable tr')
                                                    .children().length - 1;
                                            $('#fileTable')
                                                    .append(
                                                            '<tr><td>'
                                                                    + '   <input type="file" name="files['+ fileIndex +']" />'
                                                                    + '</td></tr>');
                                        });
                  	  $('#subProjectUpload').change(function() {
              	        if($(this).is(":checked")) {
              			var aliasProjectName  = $('#projId').val();
              			$.ajax({
              				type : "GET",
              				url : "getSubAliasProject.do",
              				cache : false,
              				data: "aliasProjectName="+aliasProjectName,
              				success : function(response) {
              					var options = '';
              					if (response != null) {
              						var obj = jQuery.parseJSON(response);
              						var options = '';
              						//options = '<option value=0>--Please Select--</option>';
              						for ( var key in obj) {
              							var attrName = key;
              							var attrValue = obj[key];
              							options = options + '<option value='+attrName+'>'
              									+ attrValue + '</option>';
              						}
              						$('#aliasSubProjectName').html(options);
              					}
              				}
              			});
              				$("#showSubProject").show();
              	        }else {
              	        	$("#showSubProject").hide();
              	        }
              		}); 
              	  $('#projId').change(function() {
              		  $("#showSubProject").hide();
              		  $('#subProjectUpload').attr('checked', false);
              	  });

              	  if($('#subProjectUpload').is(':checked')) {
              	  		$("#showSubProject").show();
              	  };
 
                    });
</script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${fileAdditionSuccessful}</h2>
		</div>
		<div>

			<form:form method="post" action="saveFiles.do"
				modelAttribute="uploadForm" enctype="multipart/form-data">

				<p>Select files to upload. Press Add button to add more file
					inputs.</p>

				<table id="fileTable">
					<tr>
						<td>Alias Project Name <span id="colon">:</span>
						</td>
						<td><form:select path="aliasProjectName" cssClass="inputText"
								id="projId" items="${aliasProjectList}">
							</form:select></td>
						<td><form:errors path="aliasProjectName" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Upload For Sub Project? :</td>
						<td><form:checkbox path="subProjectUpload"
								id="subProjectUpload" /></td>
						<td><form:errors path="subProjectUpload" cssClass="error" /></td>
					</tr>
					<tr id="showSubProject">
						<td>Sub Project Name <span id="colon">:</span>
						</td>
						<td><form:select path="aliasSubProjectName"
								id="aliasSubProjectName" cssClass="inputText"
								items="${subAliasProjectList}">
								<c:if test="${projDescForm.subProjId gt '0'}">
									<option value="${projDescForm.subProjId}" selected="selected">${projDescForm.aliasSubProjectName}</option>
								</c:if>
							</form:select></td>
						<td><form:errors path="aliasSubProjectName" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:input name="files[0]" type="file" path="files"
								id="files" /></td>
						<td><form:errors path="files" cssClass="error" /></td>
					</tr>

				</table>
				<br />
				<input type="submit" value="Upload" />
				<input id="addFile" type="button" value="Add File" />
			</form:form>
		</div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>

</html>