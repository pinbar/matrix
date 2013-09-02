var hrTimesheetController = function() {
    "use strict";
    var actiontoSelectorMap = {
        timeSheetRejectClicked : ".timesheetReject",
        timesheetApproveClicked : ".timesheetApprove",
        timesheetEditClicked : ".timesheetEdit"
    }, row, id, status, employeeId, statusChanged, dataTable,

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
                    selections.all = false;
                    if ($(e.target).prop('checked')) {
                        selections.selectionArray
                                .push(id = data[0].timesheetId);
                        selections.selectionArray = $
                                .unique(selections.selectionArray);
                    } else {
                        selections.selectionArray = $.grep(
                                selections.selectionArray, function(a) {
                                    return a != data[0].timesheetId;
                                });

                    }

                });
    },

    _setCurrentStatus = function(timesheetStatus) {
        $('.sidebar-nav').find('a').removeClass('active');
        $('.sidebar-nav').find('a#' + timesheetStatus).addClass('active');
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
                        mData : 'status',
                        sTitle : 'Status'
                    },
                    {
                        mData : 'hours',
                        sTitle : 'Hours',
                        sClass : 'hours'
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
                            bServerSide : false,
                            bProcessing : true,
                            sPaginationType : "full_numbers",
                            sAjaxDataProp : "",
                            sAjaxSource : tableArgs.sourceUrl,
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

    _init = function(currentStatus) {
        _setCurrentStatus(currentStatus);
        dataTable = _setupHrTimeSheetTable();
        _setupSelections();
        _setupGlobalApproveOrReject();

        $('#timesheetModal').on(
                'show.bs.modal',
                function() {
                    var modalBody = $('#timesheetModal .modal-body');
                    modalBody.load(contextPath + '/hr/timesheets/' + status
                            + '/' + id + '?employee=' + employeeId);
                    $("#timesheetModal").find('.modal-dialog').css({
                        width : '780px',
                        height : 'auto',
                        'max-height' : '100%'
                    });
                });
        $('#timesheetModal').on('shown.bs.modal', function() {
            _setupActionControls();
        });
        $('#timesheetModal').on('hide.bs.modal', function() {
            if (statusChanged) {
                $('body').load(contextPath + '/hr/timesheets/' + status);
            }
        });

    },

    _setupAddCostCodeRowAction = function() {
        $('#addRow').attr('href', 'javascript:;');
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

    _setupSaveTimesheetAction = function() {
        $('#saveTimesheet').attr('type', 'button');
        $('#saveTimesheet').off('click');
        $('#saveTimesheet').on('click', function() {
            _saveTimesheet();
        });
    },

    _setupSubmitTimesheetAction = function() {
        $('#submitTimesheet').off('click');
        $('#submitTimesheet').on('click', function() {
            _submitTimesheet();
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
            _updateTotalHours();
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

    _updateTotalHours = function() {
        var hours = 0.00;
        $('.timesheetHours').each(function(i) {
            hours = hours + parseFloat(this.value);
        });
        $(row).find('.hours').text(hours.toFixed(2));
    },

    _saveTimesheet = function() {
        $.ajax({
            url : contextPath + '/hr/timesheets/save?employee=' + employeeId,
            type : "POST",
            dataType : "html",
            data : $("#timesheet").serialize()
        }).done(function(response, textStatus, jqXHR) {
            var errNode = null;
            errNode = $.parseHTML(response).filter(function(node, index) {
                return node.id == "errorMessages";
            })[0];

            var err = errNode ? errNode.innerHTML.length > 0 : false;

            if (err) {
                $("#errorMessages").show();
                $(".modal-body").html(response);
                _setupActionControls();
            } else {
                $(".modal-body").html(response); // do this line or this
                // +2
                _updateTotalHours();
                $('#timesheetModal').modal('hide');
            }
        }).
        // TODO : error styling and error stuff
        fail(function(response, textStatus, jqXHR) {
            $(".modal-body").html(response);
            _setupActionControls();
        });

    },

    _submitTimesheet = function() {
        $.ajax({
            url : contextPath + '/hr/timesheets/submit?employee=' + employeeId,
            type : "POST",
            dataType : "html",
            data : $("#timesheet").serialize()
        }).done(function(response, textStatus, jqXHR) {
            var errNode = null;
            errNode = $.parseHTML(response).filter(function(node, index) {
                return node.id == "errorMessages";
            })[0];

            var err = errNode ? errNode.innerHTML.length > 0 : false;

            if (err) {
                $("#errorMessages").show();
                $(".modal-body").html(response);
                _setupActionControls();
            } else {
                // $(".modal-body").html(response);
                // $('body').load(contextPath + '/hr/timesheets/' + status);
                statusChanged = true;
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
            $(".modal-body").html(response);
            statusChanged = true;
            _setupActionControls();
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
            'show' : true,
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
        _setupAddCostCodeRowAction();
        _setupSaveTimesheetAction();
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