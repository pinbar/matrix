var adminSidebarController = function() {

    var actiontoSelectorMap = {
        costcenterAddLinkClicked : "a#adminHome",
        grpListLinkClicked : "a#grouplist",
        grpAddLinkClicked : "a#groupadd",
        empListLinkClicked : "a#emplist",
        empAddLinkClicked : "a#empadd",
        costcenterListLinkClicked : "a#costcenterlist",
        costcenterAddLinkClicked : "a#costcenteradd"
    };

    var divSelectors = [ "div#adminMsgs", "div#grpList", "div#grpUpdate",
            "div#empList", "div#empUpdate", "div#costCenterList",
            "div#costCenterUpdate" ];

    var _showHideForms = function(formToShow) {
        $.each(divSelectors, function(index, data) {
            if (data !== formToShow) {
                $(data).addClass('hide');
            } else {
                $(data).removeClass('hide');
            }
        });
    };

    var _onCostcenterAdd = function(e) {
        _showHideForms("div#costCenterUpdate");
        $("form#costCenterForm").find("input[type=text], textarea").val("");
    };

    var _onCostcenterList = function(e) {
        _showHideForms("div#costCenterList");
    };

    var _onEmpAdd = function(e) {
        populateGroupOptions("", true);
        $(".errorMsg").addClass('hide');
        _showHideForms("div#empUpdate");
        $("form#employeeForm").find("input[type=text], textarea").val("");
    };

    var _onEmpList = function(e) {
        _showHideForms("div#empList");
    };

    var _onGrpAdd = function(e) {
        _showHideForms("div#grpUpdate");
        $("form#groupForm").find("input[type=text], textarea").val("");
    };

    var _onGrpList = function(e) {
        _showHideForms("div#grpList");
    };

    var _onAdminHome = function(e) {
        _showHideForms("div#adminMsgs");
    };

    var populateGroupOptions = function(selectedGroupName, alwaysShow) {
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
