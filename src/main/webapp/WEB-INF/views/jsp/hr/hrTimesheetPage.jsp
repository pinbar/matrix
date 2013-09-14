<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Dashboard</title>
<jsp:include page="../common/commonIncludes.jsp" />
<jsp:include page="../common/attachmentIncludes.jsp" />
<link
    href="${pageContext.request.contextPath}/resources/datepicker/css/datepicker.css"
    rel="stylesheet" media="screen">
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery.dataTables.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/dataTables.fnReloadAjax.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery-ui-1.10.3.custom.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/datepicker/js/bootstrap-datepicker.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/bootstrap/js/typeahead.js"></script>
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
                                <th>Manager</th>
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
                            class="btn btn-success hrTimesheetControl"
                            value="Approve"> <input
                            type="button" id="rejectBtn"
                            class="btn btn-danger hrTimesheetControl"
                            value="Reject">
                    </div>
                    <jsp:include page="timesheetDialog.jsp" />
                </div>
                <div class="horizontalRule"></div>

                <div
                    class="col-offset-4 col-sm-offset-4 col-lg-offset-4">
                    <h4>Create New Timesheet</h4>
                </div>
                <div id="employeeBootStrapData" class="hide">${employees}</div>
                <jsp:useBean id="today" class="java.util.Date"
                    scope="page" />
                <div class="container">
                    <div class="row">
                        <div
                            class="col-sm-offset-1 col-sm-6 col-md-offset-2 col-sm-6 col-lg-offset-3 col-lg-7">
                            <div class="input-group">
                                <span id="dpStart"
                                    class="input-group-addon input-group-addon-sm dp-background"><i
                                    class="glyphicon glyphicon-calendar"></i></span>
                                <input id="dp" type="text"
                                    class="input-sm"
                                    disabled="disabled"
                                    value=<fmt:formatDate value="${today}"  pattern="MM-dd-yyyy" />
                                    data-date-format="mm-dd-yyyy">
                                <input type="text" id="employees-auto"
                                    class="input-sm"
                                    placeholder="Select an employee">
                                <button id="createBtn "
                                    class="dp-background">Create
                                    TimeSheet</button>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="horizontalRule"></div>
            </div>
        </div>
    </div>
</body>
</html>