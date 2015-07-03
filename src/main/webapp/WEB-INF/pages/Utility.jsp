<script>
var tblNewSignUpRequest = $("#newSignUpRequests").dataTable();
var tblEmdDocument = $("#emdDocumentList").dataTable();
</script>


<script>
$(function() {
    var table = $("#itemDescList").dataTable( {"pageLength": 10});
	
     $("#itemDescList_columnUl").on('change', 'input:checkbox', function(e) {
        var columnName = $(this).attr('name');			  
        
		  var isColumnStatusChecked = $( this ).is( ":checked" ) 
		  if(!isColumnStatusChecked){
				$(this).attr('value', 'hidden');
		  } else{
				$(this).attr('value', '');
		  }
        $("table[id*=itemDescList] th").each(function(index, item) {			  
            if (columnName == item.innerHTML) {
                $('table[id*=itemDescList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
            }
        })
    });


    $("#itemDescList_paginate").on('click', function(e) {
        $("#itemDescList_columnUl").find('input:checkbox').each(function(index, item) {
            var columnStatus = $(this).attr('value');
            var columnName = $(this).attr('name');				  
			  
            if (columnStatus == 'hidden') {						
                $("table[id*=itemDescList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#itemDescList tr').find("td:eq(" + index + ")").css('display');
                        if (displayType != 'none') {
                            $('table[id*=itemDescList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
            }else{					
				 $("table[id*=itemDescList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#itemDescList tr').find("td:eq(" + index + ")").css('display');							  
                        if (displayType == 'none') {
                            $('table[id*=itemDescList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
			  }
        });
    });		  
});
</script>


<script>
$(function() {
    var table = $("#projectDocumentList").dataTable( {"pageLength": 10});
	
     $("#projectDocumentList_columnUl").on('change', 'input:checkbox', function(e) {
        var columnName = $(this).attr('name');			  
        
		  var isColumnStatusChecked = $( this ).is( ":checked" ) 
		  if(!isColumnStatusChecked){
				$(this).attr('value', 'hidden');
		  } else{
				$(this).attr('value', '');
		  }
        $("table[id*=projectDocumentList] th").each(function(index, item) {			  
            if (columnName == item.innerHTML) {
                $('table[id*=projectDocumentList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
            }
        })
    });


    $("#projectDocumentList_paginate").on('click', function(e) {
        $("#projectDocumentList_columnUl").find('input:checkbox').each(function(index, item) {
            var columnStatus = $(this).attr('value');
            var columnName = $(this).attr('name');				  
			  
            if (columnStatus == 'hidden') {						
                $("table[id*=projectDocumentList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#projectDocumentList tr').find("td:eq(" + index + ")").css('display');
                        if (displayType != 'none') {
                            $('table[id*=projectDocumentList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
            }else{					
				 $("table[id*=projectDocumentList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#projectDocumentList tr').find("td:eq(" + index + ")").css('display');							  
                        if (displayType == 'none') {
                            $('table[id*=projectDocumentList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
			  }
        });
    });		  
});
</script>


<script>
$(function() {
    var table = $("#projDescDocList").dataTable( {"pageLength": 10});
	
     $("#projDescDocList_columnUl").on('change', 'input:checkbox', function(e) {
        var columnName = $(this).attr('name');			  
        
		  var isColumnStatusChecked = $( this ).is( ":checked" ) 
		  if(!isColumnStatusChecked){
				$(this).attr('value', 'hidden');
		  } else{
				$(this).attr('value', '');
		  }
        $("table[id*=projDescDocList] th").each(function(index, item) {			  
            if (columnName == item.innerHTML) {
                $('table[id*=projDescDocList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
            }
        })
    });


    $("#projDescDocList_paginate").on('click', function(e) {
        $("#projDescDocList_columnUl").find('input:checkbox').each(function(index, item) {
            var columnStatus = $(this).attr('value');
            var columnName = $(this).attr('name');				  
			  
            if (columnStatus == 'hidden') {						
                $("table[id*=projDescDocList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#projDescDocList tr').find("td:eq(" + index + ")").css('display');
                        if (displayType != 'none') {
                            $('table[id*=projDescDocList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
            }else{					
				 $("table[id*=projDescDocList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#projDescDocList tr').find("td:eq(" + index + ")").css('display');							  
                        if (displayType == 'none') {
                            $('table[id*=projDescDocList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
			  }
        });
    });		  
});
</script>

<script>
$(function() {
    var table = $("#subProjectDocumentList").dataTable( {"pageLength": 10});
	
     $("#subProjectDocumentList_columnUl").on('change', 'input:checkbox', function(e) {
        var columnName = $(this).attr('name');			  
        
		  var isColumnStatusChecked = $( this ).is( ":checked" ) 
		  if(!isColumnStatusChecked){
				$(this).attr('value', 'hidden');
		  } else{
				$(this).attr('value', '');
		  }
        $("table[id*=subProjectDocumentList] th").each(function(index, item) {			  
            if (columnName == item.innerHTML) {
                $('table[id*=subProjectDocumentList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
            }
        })
    });


    $("#subProjectDocumentList_paginate").on('click', function(e) {
        $("#subProjectDocumentList_columnUl").find('input:checkbox').each(function(index, item) {
            var columnStatus = $(this).attr('value');
            var columnName = $(this).attr('name');				  
			  
            if (columnStatus == 'hidden') {						
                $("table[id*=subProjectDocumentList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#subProjectDocumentList tr').find("td:eq(" + index + ")").css('display');
                        if (displayType != 'none') {
                            $('table[id*=subProjectDocumentList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
            }else{					
				 $("table[id*=subProjectDocumentList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#subProjectDocumentList tr').find("td:eq(" + index + ")").css('display');							  
                        if (displayType == 'none') {
                            $('table[id*=subProjectDocumentList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
			  }
        });
    });		  
});
</script>