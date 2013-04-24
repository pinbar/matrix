$(document).ready(
        function() {
            $("a#adminHome").on({
                "click", function(e) {
                    $("div#grpUpdate").addClass('hide');
                    $("div#grpList").addClass('hide');
                    $("div#empUpdate").addClass('hide');
                    $("div#empList").addClass('hide');
                    $("div#costCenterUpdate").addClass('hide');
                    $("div#costCenterList").addClass('hide');
                    $("div#adminMsgs").removeClass('hide');
                }
            });
            $("a#emplist").on({
                "click",  function(e) {
                    $("div#adminMsgs").addClass('hide');
                    $("div#grpUpdate").addClass('hide');
                    $("div#grpList").addClass('hide');
                    $("div#empUpdate").addClass('hide');
                    $("div#costCenterUpdate").addClass('hide');
                    $("div#costCenterList").addClass('hide');
                    $("div#empList").removeClass('hide');
                }
            });
            $("a#empadd").on(
                    {
                        "click" , function(e) {
                            $("div#adminMsgs").addClass('hide');
                            $("div#grpUpdate").addClass('hide');
                            $("div#grpList").addClass('hide');
                            $("div#empList").addClass('hide');
                            $("div#costCenterUpdate").addClass('hide');
                            $("div#costCenterList").addClass('hide');
                            $("div#empUpdate").removeClass('hide');
                            $(".errorMsg").addClass('hide');
                            $("form#employeeForm").find(
                                    "input[type=text], textarea").val("");
                            populateGroupOptions("", true);
                        }
                    });
            $("a#grouplist").on({
                "click",  function(e) {
                    $("div#adminMsgs").addClass('hide');
                    $("div#grpUpdate").addClass('hide');
                    $("div#empList").addClass('hide');
                    $("div#empUpdate").addClass('hide');
                    $("div#costCenterUpdate").addClass('hide');
                    $("div#costCenterList").addClass('hide');
                    $("div#grpList").removeClass('hide');
                }
            });
            $("a#groupadd").on(
                    {
                        "click",  function(e) {
                            $("div#empList").addClass('hide');
                            $("div#adminMsgs").addClass('hide');
                            $("div#grpList").addClass('hide');
                            $("div#empUpdate").addClass('hide');
                            $("div#costCenterUpdate").addClass('hide');
                            $("div#costCenterList").addClass('hide');
                            $("div#grpUpdate").removeClass('hide');
                            $("form#groupForm").find(
                                    "input[type=text], textarea").val("");
                        }
                    });
            $("a#costcenterlist").on({
                "click",  function(e) {
                    $("div#adminMsgs").addClass('hide');
                    $("div#grpList").addClass('hide');
                    $("div#grpUpdate").addClass('hide');
                    $("div#empList").addClass('hide');
                    $("div#empUpdate").addClass('hide');
                    $("div#costCenterUpdate").addClass('hide');
                    $("div#costCenterList").removeClass('hide');
                }
            });
            $("a#costcenteradd").on(
                    {
                        "click",  function(e) {
                            $("div#adminMsgs").addClass('hide');
                            $("div#grpList").addClass('hide');
                            $("div#grpUpdate").addClass('hide');
                            $("div#empList").addClass('hide');
                            $("div#empUpdate").addClass('hide');
                            $("div#costCenterList").addClass('hide');
                            $("div#costCenterUpdate").removeClass('hide');
                            $("form#costCenterForm").find(
                                    "input[type=text], textarea").val("");
                        }
                    });
        });