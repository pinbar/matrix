var hrTimesheetController = function() {
    "use strict";
    var actiontoSelectorMap = {
        timeSheetRejectClicked : ".timesheetReject",
        timesheetApproveClicked : ".timesheetApprove",
        timesheetEditClicked : ".timesheetEdit"
    }, row, id, status, employeeId, dataTable, totalRecords = 0,

    selections = {
        all : false,
        selectionArray : []
    },

    _resetSelections = function() {
        selections.all = false;
        selections.selectionArray.length = 0;
    },

    _setupSelections = function() {
        _resetSelections();
        $('.selectAll').on('click', function(e) {
            $("input:checkbox").prop('checked', $(e.target).prop('checked'));
            if ($(e.target).prop('checked')) {
                selections.all = true;

                // emptying out the selected ids since all are selected
                selections.selectionArray.length = 0;
            }
        });
        $('table').on(
                'click',
                '.selections',
                function(e) {
                    var selectedRow = $(e.target).closest('tr');
                    var data = $(dataTable).dataTable()._(selectedRow);
                    if ($(e.target).prop('checked')) {
                        selections.selectionArray
                                .push(id = data[0].timesheetId);
                        selections.selectionArray = $
                                .unique(selections.selectionArray);
                    } else {
                        if (!selections.all) {
                            selections.selectionArray = $.grep(
                                    selections.selectionArray, function(a) {
                                        return a != data[0].timesheetId;
                                    });
                        } else {
                            selections.selectionArray = [];
                            for ( var i = 0; i < totalRecords; i++) {
                                selections.selectionArray.push(i + 1);
                                if (i + 1 == data[0].timesheetId) {
                                    selections.selectionArray.splice(i, 1);
                                }
                            }
                        }
                    }
                    selections.all = false;

                });
    },

    _setCurrentStatus = function(timesheetStatus) {
        $('.sidebar-nav').find('li').removeClass('active');
        $('.sidebar-nav').find('a#' + timesheetStatus).parent().addClass(
                'active');
        status = timesheetStatus;
    },

    _setupHrTimeSheetTable = function() {
        var tableArgs = {
            sourceUrl : contextPath + '/hr/timesheets/listAsJson/' + status,
            tableId : 'hrTimesheetTable',
            filterInputId : 'hrTimesheetTable_filter',
            columnConfig : [
                    {
                        bSortable : false,
                        mData : null,
                        sDefaultContent : '<input class="selections" type="checkbox">'
                    },
                    {
                        mData : 'timesheetId',
                        sTitle : 'TimeSheet Id',
                        bSearchable : false,
                        bVisible : false
                    },
                    {
                        mData : 'weekEnding',
                        sTitle : 'Week Ending'
                    },
                    {
                        mData : 'employeeName',
                        sTitle : 'Employee'
                    },
                    {
                        mData : 'managerName',
                        sTitle : 'Manager'
                    },
                    {
                        mData : 'status',
                        sTitle : 'Status'
                    },
                    {
                        fnRender : function(oObj) {
                            var hours = oObj.aData.hours;
                            return commonUtils.formatTwoDecimals(hours);
                        },
                        aTargets : [ 4 ],
                        mData : 'hours',
                        sTitle : 'Hours',
                        sClass : 'hours'
                    },
                    {
                        mData : 'warnings',
                        sTitle : 'Warnings',
                        bSearchable : false,
                        bVisible : false
                    },
                    {
                        fnRender : function(oObj) {
                            var warnings = oObj.aData.warnings, retStr = "";
                            if (warnings && warnings.length > 0) {
                                retStr = '<a class="error showWarning" href="javascript:;"><i class="glyphicon glyphicon-info-sign"></i></span>'
                            }
                            return retStr;
                        },
                        aTargets : [ 6 ],
                        bSortable : false,
                        mData : null
                    },
                    {
                        bSortable : false,
                        mData : null,
                        sDefaultContent : '<a class=\"timesheetEdit\" href=\"javascript:;\" > <i class=\"glyphicon glyphicon-pencil\"></i> </a>'
                    },
                    {
                        bSortable : false,
                        mData : null,
                        sDefaultContent : '<a class=\"timesheetApprove\" href=\"javascript:;\" > <i class=\"glyphicon glyphicon-ok\"></i> </a>'
                    },
                    {
                        bSortable : false,
                        mData : null,
                        sDefaultContent : '<a class=\"timesheetReject\" href=\"javascript:;\"><i class=\"glyphicon glyphicon-remove\" ></i> </a>'
                    } ]
        };
        return $("#" + tableArgs.tableId)
                .dataTable(
                        {
                            bLengthChange : false,
                            bPaginate : true,
                            bInfo : false,
                            bFilter : true,
                            // bDestroy: true,
                            aaSorting : [],
                            iDisplayLength : 25,
                            bServerSide : false,
                            bProcessing : true,
                            sPaginationType : "full_numbers",
                            sAjaxDataProp : "timesheets",
                            sAjaxSource : tableArgs.sourceUrl,
                            fnServerData : function(sSource, aoData, fnCallback) {
                                $.getJSON(sSource, aoData, function(json) {
                                    totalRecords = json.iTotalRecords;
                                    fnCallback(json);
                                });
                            },
                            fnRowCallback : function(nRow, aData, iDisplayIndex) {
                                if (aData.warnings && aData.warnings.length > 0) {
                                    $(nRow).addClass('warning');
                                }
                                return nRow;
                            },
                            fnInitComplete : function() {
                                $("#" + tableArgs.filterInputId + " label")
                                        .contents().filter(function() {
                                            return this.nodeType != 1;
                                        }).replaceWith("");
                                $("#" + tableArgs.filterInputId).addClass(
                                        "input-prepend");
                                $("#" + tableArgs.filterInputId + " label")
                                        .append(
                                                "<span class=\"add-on\"> <span class=\"glyphicon glyphicon-search\"></span></span>");
                                $('.rt').width('100%');
                                $('.rt th').width('auto');
                                _setupWarningDialog();
                            },
                            aoColumns : tableArgs.columnConfig
                        });
    },

    _setupGlobalApproveOrReject = function() {
        $('.hrTimesheetControl').on('click', function(e) {
            var action = $(e.target).attr('value').toLowerCase();
            $.ajax({
                url : contextPath + '/hr/timesheets/' + status + '/' + action,
                type : "POST",
                contentType : 'application/json',
                dataType : "html",
                data : JSON.stringify(selections)
            }).done(function(response, textStatus, jqXHR) {
                $('body').html(response);
                _resetSelections();
            }).
            // TODO : error styling and error stuff
            fail(function(response, textStatus, jqXHR) {
                $('body').html(response);
            });
        });
    },

    _empListDialogController = (function() {
        var my = {};

        function onDialogShow() {
            _styleModalDialog(my.selector);
        }

        function fetchDataFromDom() {
            var rawData = $("#employeeBootStrapData").html();
            my.data = $.parseJSON(rawData);
            return (my.data.employees);
        }

        my.getIdFromName = function(name) {
            return my.data.employeesWithId[name];
        };

        my.init = function() {
            var localdata = fetchDataFromDom();
            // var container = $('#dpStart').parent();
            $('#dpStart').on('click', function() {
                $('#dpStart').datepicker({
                    format : "mm-dd-yyyy",
                    autoclose : "true",
                    orientation : "top"
                })
                $('#dpStart').datepicker('show');
            });
            $('#employees-auto').typeahead({
                name : 'employees',
                local : localdata
            });
            $('#employees-auto').on('typeahead:initialized', function() {
                $('#employees-auto').css('vertical-align', "");
            });
        };

        return my;
    })(),

    _setupCreate = function() {
        _empListDialogController.init();
        $(".createButton")
                .on(
                        'click',
                        function() {
                            var weekEnding = $('#dp').val();
                            employeeId = _empListDialogController
                                    .getIdFromName($('#employees-auto').val());
                            if (!weekEnding || !employeeId || weekEnding === ""
                                    || employeeId === "") {
                                return false;
                            }
                            $
                                    .ajax(
                                            {
                                                url : contextPath
                                                        + '/hr/timesheets/create/'
                                                        + weekEnding + '/'
                                                        + employeeId,
                                                type : "POST",
                                                dataType : "html"
                                            })
                                    .done(
                                            function(response, textStatus,
                                                    jqXHR) {
                                                $('#timesheetModal')
                                                        .off('show.bs.modal',
                                                                _onTimeSheetModalDialogShow);
                                                var _onTimeSheetModalDialogShowForCreate = function() {
                                                    $(
                                                            '#timesheetModal .modal-body')
                                                            .html(response);
                                                    _styleModalDialog('#timesheetModal');
                                                    id = $('#id').val(); // while
                                                    // creating
                                                    // the
                                                    // landing
                                                    // page
                                                    // doesn't
                                                    // pass
                                                    // this
                                                    // value
                                                };

                                                $('#timesheetModal')
                                                        .on('show.bs.modal',
                                                                _onTimeSheetModalDialogShowForCreate);
                                                _clearErrorsMsg();
                                                $('#timesheetModal').modal({
                                                    'show' : true
                                                });
                                                $('#timesheetModal')
                                                        .off('show.bs.modal',
                                                                _onTimeSheetModalDialogShowForCreate);
                                                $('#timesheetModal')
                                                        .on('show.bs.modal',
                                                                _onTimeSheetModalDialogShow);
                                                _setupActionControls();
                                            }).
                                    // TODO : error styling and error stuff
                                    fail(function(response, textStatus, jqXHR) {
                                        // TODO error
                                    });
                        })
    },

    _styleModalDialog = function(selector) {
        $(selector).find('.modal-dialog').css({
            width : '780px',
            height : 'auto',
            'max-height' : '100%'
        });
    },

    _onTimeSheetModalDialogShow = function() {
        var modalBody = $('#timesheetModal .modal-body');
        modalBody.load(contextPath + '/hr/timesheets/' + status + '/' + id
                + '?employee=' + employeeId);
        _styleModalDialog('#timesheetModal');
    },

    _setupWarningDialog = function() {
        $('.showWarning')
                .on(
                        'click',
                        function(e) {
                            var row = $(e.target).closest('tr'), data = dataTable
                                    ._(row), warnings = data[0].warnings,

                            warningsToggle = function() {
                                var removePopover = function(e) {
                                    $('div.popover.in').each(
                                            function() {
                                                $(this).parent().off('click',
                                                        attachStopPropogation);
                                                $(this).remove();
                                                $(document).off('click',
                                                        removePopover);
                                            });
                                };
                                var attachStopPropogation = function(e) {
                                    if ($('div.popover.in').length > 0) {
                                        e.stopPropagation();
                                    }
                                };
                                $(document).on('click', removePopover);
                                $('div.popover.in').parent().on('click',
                                        attachStopPropogation);
                            };

                            $(e.target).off('shown.bs.popover', warningsToggle);
                            $(e.target).on('shown.bs.popover', warningsToggle);

                            if ($(e.target).closest('td')
                                    .find('div.popover.in').length > 0) {
                                $(e.target).popover('destroy');
                            } else if ($(e.target).closest('td').find(
                                    'div.popover').length > 0) {

                                $(e.target).popover('show');
                                $(e.target).closest('td')
                                        .find('div.popover.in').removeClass(
                                                'hide');
                            } else {
                                $(e.target)
                                        .popover(
                                                {
                                                    title : "<i class=\"glyphicon glyphicon-info-sign\"></i><strong> Warnings</strong>",
                                                    content : function() {
                                                        var html = '<ul>';
                                                        $
                                                                .each(
                                                                        warnings,
                                                                        function(
                                                                                i) {
                                                                            html = html
                                                                                    + '<li>'
                                                                                    + warnings[i]
                                                                                    + '</li>';
                                                                        });
                                                        html = html + '</ul>';
                                                        return html;
                                                    },
                                                    trigger : 'manual',
                                                    html : true,
                                                    container : $(e.target)
                                                            .closest('td')
                                                }).popover('show');
                            }
                            e.stopPropagation();
                        });

    },

    _init = function(currentStatus) {
        _setCurrentStatus(currentStatus);
        dataTable = _setupHrTimeSheetTable();
        _setupSelections();
        _setupGlobalApproveOrReject();
        _setupCreate();

        $('#timesheetModal').on('show.bs.modal', _onTimeSheetModalDialogShow);
        $('#timesheetModal').on('shown.bs.modal', function() {
            _setupActionControls();
        });
        $('#timesheetModal').on('hide.bs.modal', function() {
            window.location.href = contextPath + '/hr/timesheets/' + status;
        });

    },

    _setupAddCostCodeRowAction = function() {
        $('#addRow').attr('href', 'javascript:;'); // the outer <a tag
        $('#addRow').off('click');
        $('#addRow').on('click', function() {
            _addCostCodeRow();
        });
    },

    _setupDeleteCostCodeRowAction = function() {
        $('.delCostCodeRow').each(function() {
            $(this).attr('href', 'javascript:;');
            $(this).off('click');
            $(this).on('click', function(e) {
                _deleteCostCodeRow(e);
            });
        });

    },

    _setupSubmitTimesheetAction = function() {
        $('.submitTimesheet').off('click');
        $('.submitTimesheet').on('click', function(e) {
            _submitTimesheet(e);
        });
    },

    _setupActivateTimesheetAction = function() {
        $('#activateTimesheet').off('click');
        $('#activateTimesheet').on('click', function() {
            _activateTimesheet();
        });
    },

    _doFail = function(response) {
        $(".modal-body").html(response);
        _setupActionControls();

    },

    _deleteCostCodeRow = function(e) {
        var costCode = $(e.target).closest('tr').data('costcode');
        $.ajax(
                {
                    url : contextPath
                            + '/hr/timesheets/deleteCostCodeRow?timesheetId='
                            + id + '&employee=' + employeeId + '&costCode='
                            + costCode,
                    type : "GET",
                    dataType : "html"
                }).done(function(response, textStatus, jqXHR) {
            $(".modal-body").html(response);
            _setupActionControls();
        }).
        // TODO : error styling and error stuff
        fail(function(response, textStatus, jqXHR) {
            _doFail(response);
        });

    },

    _clearErrorsMsg = function() {
        $(".error").each(function() {
            $(this).empty();
        });
    },

    _submitTimesheet = function(e) {
        var urlFragment = $(e.target).val() === 'Save' ? '/hr/timesheets/save?employee='
                : '/hr/timesheets/submit?employee=';
        $.ajax({
            url : contextPath + urlFragment + employeeId,
            type : "POST",
            dataType : "html",
            data : $("#timesheet").serialize()
        }).done(
                function(response, textStatus, jqXHR) {
                    var statusFrmResponse = $('input[id=status]', response)
                            .val();
                    var err = $('input[id=errorMessages]', response).val();

                    status = statusFrmResponse ? statusFrmResponse
                            .toLowerCase() : status;
                    if (err) {
                        $("#errorMessages").show();
                        $(".modal-body").html(response);
                        _setupActionControls();
                    } else {
                        $('#timesheetModal').modal('hide');
                    }
                    ;
                }).
        // TODO : error styling and error stuff
        fail(function(response, textStatus, jqXHR) {
            _doFail(response);
        });
    },

    _activateTimesheet = function() {
        $.ajax(
                {
                    url : contextPath + '/hr/timesheets/activate?&employee='
                            + employeeId + '&status=' + status,
                    type : "POST",
                    dataType : "html",
                    data : $("form#timesheet").find("input, select ,textarea")
                            .removeAttr("disabled", "disabled").serialize()
                }).done(function(response, textStatus, jqXHR) {

            var errNode = null;
            errNode = $.parseHTML(response).filter(function(node, index) {
                return node.id == "errorMessages";
            })[0];

            var err = errNode ? errNode.innerHTML.length > 0 : false;
            $(".modal-body").html(response);
            _setupActionControls();

            if (err) {
                $("#errorMessages").show();
                timesheetContentController.disableTimesheet();
            } else {
                status = "pending";
            }
        })
        // TODO : error styling and error stuff
        .fail(function(response, textStatus, jqXHR) {
            _doFail(response);
        });
    },

    _addCostCodeRow = function() {
        $.ajax(
                {
                    url : contextPath
                            + '/hr/timesheets/addCostCodeRow?timesheetId=' + id
                            + '&employee=' + employeeId,
                    type : "POST",
                    dataType : "html"
                }).done(function(response, textStatus, jqXHR) {
            $(".modal-body").html(response);
            _setupActionControls();
        }).
        // TODO : error styling and error stuff
        fail(function(response, textStatus, jqXHR) {
            _doFail(response);
        });
    },

    _onTimeSheetEdit = function(e) {
        row = $(e.target).closest('tr');
        var data = $(dataTable).dataTable()._(row);

        id = data[0].timesheetId;
        employeeId = data[0].employeeId;
        _clearErrorsMsg();
        $('#timesheetModal').modal({
            'show' : true
        });
    },

    _onTimeSheetApproveReject = function(e) {
        row = $(e.target).closest('tr');
        var data = $(dataTable).dataTable()._(row);
        var action = $(e.target).closest('a').attr('class').replace(
                'timesheet', '').toLowerCase();

        id = data[0].timesheetId;
        employeeId = data[0].employeeId;
        _clearErrorsMsg();
        $.get(
                contextPath + '/hr/timesheets/' + status + '/' + id + '/'
                        + action).done(function(data) {
            $('body').html(data);
        });
    },

    _setupActionControls = function() {
        timesheetContentController.init();
        timesheetContentController.setUpActivateTimesheet();
        timesheetContentController.disableTimesheet();
        _setupAddCostCodeRowAction();
        _setupSubmitTimesheetAction();
        _setupDeleteCostCodeRowAction();
        _setupActivateTimesheetAction();
    };

    return ({
        setCurrentStatus : _setCurrentStatus,
        timesheetApproveClicked : _onTimeSheetApproveReject,
        timeSheetRejectClicked : _onTimeSheetApproveReject,
        timesheetEditClicked : _onTimeSheetEdit,
        actiontoSelectorMap : actiontoSelectorMap,
        totalRecords : totalRecords,
        init : _init
    });
}();

var hrTimeSheetSideBarController = function() {
    "use strict";
    var actiontoLinkSelectorMap = {
        pending : "a#pending",
        submitted : "a#submitted",
        accepted : "a#accepted",
        rejected : "a#rejected"
    }, currentStatus,

    _getCurrentStatus = function() {
        return currentStatus;
    },

    _init = function() {
        var status = $(location).attr('pathname').replace(
                contextPath + '/hr/timesheets', '');
        currentStatus = $.trim(status) === '' ? 'pending' : $.trim(status
                .replace('/', ""));
    };

    return ({
        actiontoLinkSelectorMap : actiontoLinkSelectorMap,
        getCurrentStatus : _getCurrentStatus,
        init : _init
    });
}();

$(document).ready(
        function() {
            hrTimeSheetSideBarController.init();
            hrTimesheetController.init(hrTimeSheetSideBarController
                    .getCurrentStatus());
            $.each(hrTimeSheetSideBarController.actiontoLinkSelectorMap,
                    function(key, value) {
                        $(value).on("click", function(e) {
                            hrTimeSheetSideBarController.currentStatus = key;
                            hrTimesheetController.setCurrentStatus(key);
                        });
                    });
            $.each(hrTimesheetController.actiontoSelectorMap, function(key,
                    value) {
                $('table').on('click', value, hrTimesheetController[key]);
            });

        });