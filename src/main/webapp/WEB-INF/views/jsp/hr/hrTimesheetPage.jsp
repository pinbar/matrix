<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Dashboard</title>
<jsp:include page="../common/commonIncludes.jsp" />
<jsp:include page="../common/attachmentIncludes.jsp" />
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery.dataTables.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/dataTables.fnReloadAjax.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery-ui-1.10.3.custom.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/js/timesheet.js"></script>

<script
    src="${pageContext.request.contextPath}/resources/js/hrTimesheet.js"></script>
</head>

<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <div class="row">
            <div
                class="col-5 col-sm-3 col-lg-2 navbar-collapse collapse sidebar-collapse">
                <jsp:include page="hrTimesheetSideBar.jsp" />
            </div>
            <div class="col-12 col-sm-9 col-lg-10 border">
                <div>
                    <div id="errorMessages" class="text-error">${error}</div>
                    <table id="hrTimesheetTable"
                        class="table table-bordered  table-striped table-condensed rt">
                        <thead>
                            <tr>
                                <th><input class="selectAll"
                                    type="checkbox"></th>
                                <th>TimeSheet Id</th>
                                <th>Week Ending</th>
                                <th>Employee</th>
                                <th>Status</th>
                                <th>Hours</th>
                                <th>Edit</th>
                                <th>Approve</th>
                                <th>Reject</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <div class="control-group">
                        <input type="button" id="approveBtn"
                            class="btn btn-success hrTimesheetControl" value="Approve">
                        <input type="button" id="rejectBtn"
                            class="btn btn-danger hrTimesheetControl"
                            value="Reject">
                       </div>
                    <jsp:include page="timesheetDialog.jsp" />
                </div>
            </div>
        </div>
    </div>
</body>
</html>