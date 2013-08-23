var hrTimesheetController = function() {
    "use strict";
    var actiontoSelectorMap = {
        timeSheetRejectClicked : ".timesheetReject",
        timesheetApproveClicked : ".timesheetApprove",
        timesheetEditClicked : ".timesheetEdit"
    }, row, id, status, employeeId, _init = function() {
        $('#timesheetModal').on('show.bs.modal', function() {
            $("#timesheetModal").find('.modal-dialog').css({
                width : 'auto',
                height : 'auto',
                'max-height' : '100%'
            });
        });
        $('#timesheetModal').on('shown.bs.modal', function() {
            _setupActionControls();
        });
    },

    _setupAddCostCodeRowAction = function() {
        $('#addRow').attr('href', 'javascript:;');
        $('#addRow').off('click', '**');
        $('#addRow').on('click', function() {
            _addCostCodeRow();
        });
    },

    _setupDeleteCostCodeRowAction = function() {
        $('.delCostCodeRow').each(function() {
            $(this).attr('href', 'javascript:;');
            $(this).off('click', '**');
            $(this).on('click', function(e) {
                _deleteCostCodeRow(e);
            });
        });

    },

    _setupSaveTimesheetAction = function() {
        $('#saveTimesheet').attr('type', 'button');
        $('#saveTimesheet').off('click', '**');
        $('#saveTimesheet').on('click', function() {
            _saveTimesheet();
        });
    },

    _deleteCostCodeRow = function(e) {
        var costCode = $(e.target).closest('tr').data('costcode');
        $.ajax({
            url : contextPath + '/hr/timesheets/deleteCostCodeRow?timesheetId='
                    + id + '&employee=' + employeeId + '&costCode=' + costCode,
            type : "GET",
            dataType : "html",
            success : function(response, textStatus, jqXHR) {
                $(".modal-body").html(response);
                _setupActionControls();
                _updateTotalHours();
            },
            // TODO : error styling and error stuff
            error : function(response, textStatus, jqXHR) {
                $(".modal-body").html(response);
                _setupActionControls();
            }
        });

    },

    _clearErrorsMsg = function() {
        $(".error").each(function() {
            $(this).empty();
        });
    },

    _updateTotalHours = function(){
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
            data : $("#timesheet").serialize(),
            success : function(response, textStatus, jqXHR) {
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
                    $(".modal-body").html(response);
                    _updateTotalHours();
                    $('#timesheetModal').modal('hide');
                }
            },
            // TODO : error styling and error stuff
            error : function(response, textStatus, jqXHR) {
                $(".modal-body").html(response);
                _setupActionControls();
            }
        });
    },

    _addCostCodeRow = function() {
        $.ajax({
            url : contextPath + '/hr/timesheets/addCostCodeRow?timesheetId='
                    + id + '&employee=' + employeeId,
            type : "POST",
            // dataType : "html",
            success : function(response, textStatus, jqXHR) {
                $(".modal-body").html(response);
                _setupActionControls();
            },
            // TODO : error styling and error stuff
            error : function(response, textStatus, jqXHR) {
                $(".modal-body").html(response);
                _setupActionControls();
            }
        });
    },

    _onTimeSheetEdit = function(e) {
        row = $(e.target).closest('tr');
        id = $(row).data('id');
        status = $(row).data('status');
        employeeId = $(row).data('employeeid');
        _clearErrorsMsg();
        $('#timesheetModal').modal(
                {
                    'show' : true,
                    'remote' : contextPath + '/hr/timesheets/' + status + '/'
                            + id + '?employee=' + employeeId
                });
    },

    _setupActionControls = function() {
        _setupAddCostCodeRowAction();
        _setupSaveTimesheetAction();
        _setupDeleteCostCodeRowAction();
    };

    return ({
        // timesheetApproveClicked : _onTimeSheetApprove,
        // timeSheetRejectClicked : _onTimeSheetReject,
        timesheetEditClicked : _onTimeSheetEdit,
        actiontoSelectorMap : actiontoSelectorMap,
        init : _init
    });
}();

$(document).ready(function() {
    hrTimesheetController.init();
    $.each(hrTimesheetController.actiontoSelectorMap, function(key, value) {
        $(value).on("click", hrTimesheetController[key]);
    });

});