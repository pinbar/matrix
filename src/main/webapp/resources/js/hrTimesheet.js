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
            _setupAddCostCodeRowAction();
            _setupSaveTimesheetAction();
        });
    },

    _setupAddCostCodeRowAction = function() {
        $('#addRow').attr('href', 'javascript:;');
        $('#addRow').on('click', function() {
            _addCostCodeRow();
        });
    },

    _setupSaveTimesheetAction = function() {
        $('#saveTimesheet').attr('type', 'button');
        $('#saveTimesheet').on('click', function() {
            _saveTimesheet();
        });
    },

    _saveTimesheet = function() {
        $
                .ajax({
                    url : contextPath + '/hr/timesheets/save?employee='
                            + employeeId,
                    type : "POST",
                    dataType : "html",
                    data : $("#timesheet").serialize(),
                    success : function(response, textStatus, jqXHR) {
                        var errNode = null;
                        errNode = $.parseHTML(response).filter(
                                function(node, index) {
                                    return node.id == "errorMessages";
                                })[0];

                        var err = errNode ? errNodeerrNode.innerHTML.length > 0
                                : false;

                        if (err) {
                            $("#errorMessages").show();
                            $(".modal-body").html(response);
                            _setupAddCostCodeRowAction();
                            _setupSaveTimesheetAction();
                        } else {
                            $('#timesheetModal').modal('hide');
                        }
                    },
                    // TODO : error styling and error stuff
                    error : function(response, textStatus, jqXHR) {
                        $(".modal-body").html(response);
                        _setupAddCostCodeRowAction();
                        _setupSaveTimesheetAction();
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
                _setupAddCostCodeRowAction();
                _setupSaveTimesheetAction();
            },
            // TODO : error styling and error stuff
            error : function(response, textStatus, jqXHR) {
                $(".modal-body").html(response);
                _setupAddCostCodeRowAction();
                _setupSaveTimesheetAction();
            }
        });
    }, _onTimeSheetEdit = function(e) {
        row = $(e.target).closest('tr');
        id = $(row).data('id');
        status = $(row).data('status');
        employeeId = $(row).data('employeeid');

        $('#timesheetModal').modal(
                {
                    'show' : true,
                    'remote' : contextPath + '/hr/timesheets/' + status + '/'
                            + id + '?employee=' + employeeId
                });
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