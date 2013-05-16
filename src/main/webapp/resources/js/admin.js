$(document).ready(
        function() {
            var grpTableArgs = {
                sourceUrl : contextPath + '/admin/group/listAsJson',
                tableId : 'grpListTable',
                filterInputId : 'grpListTable_filter',
                columnConfig : [ {
                    mData : 'id',
                    sTitle : 'Group Id'
                }, {
                    mData : 'name',
                    sTitle : 'Group Name'
                }, {
                    mData : 'authority',
                    sTitle : 'Group Authority'
                } ]
            }, empTableArgs = {
                sourceUrl : contextPath + '/admin/employee/listAsJson',
                tableId : 'empListTable',
                filterInputId : 'empListTable_filter',
                columnConfig : [ {
                    mData : 'id',
                    sTitle : 'Employee Id'
                }, {
                    mData : 'userName',
                    sTitle : 'User Name'
                }, {
                    mData : 'firstName',
                    sTitle : 'First Name'
                }, {
                    mData : 'lastName',
                    sTitle : 'Last Name'
                }, {
                    mData : 'groupName',
                    sTitle : 'Group Name'
                }, {
                    mData : 'active',
                    sTitle : 'Active'
                } ]
            }, costCenterTableArgs = {
                sourceUrl : contextPath + '/admin/costCenter/listAsJson',
                tableId : 'costCenterListTable',
                filterInputId : 'costCenterListTable_filter',
                columnConfig : [ {
                    mData : 'id',
                    sTitle : 'Id'
                }, {
                    mData : 'costCode',
                    sTitle : 'Cost Code'
                }, {
                    mData : 'name',
                    sTitle : 'Name'
                }, {
                    mData : 'clientName',
                    sTitle : 'Client'
                } ]
            }, clientTableArgs = {
                sourceUrl : contextPath + '/admin/client/listAsJson',
                tableId : 'clientListTable',
                filterInputId : 'clientListTable_filter',
                columnConfig : [ {
                    mData : 'id',
                    sTitle : 'Client Id'
                }, {
                    mData : 'name',
                    sTitle : 'Name'
                } ]
            };

            initTable(empTableArgs);
            initTable(grpTableArgs);
            initTable(costCenterTableArgs);
            initTable(clientTableArgs);
            bindEmpResetPassword();
            bindUpdateRows();
            bindDeleteRows();
        });
function bindUpdateRows() {
    $("table")
            .on(
                    "click",
                    ".updateRow",
                    function(e) {
                        var table = $.fn.dataTable.fnTables(true), bindingData = getFormData(
                                e, table, "update"), divToShow = bindingData.div, url = bindingData.url;

                        $
                                .ajax({
                                    url : url,
                                    type : "get",
                                    dataType : "json",
                                    success : function(response, textStatus,
                                            jqXHR) {
                                        $("div#adminMsgs").addClass('hide');
                                        $("div#empList").addClass('hide');
                                        $("div#grpList").addClass('hide');
                                        $("div#costCenterList")
                                                .addClass('hide');
                                        $("div#clientList").addClass('hide');
                                        populate($("div#" + divToShow),
                                                response);
                                        $("div#" + divToShow
                                                + " input[type='submit']").value = "Save";
                                        $("div#" + divToShow).removeClass(
                                                'hide');
                                    },
                                    // TODO : error styling and error stuff
                                    error : function(response, textStatus,
                                            jqXHR) {
                                        alert("error");
                                    }
                                });
                    });
}

function bindDeleteRows() {
    $("table")
            .on(
                    "click",
                    ".deleteRow",
                    function(e) {
                        var table = $.fn.dataTable.fnTables(true), bindingData = getFormData(
                                e, table, "delete"), url = bindingData.url;

                        $.ajax({
                            url : url,
                            type : "POST",
                            dataType : "json",// "html",
                            success : function(response, textStatus, jqXHR) {
                                if (table.length > 0) {
                                    $(table).dataTable().fnReloadAjax();
                                }
                            },
                            // TODO : error styling and error stuff
                            error : function(response, textStatus, jqXHR) {
                                alert("error");
                            }
                        });
                    });
}

function bindEmpResetPassword() {
    $("table")
            .on(
                    "click",
                    ".resetEmp",
                    function(e) {
                        var table = $.fn.dataTable.fnTables(true), row = $(
                                e.target).closest('tr'), data = $(table)
                                .dataTable()._(row), userName = data[0].userName, active = data[0].active, divToShow = 'empResetPassword';
                        $("div#adminMsgs").addClass('hide');
                        $("div#empList").addClass('hide');
                        $("div#grpList").addClass('hide');
                        $("div#costCenterList").addClass('hide');
                        populate($("div#" + 'empResetPassword'), {
                            userName : userName,
                            active : active
                        });
                        $("div#" + divToShow).removeClass('hide');
                    });

}
function bindEmpReset() {
    $("table")
            .on(
                    "click",
                    ".resetEmp",
                    function(e) {
                        var table = $.fn.dataTable.fnTables(true), row = $(
                                e.target).closest('tr'), data = $(table)
                                .dataTable()._(row), userName = data[0].userName, divToShow = 'empReset';
                        $("div#adminMsgs").addClass('hide');
                        $("div#empList").addClass('hide');
                        $("div#grpList").addClass('hide');
                        $("div#costCenterList").addClass('hide');
                        populate($("div#" + 'empReset'), {
                            userName : userName
                        });
                        $("div#" + divToShow).removeClass('hide');
                    });

}

function capitaliseFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function getFormData(e, table, action) {
    var row = $(e.target).closest('tr'), tableId = $(table).attr('id'), data = $(
            table).dataTable()._(row), id = data[0].id, retArgs = {};
    if (tableId.indexOf("grpListTable") >= 0) {
        retArgs['url'] = contextPath + "/admin/group/" + action + "?id=" + id;
        retArgs.div = 'grp' + capitaliseFirstLetter(action);
    } else if (tableId.indexOf("empListTable") >= 0) {
        retArgs['url'] = contextPath + "/admin/employee/" + action + "?id="
                + id;
        retArgs.div = 'emp' + capitaliseFirstLetter(action);
    } else if (tableId.indexOf("costCenterListTable") >= 0) {
        retArgs['url'] = contextPath + "/admin/costCenter/" + action + "?id="
                + id;
        retArgs.div = 'costCenter' + capitaliseFirstLetter(action);
    } else {
        retArgs['url'] = contextPath + "/admin/client/" + action + "?id=" + id;
        retArgs.div = 'client' + capitaliseFirstLetter(action);
    }
    retArgs.row = row;
    return retArgs;
}

function initTable(args) {
    var columns = args.columnConfig;
    columns
            .push({
                bSortable : false,
                mData : null,
                sDefaultContent : '<a class=\"updateRow\" href=\"javascript:;\" > <i class=\"icon-pencil\"></i> </a>'
            });
    columns
            .push({
                bSortable : false,
                mData : null,
                sDefaultContent : '<a class=\"deleteRow\" href=\"javascript:;\"><i class=\"icon-trash\" ></i> </a>'
            });
    if (args.tableId === "empListTable") {
        columns
                .push({
                    bSortable : false,
                    mData : null,
                    sDefaultContent : '<a class=\"resetEmp\" href=\"javascript:;\"><i class=\"icon-lock\" ></i> </a>'
                });
    }

    return $("#" + args.tableId)
            .dataTable(
                    {
                        bLengthChange : false,
                        bPaginate : true,
                        bInfo : false,
                        bFilter : true,
                        // bDestroy: true,
                        aaSorting : [],
                        bServerSide : false,
                        bProcessing : true,
                        sPaginationType : "full_numbers",
                        sAjaxDataProp : "",
                        sAjaxSource : args.sourceUrl,
                        fnInitComplete : function() {
                            $("#" + args.filterInputId + " label").contents()
                                    .filter(function() {
                                        return this.nodeType != 1;
                                    }).replaceWith("");
                            $("#" + args.filterInputId).addClass(
                                    "input-prepend");
                            $("#" + args.filterInputId + " label")
                                    .prepend(
                                            "<span class=\"add-on\"><span class=\"icon-search\"></span></span>");
                        },
                        aoColumns : columns
                    });
}

function populate(container, data) {
    var selectedGroupName = "";
    var selectedClientName = "";
    $.each(data,
            function(key, value) {
                $('[id=' + key + ']', container).val(value);
                var checkBox = $('[type= "checkbox"]' + '[name=' + key + ']',
                        container);
                if (checkBox) // TODO check if value is a list if a list loop
                    // through
                    checkBox.attr('checked', value);
                if (key === "groupName") {
                    selectedGroupName = value;
                }
                if (key === "clientName") {
                    selectedClientName = value;
                }
            });
    if (container.attr("id") == "empUpdate") {
        adminSidebarController.populateOptions({selectedName:selectedGroupName, alwaysShow:true,url:"/admin/group/listAsJson",optionsContainer:"#groupName"});
    }
    if (container.attr("id") == "costCenterUpdate") {
        adminSidebarController.populateOptions({selectedName:"Internal",alwaysShow:true,url:"/admin/client/listAsJson",optionsContainer:"#clientName"});;
    }
}
