/*
 * This code controls the interaction in the add costcode panel of employee 
 * edit/add  
 * 
 */
var empCostCodeController = function() {
    var tableId = "empCostCodeListTable", tableNode = $("#" + tableId),
    costCenterurl ="/admin/costCenter/listAsJson" ,
    getHtmlTemplate = function() {
        var id = getAppendId(), html = "<tr data-id=\""
                + id
                + "\"id=\"costCodeListTr_"
                + id
                + "\" ><td><select class=\"tableInput ccSelect\" id =\"costCodeListSelect_"
                + id
                + "\"></select></td><td><input class=\"dp tableInput\" type=\"text\" id=\"startDate_"
                + id
                + "\"></td>"
                + "<td><input class=\"dp tableInput\" type=\"text\" id=\"endDate_"
                + id
                + "\"></td>"
                + "<td><a class=\"saveCostCodeItem\" href=\"javascript:;\"><i class=\"icon-upload\" ></i> </a></td>"
                + "<td><a class=\"delCostCodeItem\" href=\"javascript:;\"><i class=\"icon-trash\" ></i> </a></td></tr>";
        return html;
    }, getAppendId = function() {
        var lastTr = $("#empCostCodeListTable tbody>tr:last"), id = (lastTr.length === 0) ? 0
                : parseInt(lastTr.attr('data-id'), 10) + 1;
        return id;
    }, getAllCostCodeForEmp = function(e) {
        var employeeId = $('#employeeForm #id').val(), url = contextPath+"/admin/employee/costCenters/listAsJson?employeeId="
                + employeeId;
        $.ajax({
            url : url,
            type : "GET",
            dataType : "json",
            contentType : 'application/json',
            success : function(response, textStatus, jqXHR) {
                populateTable(response, true);
                disableUploadControls();
            },
            error : function(response, textStatus, jqXHR) {
                alert("error");
            }
        });
    }, delCostCodeItem = function(e) {
        var postData = [], rowData = {}, row = $(e.target).closest('tr'), id = row
                .attr('data-id'), url = contextPath+"/admin/employee/costCenters/delete";
        rowData.costCode = row.find('select').find('option:selected').val();
        rowData.startDate = $('#startDate_' + id).val();
        rowData.endDate = $('#endDate_' + id).val();
        rowData.employeeId = $('#id').val();
        postData.push(rowData);
        $.ajax({
            url : url,
            type : "POST",
            dataType : "json",
            data : JSON.stringify(postData),
            contentType : 'application/json',
            success : function(response, textStatus, jqXHR) {
                deleteRow(row);
            },
            error : function(response, textStatus, jqXHR) {
                alert("error");
            }
        });
    }, deleteRow = function(row) {
        if (row) {
            row.remove();
        } else {
            tableNode.find('tbody').empty();
        }
    }, saveCostCodeItem = function(e) {
        var row = $(e.target).closest('tr'), id = row.attr('data-id'), postArgs = [], postArg = {}, url = contextPath+"/admin/employee/costCenters/save";
        postArg.costCode = row.find('select').find('option:selected').val();
        postArg.startDate = $('#startDate_' + id).val();
        postArg.endDate = $('#endDate_' + id).val();
        postArg.employeeId = $('#id').val();
        postArgs.push(postArg);
        $.ajax({
            url : url,
            type : "POST",
            dataType : "json",
            contentType : 'application/json',
            data : JSON.stringify(postArgs),
            success : function(response, textStatus, jqXHR) {
                disableUploadControls(row);
            },
            error : function(response, textStatus, jqXHR) {
                alert("error");
            }
        });
    },

    saveTable = function(e) {
        var postData = [], url =contextPath+"/admin/employee/costCenters/save";
        tableNode.find('tbody>tr').each(
                function() {
                    var row = {}, id = this.getAttribute('data-id');
                    row.costCode = $(this).find('select').find(
                            'option:selected').val();
                    row.startDate = $('#startDate_' + id).val();
                    row.endDate = $('#endDate_' + id).val();
                    row.employeeId = $('#id').val();
                    postData.push(row);
                });
        $.ajax({
            url : url,
            type : "POST",
            dataType : "json",
            data : JSON.stringify(postData),
            contentType : 'application/json',
            success : function(response, textStatus, jqXHR) {
                disableUploadControls();
            },
            error : function(response, textStatus, jqXHR) {
                alert("error");
            }
        });
    }, appendEmptyLastRow = function() {
        var id = getAppendId();
        tableNode.append(getHtmlTemplate());
        populateOptions({
            selectedName : "HOL",
            alwaysShow : true,
            url : costCenterurl,
            optionsContainer : "#costCodeListSelect_" + id,
            id : id
        });
        $("#costCodeListTr_" + id).find(".dp").datepicker({
            format : 'mm-dd-yyyy'
        });
        var saveAllBtn = $('#saveTable');
        if (saveAllBtn.attr('class').indexOf('disabled') >= 0) {
            saveAllBtn.on('click', function(e) {
                empCostCodeController.saveTable(e);
            });
            saveAllBtn.removeClass("disabled");
        }
    }, disableUploadControls = function(row) {
        if (row) {
            var control = row.find(".saveCostCodeItem");
            if (control) {
                control.removeClass("saveCostCodeItem")
                control.empty();
                control.html("<i class=\"icon-ok\" ></i> ");
            }
        } else {
            $(".saveCostCodeItem").each(function(index, item) {
                var control = $(item);
                control.removeClass("saveCostCodeItem")
                control.empty();
                control.html("<i class=\"icon-ok\" ></i> ");
            });
        }
        // all have been saved to the server
        if ($(".icon-upload").length == 0) {
            $('#saveTable').off('click');
            $('#saveTable').addClass('disabled')
        }
    }, enableUploadControls = function(row) {
        if (row) {
            var control = $(row.find("a")[0]);
            if (control) {
                control.addClass("saveCostCodeItem")
                control.empty();
                control.html("<i class=\"icon-upload\" ></i> ");
            }
        } else {
            $(".icon-ok").each(function(index, item) {
                var control = $(item).closest('a');
                control.addClass("saveCostCodeItem")
                control.empty();
                control.html("<i class=\"icon-upload\" ></i> ");
            });
        }
        // all have not been saved to the server
        if ($(".icon-upload").length > 0) {
            $('#saveTable').on('click', function(e) {
                saveTable(e);
            });
            $('#saveTable').removeClass('disabled')
        }
    }, initTable = function(args) {
        if (tableNode) {
            appendEmptyLastRow();
        }
    }, populateTable = function(response, rerender) {
        if (rerender) {
            // send the rerender flag if the table needs to be redrawn
            tableNode.find('tbody').empty();
        }
        $.each(response, function(key, value) {
            var id = getAppendId();
            tableNode.append(getHtmlTemplate());
            populateOptions({
                selectedName : value.costCode,
                alwaysShow : true,
                url : costCenterurl,
                optionsContainer : "#costCodeListSelect_" + id,
                id : id
            });
            $('[id= startDate_' + id + ']', tableNode).val(value.startDate);
            $('[id= endDate_' + id + ']', tableNode).val(value.endDate);
            $("#costCodeListTr_" + id).find(".dp").datepicker({
                format : 'mm-dd-yyyy'
            });
        });

    }, populateOptions = function(args) {
        var selectedName = args.selectedName, selectNode = $(args.optionsContainer), optionsHtml, optionsContainer = args.optionsContainer;
        $(".ccSelect").each(function(id, node) {
            if (node.innerHTML) {
                optionsHtml = node.innerHTML;
                return (false);
            }
        })
        if (optionsHtml) {
            if (selectNode.length === 0) {
                var selectHtml = "<select id=\"costCodeListSelect_" + args.id
                        + "\"></select>";
                $("#empCostCodeListTable tbody>tr:last").find('td')[0].epmty()
                        .append(selectHtml);
                optionsContainer = "costCodeListSelect_" + args.id;
            }
            $(optionsContainer).empty().append(optionsHtml);
            $(optionsContainer).find("option").filter(function() {
                return $(this).val() == selectedName;
            }).attr("selected", "selected");
        } else {
            // create this only once via ajax
            adminSidebarController.populateOptions(args);
        }
    };

    return ({
        initTable : initTable,
        addRow : appendEmptyLastRow,
        populateOptions : populateOptions,
        populateTable : populateTable,
        getAllCostCodeForEmp : getAllCostCodeForEmp,
        saveCostCodeItem : saveCostCodeItem,
        delCostCodeItem : delCostCodeItem,
        saveTable : saveTable,
        enableUploadControls:enableUploadControls, 
        tableNode : tableNode
    });
}();

$(document)
        .ready(
                function() {
                   var  tableNode = empCostCodeController.tableNode;
                    $(function() {
                        $('#addProjectRow').click(function(e) {
                            empCostCodeController.addRow();
                        });
                        $('#projectsToggle')
                                .click(
                                        function(e) {
                                            if (!$("#id").val()) {
                                                $("#errorMessages")
                                                        .empty()
                                                        .append(
                                                                'You must save the employee before adding details.')
                                                        .removeClass('hide');
                                                return;
                                            }
                                            $("div#projects")
                                                    .collapse("toggle");
                                            // if shown fetch data
                                            if ($('div#projects').attr('class')
                                                    .indexOf('in') >= 0) {
                                               
                                                    empCostCodeController
                                                            .getAllCostCodeForEmp(e);
                                               
                                            }
                                        });
                        tableNode.on("click", ".saveCostCodeItem", function(e) {
                            empCostCodeController.saveCostCodeItem(e);
                        });
                        tableNode.on("click", ".delCostCodeItem", function(e) {
                            empCostCodeController.delCostCodeItem(e);
                        });
                        $('#saveTable').on("click", function(e) {
                            empCostCodeController.saveTable(e);
                        });
                       tableNode.on ("change", ".tableInput",function(e){
                           empCostCodeController.enableUploadControls($(e.target).closest('tr'))
                           });    
                       
                    });
                });