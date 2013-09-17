var timesheetLandingController = function() {
    "use strict";
    var dataTable, totalRecords,

    _init = function() {
        dataTable = _setupSummaryTable();
        _bindEditTimeSheet();
        _initializeDatePicker();
    },

    _initializeDatePicker = function() {
        $('#dpStart').on('click', function() {
            $('#dpStart').datepicker({
                format : "mm-dd-yyyy",
                autoclose : "true",
                orientation : "top"
            })
             $('#dpStart').datepicker('show');
        });
        $('.createButton').on(
                'click',
                function() {
                    var tsCreateDate = $('#dp').val();
                    window.location = contextPath + "/timesheet/weekendings/"
                            + tsCreateDate;
                });
    },

    _bindEditTimeSheet = function() {

        $(dataTable).on(
                "click",
                ".updateRow",
                function(e) {
                    var row = $(e.target).closest('tr'),

                    data = $(dataTable).dataTable()._(row),

                    weekEnding = data[0].weekEnding,

                    id = data[0].id;

                    id != null ? window.location = contextPath + '/timesheet/'
                            + id : window.location = contextPath
                            + '/timesheet/weekendings/' + weekEnding;
                });
    },

    _attachFilterComponent = function(tableArgs) {
        $("#" + tableArgs.filterInputId + " label").contents().filter(
                function() {
                    return this.nodeType != 1;
                }).replaceWith("");
        $("#" + tableArgs.filterInputId).addClass("input-prepend");
        $("#" + tableArgs.filterInputId + " label")
                .append(
                        "<span class=\"add-on\"> <span class=\"glyphicon glyphicon-search\"></span></span>");
    },

    _overrideTableClassesToMakeResponsive = function() {
        $('.rt').width('100%');
        $('.rt th').width('auto');
    },

    _colorCodeRowsPerStatus = function(aData, nRow) {
        if (aData.status) {
            if (aData.status.toUpperCase() === "APPROVED") {
                $(nRow).addClass('success');
            } else if (aData.status.toUpperCase() === "REJECTED") {
                $(nRow).addClass('danger');
            } else if (aData.status.toUpperCase() === "SUBMITTED") {
                $(nRow).addClass('warning');
            }
        }
        return nRow;

    },

    _setupSummaryTable = function() {
        var tableArgs = {
            sourceUrl : contextPath + '/timesheet/listAsJson',
            tableId : 'timesheetSummaryTable',
            filterInputId : 'timesheetSummaryTable_filter',
            columnConfig : [
                    {
                        mData : 'id',
                        sTitle : 'Id',
                        bSearchable : false,
                        bVisible : false
                    },
                    {
                        mData : 'weekEnding',
                        sTitle : 'Week Ending'
                    },
                    {
                        mData : 'status',
                        sTitle : 'Status'
                    },
                    {
                        fnRender : function(oObj) {
                            var hours = oObj.aData.regularHours;
                            return commonUtils.formatTwoDecimals(hours);
                        },
                        aTargets : [3],
                        mData : 'regularHours',
                        sTitle : 'Regular Hours',
                    },
                    {
                        "fnRender" : function(oObj) {
                            var hours = oObj.aData.overTimeHours;
                            return commonUtils.formatTwoDecimals(hours);
                        },
                        aTargets : [4],
                        mData : 'overTimeHours',
                        sTitle : 'OverTime Hours',
                    },
                    {
                        "fnRender": function ( oObj ) {
                            var hours = oObj.aData.ptoHours;
                            return commonUtils.formatTwoDecimals(hours);
                        },
                        aTargets: [5],
                        mData : 'ptoHours',
                        sTitle : 'PTO Hours',
                    },
                    {
                        "fnRender" : function(oObj) {
                            var hours = oObj.aData.totalHours;
                            return commonUtils.formatTwoDecimals(hours);
                        },
                        aTargets : [6],
                        mData : 'totalHours',
                        sTitle : 'Total Hours',
                        sClass : 'hours'
                    },
                    {
                        bSortable : false,
                        mData : null,
                        sDefaultContent : '<a class=\"updateRow\" href=\"javascript:;\" > <i class=\"glyphicon glyphicon-pencil\"></i> </a>'
                    } ]
        };
        return $("#" + tableArgs.tableId).dataTable({
            bLengthChange : false,
            bPaginate : true,
            bInfo : false,
            bFilter : true,
            // bDestroy: true,
            aaSorting : [ [ 1, 'desc' ] ],
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
                return _colorCodeRowsPerStatus(aData, nRow);
            },
            fnInitComplete : function() {
                _attachFilterComponent(tableArgs);
                _overrideTableClassesToMakeResponsive();
            },
            aoColumns : tableArgs.columnConfig
        });
    };
    return ({
        init : _init
    });
}();
$(document).ready(function() {
    timesheetLandingController.init();
    // $('.rt th').width('100%');
    $('.rt').width('auto');
});