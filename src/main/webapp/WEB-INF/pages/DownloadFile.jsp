<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Download File</title>

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
              	  
              	$(function() {
              		var table = $("#projectFileList").dataTable();
              	})
 
                    });
</script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
 
        <form:form method="post" action="getFiles.do"
            modelAttribute="downloadForm" enctype="multipart/form-data">
 
            <table id="fileTable">
                            <tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="aliasProjectName"
										cssClass="inputText" id="projId" items="${aliasProjectList}" >
									</form:select></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Download For Sub Project? :</td>
								<td><form:checkbox path="subProjectUpload" id="subProjectUpload"/></td>
								<td><form:errors path="subProjectUpload" cssClass="error" /></td>
							</tr>
							<tr id="showSubProject">
								<td>Sub Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="aliasSubProjectName"
										id="aliasSubProjectName" cssClass="inputText"  items="${subAliasProjectList}">
										<c:if test="${projDescForm.subProjId gt '0'}">
											<option value="${projDescForm.subProjId}" selected="selected">${projDescForm.aliasSubProjectName}</option>
										</c:if>
									</form:select></td>
								<td><form:errors path="aliasSubProjectName"
										cssClass="error" /></td>
							</tr>
							</table>
            <br />
            <input type="submit" value="Download" />
        </form:form>
 
        <br />
    </div>
    
	<div>
		
		<c:if test="${projectFileSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectName} File Details</h1>
				<table id="projectFileList" class="gridView">
					<thead>
						<tr>
							<th>File Name</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty projectFileList}">
							<c:forEach var="fileDoc" items="${projectFileList}">
								<tr>
									<td><a href="/pms/emp/myview/downloadFile/downloadFiles.web?path=${fileDoc.filePath}">${fileDoc.fileName}</a></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
		
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>