<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Dashboard</title>
<jsp:include page="../common/commonIncludes.jsp" />
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/jQuery-File-Upload/css/jquery.fileupload-ui.css">

</head>

<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <div class="row">
            <div
                class="col-5 col-sm-3 col-lg-2 nav-collapse collapse sidebar-collapse">
                <jsp:include page="hrTimesheetSideBar.jsp" />
            </div>
            <div class="col-12 col-sm-9 col-lg-10 well">
                <div id="errorMessages" class="text-error">${error}</div>
                <table class="table table-bordered table-condensed">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Week Ending</th>
                            <th>Employee</th>
                            <th>Status</th>
                            <th>Hours</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${hrTimesheetList}"
                            var="hrTimesheet">
                            <tr
                                data-employeeid="${hrTimesheet.employeeId}"
                                data-id="${hrTimesheet.timesheetId}">
                                <td class=checkBoxRow><input
                                    type="checkbox"
                                    id="${hrTimesheet.employeeId}"></td>
                                <td>${hrTimesheet.weekEnding}</td>
                                <td>${hrTimesheet.employeeName}</td>
                                <td>${hrTimesheet.status}</td>
                                <td class="hours">${hrTimesheet.hours}</td>
                                <td class="timesheetEdit"><a
                                    href="javascript:;"> <i
                                        class="glyphicon glyphicon-pencil"></i>
                                </a></td>
                                <td class="timesheetReject"><a
                                    href="javascript:;"><i
                                        class="glyphicon glyphicon-ok"></i>
                                </a></td>
                                <td class="timesheetReject"><a
                                    href="javascript:;"><i
                                        class="glyphicon glyphicon-remove"></i>
                                </a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <jsp:include page="timesheetDialog.jsp" />
            </div>
        </div>

    </div>
    <script
        src="${pageContext.request.contextPath}/resources/js/hrTimesheet.js"></script>

</body>
</html>