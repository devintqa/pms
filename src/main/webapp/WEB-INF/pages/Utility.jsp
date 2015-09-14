<script>
    var tblNewSignUpRequest = $("#newSignUpRequests").dataTable();
    var tblEmdDocument = $("#depositDocumentList").dataTable();
</script>


<script>
    $(function() {

        var table = $('#depositDetails').DataTable({
            "scrollCollapse": true,
            "paging": false
        });
        
        //     var table = $("#depositDetails").dataTable({"pageLength": 10});

        $("#depositDetails_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            $("table[id*=depositDetails] th").each(function(index, item) {
                if (columnName == item.innerHTML) {
                    $('table[id*=depositDetails] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
                }
            })
        });


        $("#depositDetails_paginate").on('click', function(e) {
            $("#depositDetails_columnUl").find('input:checkbox').each(function(index, item) {
                var columnStatus = $(this).attr('value');
                var columnName = $(this).attr('name');

                if (columnStatus == 'hidden') {
                    $("table[id*=depositDetails] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#depositDetails tr').find("td:eq(" + index + ")").css('display');
                            if (displayType != 'none') {
                                $('table[id*=depositDetails] tr').find("td:eq(" + index + ")").toggle();
                            }
                        }
                    })
                } else {
                    $("table[id*=depositDetails] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#depositDetails tr').find("td:eq(" + index + ")").css('display');
                            if (displayType == 'none') {
                                $('table[id*=depositDetails] tr').find("td:eq(" + index + ")").toggle();
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

        var table = $('#depositDocumentList').DataTable({
            "scrollCollapse": true,
            "paging": false
        });
        //     var table = $("#depositDocumentList").dataTable({"pageLength": 10});

        $("#depositDocumentList_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            $("table[id*=depositDocumentList] th").each(function(index, item) {
                if (columnName == item.innerHTML) {
                    $('table[id*=depositDocumentList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
                }
            })
        });


        $("#depositDocumentList_paginate").on('click', function(e) {
            $("#depositDocumentList_columnUl").find('input:checkbox').each(function(index, item) {
                var columnStatus = $(this).attr('value');
                var columnName = $(this).attr('name');

                if (columnStatus == 'hidden') {
                    $("table[id*=depositDocumentList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#depositDocumentList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType != 'none') {
                                $('table[id*=depositDocumentList] tr').find("td:eq(" + index + ")").toggle();
                            }
                        }
                    })
                } else {
                    $("table[id*=depositDocumentList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#depositDocumentList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType == 'none') {
                                $('table[id*=depositDocumentList] tr').find("td:eq(" + index + ")").toggle();
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

        var table = $('#baseDescriptionList').DataTable({
            "scrollCollapse": true,
            "paging": false
        });

        //     var table = $("#baseDescriptionList").dataTable({"pageLength": 10});

        $("#baseDescriptionList_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            $("table[id*=baseDescriptionList] th").each(function(index, item) {
                if (columnName == item.innerHTML) {
                    $('table[id*=baseDescriptionList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
                }
            })
        });


        $("#baseDescriptionList_paginate").on('click', function(e) {
            $("#baseDescriptionList_columnUl").find('input:checkbox').each(function(index, item) {
                var columnStatus = $(this).attr('value');
                var columnName = $(this).attr('name');

                if (columnStatus == 'hidden') {
                    $("table[id*=baseDescriptionList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#baseDescriptionList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType != 'none') {
                                $('table[id*=baseDescriptionList] tr').find("td:eq(" + index + ")").toggle();
                            }
                        }
                    })
                } else {
                    $("table[id*=baseDescriptionList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#baseDescriptionList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType == 'none') {
                                $('table[id*=baseDescriptionList] tr').find("td:eq(" + index + ")").toggle();
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

        var table = $('#employeeDetails').DataTable({
            "scrollCollapse": true,
            "paging": false
        });
        //     var table = $("#employeeDetails").dataTable({"pageLength": 10});

        $("#employeeDetails_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            $("table[id*=employeeDetails] th").each(function(index, item) {
                if (columnName == item.innerHTML) {
                    $('table[id*=employeeDetails] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
                }
            })
        });


        $("#employeeDetails_paginate").on('click', function(e) {
            $("#employeeDetails_columnUl").find('input:checkbox').each(function(index, item) {
                var columnStatus = $(this).attr('value');
                var columnName = $(this).attr('name');

                if (columnStatus == 'hidden') {
                    $("table[id*=employeeDetails] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#employeeDetails tr').find("td:eq(" + index + ")").css('display');
                            if (displayType != 'none') {
                                $('table[id*=employeeDetails] tr').find("td:eq(" + index + ")").toggle();
                            }
                        }
                    })
                } else {
                    $("table[id*=employeeDetails] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#employeeDetails tr').find("td:eq(" + index + ")").css('display');
                            if (displayType == 'none') {
                                $('table[id*=employeeDetails] tr').find("td:eq(" + index + ")").toggle();
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

        var table = $('#projectDocumentList').DataTable({
        	"scrollY":        "500px",
            "scrollCollapse": true,
            "paging": false
        });

        $("#projectDocumentList_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            
            $("div[id*=projectDocumentList_wrapper] table").each(function() {
               console.log(this);
             })
             
            var tables = $("div[id*=projectDocumentList_wrapper] table");
            if(tables.length > 0){
	            $(tables[0]).find('th').each(function(index, item) {
	            	 if (columnName == item.innerHTML) {
	                     $('table[id*=projectDocumentList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
	                     $(this).toggle();
	                 }
	            });
            }
        });
    });
</script>




<script>
    $(function() {

        var table = $('#projDescDocList').DataTable({
        	"scrollY":        "500px",
            "scrollCollapse": true,
            "paging": false
        });

        $("#projDescDocList_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            
            $("div[id*=projDescDocList_wrapper] table").each(function() {
               console.log(this);
             })
             
            var tables = $("div[id*=projDescDocList_wrapper] table");
            if(tables.length > 0){
	            $(tables[0]).find('th').each(function(index, item) {
	            	 if (columnName == item.innerHTML) {
	                     $('table[id*=projDescDocList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
	                     $(this).toggle();
	                 }
	            });
            }
        });
    });
</script>

<script>
    $(function() {

        var table = $('#subProjectDocumentList').DataTable({
            "scrollCollapse": true,
            "paging": false
        });

        //     var table = $("#subProjectDocumentList").dataTable( {"pageLength": 10});

        $("#subProjectDocumentList_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
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
                } else {
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

<script>
    $(function() {

        var table = $('#projectItemPriceList').DataTable({
            "scrollCollapse": true,
            "paging": false
        });

        //     var table = $("#projectItemPriceList").dataTable({"pageLength": 10});

        $("#projectItemPriceList_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            $("table[id*=projectItemPriceList] th").each(function(index, item) {
                if (columnName == item.innerHTML) {
                    $('table[id*=projectItemPriceList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
                }
            })
        });


        $("#projectItemPriceList_paginate").on('click', function(e) {
            $("#projectItemPriceList_columnUl").find('input:checkbox').each(function(index, item) {
                var columnStatus = $(this).attr('value');
                var columnName = $(this).attr('name');

                if (columnStatus == 'hidden') {
                    $("table[id*=projectItemPriceList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#projectItemPriceList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType != 'none') {
                                $('table[id*=projectItemPriceList] tr').find("td:eq(" + index + ")").toggle();
                            }
                        }
                    })
                } else {
                    $("table[id*=projectItemPriceList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#projectItemPriceList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType == 'none') {
                                $('table[id*=projectItemPriceList] tr').find("td:eq(" + index + ")").toggle();
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

        var table = $('#aggregateItemList').DataTable({
            "scrollCollapse": true,
            "paging": false
        });

        //     var table = $("#aggregateItemList").dataTable({"pageLength": 10});

        $("#aggregateItemList_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            $("table[id*=aggregateItemList] th").each(function(index, item) {
                if (columnName == item.innerHTML) {
                    $('table[id*=aggregateItemList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
                }
            })
        });


        $("#aggregateItemList_paginate").on('click', function(e) {
            $("#aggregateItemList_columnUl").find('input:checkbox').each(function(index, item) {
                var columnStatus = $(this).attr('value');
                var columnName = $(this).attr('name');

                if (columnStatus == 'hidden') {
                    $("table[id*=aggregateItemList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#aggregateItemList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType != 'none') {
                                $('table[id*=aggregateItemList] tr').find("td:eq(" + index + ")").toggle();
                            }
                        }
                    })
                } else {
                    $("table[id*=aggregateItemList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#aggregateItemList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType == 'none') {
                                $('table[id*=aggregateItemList] tr').find("td:eq(" + index + ")").toggle();
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

        var table = $('#compareDescList').DataTable({
            "scrollCollapse": true,
            "paging": false
        });

        //     var table = $("#compareDescList").dataTable({"pageLength": 10});

        $("#compareDescList_columnUl").on('change', 'input:checkbox', function(e) {
            var columnName = $(this).attr('name');

            var isColumnStatusChecked = $(this).is(":checked")
            if (!isColumnStatusChecked) {
                $(this).attr('value', 'hidden');
            } else {
                $(this).attr('value', '');
            }
            $("table[id*=compareDescList] th").each(function(index, item) {
                if (columnName == item.innerHTML) {
                    $('table[id*=compareDescList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
                }
            })
        });


        $("#compareDescList_paginate").on('click', function(e) {
            $("#compareDescList_columnUl").find('input:checkbox').each(function(index, item) {
                var columnStatus = $(this).attr('value');
                var columnName = $(this).attr('name');

                if (columnStatus == 'hidden') {
                    $("table[id*=compareDescList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#compareDescList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType != 'none') {
                                $('table[id*=compareDescList] tr').find("td:eq(" + index + ")").toggle();
                            }
                        }
                    })
                } else {
                    $("table[id*=compareDescList] th").each(function(index, item) {
                        if (columnName == item.innerHTML) {
                            var displayType = $('#compareDescList tr').find("td:eq(" + index + ")").css('display');
                            if (displayType == 'none') {
                                $('table[id*=compareDescList] tr').find("td:eq(" + index + ")").toggle();
                            }
                        }
                    })
                }
            });
        });
    });
</script>