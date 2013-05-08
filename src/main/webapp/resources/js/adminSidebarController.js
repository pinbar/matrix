var adminSidebarController = function() {
    "use strict";

    var actiontoSelectorMap = {
        adminHomeLinkClicked : "a#adminHome",
        grpListLinkClicked : "a#grouplist",
        grpAddLinkClicked : "a#groupadd",
        empListLinkClicked : "a#emplist",
        empAddLinkClicked : "a#empadd",
        costcenterListLinkClicked : "a#costcenterlist",
        costcenterAddLinkClicked : "a#costcenteradd"
    },

        _divSelectors = [ "div#adminMsgs", "div#grpList", "div#grpUpdate",
            "div#empList", "div#empUpdate", "div#costCenterList",
            "div#costCenterUpdate" ],

        _showHideForms = function(formToShow) {
            $.each(_divSelectors, function(index, data) {
                if (data !== formToShow) {
                    $(data).addClass('hide');
                } else {
                    $(data).removeClass('hide');
                }
            });
        },

        _onCostcenterAdd = function(e) {
        _showHideForms("div#costCenterUpdate");
        $("form#costCenterForm").find("input[type=text], textarea").val("");
    },

        _onCostcenterList = function(e) {
        _showHideForms("div#costCenterList");
    },

        _onEmpAdd = function(e) {
        populateGroupOptions("Employees", true);//set 'employees' by default
        $(".errorMsg").addClass('hide');
        _showHideForms("div#empUpdate");
        $("form#employeeForm").find("input, textarea").not(':button, :submit, :reset').val("");
    },

        _onEmpList = function(e) {
        _showHideForms("div#empList");
    },

        _onGrpAdd = function(e) {
        _showHideForms("div#grpUpdate");
        $("form#groupForm").find("input[type=text], textarea").val("");
    },

        _onGrpList = function(e) {
        _showHideForms("div#grpList");
    },

        _onAdminHome = function(e) {
        _showHideForms("div#adminMsgs");
    },

        populateGroupOptions = function(selectedGroupName, alwaysShow) {
        var isEmpUpdateForm = alwaysShow
                || !($("div#empUpdate").hasClass("hide"));
        if (isEmpUpdateForm) {
            $
                    .ajax({
                        url : contextPath + "/admin/group/listAsJson",
                        type : "GET",
                        dataType : "json",
                        success : function(response, textStatus, jqXHR) {
                            var html = "";
                            $
                                    .each(
                                            response,
                                            function(key, value) {
                                                var grpName = value.name;
                                                html = html
                                                        + "<option value=\""
                                                        + grpName
                                                        + "\""
                                                        + (selectedGroupName
                                                                && grpName === selectedGroupName ? " selected "
                                                                : "") + " >"
                                                        + grpName + "</option>";
                                            });
                            $("#groupName").empty().append(html);
                        },
                        error : function(response, textStatus, jqXHR) {
                            alert("error");
                        }
                    });
        }
    };

    return ({
        costcenterAddLinkClicked : _onCostcenterAdd,
        costcenterListLinkClicked : _onCostcenterList,
        empAddLinkClicked : _onEmpAdd,
        empListLinkClicked : _onEmpList,
        grpAddLinkClicked : _onGrpAdd,
        grpListLinkClicked : _onGrpList,
        adminHomeLinkClicked : _onAdminHome,
        actiontoSelectorMap : actiontoSelectorMap,
        populateGroupOptions : populateGroupOptions
    });
}();

$(document).ready(function() {
    $.each(adminSidebarController.actiontoSelectorMap, function(key, value) {
        $(value).on("click", adminSidebarController[key]);
    });

});
