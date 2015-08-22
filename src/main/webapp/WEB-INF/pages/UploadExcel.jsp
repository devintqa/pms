<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Create Project Description</title>

<%@include file="Script.jsp" %>

<script>
    $(document).ready(function() {
              $("#showSubProject").hide();
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

           	  $('#projId').change(function() {
                  var governmentEst  = "N";
                  if($('#governmentEst').is(':checked')) {
                  	governmentEst = "Y";
  	       	  	 }; 
                          if(!$('#subProjectUpload').is(':checked')) {
                                var projectId = this.value;                            
                                 $.ajax({
                       				type : "GET",
                       				url : "checkProjectDesc.do",
                       				cache : false,
                       				data: {
                       		            "projectId" : projectId,
                       		            "governmentEst" : governmentEst
                       		        },
                       				success : function(data) {
                       				if(data == "Exists") {
                       				    $( "#dialog-alert" ).html("Project contains ProjectDescription data. Uploading file will over write the existing data.");
                       				    $( "#dialog-alert" ).dialog({
                       				         modal: true,
                       				         height: 200,
                                                width: 400,
                                                    buttons: {
                                                       Ok: function() {
                                                         $( this ).dialog( "close" );
                                                       }
                       				             }
                       				    });
                       		        }
                                    }
                       		  });
                          }
           	          }
                  )

                  $('#aliasSubProjectName').change(function() {
                	  var governmentEst  = "N";
                      if($('#governmentEst').is(':checked')) {
                      	governmentEst = "Y";
      	       	  	 };
                            var subProjectId = this.value;
                            $.ajax({
                                 type : "GET",
                                 url : "checkSubProjectDesc.do",
                                 cache : false,
                                 data: {
                    		            "subProjectId" : subProjectId,
                    		            "governmentEst" : governmentEst
                    		        },
                                 success : function(data) {
                                 if(data == "Exists"){
                                       $( "#dialog-alert" ).html("Sub Project already contains ProjectDescription data. Uploading file will over write the existing data.");
                                       $( "#dialog-alert" ).dialog({
                                           modal: true,
                                           height: 200,
                                           width: 400,
                                               buttons: {
                                                 Ok: function() {
                                                   $( this ).dialog( "close" );
                                                 }
                                               }
                                       })
                                 }
                            }
                       });
                    }

                  )
            });

</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${fileAdditionSuccessful}</h2>
			<h2
				style="text-align: left; font-family: arial; color: #FF0000; font-size: 14px;">${uploadProjectDescriptionFailed}</h2>
		</div>
		<div>

			<form:form method="post" action="saveProjectDescription.do" modelAttribute="uploadForm" enctype="multipart/form-data">
				<fieldset style="margin: 1em; text-align: left;">
					<legend>Project Description</legend>
					<table id="fileTable">
						<tr>
							<td>Is Government Estimation? <span id="colon">:</span></td>
							<td><form:checkbox path="governmentEst" id="governmentEst" /></td>
							<td></td>
						</tr>
						<tr>
							<td>Alias Project Name <span id="colon">:</span></td>
							<td><form:select path="aliasProjectName"
									cssClass="inputText" id="projId" items="${aliasProjectList}">
								</form:select></td>
							<td><form:errors path="aliasProjectName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Upload For Sub Project? <span id="colon">:</span></td>
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
					<br /> <input class="button" type="submit" value="Upload" />
					<form:hidden path="employeeId" />
					</fieldset>
			</form:form>
			<br />
		</div>
		<div id="dialog-alert" title="Warning"></div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>